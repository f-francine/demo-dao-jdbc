package application;

import java.util.List;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		System.out.println("::::: TEST 01 - FIND BY ID :::::");
		
		SellerDAO sellerDao = DAOFactory.createSellerDAO();
		Seller seller = sellerDao.findById(6);

		System.out.println(seller);
		
		System.out.println("\n::::: TEST 02 - FIND BY DEPARTMENT :::::");
		
		Department department = new Department(4, null);
		List<Seller> sellerList = sellerDao.findByDepartment(department);
		
		
		for(Seller obj : sellerList) {
			System.out.println(obj);
			System.out.println(sellerList.size());
		}
	}

}
