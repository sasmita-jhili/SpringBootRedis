package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Product;
import com.app.repo.ProductRepository;

@RestController
@RequestMapping("/product")
@EnableCaching
public class ProductController {
	@Autowired
	private ProductRepository repo;

	@PostMapping
	public Product save(@RequestBody Product product) {
		return repo.save(product);
	}

	@GetMapping
	public List<Product> getAllProducts() {
		return repo.findAll();
	}

	@GetMapping("/{id}")
	@Cacheable(key = "#id", value = "Product",unless = "#result.price > 1000")
	public Product findProduct(@PathVariable int id) {
		Product findProductById = repo.findProductById(id);
		return findProductById;
	}

	@DeleteMapping("/{id}")
	public String remove(@PathVariable int id) {
		return repo.deleteProduct(id);
	}

}
