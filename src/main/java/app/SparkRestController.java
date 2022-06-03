package app;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFiles;

import response.CorsFilter;
import services.VagasService;
import services.EmpresaService;
import services.ExperienciasService;
import services.CertificacoesService;
import services.SistemaInteligenteService;
import services.UsuarioService;


public class SparkRestController {
	public static void main(String[] args) {
    
		final UsuarioService usuarioService = new UsuarioService();
		final SistemaInteligenteService sistemaInteligenteService = new SistemaInteligenteService();
		final CertificacoesService certificacoesService = new CertificacoesService();
		final EmpresaService empresaService = new EmpresaService();
		final VagasService vagasService = new VagasService();
		final ExperienciasService experienciasService = new ExperienciasService();
		port(6788);
		staticFiles.location("/public");
		CorsFilter.enableCORS();
	    
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
		get("/sistemaInteligente", (request, response) -> {
			return sistemaInteligenteService.treinaModelo();
		});
		
		post("/certificacoes", (request, response) -> {
			return certificacoesService.addCertificacoes(request, response);
		});

		get("/certificacoes", (request, response) -> {
			return certificacoesService.getCertificacao(request, response);
		});

		get("/certificacoes/:id", (request, response) -> {
            return certificacoesService.getCertificacoes(request, response);
		});

		put("/certificacoes/:id", (request, response) -> {
            return certificacoesService.editCertificacoes(request, response);
		});

		delete("/certificacoes/:id", (request, response) -> {
			return certificacoesService.deleteCertificacoes(request, response);
		});
		
		post("/empresa", (request, response) -> {
			return empresaService.addEmpresaBanco(request, response);
		});

		get("/empresa", (request, response) -> {
			return empresaService.getEmpresa(request, response);
		});

		get("/empresa/:id", (request, response) -> {
            return empresaService.getEmpresas(request, response);
		});

		put("/empresa/:id", (request, response) -> {
            return empresaService.editEmpresa(request, response);
		});

		delete("/empresa/:id", (request, response) -> {
			return empresaService.deleteEmpresa(request, response);
		});
		
		post("/vagas", (request, response) -> {
			return vagasService.addVagas(request, response);
		});

		get("/vagas", (request, response) -> {
			return vagasService.getVaga(request, response);
		});

		get("/vagas/:id", (request, response) -> {
            return vagasService.getVagas(request, response);
		});

		put("/vagas/:id", (request, response) -> {
            return vagasService.editVagas(request, response);
		});

		delete("/vagas/:id", (request, response) -> {
			return vagasService.deleteVagas(request, response);
		});
		
		post("/experiencias", (request, response) -> {
			return experienciasService.addExperiencias(request, response);
		});

		get("/experiencias", (request, response) -> {
			return experienciasService.getExperiencia(request, response);
		});

		get("/experiencias/:id", (request, response) -> {
            return experienciasService.getExperiencias(request, response);
		});

		put("/experiencias/:id", (request, response) -> {
            return experienciasService.editExperiencias(request, response);
		});

		delete("/experiencias/:id", (request, response) -> {
			return experienciasService.deleteExperiencias(request, response);
		});


	}
	
	
}