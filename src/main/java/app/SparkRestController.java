package app;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFiles;

import services.EmployeeService;


public class SparkRestController {
	public static void main(String[] args) {

		final EmployeeService employeeService = new EmployeeService();
		port(6787);
		staticFiles.location("/public");
		post("/employees", (request, response) -> {
			return employeeService.addEmployeeBanco(request, response);
		});

		get("/employees", (request, response) -> {
			return employeeService.getEmployees(request, response);
		});

		get("/employees/:id", (request, response) -> {
            return employeeService.getEmployee(request, response);
		});

		put("/employees/:id", (request, response) -> {
            return employeeService.editEmployee(request, response);
		});

		delete("/employees/:id", (request, response) -> {
			return employeeService.deleteEmployee(request, response);
		});

	}
}