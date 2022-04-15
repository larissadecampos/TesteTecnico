package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository repository;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public List<Customer> findAll() {
		return repository.findAllByOrderByNameAsc();
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

	// Validação dos campos verificando se existe usuários cadastrados com o mesmo e-mail.

	public Optional<Customer> registerCustomer(Customer customer) {
		if (repository.findByEmail(customer.getEmail()).isPresent())
			return Optional.empty();

		return Optional.of(repository.save(customer));
	}

	public Optional<Customer> updateCustomer(Customer customer) {

		if (repository.findById(customer.getId()).isPresent()) {
			Optional<Customer> buscaCustomer = repository.findByEmail(customer.getEmail());

			if ((buscaCustomer.isPresent()) && (buscaCustomer.get().getId() != customer.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado!", null);

			return Optional.ofNullable(repository.save(customer));

		}
		
		return Optional.empty();

	}

}