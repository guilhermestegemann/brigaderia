package br.com.brigaderia.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.service.ProdutosService;


@Path("produtos")

public class ProdutosRest extends UtilRest{
	
	static final String ERROINESPERADO = "Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.";
	
	@GET
	@Path("/listarIngredientes")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response listarIngredientes() throws BrigaderiaException{
		
		try {
			ProdutosService service = new ProdutosService();
			return this.buildResponse(service.buscarIngredientes());
		}catch (BrigaderiaException e) {
			return buildErrorResponse(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(ERROINESPERADO);
		}
	}
	
}
