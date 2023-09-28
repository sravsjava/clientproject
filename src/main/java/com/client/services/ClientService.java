package com.client.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.client.exceptions.DuplicateIDException;
import com.client.exceptions.DuplicateMobileNumberException;
import com.client.exceptions.ValidSouthAfricanIdException;
import com.client.models.Client;
import com.client.models.ClientDTO;
import com.client.repositories.ClientRepository;

@Service
public class ClientService {

	private final short idLength = 13;
	@Autowired
	private ClientRepository clientRepository;

	/**
	 * This method is used to save or update the client
	 * 
	 * @param firstName
	 * @param lastName
	 * @param mobileNumber
	 * @param idNumber
	 * @param physicalAddress
	 */
	public Client saveOrUpdateClient(Client clientDto) {
		// Check if the idNumber is valid
		if (!clientDto.getMobileNumber().isEmpty() && clientDto.getMobileNumber().length() != idLength) {
			throw new ValidSouthAfricanIdException("Please enter valid South African ID");
		}

		// Check if IdNumber already exists
		List<Client> clientList1 = clientRepository.findByIdNumber(clientDto.getIdNumber());
		if (!clientList1.isEmpty()) {
			throw new DuplicateIDException("An entry with provided ID=" + clientDto.getIdNumber() + " already exists");
		}
		// Check if IdNumber already exists
		List<Client> clientList2 = clientRepository.findByMobileNumber(clientDto.getMobileNumber());
		if (!clientList2.isEmpty()) {
			throw new DuplicateMobileNumberException(
					"An entry with provided mobileNumber=" + clientDto.getMobileNumber() + " already exists");
		}

		// Create a new Client and save to the database
		Client client = new Client();
		client.setFirstName(clientDto.getFirstName());
		client.setLastName(clientDto.getLastName());
		client.setIdNumber(clientDto.getLastName());
		client.setMobileNumber(clientDto.getMobileNumber());
		client.setPhysicalAddress(clientDto.getPhysicalAddress());

		return clientRepository.save(client);

	}

	/**
	 * This method is used to save or update the client
	 * 
	 * @param firstName
	 * @param lastName
	 * @param mobileNumber
	 * @param idNumber
	 * @param physicalAddress
	 */
	public void saveClient(String firstName, String lastName, String mobileNumber, String idNumber,
			String physicalAddress) {
		// Check if the idNumber is valid
		if (!mobileNumber.isEmpty() && mobileNumber.length() != idLength) {
			throw new ValidSouthAfricanIdException("Please enter valid South African ID");
		}

		// Check if IdNumber already exists
		List<Client> clientList1 = clientRepository.findByIdNumber(idNumber);
		if (!clientList1.isEmpty()) {
			throw new DuplicateIDException("An entry with provided ID=" + idNumber + " already exists");
		}
		// Check if IdNumber already exists
		List<Client> clientList2 = clientRepository.findByMobileNumber(mobileNumber);
		if (!clientList2.isEmpty()) {
			throw new DuplicateMobileNumberException(
					"An entry with provided mobileNumber=" + mobileNumber + " already exists");
		}

		// Create a new Client and save to the database
		Client client = new Client();
		client.setFirstName(firstName);
		client.setLastName(lastName);
		client.setIdNumber(idNumber);
		client.setMobileNumber(mobileNumber);
		client.setPhysicalAddress(physicalAddress);

		clientRepository.save(client);

	}

	/**
	 * This method is used to search the client with below params
	 * 
	 * @param firstName
	 * @param idNumber
	 * @param mobileNumber
	 * @return
	 */
	public List<Client> searchClient(String firstName, String idNumber, String mobileNumber) {

		// Query with firstName
		if (firstName != null) {
			List<Client> clientList1 = clientRepository.findByFirstName(firstName);
			return clientList1;
		}

		// Query with idNumber
		if (idNumber != null) {
			List<Client> clientList2 = clientRepository.findByIdNumber(idNumber);
			return clientList2;
		}

		// Query with mobileNumber
		if (mobileNumber != null) {
			List<Client> clientList3 = clientRepository.findByMobileNumber(mobileNumber);
			return clientList3;
		}

		return null;
	}
	
	/**
	 * This method is to return client details with id
	 * @param id
	 * @return
	 */
    public Optional<Client> getClientById(String id) {
        return clientRepository.findById(id);
    }
}