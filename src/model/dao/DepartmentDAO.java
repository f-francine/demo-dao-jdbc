package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDAO {
	
	List<Department> findAll();
	public Department findById(Integer id);
	public void deleteById(Integer id);
	public void insert(Department department);
	public void update(Department department);
}
