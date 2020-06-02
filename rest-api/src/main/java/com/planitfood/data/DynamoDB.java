package com.planitfood.data;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DynamoDB implements InitializingBean {

    @Value("${environment.current}")
    private String environment;
    private AmazonDynamoDB client;
    private DynamoDBMapper mapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.client = getDataBase();
        this.mapper = initMapper();
    }

    private AmazonDynamoDB getDataBase() {
        AmazonDynamoDBClientBuilder client = AmazonDynamoDBClientBuilder.standard();

        if(this.environment.equals("DEV")) {
            return client.withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1")
            ).build();
        } else {
            return client.withRegion(Regions.EU_WEST_2).build();
        }
    }

    private DynamoDBMapper initMapper() {
        AmazonDynamoDB client = getDataBase();
        return new DynamoDBMapper(client);
    }

    public DynamoDBMapper getMapper() {
        return this.mapper;
    }

}
