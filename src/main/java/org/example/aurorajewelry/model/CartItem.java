package org.example.aurorajewelry.model;

import java.math.BigDecimal;

public class CartItem {
    private int productId;
    private Integer variantId; // có thể null
    private String productName;
    private BigDecimal unitPrice; // giá 1 sản phẩm
    private int quantity;
    private String imageUrl;
    private String variantSize;

    // ----- GETTERS -----
    public int getProductId() { return productId; }
    public Integer getVariantId() { return variantId; }
    public String getProductName() { return productName; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
    public String getImageUrl() { return imageUrl; }
    public String getVariantSize() { return variantSize; }

    // ----- SETTERS -----
    public void setProductId(int productId) { this.productId = productId; }
    public void setVariantId(Integer variantId) { this.variantId = variantId; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setVariantSize(String variantSize) { this.variantSize = variantSize; }

    // Tổng tiền = unitPrice * quantity
    public BigDecimal getTotal() {
        return unitPrice.multiply(new BigDecimal(quantity));
    }

    // key dùng trong session map: productId_variantId
    public String key() {
        int v = variantId == null ? 0 : variantId;
        return productId + "_" + v;
    }
}
