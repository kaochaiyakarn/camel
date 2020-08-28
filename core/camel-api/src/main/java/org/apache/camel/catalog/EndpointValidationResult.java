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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Details result of validating endpoint uri.
 */
public class EndpointValidationResult extends PropertiesValidationResult implements Serializable {

    private final String uri;

    private Set<String> lenient;
    private Set<String> notConsumerOnly;
    private Set<String> notProducerOnly;

    public EndpointValidationResult() {
        this(null);
    }

    public EndpointValidationResult(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public boolean isSuccess() {
        boolean ok = super.isSuccess();
        if (ok) {
            ok = notConsumerOnly == null && notProducerOnly == null;
        }
        return ok;
    }

    public void addLenient(String name) {
        if (lenient == null) {
            lenient = new LinkedHashSet<>();
        }
        if (!lenient.contains(name)) {
            lenient.add(name);
        }
    }

    public void addNotConsumerOnly(String name) {
        if (notConsumerOnly == null) {
            notConsumerOnly = new LinkedHashSet<>();
        }
        if (!notConsumerOnly.contains(name)) {
            notConsumerOnly.add(name);
            errors++;
        }
    }

    public void addNotProducerOnly(String name) {
        if (notProducerOnly == null) {
            notProducerOnly = new LinkedHashSet<>();
        }
        if (!notProducerOnly.contains(name)) {
            notProducerOnly.add(name);
            errors++;
        }
    }

    public Set<String> getNotConsumerOnly() {
        return notConsumerOnly;
    }

    public Set<String> getNotProducerOnly() {
        return notProducerOnly;
    }

    public Set<String> getLenient() {
        return lenient;
    }

    /**
     * A human readable summary of the validation errors.
     *
     * @param includeHeader    whether to include a header
     * @return the summary, or <tt>null</tt> if no validation errors
     */
    public String summaryErrorMessage(boolean includeHeader) {
        return summaryErrorMessage(includeHeader, true, false);
    }

    /**
     * A human readable summary of the validation errors.
     *
     * @param includeHeader    whether to include a header
     * @param ignoreDeprecated whether to ignore deprecated options in use as an error or not
     * @param includeWarnings  whether to include warnings as an error or not
     * @return the summary, or <tt>null</tt> if no validation errors
     */
    public String summaryErrorMessage(boolean includeHeader, boolean ignoreDeprecated, boolean includeWarnings) {
        boolean ok = isSuccess();

        // build a table with the error summary nicely formatted
        // lets use 24 as min length
        int maxLen = 24;
        for (String key : options.keySet()) {
            maxLen = Math.max(maxLen, key.length());
        }
        String format = "%" + maxLen + "s    %s";

        // build the human error summary
        StringBuilder sb = new StringBuilder();
        if (includeHeader) {
            sb.append("Endpoint validator error\n");
            sb.append("---------------------------------------------------------------------------------------------------------------------------------------\n");
            sb.append("\n");
        }
        if (uri != null) {
            sb.append("\t").append(uri).append("\n");
        } else {
            sb.append("\n");
        }
        for (Map.Entry<String, String> option : options.entrySet()) {
            String out = String.format(format, option.getKey(), option.getValue());
            sb.append("\n\t").append(out);
        }

        return sb.toString();
    }

}
