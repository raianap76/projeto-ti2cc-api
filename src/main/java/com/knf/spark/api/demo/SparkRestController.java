package com.knf.spark.api.demo;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.gson.Gson;

public class SparkRestController {
	public static void main(String[] args) {
		final EmployeeService employeeService = new EmployeeServiceImpl();
		port(6787);
		post("/employees", (request, response) -> {
			response.type("application/json");

			Employee employee = new Gson().fromJson(request.body(), Employee.class);
			boolean status = employeeService.addEmployeeBanco(employee);
			if (status == true) {
				return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "Adicionado com sucesso"));
			} else {
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
						new Gson().toJson("Erro ao inserir o  usuario na tabela de dados")));
			}

		});

		get("/employees", (request, response) -> {
			response.type("application/json");

			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
					new Gson().toJsonTree(employeeService.getEmployees())));
		});

		get("/employees/:id", (request, response) -> {
			response.type("application/json");

			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
					new Gson().toJsonTree(employeeService.getEmployee(request.params(":id")))));
		});

		put("/employees/:id", (request, response) -> {
			response.type("application/json");

			Employee toEdit = new Gson().fromJson(request.body(), Employee.class);
			boolean editedEmployee = employeeService.editEmployee(toEdit);

			if (editedEmployee == true) {
				return new Gson()
						.toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(editedEmployee)));
			} else {
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
						new Gson().toJson("Usuario não encontrado , preencha todos os campos!")));
			}
		});

		delete("/employees/:id", (request, response) -> {
			response.type("application/json");

			employeeService.deleteEmployee(request.params(":id"));
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "usuario deletado com sucesso"));
		});

	}
}