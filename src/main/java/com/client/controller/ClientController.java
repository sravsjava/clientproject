package com.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.client.exceptions.DuplicateIDException;
import com.client.exceptions.DuplicateMobileNumberException;
import com.client.exceptions.ValidSouthAfricanIdException;
import com.client.models.Client;
import com.client.services.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	@Autowired
	private ClientService clientService;

	/**
	 * This method is used to retrieve the client details using idNumber
	 * 
	 * @param idNumber
	 * @return
	 */
	@GetMapping("/idNumbers/{idNumber}")
	public ResponseEntity<List<Client>> getClientWithIdNumber(@PathVariable String idNumber) {
		List<Client> clients = clientService.searchClient(null, idNumber, null);
		return ResponseEntity.ok(clients);
	}

	/**
	 * This method is used to retrieve the client details using mobileNumber
	 * 
	 * @param idNumber
	 * @return
	 */
	@GetMapping("/mobileNumbers/{mobileNumber}")
	public ResponseEntity<List<Client>> getClientWithMobileNumber(@PathVariable String mobileNumber) {
		List<Client> clients = clientService.searchClient(null, null, mobileNumber);
		return ResponseEntity.ok(clients);
	}

	/**
	 * This method is used to retrieve the client details using firstName
	 * 
	 * @param idNumber
	 * @return
	 */
	@GetMapping("/names/{firstName}")
	public ResponseEntity<List<Client>> getClientWithFirstName(@PathVariable String firstName) {
		List<Client> clients = clientService.searchClient(firstName, null, null);
		return ResponseEntity.ok(clients);
	}

	/**
	 * This method is used to save the client details
	 * 
	 * @param clientDto
	 * @return
	 */
	@PostMapping("/client")
	public ResponseEntity<String> saveClient(@RequestBody Client clientDto) {
		try {
			clientService.saveOrUpdateClient(clientDto);
			return ResponseEntity.ok("Client Details saved successfully");
		} catch (ValidSouthAfricanIdException e1) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e1.getMessage());
		} catch (DuplicateIDException e2) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e2.getMessage());
		} catch (DuplicateMobileNumberException e3) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e3.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during saving client details");
		}
	}

	/**
	 * This method is used to update the client details
	 * 
	 * @param clientDto
	 * @return
	 */
	@PatchMapping("/{clientId}/client")
	public Object updateClient(@PathVariable String clientId, @RequestBody Client clientDto) {
		try {
			clientService.saveOrUpdateClient(clientDto);
			return clientService.getClientById(clientId).map(savedClient -> {

				savedClient.setFirstName(clientDto.getFirstName());
				savedClient.setLastName(clientDto.getLastName());
				savedClient.setIdNumber(clientDto.getIdNumber());
				savedClient.setMobileNumber(clientDto.getMobileNumber());
				savedClient.setPhysicalAddress(clientDto.getPhysicalAddress());

				Client updatedClient = clientService.saveOrUpdateClient(savedClient);
				return new ResponseEntity<>(updatedClient, HttpStatus.OK);

			}).orElseGet(() -> ResponseEntity.notFound().build());
		} catch (ValidSouthAfricanIdException e1) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e1.getMessage());
		} catch (DuplicateIDException e2) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e2.getMessage());
		} catch (DuplicateMobileNumberException e3) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e3.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during updating client details");
		}
	}
}