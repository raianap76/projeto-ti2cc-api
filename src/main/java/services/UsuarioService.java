package services;

import com.google.gson.Gson;

import DAO.UsuarioDAO;
import error.GeneralException;
import model.Usuario;
import response.StandardResponse;
import response.StatusResponse;
import spark.Request;
import spark.Response;

public class UsuarioService {
	private UsuarioDAO usuarioDao = new UsuarioDAO();

	public void addEmployee(Usuario emp) {
		System.out.print(emp);
	}

	public String addUsuario(Request request, Response response) throws Exception {
		// Employee employee = new Employee(emp.getId(), emp.getFirstName(),
		// emp.getLastName(), emp.getEmail());
		response.type("application/json");
		Usuario usuario = new Gson().fromJson(request.body(), Usuario.class);
		boolean status = usuarioDao.insert(usuario);
		if (status == true) {
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "Adicionado com sucesso"));
		} else {
			return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
					new Gson().toJson("Erro ao inserir o  usuario na tabela de dados")));
		}
	}

	public String getUsuarios(Request request, Response response) {
		response.type("application/json");
		return new Gson()
				.toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(usuarioDao.getUsuario())));

	}

	public String getUsuario(Request request, Response response) {

		response.type("application/json");
		return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
				new Gson().toJsonTree(usuarioDao.get(Integer.parseInt(request.params(":id"))))));

	}

	public String editUsuario(Request request, Response response) throws GeneralException {
		response.type("application/json");

		Usuario forEdit = new Gson().fromJson(request.body(), Usuario.class);

		try {

			Usuario toEdit = usuarioDao.get(Integer.parseInt((request.params(":id"))));

			if (toEdit == null)
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
						new Gson().toJson("Usuario n�o encontrado , preencha todos os campos!")));

			if (forEdit.getTipoUsuario() != null) {
				toEdit.setTipoUsuario(forEdit.getTipoUsuario());
			}
			if (forEdit.getSenha() != null) {
				toEdit.setSenha(forEdit.getSenha());
			}

			if (usuarioDao.atualizarUsuario(toEdit) == true) {
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

	public String deleteUsuario(Request request, Response response) {

		response.type("application/json");
		System.out.println(request.params(":id"));
		System.out.println("eentrou no delete");
		boolean delete = usuarioDao.delete(Integer.parseInt((request.params(":id"))));
		if (delete) {
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "usuario deletado com sucesso"));
		} else {

			return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
					new Gson().toJson("Erro ao deletar , usuario n�o existe na base!")));
		}

	}

	public boolean employeeExist(String id) {
		// return employeeMap.containsKey(id);
		return false;
	}

}