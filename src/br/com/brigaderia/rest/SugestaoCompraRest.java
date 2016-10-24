package br.com.brigaderia.rest;

import java.sql.SQLException;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.service.SugestaoCompraService;

@Path("sugestaoCompra")

public class SugestaoCompraRest extends UtilRest{

	static final String ERROINESPERADO = "Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.";
	


	
	@GET
	@Path("/listarClientes")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response listarClientes(){
		try {
			SugestaoCompraService service = new SugestaoCompraService();
			return this.buildResponse(service.listarClientes());
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar clientes. Entre em contato com o administrador do sistema.");
		}
	}
	
	@GET
	@Path("/gerarSugestao/{cliente}/{dataInicio}/{dataFim}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	
	public Response gerarSugestao (@PathParam("cliente") int cliente, 
			                       @PathParam("dataInicio") String dataInicio,
			                       @PathParam("dataFim") String dataFim) throws BrigaderiaException{
		
		try {
			
			SugestaoCompraService service = new SugestaoCompraService();
			return buildResponse(service.gerarSugestao(cliente, dataInicio, dataFim));
		}catch(SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao gerar sugestão de compra. Entre em contato com o administrador do sistema.");
		}
	}
}