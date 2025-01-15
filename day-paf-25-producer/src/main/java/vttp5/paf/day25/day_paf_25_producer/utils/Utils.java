package vttp5.paf.day25.day_paf_25_producer.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp5.paf.day25.day_paf_25_producer.model.Order;
import vttp5.paf.day25.day_paf_25_producer.model.OrderDetail;

public class Utils {

    public static Order toOrder (String rawJsonString) {
        JsonReader reader = Json.createReader(new StringReader(rawJsonString));
        JsonObject root = reader.readObject();
        String fullName = root.getString("fullName");
        JsonArray jArray = root.getJsonArray("lineItem");
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (JsonObject jsonObject : jArray.getValuesAs(JsonObject.class)) {
            OrderDetail item = toOrderDetails(jsonObject);
            orderDetails.add(item);
        }

        Order order = new Order(fullName, orderDetails);

        return order;

    }

    public static OrderDetail toOrderDetails(JsonObject jsonObject) {
        String productName = jsonObject.getString("productName");
        int price = jsonObject.getJsonNumber("price").intValue();
        int quantity = jsonObject.getJsonNumber("quantity").intValue();
        OrderDetail orderDetail = new OrderDetail(productName, price, quantity);

        return orderDetail;
    }

    public static JsonObject toJsonObject(Order order) {

        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
    
        // Build JsonArray for line items
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        if (order.getLineItem() != null) {
            for (OrderDetail item : order.getLineItem()) {
                if (item != null) {
                    JsonObject itemJsonObject = Json.createObjectBuilder()
                                                    .add("productName", item.getProductName())
                                                    .add("price", item.getPrice())
                                                    .add("quantity", item.getQuantity())
                                                    .build();
                    arrayBuilder.add(itemJsonObject);
                }
            }
        }
    
        // Build the main JsonObject for the Order
        return Json.createObjectBuilder()
                   .add("fullName", order.getFullName())
                   .add("lineItem", arrayBuilder.build())
                   .build();
    }
    
}
