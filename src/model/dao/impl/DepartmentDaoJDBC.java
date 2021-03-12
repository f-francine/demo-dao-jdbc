package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDAO {
	
	private  Connection connection;

	public DepartmentDaoJDBC(Connection connection) { // Dependency injection
		this.connection = connection;
	}
	
	@Override
	public List<Department> findAll() {
		PreparedStatement statment = null;
		ResultSet resultSet = null;
		
		try {
			statment = connection.prepareStatement("SELECT * FROM department ORDER BY Id");
			
			resultSet = statment.executeQuery();
			List<Department> departmentList = new ArrayList<>();
			
			while (resultSet.next()) {
				Department dep = new Department();
				dep.setId(resultSet.getInt("Id"));
				dep.setName(resultSet.getString("Name"));
				departmentList.add(dep);
			}
			return departmentList;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statment);
			DB.closeResultSet(resultSet);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement statment = null;
		ResultSet resultSet = null;
		
		try {
			statment = connection.prepareStatement("SELECT * FROM department "
					+ "WHERE id = ? "
					+ "ORDER BY Id");
			
			statment.setInt(1, id);
			resultSet = statment.executeQuery();
			
			if (resultSet.next()) {
				Department dep = new Department();
				dep.setId(resultSet.getInt("Id"));
				dep.setName(resultSet.getString("Name"));
				
				return dep;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statment);
			DB.closeResultSet(resultSet);
		}
	}

	@Override
	public void deleteById(Integer id) {

	}

	@Override
	public void insert(Department department) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Department department) {
		// TODO Auto-generated method stub
		
	}
}
