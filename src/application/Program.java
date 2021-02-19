package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Program {
	
	public static void main(String[] args) {
		
		Department obj = new Department(1, "Books");
		Seller seller = new Seller(new Date(), 1700.00, 2, "john@sapo.com", "john frog", obj);
		
		System.out.println(seller);
	}

}
