package app;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFiles;

import services.UsuarioService;


public class SparkRestController {
	public static void main(String[] args) {

		final UsuarioService usuarioService = new UsuarioService();
		port(6787);
		staticFiles.location("/public");
		post("/usuario", (request, response) -> {
			return usuarioService.addUsuario(request, response);
		});

		get("/usuario", (request, response) -> {
			return usuarioService.getUsuarios(request, response);
		});

		get("/usuario/:id", (request, response) -> {
            return usuarioService.getUsuario(request, response);
		});

		put("/usuario/:id", (request, response) -> {
            return usuarioService.editUsuario(request, response);
		});

		delete("/usuario/:id", (request, response) -> {
			return usuarioService.deleteUsuario(request, response);
		});

	}
}