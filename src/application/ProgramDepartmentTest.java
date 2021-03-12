package application;

import java.util.List;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class ProgramDepartmentTest {

	public static void main(String[] args) {
		
		DepartmentDAO departmentDAO = DAOFactory.createDepartmentDAO();

		System.out.println("::::: TEST 01 - FIND ALL :::::");
		List<Department> departmentList = departmentDAO.findAll();		
		for(Department dep : departmentList) {
			System.out.println(dep);
		}
		
		System.out.println("::::: TEST 02 - FIND BY ID :::::");
		Department department = departmentDAO.findById(1);
		System.out.println(department);
	}
}
