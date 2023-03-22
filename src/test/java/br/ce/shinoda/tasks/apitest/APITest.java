package br.ce.shinoda.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
		
	}
	
	@Test
	public void deveRetrnarTarefas() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
		 	.statusCode(200)
		;
	}
	
	@Test
     public void deveAdcionarTarefaComSucesso () {
		RestAssured.given()
		 	.body("{\"task\": \"Teste via API\", \"dueDate\": \"2023-03-22\"}")
		    .contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
		    .log().all()
		 	.statusCode(201)
		;
	
	}
	
	@Test
    public void nãoDeveAdcionarTarefaInvalida () {
		RestAssured.given()
		 	.body("{\"task\": \"Teste via API\", \"dueDate\": \"2010-03-22\"}")
		    .contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
		    .log().all()
		 	.statusCode(400)
		 	.body("message",CoreMatchers.is("Due date must not be in past"))
		;
	
	}
	
}

