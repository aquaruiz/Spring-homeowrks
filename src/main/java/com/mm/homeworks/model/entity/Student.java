package com.mm.homeworks.model.entity;

import java.util.Collections;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "students")
public class Student {
	@Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(
            name = "uuid-string",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "user_id", nullable = false, unique = true, updatable = false)
    private String id;
	
	@NotEmpty
	@Size(max = 50, min = 2)
	private String firstName;

	@NotEmpty
	@Size(max = 50, min = 2)
	private String lastName;

	@Min(16)
	@Max(40)
	@Column(columnDefinition = "integer default 25")
	private int age;

	@Column(columnDefinition = "Decimal(3, 2)")
	private double totalScore;
	
	@ManyToMany(mappedBy = "students")
	private Set<Subject> subjects;

	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;

	public Student() {
		this.totalScore = -1.0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	
	public void removeSubject(Subject subject) {
		if (subject == null) {
			return;
		}
		
		this.subjects.remove(subject);
	}

}
