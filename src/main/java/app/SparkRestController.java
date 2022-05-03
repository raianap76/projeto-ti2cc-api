package app;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFiles;

import com.google.gson.Gson;

import Services.EmployeeService;
import Services.EmployeeServiceImpl;
import model.Employee;
import response.StandardResponse;
import response.StatusResponse;

public class SparkRestController {
	public static void main(String[] args) {
		final EmployeeService employeeService = new EmployeeServiceImpl();
		port(6787);
		staticFiles.location("/public");
		post("/employees", (request, response) -> {
			response.type("application/json");
			response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "POST");
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");

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
			response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
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