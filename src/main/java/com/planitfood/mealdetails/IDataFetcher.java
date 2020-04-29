package com.planitfood.mealdetails;

import graphql.schema.DataFetchingEnvironment;

import java.util.List;

public interface IDataFetcher {
    public String getArgument(DataFetchingEnvironment dataFetchingEnvironment);

    public <T> List<T> getAllValues();

    public <T> T findValueFromArg(String arg);
}
