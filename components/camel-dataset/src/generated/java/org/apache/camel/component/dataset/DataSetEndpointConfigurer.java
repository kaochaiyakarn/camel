/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.dataset;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.GeneratedPropertyConfigurer;
import org.apache.camel.support.component.PropertyConfigurerSupport;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@SuppressWarnings("unchecked")
public class DataSetEndpointConfigurer extends PropertyConfigurerSupport implements GeneratedPropertyConfigurer {

    @Override
    public boolean configure(CamelContext camelContext, Object obj, String name, Object value, boolean ignoreCase) {
        DataSetEndpoint target = (DataSetEndpoint) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "datasetindex":
        case "dataSetIndex": target.setDataSetIndex(property(camelContext, java.lang.String.class, value)); return true;
        case "bridgeerrorhandler":
        case "bridgeErrorHandler": target.setBridgeErrorHandler(property(camelContext, boolean.class, value)); return true;
        case "initialdelay":
        case "initialDelay": target.setInitialDelay(property(camelContext, long.class, value)); return true;
        case "minrate":
        case "minRate": target.setMinRate(property(camelContext, int.class, value)); return true;
        case "preloadsize":
        case "preloadSize": target.setPreloadSize(property(camelContext, long.class, value)); return true;
        case "producedelay":
        case "produceDelay": target.setProduceDelay(property(camelContext, long.class, value)); return true;
        case "exceptionhandler":
        case "exceptionHandler": target.setExceptionHandler(property(camelContext, org.apache.camel.spi.ExceptionHandler.class, value)); return true;
        case "exchangepattern":
        case "exchangePattern": target.setExchangePattern(property(camelContext, org.apache.camel.ExchangePattern.class, value)); return true;
        case "assertperiod":
        case "assertPeriod": target.setAssertPeriod(property(camelContext, long.class, value)); return true;
        case "consumedelay":
        case "consumeDelay": target.setConsumeDelay(property(camelContext, long.class, value)); return true;
        case "expectedcount":
        case "expectedCount": target.setExpectedCount(property(camelContext, int.class, value)); return true;
        case "failfast":
        case "failFast": target.setFailFast(property(camelContext, boolean.class, value)); return true;
        case "lazystartproducer":
        case "lazyStartProducer": target.setLazyStartProducer(property(camelContext, boolean.class, value)); return true;
        case "reportgroup":
        case "reportGroup": target.setReportGroup(property(camelContext, int.class, value)); return true;
        case "resultminimumwaittime":
        case "resultMinimumWaitTime": target.setResultMinimumWaitTime(property(camelContext, long.class, value)); return true;
        case "resultwaittime":
        case "resultWaitTime": target.setResultWaitTime(property(camelContext, long.class, value)); return true;
        case "retainfirst":
        case "retainFirst": target.setRetainFirst(property(camelContext, int.class, value)); return true;
        case "retainlast":
        case "retainLast": target.setRetainLast(property(camelContext, int.class, value)); return true;
        case "sleepforemptytest":
        case "sleepForEmptyTest": target.setSleepForEmptyTest(property(camelContext, long.class, value)); return true;
        case "copyonexchange":
        case "copyOnExchange": target.setCopyOnExchange(property(camelContext, boolean.class, value)); return true;
        case "basicpropertybinding":
        case "basicPropertyBinding": target.setBasicPropertyBinding(property(camelContext, boolean.class, value)); return true;
        case "synchronous": target.setSynchronous(property(camelContext, boolean.class, value)); return true;
        default: return false;
        }
    }

}
