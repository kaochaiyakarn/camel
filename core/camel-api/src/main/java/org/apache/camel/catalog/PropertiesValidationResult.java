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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

abstract class PropertiesValidationResult implements Serializable {

    int errors;
    int warnings;

    // general error
    String syntaxError;
    // general warnings
    String unknownComponent;
    String incapable;

    // options
    Set<String> unknown;
    Map<String, String[]> unknownSuggestions;
    Set<String> required;
    Set<String> deprecated;
    Map<String, String> invalidEnum;
    Map<String, String[]> invalidEnumChoices;
    Map<String, String[]> invalidEnumSuggestions;
    Map<String, String> invalidMap;
    Map<String, String> invalidArray;
    Map<String, String> invalidReference;
    Map<String, String> invalidBoolean;
    Map<String, String> invalidInteger;
    Map<String, String> invalidNumber;
    Map<String, String> invalidDuration;
    Map<String, String> defaultValues;

    

}
