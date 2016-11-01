package br.com.brigaderia.rest;

import java.sql.SQLException;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.service.VendaService;

@Path("vendas")

public class VendasRest extends UtilRest{

	static final String ERROINESPERADO = "Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.";
	


	
	@GET
	@Path("/listarClientes")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response listarClientes(){
		try {
			VendaService service = new VendaService();
			return this.buildResponse(service.listarClientes());
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar clientes. Entre em contato com o administrador do sistema.");
		}
	}
	
	@GET
	@Path("/listarProdutos")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response listarProdutos(){
		
		try {
			VendaService service = new VendaService();
			return this.buildResponse(service.buscarProdutos());
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar produtos. Entre em contato com o administrador do sistema.");
		}
	}
	
	@GET
	@Path("/gerarPorProduto/{dataInicio}/{dataFim}/{produto}/{numRegistro}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	
	public Response gerarPorProduto (@PathParam("dataInicio") String dataInicio, 
			                       @PathParam("dataFim") String dataFim,
			                       @PathParam("produto") int produto,
			                       @PathParam("numRegistro") int numRegistro) throws BrigaderiaException{
		
		try {
			
			VendaService service = new VendaService();
			return buildResponse(service.gerarPorProduto(dataInicio, dataFim, produto, numRegistro));
		}catch(SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao gerar sugestão de compra. Entre em contato com o administrador do sistema.");
		}
	}
}