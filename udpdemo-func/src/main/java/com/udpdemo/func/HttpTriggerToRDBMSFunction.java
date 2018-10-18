package com.udpdemo.func;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.sql.*;
import java.util.logging.Logger;

/**
 * Azure Functions with EventHub Trigger.
 */
public class HttpTriggerToRDBMSFunction {

    Logger logger;

    @FunctionName("storetordbms")
    public String helloStoreToRDBMS(
                        @HttpTrigger(name = "storetordbmsTrigger",
                                     methods = {HttpMethod.GET, HttpMethod.POST},
                                    authLevel = AuthorizationLevel.ANONYMOUS)  String message,
                        ExecutionContext context) {

        logger = context.getLogger();

        String jsonString = message;

        //json to parking object
        ParkingCar parkingCar = getJsonToObject(jsonString);

        logger.info(">>>>>>>>>>>>>>> Event Hub trigger function processed a message to parkingCar Result: " + parkingCar);

        //parking data store
        storeParkingDataToRDBMS(parkingCar);

        return jsonString;
    }

    //BIZ LOGIC..........

    private void storeParkingDataToRDBMS(ParkingCar parkingCar) {
        Connection connection = null;

        try {
            connection = getConnection();
            logger.info("Successful connection ");

            logger.info("Query Start");
            logger.info("=========================================");

            String insertSql = "INSERT INTO ParkingService (service_id, event_type, parking_id, parking_floor, parking_area, parking_number, time, car_image, car_number, gate_id, lpr_image, device_id, operation, result) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstat = connection.prepareStatement(insertSql)) {

                pstat.setString(1, parkingCar.getService_id());
                pstat.setString(2, parkingCar.getEvent_type());
                pstat.setString(3, parkingCar.getParking_id());
                pstat.setString(4, parkingCar.getParking_floor());
                pstat.setString(5, parkingCar.getParking_area());
                pstat.setString(6, parkingCar.getParking_number());
                pstat.setString(7, parkingCar.getTime());
                pstat.setString(8, parkingCar.getCar_image());
                pstat.setString(9, parkingCar.getCar_number());
                pstat.setString(10, parkingCar.getGate_id());
                pstat.setString(11, parkingCar.getLpr_image());
                pstat.setString(12, parkingCar.getDevice_id());
                pstat.setString(13, parkingCar.getOperation());
                pstat.setString(14, parkingCar.getResult());

                int cnt = pstat.executeUpdate();
                logger.info("fetch count "+cnt);

                connection.close();
            }
        }
        catch (Exception e) {
            logger.info("Exception "+e.getMessage());
        }
    }

    private ParkingCar getJsonToObject(String jsonString) {
        ParkingCar parkingCar = null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            parkingCar = mapper.readValue(jsonString.replaceAll("'","\""), ParkingCar.class);
            logger.info("Parse Parkingcar Object toString: "+ parkingCar);

        } catch (Exception e) {
            logger.info("Parse Parkingcar Object toString error : "+ e.getMessage());
        }

        return parkingCar;
    }

    private Connection getConnection() throws SQLException {
        // Connect to database
        String hostName = "udprdbserver.database.windows.net";
        String dbName = "UDPRdb";
        String user = "udpadmin";
        String password = "1q2w#E$R";
        String url = "jdbc:sqlserver://"+hostName+":1433;database="+dbName+";user="+user+";password="+password+";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        Connection connection = null;

        logger.info("SQL Database Connection Start");

        // check that the driver is installed
        try
        {
            logger.info("Class Load ");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            logger.info("Class Load Success ");
        }
        catch (ClassNotFoundException e)
        {
            logger.warning("JDBC driver NOT detected in library path."+e.getMessage());
        }

        return DriverManager.getConnection(url);
    }

    //.......... END BIZ LOGIC
}
