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
package org.apache.camel.component.extension;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.camel.Component;
import org.apache.camel.component.extension.ComponentVerifierExtensionHelper.ErrorAttribute;
import org.apache.camel.component.extension.ComponentVerifierExtensionHelper.ErrorCode;
import org.apache.camel.component.extension.ComponentVerifierExtensionHelper.ExceptionErrorAttribute;
import org.apache.camel.component.extension.ComponentVerifierExtensionHelper.GroupErrorAttribute;
import org.apache.camel.component.extension.ComponentVerifierExtensionHelper.HttpErrorAttribute;
import org.apache.camel.component.extension.ComponentVerifierExtensionHelper.StandardErrorCode;

public interface ComponentVerifierExtension extends ComponentExtension {

    Result verify(Scope scope, Map<String, Object> parameters);

    interface Result extends Serializable {


        enum Status {
        
            OK,
        
            ERROR,
            
            UNSUPPORTED
        }

        Scope getScope();

        Status getStatus();

        List<VerificationError> getErrors();
    }

    enum Scope {

        PARAMETERS,
        CONNECTIVITY;

        public static Scope fromString(String scope) {
            return Scope.valueOf(scope != null ? scope.toUpperCase() : null);
        }
    }

    interface VerificationError extends Serializable {

        Code getCode();
        String getDescription();
        Set<String> getParameterKeys();

        Map<Attribute, Object> getDetails();
        default Object getDetail(Attribute attribute) {
            Map<Attribute, Object> details = getDetails();
            if (details != null) {
                return details.get(attribute);
            }
            return null;
        }

        default Object getDetail(String attribute) {
            return getDetail(asAttribute(attribute));
        }


        static Code asCode(String code) {
            return new ErrorCode(code);
        }

        static Attribute asAttribute(String attribute) {
            return new ErrorAttribute(attribute);
        }

        interface Code extends Serializable {
        
            String name();
            default String getName() {
                return name();
            }
        }

        interface StandardCode extends Code {
        
            StandardCode AUTHENTICATION = new StandardErrorCode("AUTHENTICATION");
            StandardCode EXCEPTION = new StandardErrorCode("EXCEPTION");
            StandardCode INTERNAL = new StandardErrorCode("INTERNAL");
            StandardCode MISSING_PARAMETER = new StandardErrorCode("MISSING_PARAMETER");
            StandardCode UNKNOWN_PARAMETER = new StandardErrorCode("UNKNOWN_PARAMETER");
            StandardCode ILLEGAL_PARAMETER = new StandardErrorCode("ILLEGAL_PARAMETER");
            StandardCode ILLEGAL_PARAMETER_GROUP_COMBINATION = new StandardErrorCode("ILLEGAL_PARAMETER_GROUP_COMBINATION");
            StandardCode ILLEGAL_PARAMETER_VALUE = new StandardErrorCode("ILLEGAL_PARAMETER_VALUE");
            StandardCode INCOMPLETE_PARAMETER_GROUP = new StandardErrorCode("INCOMPLETE_PARAMETER_GROUP");
            StandardCode UNSUPPORTED = new StandardErrorCode("UNSUPPORTED");
            StandardCode UNSUPPORTED_SCOPE = new StandardErrorCode("UNSUPPORTED_SCOPE"); requested {@link Component} is not supported
             */
            StandardCode UNSUPPORTED_COMPONENT = new StandardErrorCode("UNSUPPORTED_COMPONENT");
            StandardCode GENERIC = new StandardErrorCode("GENERIC");
        }

        
        interface Attribute extends Serializable {
            String name();

            default String getName() {
                return name();
            }
        }
        interface ExceptionAttribute extends Attribute {
            ExceptionAttribute EXCEPTION_INSTANCE = new ExceptionErrorAttribute("EXCEPTION_INSTANCE");
            ExceptionAttribute EXCEPTION_CLASS = new ExceptionErrorAttribute("EXCEPTION_CLASS");
        }
        interface HttpAttribute extends Attribute {
            HttpAttribute HTTP_CODE = new HttpErrorAttribute("HTTP_CODE");
            HttpAttribute HTTP_TEXT = new HttpErrorAttribute("HTTP_TEXT");
            HttpAttribute HTTP_REDIRECT = new HttpErrorAttribute("HTTP_REDIRECT");
        }
        interface GroupAttribute extends Attribute {
            GroupAttribute GROUP_NAME = new GroupErrorAttribute("GROUP_NAME");
            GroupAttribute GROUP_OPTIONS = new GroupErrorAttribute("GROUP_OPTIONS");
        }
    }
}
