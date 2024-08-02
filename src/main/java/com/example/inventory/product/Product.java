package com.example.inventory.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

// import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Product {
    @Id @GeneratedValue UUID id;
    @NotEmpty @Column(length=100, nullable=false) String name;
    String description;
    @Positive @Column(nullable=false, updatable=true) Double unitPrice;
    // @Positive @Column(nullable=false, updatable=true) Double buyingPrice;
    @Positive @Column(nullable=false, updatable=true) Integer reorderQuantity;

    @CreationTimestamp LocalDateTime created;
    @UpdateTimestamp LocalDateTime modified;

    public Product () {
        
    }

    public Product (UUID id,
        String name,
        String description,
        Double unitPrice,
        //Double buyingPrice,
        Integer reorderQuantity) {
            this.id = id; 
            this.name = name; 
            this.description = description; 
            this.unitPrice = unitPrice;
            //this.buyingPrice = buyingPrice;
            this.reorderQuantity = reorderQuantity;
            this.created = null;
            this.modified = null;
        }

    public Product (String name,
        String description,
        Double unitPrice,
        //Double buyingPrice,
        Integer reorderQuantity) {
            this(UUID.randomUUID(), 
                name, 
                description, 
                unitPrice, 
                //buyingPrice, 
                reorderQuantity);
        }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
       this.unitPrice = unitPrice;
    }
    
    public Integer getReorderQuantity() {
        return reorderQuantity;
    }
    
    public void setReorderQuantity(Integer reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
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

        return "Product(" + 
            myId + ", " +
            name + ", " +
            description + ", " +
            unitPrice.toString() + ", " +
            // buyingPrice.toString() + ", " +
            reorderQuantity.toString() + ", " +
            myCreated + ", " +
            myModified + ")";
    } 
}
