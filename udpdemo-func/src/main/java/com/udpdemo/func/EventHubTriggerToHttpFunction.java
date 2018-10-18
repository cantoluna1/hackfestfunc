package com.udpdemo.func;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.EventHubTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

/**
 * Azure Functions with EventHub Trigger.
 */
public class EventHubTriggerToHttpFunction {

    Logger logger;
    String baseUrl = "https://udpfunc.azurewebsites.net/api";

    @FunctionName("subeventhub")
    public String helloSubEventhub(@EventHubTrigger(name = "subeventhubTrigger",
            eventHubName = "udpeventfn01",
            connection = "eventHubConnectionString") String message,
                                ExecutionContext context) {
        logger = context.getLogger();

        String temp = getFormattedJson(message);

        ParkingCar parkingCar = getJsonToObject(temp);

        if(parkingCar != null && !parkingCar.toString().equals((new ParkingCar()).toString())) {
            processHttpTriggerEvent(message, "/storetoblob?clientId=default");
            if(parkingCar.getEvent_type().equals("in") || parkingCar.getEvent_type().equals("out")) {
                String json = getObjectToJson(parkingCar);
                processHttpTriggerEvent(json, "/storetocosmosdb?clientId=default");
                processHttpTriggerEvent(json, "/delivertoeventhub?clientId=default");
                processHttpTriggerEvent(json, "/storetordbms?clientId=default");
            }
        }

        return temp;
    }

    //BIZ LOGIC..........

    private String getFormattedJson(String message) {
        String temp = message.replaceAll("\\\\", "");
        if (temp.startsWith("\"")) {
            temp = temp.substring(1);
        }

        if (temp.endsWith("\"")) {
            temp = temp.substring(0, temp.length() - 1);
        }

        return temp;
    }

    private void processHttpTriggerEvent(String data, String uri) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baseUrl+uri);

        logger.info("==============> httpClient payload Data : " + data);

        try {
            StringEntity entity = new StringEntity(data);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse resp = client.execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            logger.info("==============> UnsupportedEncodingException : " + e.getMessage());
        } catch (IOException e) {
            logger.info("==============> IOException : " + e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.info("IOException : "+e.getMessage());
            }
        }
    }

    private ParkingCar getJsonToObject(String jsonString) {
        ParkingCar parkingCar = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            parkingCar = mapper.readValue(jsonString.replaceAll("'", "\""), ParkingCar.class);
            logger.info("Parse ParkingService Object toString: " + parkingCar);

        } catch (Exception e) {
            logger.info("Parse ParkingService Object toString error : " + e.getMessage());
        }

        return parkingCar;
    }


    private String getObjectToJson(ParkingCar parkingCar) {
        String parkingCarJson = "";

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {

            parkingCarJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(parkingCar);

            logger.info("<<<<<<<<<< Parse Parkingcar to Json parkingCarJson String: " + parkingCarJson);
        } catch (Exception e) {
            logger.info("Parse Parkingcar Object toString error : " + e.getMessage());
        }

        return parkingCarJson;
    }

    //.......... END BIZ LOGIC
}
