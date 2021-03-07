package application;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Seller;

public class Program {
	
	public static void main(String[] args) {
		
		SellerDAO sellerDao = DAOFactory.createSellerDAO();
		
		Seller seller = sellerDao.findById(6);
		
		System.out.println("::::: TEST 01 - FIND BY ID :::::");
		
		System.out.println(seller);
	}

}
