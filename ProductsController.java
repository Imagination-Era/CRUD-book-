package com.BookWeb.BookSell.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BookWeb.BookSell.models.Product;
import com.BookWeb.BookSell.models.ProductDTO;
import com.BookWeb.BookSell.services.ProductsRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	
	private ProductsRepository repo;
	@GetMapping({"","/"})
	public String showProducts(Model model) {
	List<Product> products=repo.findAll();
	model.addAttribute("products",products);
	 return ("products/index");
	
	}
	@GetMapping("/create")
	public String ShowCreatePage(Model model) {
		
		ProductDTO productDto =new ProductDTO();
		model.addAttribute("productDto" ,productDto);
		return("products/CreateProduct");
		
	}
	@PostMapping("/create")
	public String  createProduct(
			@Valid @ModelAttribute ProductDTO productDto,
			BindingResult result
			
			) {
		if(result.hasErrors()) {
			return("products/CreateProduct");
		}
	
	Date createdAt=new Date(0);
	Product product =new Product();
	product.setName(productDto.getName());
	product.setBrand(productDto.getBrand());
	
	product.setCategory(productDto.getCategory());
	
	product.setPrice(productDto.getPrice());
	
	product.setDescription(productDto.getDescription());
	product.setCreatedAt(createdAt);
	
	repo.save(product);

	return ("redirect:/products");
}
	
	
	@GetMapping("/edit") 
	public String showEditPage(Model model,
			@RequestParam int id) {
		
		try{
			Product product=repo.findById(id).get();
			model.addAttribute("product",product);
			
			ProductDTO productDto=new ProductDTO();
			productDto.setName(product.getName());
			productDto.setBrand(product.getBrand());
			productDto.setPrice(product.getPrice());
			productDto.setDescription(product.getDescription());
			
			model.addAttribute("productDto",productDto);
			
			
		}
		
		catch(Exception e) {
			
			System.out.println("Exception: "+ e.getMessage());
			return "redirect:/product";
			
		}
		
		
	
	return "products/EditProduct";
}
	
	
		@PostMapping("/edit")
		public String updateProduct(
	 
			 Model model,
			 @RequestParam int id,
			 @Valid @ModelAttribute ProductDTO productdto,
			 BindingResult result
		 
			 ) {
	 
		  try {
			  Product  product=repo.findById(id).get();
			  model.addAttribute("product",product);
			  if(result.hasErrors()) {
				  
				  return "products/EditProduct";
			  }
			  product.setName(productdto.getName());
				product.setBrand(productdto.getBrand());
				product.setPrice(productdto.getPrice());
				product.setCategory(productdto.getCategory());
				product.setDescription(productdto.getDescription());
			  			  repo.save(product);
		  }
		 
		 catch(Exception ex) {
			 
			 System.out.println("Exception: "+ex.getMessage() );
			 
		 }
		  return "redirect:/products";
		 
	 }
	
		@GetMapping("/delete")
		public String DeleteProduct(
			
			@RequestParam int id){
				
				try {
					Product product =repo.findById(id).get();	
					repo.delete(product);
				}
				catch(Exception ex){
					System.out.println("Exception :  "+ ex.getMessage());
					
				}
				
			
			return ("redirect:/products");
		
	
		}
	
	
}