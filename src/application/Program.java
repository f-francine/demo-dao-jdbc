package application;

import java.util.Date;
import java.util.List;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		Department department = new Department(4, null);

		System.out.println("::::: TEST 01 - FIND BY ID :::::");
		SellerDAO sellerDao = DAOFactory.createSellerDAO();
		Seller seller = sellerDao.findById(6);
		System.out.println(seller);

		System.out.println("\n::::: TEST 02 - FIND BY DEPARTMENT :::::");
		List<Seller> sellerList = sellerDao.findByDepartment(department);
		for(Seller obj : sellerList) {
			System.out.println(obj);
		} 

		System.out.println("\n::::: TEST 03 - FIND ALL :::::");		
		sellerList = sellerDao.findAll();
		for(Seller obj : sellerList) {
			System.out.println(obj);
		} 
		
		/*
		System.out.println("\n::::: TEST 04 - INSERT SELLER :::::");
		Seller newSeller = new Seller(new Date(), 4000.00, null, "may@illea.com.ill", "May Singer", department);
		sellerDao.insert(newSeller);
		System.out.println("INSERTED! NEW SELLER ID -> " + newSeller.getId());
		*/
		
		/*
		System.out.println("\n::::: TEST 05 - UPDATE SELLER :::::");
		seller = sellerDao.findById(10);
		System.out.println("\nUPDATING ->" + seller);
		seller.setName("Aspen Leger");
		seller.setEmail("aspenleger@illea.com.ill");
		sellerDao.update(seller);
		
		
		System.out.println("\nUPDATE COMPLETED! CHECK THE ALTERATIONS:");
		System.out.println(seller);
		*/
		
		System.out.println("\n::::: TEST 06 - DELETE BY ID :::::");
		sellerDao.deleteById(16);
		System.out.println("OK");
	}
}
