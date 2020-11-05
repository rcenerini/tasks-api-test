package be.sp.rcenerini.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://100.25.148.10:8080/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200)			
		;
		
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured.given()
			.body("{\r\n"
					+ "	\"task\": \"Teste via API\", \"dueDate\": \"2020-12-30\"\r\n"
					+ "}").contentType(ContentType.JSON)		
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
		
		;
	}

	@Test
	public void naodeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{\r\n"
					+ "	\"task\": \"Teste via API\", \"dueDate\": \"2020-12-30\"\r\n"
					+ "}").contentType(ContentType.JSON)		
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		
		;
	}
	
	@Test
	public void deveRemoverTarefaComSucesso() {
		//inserir
		Integer id = RestAssured.given()
			.body("{ \"task\": \"Tarefa para remoção\", \"dueDate\": \"2020-12-30\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
//			.log().all()
			.statusCode(201)
			.extract().path("id")
		;
		
		System.out.println(id);
		
		;
	}
}


