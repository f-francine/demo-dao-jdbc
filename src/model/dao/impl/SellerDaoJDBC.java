package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDAO {
	
	private Connection connection;

	public SellerDaoJDBC(Connection connection) { // Dependency injection
		this.connection = connection;
	}
	
	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement statment = null;
		ResultSet resultSet = null;
		try {
			statment = connection.prepareStatement(
					"SELECT seller.*, department.Name as DepartmentName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = departmentId "
					+ "WHERE seller.Id = ?");
			
			
			statment.setInt(1, id); // 1 == first '?'
			resultSet = statment.executeQuery();
			if(resultSet.next()) {
				Department department = new Department(); // Instantiate a department
				department.setId(resultSet.getInt("DepartmentId")); // Set departments value
				department.setName(resultSet.getString("DepartmentName"));
				
				Seller seller = new Seller();
				seller.setId(resultSet.getInt("Id"));
				seller.setName(resultSet.getString("Name"));
				seller.setEmail(resultSet.getString("Email"));
				seller.setBirthDate(resultSet.getDate("BirthDate"));
				seller.setSalary(resultSet.getDouble("BaseSalary"));
				seller.setDepartmet(department);
				
				return seller;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statment);
			DB.closeResultSet(resultSet);
		}
		return null;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub
		
	}

}
