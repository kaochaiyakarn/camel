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

import java.net.URISyntaxException;
import java.util.Map;

import org.apache.camel.CamelContextAware;
import org.apache.camel.StaticService;
import org.apache.camel.component.extension.ComponentVerifierExtension;
import org.apache.camel.spi.SendDynamicAware;

/**
 * Runtime catalog which limited API needed by components that supports
 * {@link ComponentVerifierExtension} or {@link SendDynamicAware}.
 */
public interface RuntimeCamelCatalog extends StaticService, CamelContextAware {

    String FACTORY = "runtime-camelcatalog";

    String componentJSonSchema(String name);

    Map<String, String> endpointProperties(String uri) throws URISyntaxException;

    Map<String, String> endpointLenientProperties(String uri) throws URISyntaxException;

    EndpointValidationResult validateProperties(String scheme, Map<String, String> properties);

    String asEndpointUri(String scheme, Map<String, String> properties, boolean encode) throws URISyntaxException;

}
