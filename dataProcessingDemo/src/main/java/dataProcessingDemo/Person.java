package dataProcessingDemo;

public class Person {
	
	private String name;
	private String email;
	private String country;
	private int salary;
	
	public Person(String name, String email, String country, int salary) {
		super();
		this.name = name;
		this.email = email;
		this.country = country;
		this.salary = salary;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	

}
