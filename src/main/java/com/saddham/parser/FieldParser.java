package com.saddham.parser;

import com.saddham.model.field.expression.And;
import com.saddham.model.field.expression.Between;
import com.saddham.model.field.expression.Every;
import com.saddham.model.field.expression.FieldExpression;
import com.saddham.model.field.expression.On;
import com.saddham.model.field.value.IntegerFieldValue;
import org.apache.commons.lang3.StringUtils;

import static com.saddham.model.field.expression.FieldExpression.always;

/**
 * Parses a field from a cron expression.
 */
public class FieldParser {
    private static final String SLASH = "/";
    private static final String EMPTY_STRING = "";
    private static final String ASTERISK = "*";
    private static final String ASTERISK_ALWAYS_VALUE = "1";
    private static final char[] SPECIAL_CHARS_MINUS_STAR = new char[]{'/', '-', ','};

    public FieldParser() {
    }

    /**
     * Parse given expression for a single cron field.
     *
     * @param expression - String
     * @return CronFieldExpression object that with interpretation of given String parameter
     */
    public FieldExpression parse(final String expression) {
        if (!StringUtils.containsAny(expression, SPECIAL_CHARS_MINUS_STAR)) {
            return noSpecialCharsNorStar(expression);
        } else {
            final String[] array = expression.split(",");
            if (array.length > 1) {
                return commaSplitResult(array);
            } else {
                final String[] splitted = expression.split("-");
                if (expression.contains("-") && splitted.length != 2) {
                    throw new IllegalArgumentException("Missing values for range: " + expression);
                }
                return dashSplitResult(expression, splitted);
            }
        }
    }

    private FieldExpression noSpecialCharsNorStar(final String expression) {
        if (ASTERISK.equals(expression)) { // all crons support asterisk
            return always();
        } else {
            return parseOn(expression);
        }
    }

    private FieldExpression dashSplitResult(final String expression, final String[] betweenArray) {
        if (betweenArray.length > 1) {
            return parseBetween(betweenArray);
        } else {
            return slashSplit(expression, expression.split(SLASH));
        }
    }

    private FieldExpression commaSplitResult(final String[] array) {
        final And and = new And();
        for (final String exp : array) {
            and.and(parse(exp));
        }
        return and;
    }

    private FieldExpression slashSplit(final String expression, final String[] values) {
        if (values.length == 2) {
            final String start = values[0];
            final String value = values[1];
            return asteriskOrEmpty(start, value);
        } else if (values.length == 1) {
            throw new IllegalArgumentException("Missing steps for expression: " + expression);
        } else {
            throw new IllegalArgumentException("Invalid expression: " + expression);
        }
    }

    private FieldExpression asteriskOrEmpty(final String start, final String value) {
        final String trimmedStart = start.trim();
        if (ASTERISK.equals(trimmedStart) && value.equals(ASTERISK_ALWAYS_VALUE)) {
            return always();
        }
        if (ASTERISK.equals(trimmedStart) || EMPTY_STRING.equals(trimmedStart)) {
            return new Every(new IntegerFieldValue(Integer.parseInt(value)));
        } else {
            return new Every(new On(mapToIntegerFieldValue(start)), new IntegerFieldValue(Integer.parseInt(value)));
        }
    }

    protected FieldExpression parseBetween(final String[] array) {
        if (array[0].isEmpty() || array[1].isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Invalid expression! Expression: %s-%s does not describe a range. Negative numbers are not allowed.", array[0], array[1]));
        }
        if (array[1].contains(SLASH)) {
            final String[] every = array[1].split(SLASH);
            return new Every(new Between(mapToIntegerFieldValue(array[0]), mapToIntegerFieldValue(every[0])), mapToIntegerFieldValue(every[1]));
        } else {
            return new Between(mapToIntegerFieldValue(array[0]), mapToIntegerFieldValue(array[1]));
        }
    }

    protected On parseOn(final String exp) {
        return new On(mapToIntegerFieldValue(exp));
    }

    protected IntegerFieldValue mapToIntegerFieldValue(final String value) {
        try {
            return new IntegerFieldValue(Integer.parseInt(value));
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Invalid value. Expected some integer, found %s", value));
        }
    }
}
