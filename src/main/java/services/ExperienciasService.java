package services;

import com.google.gson.Gson;

import DAO.ExperienciasDAO;
import error.GeneralException;
import model.Experiencias;
import response.StandardResponse;
import response.StatusResponse;
import spark.Request;
import spark.Response;

public class ExperienciasService {

	private ExperienciasDAO experienciasDao = new ExperienciasDAO();

	public void addExperiencias(Experiencias emp) {
		System.out.print(emp);
	}

	public String addExperiencias(Request request, Response response) {
		// Employee employee = new Employee(emp.getId(), emp.getFirstName(),
		// emp.getLastName(), emp.getEmail());


		Experiencias experiencias = new Gson().fromJson(request.body(), Experiencias.class);
		boolean status = experienciasDao.insert(experiencias);
		if (status == true) {
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "Adicionado com sucesso"));
		} else {
			return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
					new Gson().toJson("Erro ao inserir a experiencia na tabela de dados")));
		}
	}
	
	public String getExperiencias(Request request, Response response) {
		response.type("application/json");
		return new Gson()
				.toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(experienciasDao.getExperiencias())));

	}
	
	public String getExperiencia(Request request, Response response) {
	
		response.type("application/json");
		return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
				new Gson().toJsonTree(experienciasDao.get(Integer.parseInt(request.params(":id"))))));

	}
	
	public String editExperiencias(Request request, Response response) throws GeneralException {
		response.type("application/json");

		Experiencias forEdit = new Gson().fromJson(request.body(), Experiencias.class);

		try {

			Experiencias toEdit = experienciasDao.get(Integer.parseInt((request.params(":id"))));

			if (toEdit == null)
				return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
						new Gson().toJson("Certificacao n�o encontrada , preencha todos os campos!")));

			if (forEdit.getTitulo() != null) {
				toEdit.setTitulo(forEdit.getTitulo());
			}
			if (forEdit.getTipo_emprego() != null) {
				toEdit.setTipo_emprego(forEdit.getTipo_emprego());
			}
			if (forEdit.getNome_empresa() != null) {
				toEdit.setNome_empresa(forEdit.getNome_empresa());
			}
			if (forEdit.getLocalidade() != null) {
				toEdit.setLocalidade(forEdit.getLocalidade());
			}
			if (forEdit.getData_inicio() != null) {
				toEdit.setData_inicio(forEdit.getData_inicio());
			}
			if (forEdit.getData_fim() != null) {
				toEdit.setData_fim(forEdit.getData_fim());
			}
			if (forEdit.getSetor() != null) {
				toEdit.setSetor(forEdit.getSetor());
			}
			if (forEdit.getDescricao() != null) {
				toEdit.setDescricao(forEdit.getDescricao());
			}

			if (experienciasDao.atualizarExperiencias(toEdit) == true) {
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
	
	public String deleteExperiencias(Request request, Response response) {

		response.type("application/json");


		boolean delete = experienciasDao.delete(Integer.parseInt(request.params(":id")));
		if (delete) {
			return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "experiencia deletada com sucesso"));
		} else {

			return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
					new Gson().toJson("Erro ao deletar , experiencia n�o existe na base!")));
		}

	}

	public boolean experienciasExist(String id) {
		// return experienciasMap.containsKey(id);
		return false;
	}
	
}
