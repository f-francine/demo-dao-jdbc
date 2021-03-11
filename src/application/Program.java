package application;

import java.util.List;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		Department department = new Department(2, null);
		
		//Seller seller1 = new Seller(new Date(), 2500.00, 1, "petermaverik@sapo.com", "Peter Maverik", department);

		System.out.println("::::: TEST 01 - FIND BY ID :::::");
		
		SellerDAO sellerDao = DAOFactory.createSellerDAO();
		/*
		Seller seller = sellerDao.findById(6);

		System.out.println(seller);
		*/
		System.out.println("\n::::: TEST 02 - FIND BY DEPARTMENT :::::");
		
		List<Seller> sellerList = sellerDao.findByDepartment(department);
		
		System.out.println("\n::::: TEST 03 - FIND ALL :::::");
		
		sellerList = sellerDao.findAll();
		
		
		for(Seller obj : sellerList) {
			System.out.println(obj);
		}
	}

}
