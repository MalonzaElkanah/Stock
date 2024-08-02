package com.example.inventory.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.CascadeType;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name="Account")
public class User {
	// TODO Make username unique
	@Id @GeneratedValue private UUID id;
	@NotBlank @Column(length=500, nullable=false) private String name;

	// @NaturalId(mutable=true)
	@Email @NotEmpty @Column(length=500, nullable=false)
	private String email;

	@NotBlank @NaturalId(mutable=true) @Column(length=100, nullable=false)
	private String userName;

	@NotBlank @Column(length=600, nullable=false) private String password;
    @Column(nullable=false) private boolean enabled = false;

    @CreationTimestamp LocalDateTime created;
    @UpdateTimestamp LocalDateTime modified;

    @OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "roleId", referencedColumnName = "id",
        foreignKey = @ForeignKey(name="userRoles"))
	private Set<Role> roles = new HashSet<>();

    public User(UUID id,
		String name,
		String email,
		String userName,
		String password,
    	boolean enabled,
    	Set<Role> roles) {
	    	this.id = id;
			this.name = name;
			this.email = email;
			this.userName = userName;
			this.password = password;
	    	this.enabled = enabled;
	    	this.roles = roles;
    }

    public User(String name,
		String email,
		String userName,
		String password,
    	boolean enabled,
    	Set<Role> roles) {
	    	this.id = null;
			this.name = name;
			this.email = email;
			this.userName = userName;
			this.password = password;
	    	this.enabled = enabled;
	    	this.roles = roles;
    }

    public User() {

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String password() {
		return password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

    public Set<Role> getRoles() {
        return roles;
    }
            
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String toString() {
    	return "User(id=" + this.id.toString() + ", name=" + this.name + ")";
    }

	/*public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof User)) {
			return false;
		}

		User user = (User) o;

		return Objects.equals(id, user.id());
	}*/

}
