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
import java.util.Map;

/**
 * Details result of validating configuration properties (eg application.properties for camel-main).
 */
public class ConfigurationPropertiesValidationResult extends PropertiesValidationResult implements Serializable {

    private String fileName;
    private String text;
    private int lineNumber;
    private boolean accepted;

    public ConfigurationPropertiesValidationResult() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
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
            sb.append("Configuration properties error\n");
            sb.append("---------------------------------------------------------------------------------------------------------------------------------------\n");
            sb.append("\n");
        }
        if (text != null) {
            sb.append("\t").append(text).append("\n");
        } else {
            sb.append("\n");
        }
        for (Map.Entry<String, String> option : options.entrySet()) {
            String out = String.format(format, shortKey(option.getKey()), option.getValue());
            sb.append("\n\t").append(out);
        }

        return sb.toString();
    }

    private static String shortKey(String key) {
        if (key.indexOf('.') > 0) {
            return key.substring(key.lastIndexOf('.') + 1);
        } else {
            return key;
        }
    }

}
