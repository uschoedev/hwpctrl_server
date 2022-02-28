package com.hmlserver.filter.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

    private static Logger logger = LoggerFactory.getLogger(XSSRequestWrapper.class);

    public XSSRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    public String[] getParameterValues(String parameter) {

        String[] values = super.getParameterValues(parameter);

        int valueCount = values.length;
        String[] filteringValues = new String[valueCount];

        if(valueCount > 0) {

            for(int i=0; i < valueCount; i++){
                filteringValues[i] = values[i];
            }

        }

        return filteringValues;

    }

    @Override
    public String getParameter(String parameter) {

        String value = super.getParameter(parameter);

        String filteringValue = "";

        /** isEmpty 적용시 에러 발생... invoke 문제 */
        if(value != null) {
            filteringValue = xssFiltering(value);
        }

        return filteringValue;

    }

    @Override
    public String getHeader(String key) {

        String property = super.getHeader(key);

        String filteringProperty = "";

        /** isEmpty 적용시 에러 발생... invoke 문제 */
        if(property != null) {
            filteringProperty = xssFiltering(property);
        }

        return filteringProperty;

    }

    private String xssFiltering(String value) {

        logger.info("xssFiltering ====>>> ");
        logger.info(value);

        String filteringValue = value.replaceAll("&", "")
                                    .replaceAll("<", "")
                                    .replaceAll(">", "")
                                    .replaceAll("\"", "")
                                    .replaceAll("\'", "")
                                    .replaceAll("%", "")
                                    .replaceAll("%2F", "");

        return filteringValue;

    }

}
