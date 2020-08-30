/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.catalog;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import org.apache.camel.catalog.impl.AbstractCamelCatalog;
import org.apache.camel.catalog.impl.CatalogHelper;
import org.apache.camel.tooling.model.BaseModel;
import org.apache.camel.tooling.model.ComponentModel;
import org.apache.camel.tooling.model.DataFormatModel;
import org.apache.camel.tooling.model.EipModel;
import org.apache.camel.tooling.model.JsonMapper;
import org.apache.camel.tooling.model.LanguageModel;
import org.apache.camel.tooling.model.MainModel;
import org.apache.camel.tooling.model.OtherModel;
import org.apache.camel.util.json.JsonObject;

/**
 * Default {@link CamelCatalog}.
 */
public class DefaultCamelCatalog extends AbstractCamelCatalog implements CamelCatalog {

    private static final String MODELS_CATALOG = "org/apache/camel/catalog/models.properties";
    private static final String MODEL_DIR = "org/apache/camel/catalog/models";
    private static final String DOC_DIR = "org/apache/camel/catalog/docs";
    private static final String ARCHETYPES_CATALOG = "org/apache/camel/catalog/archetypes/archetype-catalog.xml";
    private static final String SCHEMAS_XML = "org/apache/camel/catalog/schemas";
    private static final String MAIN_DIR = "org/apache/camel/catalog/main";

    private final VersionHelper version = new VersionHelper();

    // 3rd party components/data-formats
    private final Map<String, String> extraComponents = new HashMap<>();
    private final Map<String, String> extraComponentsJSonSchema = new HashMap<>();
    private final Map<String, String> extraDataFormats = new HashMap<>();
    private final Map<String, String> extraDataFormatsJSonSchema = new HashMap<>();

    // cache of operation -> result
    private final Map<String, Object> cache = new HashMap<>();

    private boolean caching;
    private VersionManager versionManager = new DefaultVersionManager(this);
    private RuntimeProvider runtimeProvider = new DefaultRuntimeProvider(this);

    /**
     * Creates the {@link CamelCatalog} without caching enabled.
     */
    public DefaultCamelCatalog() {
        this(false);
    }

    /**
     * Creates the {@link CamelCatalog}
     *
     * @param caching  whether to use cache
     */
    public DefaultCamelCatalog(boolean caching) {
        this.caching = caching;
        setJSonSchemaResolver(new CamelCatalogJSonSchemaResolver(this, extraComponents, extraComponentsJSonSchema, extraDataFormats, extraDataFormatsJSonSchema));
    }

    private List<String> findNames(String filter, Supplier<List<String>> findNames, Function<String, ? extends BaseModel<?>> modelLoader) {
        List<String> answer = new ArrayList<>();
        List<String> names = findNames.get();
        for (String name : names) {
            BaseModel<?> model = modelLoader.apply(name);
            if (model != null) {
                String label = model.getLabel();
                String[] parts = label.split(",");
                for (String part : parts) {
                    try {
                        if (part.equalsIgnoreCase(filter) || CatalogHelper.matchWildcard(part, filter) || part.matches(filter)) {
                            answer.add(name);
                        }
                    } catch (PatternSyntaxException e) {
                        // ignore as filter is maybe not a pattern
                    }
                }
            }
        }
        return answer;
    }

    private String getAlternativeComponentName(String componentName, String alternativeTo) {
        // optimize for this very call to avoid loading all schemas
        String json = componentJSonSchema(componentName);
        if (json.contains("alternativeSchemes") && json.contains(alternativeTo)) {
            ComponentModel model = componentModel(componentName);
            if (model != null) {
                return model.getAlternativeSchemes();
            }
        }
        return null;
    }

    private String doComponentAsciiDoc(String componentName) {
        // special for mail component
        String name;
        if (componentName.equals("imap") || componentName.equals("imaps") || componentName.equals("pop3") || componentName.equals("pop3s") || componentName.equals("smtp") || componentName.equals("smtps")) {
            name = "mail";
        } else {
            name = componentName;
        }
        String file = DOC_DIR + "/" + name + "-component.adoc";
        return cache(file, () -> {
            if (findComponentNames().contains(componentName)) {
                return loadResource(file);
            } else if (extraComponents.containsKey(name)) {
                String className = extraComponents.get(name);
                String packageName = className.substring(0, className.lastIndexOf('.'));
                packageName = packageName.replace('.', '/');
                String path = packageName + "/" + name + "-component.adoc";
                return loadResource(path);
            } else {
                return null;
            }
        });
    }

    private String doComponentHtmlDoc(String componentName) {
        // special for mail component
        String name;
        if (componentName.equals("imap") || componentName.equals("imaps") || componentName.equals("pop3") || componentName.equals("pop3s") || componentName.equals("smtp") || componentName.equals("smtps")) {
            name = "mail";
        } else {
            name = componentName;
        }
        String file = DOC_DIR + "/" + name + "-component.html";
        return cache(file, () -> {
            if (findComponentNames().contains(name)) {
                return loadResource(file);
            } else if (extraComponents.containsKey(name)) {
                String className = extraComponents.get(name);
                String packageName = className.substring(0, className.lastIndexOf('.'));
                packageName = packageName.replace('.', '/');
                String path = packageName + "/" + name + "-component.html";
                return loadResource(path);
            } else {
                return null;
            }
        });
    }

    private SortedSet<String> findLabels(Supplier<List<String>> findNames, Function<String, ? extends BaseModel<?>> loadModel) {
        TreeSet<String> answer = new TreeSet<>();
        List<String> names = findNames.get();
        for (String name : names) {
            BaseModel<?> model = loadModel.apply(name);
            if (model != null) {
                String label = model.getLabel();
                String[] parts = label.split(",");
                Collections.addAll(answer, parts);
            }
        }
        return answer;
    }

    private int getArchetypesCount() {
        int archetypes = 0;
        try {
            String xml = archetypeCatalogAsXml();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", Boolean.TRUE);
            Document dom = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
            Object val = XPathFactory.newInstance().newXPath().evaluate("count(/archetype-catalog/archetypes/archetype)", dom, XPathConstants.NUMBER);
            double num = (double) val;
            archetypes = (int) num;
        } catch (Exception e) {
            // ignore
        }
        return archetypes;
    }

    @SuppressWarnings("unchecked")
    private <T> T cache(String name, Supplier<T> loader) {
        if (caching) {
            T t = (T) cache.get(name);
            if (t == null) {
                t = loader.get();
                if (t != null) {
                    cache.put(name, t);
                }
            }
            return t;
        } else {
            return loader.get();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T cache(String key, String name, Function<String, T> loader) {
        if (caching) {
            T t = (T) cache.get(key);
            if (t == null) {
                t = loader.apply(name);
                if (t != null) {
                    cache.put(key, t);
                }
            }
            return t;
        } else {
            return loader.apply(name);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T cache(String name, Function<String, T> loader) {
        if (caching) {
            T t = (T) cache.get(name);
            if (t == null) {
                t = loader.apply(name);
                if (t != null) {
                    cache.put(name, t);
                }
            }
            return t;
        } else {
            return loader.apply(name);
        }
    }

    private String loadResource(String file) {
        try (InputStream is = versionManager.getResourceAsStream(file)) {
            return is != null ? CatalogHelper.loadText(is) : null;
        } catch (IOException e) {
            return null;
        }
    }

    // CHECKSTYLE:ON

}
