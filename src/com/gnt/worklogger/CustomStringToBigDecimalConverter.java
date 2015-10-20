package com.gnt.worklogger;

import com.vaadin.data.util.converter.StringToBigDecimalConverter;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Simple change, this one returns zero instead of null on no input.
 */
@SuppressWarnings("serial")
public class CustomStringToBigDecimalConverter extends StringToBigDecimalConverter {
    @Override
    public BigDecimal convertToModel(String value, Class<? extends BigDecimal> targetType, Locale locale) throws ConversionException {
        if (value == null || value.isEmpty()) {
            return new BigDecimal(0);
        } else {
            return super.convertToModel(value, targetType, locale);
        }
    }
}
