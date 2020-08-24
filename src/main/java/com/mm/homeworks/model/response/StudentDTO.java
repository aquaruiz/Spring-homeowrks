package com.mm.homeworks.model.response;

public class StudentDTO {
	private String firstName;
	private String lastName;
	private int age;
	private double totalScore;

	public StudentDTO() {
	}
	
	public StudentDTO(String firstName, String lastName, int age, double totalScore) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.totalScore = totalScore;
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

}
