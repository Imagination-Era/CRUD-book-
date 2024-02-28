package com.BookWeb.BookSell.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookWeb.BookSell.models.Product;

public interface ProductsRepository extends JpaRepository<Product,Integer> {

}
