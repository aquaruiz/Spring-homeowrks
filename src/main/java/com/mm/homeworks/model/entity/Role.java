package com.mm.homeworks.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(nullable = false)
    private Authority authority;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();
    
    public Role(Authority authority) {
    	this.authority = authority;
	}
    
    public Role() {
    	this.users = new HashSet<User>();
    }

	public Long getId() {
		return id;
	}
    
    public void setId(Long id) {
		this.id = id;
	}

    @Override
    public String getAuthority() {
        return authority.asRole();
    }

    public Authority authorityAsEnum() {
        return this.authority;
    }
    
    public void setAuthority(Authority authority) {
		this.authority = authority;
	}
}
