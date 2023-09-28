package com.client.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import com.client.exceptions.ValidSouthAfricanIdException;
import com.client.models.Client;
import com.client.services.ClientService;

@ComponentScan(basePackages = "com.client")
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ClientController.class)
@WithMockUser
public class ClientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ClientService clientService;

	Client mockClient = new Client("1", "Sravanthi", "Gar", "0652285666", "0998877665543", "Sandton");
	Client clientWithInvalidID = new Client("1", "Sravanthi", "Gar", "0652285666", "09988776655", "Sandton");
	List<Client> mockClientList = new ArrayList<>();
	Optional<Client> mockClientOpList = Optional.ofNullable(mockClient) ;

	String exampleClientJson = "{\"firstName\":\"Sravanthi\",\"lastName\":\"Gar\",\"mobileNumber\":\"0652285666\",\"idNumber\":\"0998877665543\",\"physicalAddress\":\"Sandton\"}";

	@Test
	public void getClientWithIdNumber() throws Exception {
		System.out.println("getClientWithIdNumber::Start");
		mockClientList.add(mockClient);
		Mockito.when(clientService.searchClient(Mockito.isNull(), Mockito.anyString(), Mockito.isNull()))
				.thenReturn(mockClientList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/clients/idNumbers/1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("RESPONSE=" + result.getResponse().getContentAsString());
		String expected = "[{\"firstName\":\"Sravanthi\",\"lastName\":\"Gar\",\"mobileNumber\":\"0652285666\",\"idNumber\":\"0998877665543\",\"physicalAddress\":\"Sandton\"}]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void getClientWithMobileNumber() throws Exception {
		System.out.println("getClientWithMobileNumber::Start");
		mockClientList.add(mockClient);
		Mockito.when(clientService.searchClient(Mockito.isNull(), Mockito.isNull(), Mockito.anyString()))
				.thenReturn(mockClientList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/clients/mobileNumbers/0652286999")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("RESPONSE=" + result.getResponse().getContentAsString());
		String expected = "[{\"firstName\":\"Sravanthi\",\"lastName\":\"Gar\",\"mobileNumber\":\"0652285666\",\"idNumber\":\"0998877665543\",\"physicalAddress\":\"Sandton\"}]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void getClientWithFirstName() throws Exception {
		System.out.println("getClientWithFirstName::Start");
		mockClientList.add(mockClient);
		Mockito.when(clientService.searchClient(Mockito.anyString(), Mockito.isNull(), Mockito.isNull()))
				.thenReturn(mockClientList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/clients/names/sravanthi")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("RESPONSE=" + result.getResponse().getContentAsString());
		String expected = "[{\"firstName\":\"Sravanthi\",\"lastName\":\"Gar\",\"mobileNumber\":\"0652285666\",\"idNumber\":\"0998877665543\",\"physicalAddress\":\"Sandton\"}]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testSaveClient() {

		Mockito.when(clientService.saveOrUpdateClient(Mockito.any(Client.class))).thenReturn(mockClient);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/clients/client")
				.accept(MediaType.APPLICATION_JSON).content(exampleClientJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();

			assertEquals(HttpStatus.OK.value(), response.getStatus());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testUpdateClient() {
		Mockito.when(clientService.getClientById(Mockito.anyString())).thenReturn(mockClientOpList);
		Mockito.when(clientService.saveOrUpdateClient(Mockito.any(Client.class))).thenReturn(mockClient);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/clients/123/client")
				.accept(MediaType.APPLICATION_JSON).content(exampleClientJson).param("clientId","123").contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();

			assertEquals(HttpStatus.OK.value(), response.getStatus());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getClientInvalidIDTest() throws Exception {
		System.out.println("getClientInvalidIDTest::Start");
		mockClientList.add(clientWithInvalidID);
		Mockito.when(clientService.searchClient(Mockito.anyString(), Mockito.isNull(), Mockito.isNull()))
				.thenThrow(new ValidSouthAfricanIdException("Please enter valid South African ID"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/clients/names/sravanthi")
				.accept(MediaType.APPLICATION_JSON);

		assertThrows(NestedServletException.class, () -> mockMvc.perform(requestBuilder).andReturn());

	}

}