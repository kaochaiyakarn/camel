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

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.camel.catalog.impl.CatalogHelper;

/**
 * {@link JSonSchemaResolver} used by {@link CamelCatalog} that is able to load all the resources that the complete camel-catalog JAR provides.
 */
public class CamelCatalogJSonSchemaResolver implements JSonSchemaResolver {

    private static final String MODEL_DIR = "org/apache/camel/catalog/models";

    private final CamelCatalog camelCatalog;
    private ClassLoader classLoader;

    // 3rd party components/data-formats
    private final Map<String, String> extraComponents;
    private final Map<String, String> extraComponentsJSonSchema;
    private final Map<String, String> extraDataFormats;
    private final Map<String, String> extraDataFormatsJSonSchema;

    String loadFromClasspath(final String className, final String fileName) {
        if (className != null) {
            String packageName = className.substring(0, className.lastIndexOf('.'));
            packageName = packageName.replace('.', '/');
            final String path = packageName + "/" + fileName + ".json";

            return loadResourceFromVersionManager(path);
        }

        return null;
    }

    String loadResourceFromVersionManager(final String file) {
        try (final InputStream is = camelCatalog.getVersionManager().getResourceAsStream(file)) {
            if (is != null) {
                return CatalogHelper.loadText(is);
            }
        } catch (IOException e) {
            // ignore
        }
        if (classLoader != null) {
            try (InputStream is = classLoader.getResourceAsStream(file)) {
                if (is != null) {
                    return CatalogHelper.loadText(is);
                }
            } catch (IOException e) {
                // ignore
            }
        }

        return null;
    }
}
