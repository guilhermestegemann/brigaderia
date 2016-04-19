package br.com.brigaderia.rest;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.service.FichaTecnicaService;

@Path("fichaTecnica")

public class FichaTecnicaRest extends UtilRest{
	
	static final String ERROINESPERADO = "Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.";
	
	@GET
	@Path("/listarIngredientes")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response listarIngredientes() {
		
		try {
			FichaTecnicaService service = new FichaTecnicaService();
			return this.buildResponse(service.buscarIngredientes());
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar ingredientes. Entre em contato com o administrador do sistema.");
		}
	}
	
	@GET
	@Path("/buscarFichaTecnicaPeloCodigoProduto/{codigoProduto}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscarFichaTecnicaPeloCodigoProduto(@PathParam("codigoProduto")int codigoProduto) {
		try {
			FichaTecnicaService service = new FichaTecnicaService(); 
			return buildResponse(service.buscarFichaTecnicaPeloCodigoProduto(codigoProduto));
		}catch (SQLException e) {
			return buildErrorResponse("Ocorreu um erro ao buscar ficha técnica. Entre em contato com o administrador do sistema.");
		}
	}
}
