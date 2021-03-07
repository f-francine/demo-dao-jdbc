package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDAO {
	
	List<Seller> findAll();
	List<Seller> findByDepartment(Department department);
	public Seller findById(Integer id);
	public void deleteById(Integer id);
	public void insert(Seller seller);
	public void update(Seller seller);
}
