package vttp5.paf.day25.day_paf_25_producer.model;

public class OrderDetail2 {

    private Integer id;
    private String product;
    private Double unitPrice;
    private Double discount = 0.1;
    private Integer quantity;

    
    public OrderDetail2() {
    }

    

    public OrderDetail2(String product, Double unitPrice, Double discount, Integer quantity) {
        this.product = product;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.quantity = quantity;
    }


    

    public OrderDetail2(Integer id, String product, Double unitPrice, Double discount, Integer quantity) {
        this.id = id;
        this.product = product;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.quantity = quantity;
    }



    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getProduct() {
        return product;
    }


    public void setProduct(String product) {
        this.product = product;
    }


    public Double getUnitPrice() {
        return unitPrice;
    }


    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }


    public Double getDiscount() {
        return discount;
    }


    public void setDiscount(Double discount) {
        this.discount = discount;
    }


    public Integer getQuantity() {
        return quantity;
    }


    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    
    
    
}
