/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.aws.mq;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.GeneratedPropertyConfigurer;
import org.apache.camel.spi.PropertyConfigurerGetter;
import org.apache.camel.util.CaseInsensitiveMap;
import org.apache.camel.support.component.PropertyConfigurerSupport;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@SuppressWarnings("unchecked")
public class MQComponentConfigurer extends PropertyConfigurerSupport implements GeneratedPropertyConfigurer, PropertyConfigurerGetter {

    private org.apache.camel.component.aws.mq.MQConfiguration getOrCreateConfiguration(MQComponent target) {
        if (target.getConfiguration() == null) {
            target.setConfiguration(new org.apache.camel.component.aws.mq.MQConfiguration());
        }
        return target.getConfiguration();
    }

    @Override
    public boolean configure(CamelContext camelContext, Object obj, String name, Object value, boolean ignoreCase) {
        MQComponent target = (MQComponent) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "accesskey":
        case "accessKey": getOrCreateConfiguration(target).setAccessKey(property(camelContext, java.lang.String.class, value)); return true;
        case "amazonmqclient":
        case "amazonMqClient": getOrCreateConfiguration(target).setAmazonMqClient(property(camelContext, com.amazonaws.services.mq.AmazonMQ.class, value)); return true;
        case "autodiscoverclient":
        case "autoDiscoverClient": getOrCreateConfiguration(target).setAutoDiscoverClient(property(camelContext, boolean.class, value)); return true;
        case "basicpropertybinding":
        case "basicPropertyBinding": target.setBasicPropertyBinding(property(camelContext, boolean.class, value)); return true;
        case "configuration": target.setConfiguration(property(camelContext, org.apache.camel.component.aws.mq.MQConfiguration.class, value)); return true;
        case "lazystartproducer":
        case "lazyStartProducer": target.setLazyStartProducer(property(camelContext, boolean.class, value)); return true;
        case "operation": getOrCreateConfiguration(target).setOperation(property(camelContext, org.apache.camel.component.aws.mq.MQOperations.class, value)); return true;
        case "proxyhost":
        case "proxyHost": getOrCreateConfiguration(target).setProxyHost(property(camelContext, java.lang.String.class, value)); return true;
        case "proxyport":
        case "proxyPort": getOrCreateConfiguration(target).setProxyPort(property(camelContext, java.lang.Integer.class, value)); return true;
        case "proxyprotocol":
        case "proxyProtocol": getOrCreateConfiguration(target).setProxyProtocol(property(camelContext, com.amazonaws.Protocol.class, value)); return true;
        case "region": getOrCreateConfiguration(target).setRegion(property(camelContext, java.lang.String.class, value)); return true;
        case "secretkey":
        case "secretKey": getOrCreateConfiguration(target).setSecretKey(property(camelContext, java.lang.String.class, value)); return true;
        default: return false;
        }
    }

    @Override
    public Map<String, Object> getAllOptions(Object target) {
        Map<String, Object> answer = new CaseInsensitiveMap();
        answer.put("accessKey", java.lang.String.class);
        answer.put("amazonMqClient", com.amazonaws.services.mq.AmazonMQ.class);
        answer.put("autoDiscoverClient", boolean.class);
        answer.put("basicPropertyBinding", boolean.class);
        answer.put("configuration", org.apache.camel.component.aws.mq.MQConfiguration.class);
        answer.put("lazyStartProducer", boolean.class);
        answer.put("operation", org.apache.camel.component.aws.mq.MQOperations.class);
        answer.put("proxyHost", java.lang.String.class);
        answer.put("proxyPort", java.lang.Integer.class);
        answer.put("proxyProtocol", com.amazonaws.Protocol.class);
        answer.put("region", java.lang.String.class);
        answer.put("secretKey", java.lang.String.class);
        return answer;
    }

    @Override
    public Object getOptionValue(Object obj, String name, boolean ignoreCase) {
        MQComponent target = (MQComponent) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "accesskey":
        case "accessKey": return getOrCreateConfiguration(target).getAccessKey();
        case "amazonmqclient":
        case "amazonMqClient": return getOrCreateConfiguration(target).getAmazonMqClient();
        case "autodiscoverclient":
        case "autoDiscoverClient": return getOrCreateConfiguration(target).isAutoDiscoverClient();
        case "basicpropertybinding":
        case "basicPropertyBinding": return target.isBasicPropertyBinding();
        case "configuration": return target.getConfiguration();
        case "lazystartproducer":
        case "lazyStartProducer": return target.isLazyStartProducer();
        case "operation": return getOrCreateConfiguration(target).getOperation();
        case "proxyhost":
        case "proxyHost": return getOrCreateConfiguration(target).getProxyHost();
        case "proxyport":
        case "proxyPort": return getOrCreateConfiguration(target).getProxyPort();
        case "proxyprotocol":
        case "proxyProtocol": return getOrCreateConfiguration(target).getProxyProtocol();
        case "region": return getOrCreateConfiguration(target).getRegion();
        case "secretkey":
        case "secretKey": return getOrCreateConfiguration(target).getSecretKey();
        default: return null;
        }
    }
}

