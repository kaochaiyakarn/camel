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
package org.apache.camel.health;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.camel.CamelContext;
import org.apache.camel.util.ObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper for invoking {@link HealthCheck}'s.
 *
 * The helper will lookup the {@link HealthCheckRegistry} from {@link CamelContext} and gather
 * all the registered {@link HealthCheck}s and invoke them and gather their responses.
 *
 * The helper allows to filter out unwanted health checks using {@link HealthCheckFilter} or
 * to invoke only readiness or liveness checks.
 */
public final class HealthCheckHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckHelper.class);

    private HealthCheckHelper() {
    }

    /**
     * Get the group of the given check or an empty string if the group is not set.
     *
     * @param check the health check
     * @return the {@link HealthCheck#getGroup()} or an empty string if it is <code>null</code>
     */
    public static String getGroup(HealthCheck check) {
        return ObjectHelper.supplyIfEmpty(check.getGroup(), () -> "");
    }

    /**
     * Invokes the checks and returns a collection of results.
     */
    public static Collection<HealthCheck.Result> invoke(CamelContext camelContext) {
        return invoke(camelContext, check -> Collections.emptyMap(), check -> false);
    }

    /**
     * Invokes the readiness checks and returns a collection of results.
     */
    public static Collection<HealthCheck.Result> invokeReadiness(CamelContext camelContext) {
        return invoke(camelContext, check -> Collections.emptyMap(), check -> !check.isReadiness());
    }

    /**
     * Invokes the liveness checks and returns a collection of results.
     */
    public static Collection<HealthCheck.Result> invokeLiveness(CamelContext camelContext) {
        return invoke(camelContext, check -> Collections.emptyMap(), check -> !check.isLiveness());
    }

    /**
     * Invokes the checks and returns a collection of results.
     */
    public static Collection<HealthCheck.Result> invoke(
            CamelContext camelContext,
            Function<HealthCheck, Map<String, Object>> optionsSupplier) {

        return invoke(camelContext, optionsSupplier, check -> false);
    }

    /**
     * Invokes the checks and returns a collection of results.
     */
    public static Collection<HealthCheck.Result> invoke(
            CamelContext camelContext,
            HealthCheckFilter filter) {

        return invoke(camelContext, check -> Collections.emptyMap(), filter);
    }

    /**
     * Invokes the checks and returns a collection of results.
     *
     * @param camelContext the camel context.
     * @param optionsSupplier a supplier for options.
     * @param filter filter to exclude some checks.
     */

}
