package br.com.brigaderia.rest;

import java.sql.SQLException;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.service.EstoqueService;

@Path("estoque")

public class EstoqueRest extends UtilRest{

	static final String ERROINESPERADO = "Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.";
	
	@GET
	@Path("/gerarRelatorio/{tipoItem}/{ativo}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	
	public Response gerarRelatorio (@PathParam("tipoItem") String tipoItem, 
			                       @PathParam("ativo") String ativo) throws BrigaderiaException{
		
		try {
			
			EstoqueService service = new EstoqueService();
			return buildResponse(service.gerarRelatorio(tipoItem, ativo));
		}catch(SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao gerar relatório de estoque. Entre em contato com o administrador do sistema.");
		}
	}
}