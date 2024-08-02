package com.example.inventory.inventory;

import com.example.inventory.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Checkin {
    @Id @GeneratedValue UUID id;
    @NotNull @ManyToOne(optional=false) @JoinColumn(name = "productId",
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name="checkinProduct")) Product product;
    @Positive @Column(nullable=false, updatable=true) Integer quantity;
    @Positive @Column(nullable=false, updatable=true) Double buyingPrice;
    @CreationTimestamp LocalDateTime created;
    @UpdateTimestamp LocalDateTime modified;

    public Checkin(UUID id,
        Product product,
        Integer quantity,
        Double buyingPrice) {
            this.id = id;
            this.product = product;
            this.quantity = quantity;
            this.buyingPrice = buyingPrice;
            this.created = null;
            this.modified = null;
    }

    public Checkin(Product product,
        Integer quantity,
        Double buyingPrice) {
            this.id = null;
            this.product = product;
            this.quantity = quantity;
            this.buyingPrice = buyingPrice;
            this.created = null;
            this.modified = null;
    }

    public Checkin() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(Double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }
            
    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public String toString() {
        String myId = "";
        String myCreated = "";
        String myModified = "";

        if (id != null ) myId = id.toString();
        if (created != null ) myCreated = created.toString();
        if (modified != null ) myModified = modified.toString();

        return "Checkin(" + 
            myId + ", " +
            "<Product: " + product.getName() + ">, " +
            quantity.toString() + ", " +
            buyingPrice.toString() + ", " +
            myCreated + ", " +
            myModified + ")";
    }
}

