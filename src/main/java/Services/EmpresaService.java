package services;

import com.google.gson.Gson;

import dao.EmpresaDAO;
import model.Empresa;
import response.StandardResponse;
import response.StatusResponse;
import spark.Request;
import spark.Response;


public class EmpresaService {
	private EmpresaDAO empresaDao = new EmpresaDAO();

	public void addEmpresa(Empresa emp) {
		System.out.print(emp);
	}
	
	public String addEmpresaBanco(Request request, Response response) {
		// Employee employee = new Employee(emp.getId(), emp.getFirstName(),
		// emp.getLastName(), emp.getEmail());
		response.type("application/json");
		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Methods", "POST");
		response.header("Access-Control-Allow-Headers",
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");

		Empresa empresa = new Gson().fromJson(request.body(), Empresa.class);
		boolean status = empresaDao.insert(empresa);
		if (status == true) {
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "Adicionado com sucesso"));
		} else {
			return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
					new Gson().toJson("Erro ao inserir o  usuario na tabela de dados")));
		}
	}
	
	public String getEmpresa(Request request, Response response) {
		response.type("application/json");
		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Methods", "GET");
		response.header("Access-Control-Allow-Headers",
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
		return new Gson()
				.toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(empresaDao.getEmpresa())));

	}

	public String getEmployee(Request request, Response response) {
		response.type("application/json");
		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Methods", "GET");
		response.header("Access-Control-Allow-Headers",
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");

		return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
				new Gson().toJsonTree(empresaDao.get(request.params(":id")))));

	}
	
	public String editEmployee(Request request, Response response)  {
		response.type("application/json");
		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Methods", "GET");
		response.header("Access-Control-Allow-Headers",
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");

		Empresa forEdit = new Gson().fromJson(request.body(), Empresa.class);

		try {
			if (forEdit.getId() == null)
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
						new Gson().toJson("Usuario n�o encontrado , preencha todos os campos!")));

			Empresa toEdit = empresaDao.get(forEdit.getId());

			if (toEdit == null)
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
						new Gson().toJson("Usuario n�o encontrado , preencha todos os campos!")));

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
	
	public String deleteEmployee(Request request, Response response) {
		try {
			response.type("application/json");
			response.header("Access-Control-Allow-Origin", "*");
			response.header("Access-Control-Allow-Methods", "GET");
			response.header("Access-Control-Allow-Headers",
					"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
			
			empresaDao.delete(request.params(":id"));
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "usuario deletado com sucesso"));
		} catch (Exception ex) {
			return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
					new Gson().toJson("Erro ao deletar , usuario n�o existe na base!")));
		}

	}
}
