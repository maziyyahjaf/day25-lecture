package vttp5.paf.day25.day_paf_25_producer.utils;

import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp5.paf.day25.day_paf_25_producer.model.Order;
import vttp5.paf.day25.day_paf_25_producer.model.Order2;
import vttp5.paf.day25.day_paf_25_producer.model.OrderDetail;
import vttp5.paf.day25.day_paf_25_producer.model.OrderDetail2;

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

    public static Order2 toOrder2 (String rawJsonString) {
        JsonReader reader = Json.createReader(new StringReader(rawJsonString));
        JsonObject root = reader.readObject();
        String orderDateString = root.getString("orderDate");
        LocalDate orderDate = parseToLocalDate(orderDateString);
        String customerName = root.getString("customerName");
        String shipAddress = root.getString("shipAddress");
        String notes = root.getString("notes");
        Double tax = root.getJsonNumber("tax").doubleValue();
        JsonArray jArray = root.getJsonArray("lineItem");
        List<OrderDetail2> orderDetails = new ArrayList<>();
        for (JsonObject jsonObject : jArray.getValuesAs(JsonObject.class)) {
            OrderDetail2 item = toOrderDetails2(jsonObject);
            orderDetails.add(item);
        }

        Order2 order = new Order2(orderDate, customerName, shipAddress, notes, tax, orderDetails);

        return order;

    }

    public static OrderDetail2 toOrderDetails2(JsonObject jsonObject) {
        String product = jsonObject.getString("product");
        Double unitPrice = jsonObject.getJsonNumber("unitPrice").doubleValue();
        Double discount = jsonObject.getJsonNumber("discount").doubleValue();
        int quantity = jsonObject.getJsonNumber("quantity").intValue();
        OrderDetail2 orderDetail = new OrderDetail2(product, unitPrice, discount, quantity);

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

    public static JsonObject toJsonObject2(Order2 order) {

        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
    
        // Build JsonArray for line items
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        if (order.getLineItem() != null) {
            for (OrderDetail2 item : order.getLineItem()) {
                if (item != null) {
                    JsonObject itemJsonObject = Json.createObjectBuilder()
                                                    .add("product", item.getProduct())
                                                    .add("unitPrice", item.getUnitPrice())
                                                    .add("discount", item.getDiscount())
                                                    .add("quantity", item.getQuantity())
                                                    .build();
                    arrayBuilder.add(itemJsonObject);
                }
            }
        }
    
        // Build the main JsonObject for the Order2
        return Json.createObjectBuilder()
                    .add("orderDate", order.getOrderDate().toString())
                    .add("customerName", order.getCustomerName())
                    .add("shipAddress", order.getShipAddress())
                    .add("notes", order.getNotes())
                    .add("tax", order.getTax())
                   .add("lineItem", arrayBuilder.build())
                   .build();
    }

    private static LocalDate parseToLocalDate(String orderDateString) {
        LocalDate localDate = LocalDate.parse(orderDateString);
        return localDate;
    }
    
}
