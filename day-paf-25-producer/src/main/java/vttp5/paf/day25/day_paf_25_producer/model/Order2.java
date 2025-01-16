package vttp5.paf.day25.day_paf_25_producer.model;


import java.time.LocalDate;
import java.util.List;

// import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class Order2 {

    private Integer orderId;

    // @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate orderDate;
    private String customerName;
    private String shipAddress;
    private String notes;
    private Double tax = 0.05;
    private List<OrderDetail2> lineItem;

    

    public Order2() {
    }

    
    public Order2(LocalDate orderDate, String customerName, String shipAddress, String notes, Double tax,
            List<OrderDetail2> lineItem) {
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.shipAddress = shipAddress;
        this.notes = notes;
        this.tax = tax;
        this.lineItem = lineItem;
    }




    public Order2(Integer orderId, LocalDate orderDate, String customerName, String shipAddress, String notes,
            Double tax, List<OrderDetail2> lineItem) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.shipAddress = shipAddress;
        this.notes = notes;
        this.tax = tax;
        this.lineItem = lineItem;
    }




    public Integer getOrderId() {
        return orderId;
    }



    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }



    public LocalDate getOrderDate() {
        return orderDate;
    }



    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }



    public String getCustomerName() {
        return customerName;
    }



    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }



    public String getShipAddress() {
        return shipAddress;
    }



    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }



    public String getNotes() {
        return notes;
    }



    public void setNotes(String notes) {
        this.notes = notes;
    }



    public Double getTax() {
        return tax;
    }



    public void setTax(Double tax) {
        this.tax = tax;
    }



    public List<OrderDetail2> getLineItem() {
        return lineItem;
    }



    public void setLineItem(List<OrderDetail2> lineItem) {
        this.lineItem = lineItem;
    }
    
    

    
    
}
