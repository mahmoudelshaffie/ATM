package org.atm.withdraw.rest;

import static org.junit.Assert.assertEquals;

import org.atm.AtmApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {AtmApplication.class}, webEnvironment = WebEnvironment.DEFINED_PORT)
public class WithdrawRESTIntegerationTest {

	private static final String BASE_URL = "http://localhost:8080/";
	
	@Test
	public void testWithdrawAvailableAmount() {
		Long accountNo = 123453L;
		Short pin = 14;
		Long amount = 1886L;
		RequestSpecification request = RestAssured.given();
		request.header("X-ACC", accountNo);
		request.header("X-PIN", pin);
		request.queryParam("amount", amount);
		request.accept(ContentType.JSON);
		request.baseUri(BASE_URL + "withdraw");
		Response response = request.put();
		String body = response.getBody().asString();
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Long amountOf1000 = response.getBody().jsonPath().getLong("notes.1000");
		assertEquals(1, amountOf1000, 0);
		Long amountOf500 = response.getBody().jsonPath().getLong("notes.500");
		assertEquals(1, amountOf500, 0);
		Long amountOf200 = response.getBody().jsonPath().getLong("notes.200");
		assertEquals(1, amountOf200, 0);
		Long amountOf100 = response.getBody().jsonPath().getLong("notes.100");
		assertEquals(1, amountOf100, 0);
		Long amountOf50 = response.getBody().jsonPath().getLong("notes.50");
		assertEquals(1, amountOf50, 0);
		Long amountOf20 = response.getBody().jsonPath().getLong("coins.20");
		assertEquals(1, amountOf20, 0);
		Long amountOf10 = response.getBody().jsonPath().getLong("coins.10");
		assertEquals(1, amountOf10, 0);
		Long amountOf5 = response.getBody().jsonPath().getLong("coins.5");
		assertEquals(1, amountOf5, 0);
		Long amountOf1 = response.getBody().jsonPath().getLong("coins.1");
		assertEquals(1, amountOf1, 0);
	}
}
