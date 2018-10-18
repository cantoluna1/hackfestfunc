package com.udpdemo.func;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BlobOutput;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.logging.Logger;

/**
 * Azure Functions with HTTP Trigger.
 */
public class HttpTriggerToBlobFunction {

    Logger logger;

    @FunctionName("storetoblob")
    @BlobOutput(name = "storetoblobOutput", path = "parking/raw_data.txt", connection = "udpblobConnection")
    public String helloStoreToBlob(@HttpTrigger(name = "storetoblobTrigger", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) String message,
                                   ExecutionContext context) {

        logger = context.getLogger();
        String jsonString = message;

        logger.info("jsonString >>> " + jsonString);

        return jsonString;
    }
}
