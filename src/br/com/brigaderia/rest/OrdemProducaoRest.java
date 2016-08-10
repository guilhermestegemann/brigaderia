package br.com.brigaderia.rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.objetos.OrdemProducao;
import br.com.brigaderia.service.OrdemProducaoService;

@Path("ordemProducao")

public class OrdemProducaoRest extends UtilRest{

	static final String ERROINESPERADO = "Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.";
	
	@POST
	@Path ("/adicionar")
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response adicionar(String param) {
		
		try {
			OrdemProducao ordemProducao = new ObjectMapper().readValue(param, OrdemProducao.class);
			OrdemProducaoService service = new OrdemProducaoService();
			service.adicionarOrdemProducao(ordemProducao);
			return buildResponse("Ordem de Produção inserida com sucesso.");
		}catch (BrigaderiaException e){
			return buildErrorResponse(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return buildErrorResponse(ERROINESPERADO);
		}
	}
	
	@GET
	@Path("/listarProdutos")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response listarProdutos(){
		
		try {
			OrdemProducaoService service = new OrdemProducaoService();
			return this.buildResponse(service.buscarProdutos());
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar produtos. Entre em contato com o administrador do sistema.");
		}
	}
	
	@GET
	@Path("/buscarPedidosImportacao")
	@Produces({MediaType.APPLICATION_JSON})
	public Response buscarPedidosImportacao(){
		try{
			OrdemProducaoService service = new OrdemProducaoService();
			return buildResponse(service.buscarPedidosImportacao());
		}catch(SQLException e){
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar pedidos. Entre em contato com o administrador do sistema.");
		}
	}
/*	
	@GET
	@Path("/buscarPerda/{dataInicio}/{dataFim}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	
	public Response buscarPerdas (@PathParam("dataInicio") String dataInicio, @PathParam("dataFim") String dataFim) throws BrigaderiaException{
		
		try {
			
			PerdaService service = new PerdaService();
			return buildResponse(service.buscarPerdas(dataInicio, dataFim));
		}catch(SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar pedidos de compra. Entre em contato com o administrador do sistema.");
		}
	}
		
	@GET
	@Path("/buscarPerdaPeloNumero/{numero}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscarPerdaPeloNumero(@PathParam("numero")int numero) throws BrigaderiaException{
		try {
			PerdaService service = new PerdaService(); 
			return buildResponse(service.buscarPerdaPeloNumero(numero));
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar perdas. Entre em contato com o administrador do sistema.");
		}
	}
	
	@DELETE
	@Path("/deletar/{numero}")
	@Consumes("application/*")
	
	public Response deletar (@PathParam("numero") int numero) throws BrigaderiaException{
		try {
			PerdaService service = new PerdaService();
			service.deletarPerda(numero);
			return buildResponse("Perda deletada com sucesso!");
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse(ERROINESPERADO);
		}
	}
	*/
}