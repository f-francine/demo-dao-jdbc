package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				Department department = instantiateDepartment(resultSet);
				
				Seller seller = instantiateSeller(resultSet, department);
				
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

	private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
		Seller seller = new Seller();
		seller.setId(resultSet.getInt("Id"));
		seller.setName(resultSet.getString("Name"));
		seller.setEmail(resultSet.getString("Email"));
		seller.setBirthDate(resultSet.getDate("BirthDate"));
		seller.setSalary(resultSet.getDouble("BaseSalary"));
		seller.setDepartmet(department);
		return seller;
	}

	private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
		Department department = new Department(); // Instantiate a department
		department.setId(resultSet.getInt("DepartmentId")); // Set departments value
		department.setName(resultSet.getString("DepartmentName"));
		return department;
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

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement statment = null;
		ResultSet resultSet = null;
		try {
			statment = connection.prepareStatement(
					"SELECT seller.*,department.Name as DepartmentName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = departmentId "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			
			statment.setInt(1, department.getId()); // 1 == first '?'
			resultSet = statment.executeQuery();
			
			List<Seller> sellerList = new ArrayList<>();
			
			Map<Integer, Department> departmentController = new HashMap<>(); // One department can has various sellers, instead of each seller has one department. This Map will be used to control that.
			while(resultSet.next()) {
				
				Department dep = departmentController.get(resultSet.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instantiateDepartment(resultSet);
					departmentController.put(resultSet.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instantiateSeller(resultSet, dep);
				
				sellerList.add(seller);
			}
			return sellerList;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statment);
			DB.closeResultSet(resultSet);
		}
		
	}

}
