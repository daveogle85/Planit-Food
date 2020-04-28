package com.planitfood.mealdetails;

import graphql.language.StringValue;
import graphql.schema.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateScalar {
    public static final GraphQLScalarType DATE = GraphQLScalarType.newScalar()
            .name("Date")
            .description("A custom scalar that handles date")
            .coercing(new Coercing() {
                @Override
                public Object serialize(Object dataFetcherResult) {
                    return serializeDate(dataFetcherResult);
                }

                @Override
                public Object parseValue(Object input) {
                    return parseDateFromVariable(input);
                }

                @Override
                public Object parseLiteral(Object input) {
                    return parseDateFromAstLiteral(input);
                }
            }).build();

    public static boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    private static Object serializeDate(Object dataFetcherResult) {
        String possibleDateValue = String.valueOf(dataFetcherResult);
        if (isValidDate(possibleDateValue)) {
            return possibleDateValue;
        } else {
            throw new CoercingSerializeException("Unable to serialize " + possibleDateValue + " as a date");
        }
    }

    private static Object parseDateFromVariable(Object input) {
        if (input instanceof String) {
            String possibleDateValue = input.toString();
            if (isValidDate(possibleDateValue)) {
                return possibleDateValue;
            }
        }
        throw new CoercingParseValueException("Unable to parse variable value " + input + " as a date");
    }

    private static Object parseDateFromAstLiteral(Object input) {
        if (input instanceof StringValue) {
            String possibleDateValue = ((StringValue) input).getValue();
            if (isValidDate(possibleDateValue)) {
                return possibleDateValue;
            }
        }
        throw new CoercingParseLiteralException(
                "Value is not a date: '" + String.valueOf(input) + "'"
        );
    }
}