package com.example.api.web.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;
import com.example.api.service.CustomerService;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*", allowedHeaders = "*") // liberar acesso a outras aplicações.

public class CustomerController {

	private CustomerService service;


	@Autowired
	private CustomerRepository repository;

	@Autowired
	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping
	public List<Customer> findAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Customer findById(@PathVariable Long id) {
		return service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
	}

	// Adicione endpoints para criar um novo cliente, editar um cliente e exluir um
	// cliente.

	@PostMapping("/cadastrar")
	public ResponseEntity<Customer> postCustomer(@Valid @RequestBody Customer customer) {
		
		return service.registerCustomer(customer)
				.map(resp-> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	
		//Se o e-mail não for igual ele registra, se for ele dá erro.
	}

	@PutMapping("/editar")
	public ResponseEntity<Customer> putCustomer(@Valid @RequestBody Customer customer) {
		
		return service.updateCustomer(customer)
				.map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		
		//Se o e-mail existir ele pode editar, se não dá erro.
	}

	@DeleteMapping("/{id}")
	public void deleteCustomer(@PathVariable Long id) {
		repository.deleteById(id);

	}
}
