package com.example.inventory.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Role {
	@Id @GeneratedValue private UUID id;
	@Column(length=100, nullable=false) private String name;
    @Column(nullable=false, updatable=false) private LocalDateTime created;
    @Column(nullable=false, updatable=true) private LocalDateTime modified;

    public Role(UUID id,
    	String name,
    	LocalDateTime created,
    	LocalDateTime modified) {
	    	this.id = id;
			this.name = name;
	    	this.created = created;
	    	this.modified = modified;
    }

    public Role(String name,
    	LocalDateTime created,
    	LocalDateTime modified) {
	    	this.id = null;
			this.name = name;
	    	this.created = created;
	    	this.modified = modified;
    }

    public Role() {

    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

	public String name() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public LocalDateTime created() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime modified() {
        return modified;
    }
            
    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public String toString() {
    	return "Role(id=" + this.id.toString() + 
    		", name=" + this.name + 
    		", created=" + this.created + 
    		", modified=" + this.modified + ")";
    }
}
