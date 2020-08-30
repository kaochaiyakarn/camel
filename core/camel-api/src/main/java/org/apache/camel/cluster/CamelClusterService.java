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
package org.apache.camel.cluster;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.apache.camel.CamelContextAware;
import org.apache.camel.Ordered;
import org.apache.camel.Service;
import org.apache.camel.spi.IdAware;

public interface CamelClusterService extends Service, CamelContextAware, IdAware, Ordered {

    CamelClusterView getView(String namespace) throws Exception;

    void releaseView(CamelClusterView view) throws Exception;

    Collection<String> getNamespaces();

    void startView(String namespace) throws Exception;

    void stopView(String namespace) throws Exception;

    boolean isLeader(String namespace);

    default Map<String, Object> getAttributes() {
        return Collections.emptyMap();
    }

    default <T extends CamelClusterService> T unwrap(Class<T> clazz) {
        if (CamelClusterService.class.isAssignableFrom(clazz)) {
            return clazz.cast(this);
        }

        throw new IllegalArgumentException(
            "Unable to unwrap this CamelClusterService type (" + getClass() + ") to the required type (" + clazz + ")"
        );
    }

    @FunctionalInterface
    interface Selector {
        /**
         * Select a specific CamelClusterService instance among a collection.
         */
        Optional<CamelClusterService> select(Collection<CamelClusterService> services);
    }
}
