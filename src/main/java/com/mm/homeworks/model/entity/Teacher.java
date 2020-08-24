package com.mm.homeworks.model.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "teachers")
public class Teacher {
	@Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(
            name = "uuid-string",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "user_id", nullable = false, unique = true, updatable = false)
    private String id;
	
	@Enumerated(EnumType.STRING)
	private Title title;
	
	@NotEmpty
	private String fullname;

	@OneToMany(mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	private Set<Subject> subjects; // TODO check how could use Lazy loading

	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;
	
	public Teacher() {
		this.subjects = new HashSet<Subject>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Set<Subject> getSubjects() {
		return Collections.unmodifiableSet(subjects);
	}

	public void addSubject(Subject subject) {
		if (subject == null) {
			return;
		}
		
		this.subjects.add(subject);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void removeAllSubjects() {
		this.subjects = new HashSet<Subject>();
	}
}

