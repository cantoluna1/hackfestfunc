package com.udpdemo.func;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class StringTest {
    public static void main(String[] args) throws IOException {
//        stringTest();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/listJson.json");
        //HttpPost httpPost = new HttpPost("https://smyun-javafromintellij.azurewebsites.net/api/storeToRDBMS?clientId=default");

        String json = "{\n" +
                "  \"id\" : null,\n" +
                "  \"service_id\" : null,\n" +
                "  \"event_type\" : \"out\",\n" +
                "  \"parking_id\" : \"PID0000056\",\n" +
                "  \"gate_id\" : \"GID001\",\n" +
                "  \"parking_floor\" : null,\n" +
                "  \"parking_area\" : null,\n" +
                "  \"parking_number\" : null,\n" +
                "  \"time\" : \"2018/09/04 19:45:13\",\n" +
                "  \"car_number\" : \"94��2013\",\n" +
                "  \"car_type\" : null,\n" +
                "  \"lpr_image\" : null,\n" +
                "  \"car_image\" : null,\n" +
                "  \"device_id\" : null,\n" +
                "  \"operation\" : null,\n" +
                "  \"result\" : null\n" +
                "}";

        StringEntity entity = null;
        try {
            entity = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");



        try {
            client.execute(httpPost);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void stringTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String jsonString = "{'parkinglot': 9,'car': 1020,'enter': 1535948006,'leave': 1535955038}";
        ParkingCar parkingCar = mapper.readValue(jsonString.replaceAll("'","\""),ParkingCar.class);

        System.out.println(parkingCar);
    }
}
