package com.client.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.client.models.Client;

public interface ClientRepository extends MongoRepository<Client, String> {
	@Query("{'idNumber' : ?0")
	List<Client> findByIdNumber(String idNumber);

	@Query("{'mobileNumber' : ?0")
	List<Client> findByMobileNumber(String mobileNumber);

	@Query("{'firstName' : ?0")
	List<Client> findByFirstName(String firstName);

}