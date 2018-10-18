package com.udpdemo.func;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.annotation.*;

import java.util.logging.Logger;

/**
 * Azure Functions with EventHub Trigger.
 */
public class HttpTriggerToCosmosDBFunction {
    Logger logger;

    @FunctionName("storetocosmosdb")
    @CosmosDBOutput(name = "udpcosmos", databaseName = "parkingDB", collectionName = "Items", connectionStringSetting = "AzureCosmosDBConnection")
    public String helloStoreToCosmosDB(@HttpTrigger(name = "storetocosmosdbTrigger", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS)  String message,
                                       ExecutionContext context) {

        logger = context.getLogger();
        String jsonString = message;

        ParkingCar parkingCar = getJsonToObject(jsonString);
        parkingCar.setId(System.currentTimeMillis()+"");

        logger.info("Event Hub trigger function processed a message: " + jsonString);

        return getObjectToJson(parkingCar);
    }

    //BIZ LOGIC..........

    private ParkingCar getJsonToObject(String jsonString) {
        ParkingCar parkingCar = new ParkingCar();

        ObjectMapper mapper = new ObjectMapper();
        try {
            parkingCar = mapper.readValue(jsonString.replaceAll("'","\""), ParkingCar.class);
            logger.info("Parse Parkingcar Object toString: "+ parkingCar);

        } catch (Exception e) {
            logger.info("Parse Parkingcar Object toString error : "+ e.getMessage());
        }

        return parkingCar;
    }

    private String getObjectToJson(ParkingCar parkingCar) {
        String parkingCarJson = "";

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {

            parkingCarJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(parkingCar);

            logger.info("<<<<<<<<<< Parse Parkingcar to Json parkingCarJson String: "+ parkingCarJson);
        } catch (Exception e) {
            logger.info("Parse Parkingcar Object toString error : "+ e.getMessage());
        }

        return parkingCarJson;
    }

    //.......... END BIZ LOGIC
}
