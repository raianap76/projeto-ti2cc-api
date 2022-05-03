package Services;

import com.google.gson.Gson;

import DAO.EmployeeDAO;
import Erros.EmployeeException;
import model.Employee;
import response.StandardResponse;
import response.StatusResponse;
import spark.Request;
import spark.Response;

public class EmployeeServiceImpl implements EmployeeService {
	private EmployeeDAO employeeDao = new EmployeeDAO();

	@Override
	public void addEmployee(Employee emp) {
		System.out.print(emp);
	}

	@Override
	public String addEmployeeBanco(Request request, Response response) {
		// Employee employee = new Employee(emp.getId(), emp.getFirstName(),
		// emp.getLastName(), emp.getEmail());
		response.type("application/json");
		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Methods", "POST");
		response.header("Access-Control-Allow-Headers",
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");

		Employee employee = new Gson().fromJson(request.body(), Employee.class);
		boolean status = employeeDao.insert(employee);
		if (status == true) {
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "Adicionado com sucesso"));
		} else {
			return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
					new Gson().toJson("Erro ao inserir o  usuario na tabela de dados")));
		}
	}

	@Override
	public String getEmployees(Request request, Response response) {
		response.type("application/json");
		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Methods", "GET");
		response.header("Access-Control-Allow-Headers",
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
		return new Gson()
				.toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(employeeDao.getEmployee())));

	}

	@Override
	public String getEmployee(Request request, Response response) {
		response.type("application/json");
		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Methods", "GET");
		response.header("Access-Control-Allow-Headers",
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");

		return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
				new Gson().toJsonTree(employeeDao.get(request.params(":id")))));

	}

	@Override
	public String editEmployee(Request request, Response response) throws EmployeeException {
		response.type("application/json");
		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Methods", "GET");
		response.header("Access-Control-Allow-Headers",
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");

		Employee forEdit = new Gson().fromJson(request.body(), Employee.class);

		try {
			if (forEdit.getId() == null)
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
						new Gson().toJson("Usuario não encontrado , preencha todos os campos!")));

			Employee toEdit = employeeDao.get(forEdit.getId());

			if (toEdit == null)
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
						new Gson().toJson("Usuario não encontrado , preencha todos os campos!")));

			if (forEdit.getEmail() != null) {
				toEdit.setEmail(forEdit.getEmail());
			}
			if (forEdit.getFirstName() != null) {
				toEdit.setFirstName(forEdit.getFirstName());
			}
			if (forEdit.getLastName() != null) {
				toEdit.setLastName(forEdit.getLastName());
			}

			if (employeeDao.atualizarUsuario(toEdit) == true) {
				return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(forEdit)));
			} else {
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
						new Gson().toJson("Erro ao atualizar, preencha todos os campos!")));
			}

		} catch (Exception ex) {
			return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
					new Gson().toJson("Erro ao  atualizar , preencha todos os campos!")));
		}
	}

	@Override
	public String deleteEmployee(Request request, Response response) {
		try {
			response.type("application/json");
			response.header("Access-Control-Allow-Origin", "*");
			response.header("Access-Control-Allow-Methods", "GET");
			response.header("Access-Control-Allow-Headers",
					"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
			
			employeeDao.delete(request.params(":id"));
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "usuario deletado com sucesso"));
		} catch (Exception ex) {
			return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
					new Gson().toJson("Erro ao deletar , usuario não existe na base!")));
		}

	}

	@Override
	public boolean employeeExist(String id) {
		// return employeeMap.containsKey(id);
		return false;
	}

}