package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		PreparedStatement statment = null;
		ResultSet resultSet = null;
		try {
			statment = connection.prepareStatement(
					"SELECT seller.*,department.Name as DepartmentName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "ORDER BY Name");
			resultSet = statment.executeQuery();
			List<Seller> sellerList = new ArrayList<>();
			Map<Integer, Department> departmentController = new HashMap<>(); // One department can has various sellers,
																				// instead of each seller has one
																				// department. This Map will be used to
																				// control that.
			while (resultSet.next()) {
				Department dep = departmentController.get(resultSet.getInt("DepartmentId"));
				if (dep == null) {
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

	@Override
	public Seller findById(Integer id) {
		PreparedStatement statment = null;
		ResultSet resultSet = null;
		try {
			statment = connection.prepareStatement(
					"SELECT seller.*, department.Name as DepartmentName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = departmentId " + "WHERE seller.Id = ?");

			statment.setInt(1, id); // 1 == first '?'
			resultSet = statment.executeQuery();
			if (resultSet.next()) {
				Department department = instantiateDepartment(resultSet);
				Seller seller = instantiateSeller(resultSet, department);
				return seller;
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
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement statment = null;
		ResultSet resultSet = null;
		try {
			statment = connection.prepareStatement(
					"SELECT seller.*,department.Name as DepartmentName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name");

			statment.setInt(1, department.getId()); // 1 == first '?'
			resultSet = statment.executeQuery();

			List<Seller> sellerList = new ArrayList<>();

			Map<Integer, Department> departmentController = new HashMap<>(); // One department can has various sellers,
																				// instead of each seller has one
																				// department. This Map will be used to
																				// control that.
			while (resultSet.next()) {
				Department dep = departmentController.get(resultSet.getInt("DepartmentId"));
				if (dep == null) {
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

	@Override
	public void deleteById(Integer id) {
		PreparedStatement statment = null;
		
		try {
			statment = connection.prepareStatement("DELETE FROM seller WHERE id = ?");
			statment.setInt(1, id);
			
			int rowsAffected = statment.executeUpdate();
			
			if (rowsAffected == 0) {
				throw new DbException("THE ID YOU INFORM DOES NOT EXIST IN OUR DATABASE");
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statment);
		}
	}

	@Override
	public void insert(Seller seller) {
		PreparedStatement statment = null;

		try {
			statment = connection.prepareStatement("INSERT INTO seller "
					+ "(BirthDate, BaseSalary, Email, Name, DepartmentId) " + "VALUES " + "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			statment.setDate(1, new java.sql.Date(seller.getBirthDate().getTime()));
			statment.setDouble(2, seller.getSalary());
			statment.setString(3, seller.getEmail());
			statment.setString(4, seller.getName());
			statment.setInt(5, seller.getDepartment().getId());

			int rowsAffected = statment.executeUpdate(); // To check if the insertion worked

			if (rowsAffected > 0) {
				ResultSet resultSet = statment.getGeneratedKeys();
				if (resultSet.next()) {
					int id = resultSet.getInt(1); // Id is the first argument
					seller.setId(id);
				}
				DB.closeResultSet(resultSet);
			}

			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statment);
		}
	}

	@Override
	public void update(Seller seller) {
		PreparedStatement statment = null;
		try {
			statment = connection.prepareStatement(
					"UPDATE seller "
					+ "SET BirthDate = ?, BaseSalary = ?, Email = ?, Name = ?, DepartmentId = ? "
					+ "WHERE id = ?"
					);
			
			statment.setDate(1, new java.sql.Date(seller.getBirthDate().getTime()));
			statment.setDouble(2, seller.getSalary());
			statment.setString(3, seller.getEmail());
			statment.setString(4, seller.getName());
			statment.setInt(5, seller.getDepartment().getId());
			statment.setInt(6, seller.getId());
			
			statment.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statment);
		}
	}

	private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
		Seller seller = new Seller();
		seller.setId(resultSet.getInt("Id"));
		seller.setName(resultSet.getString("Name"));
		seller.setEmail(resultSet.getString("Email"));
		seller.setBirthDate(resultSet.getDate("BirthDate"));
		seller.setSalary(resultSet.getDouble("BaseSalary"));
		seller.setDepartment(department);
		return seller;
	}

	private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
		Department department = new Department(); // Instantiate a department
		department.setId(resultSet.getInt("DepartmentId")); // Set departments value
		department.setName(resultSet.getString("DepartmentName"));
		return department;
	}
}
