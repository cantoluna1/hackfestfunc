package com.udpdemo.func;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.annotation.*;

import java.util.logging.Logger;

/**
 * Azure Functions with HTTP Trigger.
 */
public class HttpTriggerToEventHub {

    Logger logger;

    @FunctionName("delivertoeventhub")
    @EventHubOutput(name = "delivertoeventhubOutput", eventHubName = "udpeventsa01", connection = "eventHubBusConnectionString")
    public String helloDeliverToEventhub(
            @HttpTrigger(name = "delivertoeventhubTrigger",
                    methods = {HttpMethod.GET, HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS) String message,
            ExecutionContext context) {

        logger = context.getLogger();

        String jsonString = message;

        logger.info("message >>> " + jsonString);

        return jsonString;
    }
}
