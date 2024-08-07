package com.example.inventory.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.CascadeType;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotBlank;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;

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

	@NotBlank @Column(length=600, nullable=false) private byte[] password;
	private byte[] salt = null;
    @Column(nullable=false) private boolean enabled = false;

    @CreationTimestamp private LocalDateTime created;
    @UpdateTimestamp private LocalDateTime modified;

    @Enumerated(EnumType.STRING)
	private Role role = Role.STOREKEEPER;

    public User(UUID id,
		String name,
		String email,
		String userName,
		String password,
    	boolean enabled,
    	Role role) {
	    	this.id = id;
			this.name = name;
			this.email = email;
			this.userName = userName;
			// this.password = password;
	    	this.enabled = enabled;
	    	this.role = role;

	    	this.setPassword(password);
    }

    public User(String name,
		String email,
		String userName,
		String password,
    	boolean enabled,
    	Role role) {
	    	this.id = null;
			this.name = name;
			this.email = email;
			this.userName = userName;
			// this.password = password;
	    	this.enabled = enabled;
	    	this.role = role;

	    	this.setPassword(password);
    }

    public User(String name,
		String email,
		String userName,
		String password,
    	boolean enabled) {
	    	this.id = null;
			this.name = name;
			this.email = email;
			this.userName = userName;
			// this.password = password;
	    	this.enabled = enabled;
	    	this.role = Role.STOREKEEPER;

	    	this.setPassword(password);
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

	public boolean checkPassword(String password) {
		String hashPassword = new String(
			this.hashPassword(password, this.salt));

		return hashPassword.equals(new String(this.password));
	}

	private byte[] getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.salt = generateSalt();
		this.password = hashPassword(password, this.salt);
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

    public Role getRole() {
        return role;
    }
            
    public void setRole(Role role) {
        this.role = role;
    }
            
    public boolean checkRole(Role role) {
        return this.role == role;
    }

    public String toString() {
    	return "User(id=" + this.id.toString() + 
    	", name=" + this.name + ", role=" + this.role + ")";
    }

    private byte[] generateSalt() {
    	// Generating a Salt
		// To introduce salt, we’ll use the SecureRandom class from java.security:
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);

		return random.generateSeed(16);
    }

    private byte[] hashPassword(String password, byte[] salt) {
    	try {
			// create a PBEKeySpec and a SecretKeyFactory which we’ll instantiate using the PBKDF2WithHmacSHA1 algorithm
			// new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 128, 128);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			// The third parameter (65536) is effectively the strength parameter.
			// It indicates how many iterations that this algorithm run for, increasing the time it takes to produce the hash.

			// Finally, we can use our SecretKeyFactory to generate the hash:
			byte[] hash = factory.generateSecret(spec).getEncoded();
			System.out.println(salt);
			System.out.println(password + ": ");
			System.out.print(hash);

			return hash;
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e);
		} catch (InvalidKeySpecException e) {
			System.err.println(e);
		}

		return null;
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
