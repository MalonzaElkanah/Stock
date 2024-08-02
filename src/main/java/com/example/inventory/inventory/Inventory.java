package com.example.inventory.inventory;

import com.example.inventory.product.Product;
import com.example.inventory.discrepancy.Discrepancy;
import com.example.inventory.discrepancy.DiscrepancyModel;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;

import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.ArrayList;

@Entity
public class Inventory {
    // @Id @GeneratedValue UUID id;
    @ManyToOne(optional=false) @JoinColumn(name = "productId",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name="inventoryProduct")) Product product;
    @Column(nullable=false, updatable=true) Integer allItems = 0;
    @Column(nullable=false, updatable=true) Integer soldItems = 0;
    @Column(nullable=false, updatable=true) Integer discrepancyItems = 0;
    @Column(nullable=false, updatable=true) Integer availableItems = 0;

    @Column(nullable=false, updatable=true) Double totalBuyingPrice = 0.0;
    @Column(nullable=false, updatable=true) Double soldBuyingPrice = 0.0;
    @Column(nullable=false, updatable=true) Double soldSellingPrice = 0.0;
    @Column(nullable=false, updatable=true) Double availableBuyingPrice = 0.0;
    @Column(nullable=false, updatable=true) Double availableSellingPrice = 0.0;
    @Column(nullable=false, updatable=true) Double discrepancyBuyingPrice = 0.0;
    @Column(nullable=false, updatable=true) Double discrepancySellingPrice = 0.0;
    
    @Column(nullable=false, updatable=true) String status = "Unknown";

    private List<Checkin> checkinItems = new ArrayList();
    private  List<Checkout> checkoutItems = new ArrayList();
    private List<Discrepancy> discrepancies = new ArrayList();

    public Inventory(Product product) {
        this.product = product;
        init();
    }

    public Inventory() {
        this.product = null;
    }

    private void init() {
        CheckinModel checkinModel = new CheckinModel();
        CheckoutModel checkoutModel = new CheckoutModel();
        DiscrepancyModel discrepancyModel = new DiscrepancyModel();

        // Check-ins
        this.checkinItems = checkinModel.findByProduct(product);
        for (Checkin item: this.checkinItems) {
            this.allItems = this.allItems + item.getQuantity();
            this.totalBuyingPrice = this.totalBuyingPrice + (item.getQuantity() * item.getBuyingPrice());
        }

        // Check-outs
        this.checkoutItems = checkoutModel.findByProduct(product);
        for (Checkout item: this.checkoutItems) {
            this.soldItems = this.soldItems  + item.getQuantity();
            this.soldSellingPrice = this.soldSellingPrice + (item.getQuantity() * item.getUnitPrice());            
        }

        // Discrepancies
        this.discrepancies = discrepancyModel.findByProduct(product);
        for (Discrepancy item: this.discrepancies) {
            this.discrepancyItems = this.discrepancyItems  + item.getQuantity();
            this.discrepancySellingPrice = this.discrepancySellingPrice + (item.getQuantity() * item.getUnitPrice());            
        }

        // Available Items
        this.availableItems = this.allItems - (this.discrepancyItems + this.soldItems);        
        this.availableSellingPrice = this.availableItems * product.getUnitPrice();

        if (availableItems == 0) {
            this.status = "EMPTY";
        } else if (availableItems <= product.getReorderQuantity()) {
            this.status = "REORDER";
        } else {
            this.status = "AVAILABLE";
        }
    }

    public void setProduct(Product product) {
        this.product = product;
        init();
    }

    public Product getProduct() {
        return product;
    }

    public List<Checkin> getProductCheckins() {
        return checkinItems;
    }

    public List<Checkout> getProductCheckouts() {
        return checkoutItems;
    }

    public List<Discrepancy> getProductDiscrepancies() {
        return discrepancies;
    }

    public String checkStatus() {
        if (availableItems == 0) {
            this.status = "EMPTY";
        } else if (availableItems <= product.getReorderQuantity()) {
            this.status = "REORDER";
        } else {
            this.status = "AVAILABLE";
        }

        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Integer getAllItems() {
        return allItems;
    }

    public void setAllItems(Integer allItems) {
        this.allItems = allItems;
    }

    public Integer getSoldItems() {
        return soldItems;
    }
            
    public void setSoldItems(Integer soldItems) {
        this.soldItems = soldItems;
    }

    public Integer getDiscrepancyItems() {
        return discrepancyItems;
    }

    public void setDiscrepancyItems(Integer discrepancyItems) {
        this.discrepancyItems = discrepancyItems;
    }

    public Integer getAvailableItems() {
        return availableItems;
    }
            
    public void setAvailableItems(Integer availableItems) {
        this.availableItems = availableItems;
    }

    public Double getTotalBuyingPrice() {
        return totalBuyingPrice;
    }

    public void setTotalBuyingPrice(Double totalBuyingPrice) {
        this.totalBuyingPrice = totalBuyingPrice;
    }

    public Double getSoldBuyingPrice() {
        return soldBuyingPrice;
    }

    public void setSoldBuyingPrice(Double soldBuyingPrice) {
        this.soldBuyingPrice = soldBuyingPrice;
    }

    public Double getSoldSellingPrice() {
        return soldSellingPrice;
    }

    public void setSoldSellingPrice(Double soldSellingPrice) {
        this.soldSellingPrice = soldSellingPrice;
    }

    public Double getAvailableBuyingPrice() {
        return availableBuyingPrice;
    }

    public void setAvailableBuyingPrice(Double availableBuyingPrice) {
        this.availableBuyingPrice = availableBuyingPrice;
    }

    public Double getAvailableSellingPrice() {
        return availableSellingPrice;
    }

    public void setAvailableSellingPrice(Double availableSellingPrice) {
        this.availableSellingPrice = availableSellingPrice;
    }

    public Double getDiscrepancyBuyingPrice() {
        return discrepancyBuyingPrice;
    }

    public void setDiscrepancyBuyingPrice(Double discrepancyBuyingPrice) {
        this.discrepancyBuyingPrice = discrepancyBuyingPrice;
    }

    public Double getDiscrepancySellingPrice() {
        return discrepancySellingPrice;
    }

    public void setDiscrepancySellingPrice(Double discrepancySellingPrice) {
        this.discrepancySellingPrice = discrepancySellingPrice;
    }

    public String toString() {
        // String myId = "null"; if (id != null ) myId = id.toString();

        return "Inventory(<Product(" + product.getName() + ")>, " +
            allItems.toString() + ", " +
            soldItems.toString() + ")";
    }
}

