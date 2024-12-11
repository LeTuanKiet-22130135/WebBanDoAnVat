package model;

import java.math.BigDecimal;

public class CartItem {
	private int id; // Unique ID for the cart item
    private int cartId; // ID of the cart this item belongs to
    private int productId; // ID of the product
    private String productName; // Name of the product
    private String productImageUrl; // Image URL for the product
    private BigDecimal productPrice; // Price of the product
    private int quantity; // Quantity of the product
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImageUrl() {
		return productImageUrl;
	}
	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
	// Method to calculate total price for this item
    public BigDecimal getTotalPrice() {
        return productPrice.multiply(new BigDecimal(quantity));
    }
}
