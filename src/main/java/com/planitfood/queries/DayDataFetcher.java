package com.planitfood.queries;

import com.planitfood.data.StaticData;
import com.planitfood.models.Day;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.time.LocalDate;
import java.util.List;

public class DayDataFetcher implements IDataFetcher {
    public String getArgument(DataFetchingEnvironment dataFetchingEnvironment) {
        return dataFetchingEnvironment.getArgument("startDate");
    }

    public List<Day> getAllValues() {
        return null;
    }

    public Day findValueFromArg(String arg) {
        return null;
    }

    public DataFetcher getDaysForDateRange() {
        return dataFetchingEnvironment -> {
            String startDateString = getArgument(dataFetchingEnvironment);

            if (startDateString == null) {
                return null;
            }

            try {
                LocalDate startDate = LocalDate.parse(getArgument(dataFetchingEnvironment));
                String endDateString = dataFetchingEnvironment.getArgument("endDate");
                LocalDate endDate = null;

                if (endDateString != null) {
                    endDate = LocalDate.parse(endDateString);
                }

                return StaticData.getDays(startDate, endDate);
            } catch (Exception e) {
                return null;
            }
        };
    }
}
