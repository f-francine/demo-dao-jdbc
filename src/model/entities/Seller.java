package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Seller implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Date birthDate;
	private Double salary;
	private Integer id;
	private String email;
	private String name;
	
	private Department department;
	
	public Seller(Date birthDate, Double salary, Integer id, String email, String name, Department department) {
		super();
		this.birthDate = birthDate;
		this.salary = salary;
		this.id = id;
		this.email = email;
		this.name = name;
		this.department = department;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}
	public void setDepartmet(Department department) {
		this.department = department;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seller other = (Seller) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Seller [birthDate=" + birthDate + ", salary=" + salary + ", id=" + id + ", email=" + email + ", name="
				+ name + ", department=" + department + "]";
	}
}
