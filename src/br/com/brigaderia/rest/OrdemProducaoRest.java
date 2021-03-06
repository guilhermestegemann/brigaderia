package br.com.brigaderia.rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
	@Path("/buscarOrdemProducao/{dataInicio}/{dataFim}/{status}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	
	public Response buscarOrdemProducao (@PathParam("dataInicio") String dataInicio, 
										 @PathParam("dataFim") String dataFim,
										 @PathParam("status") String status) throws BrigaderiaException{
		
		try {
			
			OrdemProducaoService service = new OrdemProducaoService();
			return buildResponse(service.buscarOrdens(dataInicio, dataFim, status));
		}catch(SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar ordens de produção. Entre em contato com o administrador do sistema.");
		}
	}
		
	@GET
	@Path("/buscarOrdemPeloNumero/{numero}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscarOrdemPeloNumero(@PathParam("numero")int numero) throws BrigaderiaException{
		try {
			OrdemProducaoService service = new OrdemProducaoService(); 
			return buildResponse(service.buscarOrdemPeloNumero(numero));
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar ordens. Entre em contato com o administrador do sistema.");
		}
	}
	
	@PUT
	@Path("/editarOrdemProducao")
	@Consumes("application/*")
	public Response editarOrdemProducao (String ordemEditada) throws BrigaderiaException {
		try {
			//OrdemProducao ordem = new ObjectMapper().readValue(ordemEditada, OrdemProducao.class);
			OrdemProducao ordem = new ObjectMapper().readValue(ordemEditada, OrdemProducao.class);
			OrdemProducaoService service = new OrdemProducaoService();
			service.editarOrdemProducao(ordem);
			return buildResponse("Ordem de Produção editada com sucesso");
		}catch(SQLException e){
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao editar a ordem de produção. Entre em contato com o administrador do sistema.");
		}catch(Exception e){
			e.printStackTrace();
			return buildErrorResponse(ERROINESPERADO);
		}
	}
	
	@PUT
	@Path("/iniciarProducao/{numero}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response iniciarProducao(@PathParam("numero")int numero) throws BrigaderiaException {
		try {
			OrdemProducaoService service = new OrdemProducaoService();
			return buildResponse(service.iniciarProducao(numero));
		}catch (BrigaderiaException e){
			return buildErrorResponse(e.getMessage());
		}catch(SQLException e){
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao produzir a ordem de produção. Entre em contato com o administrador do sistema.");
		}
	}
	
	@PUT
	@Path("/cancelarInicio/{numero}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response cancelarProducao(@PathParam("numero")int numero) throws BrigaderiaException {
		try {
			OrdemProducaoService service = new OrdemProducaoService();
			service.cancelarProducao(numero);
			return buildResponse("Inicio de produção cancelada com sucesso");
		}catch (BrigaderiaException e){
			return buildErrorResponse(e.getMessage());
		}catch(SQLException e){
			e.printStackTrace();
			return buildErrorResponse(ERROINESPERADO);
		}
	}
	
	@PUT
	@Path("/finalizarProducao/{numero}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response finalizarProducao(@PathParam("numero")int numero) throws BrigaderiaException {
		try {
			OrdemProducaoService service = new OrdemProducaoService();
			service.finalizarProducao(numero);
			return buildResponse("Produção finalizada com sucesso");
		}catch (BrigaderiaException e){
			return buildErrorResponse(e.getMessage());
		}catch(SQLException e){
			e.printStackTrace();
			return buildErrorResponse(ERROINESPERADO);
		}
	}
	
	@PUT
	@Path("/cancelarFinalizada/{numero}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response cancelarFinalizada(@PathParam("numero")int numero) throws BrigaderiaException {
		try {
			OrdemProducaoService service = new OrdemProducaoService();
			service.cancelarFinalizada(numero);
			return buildResponse("Produção finalizada cancelada com sucesso. Essa produção está em produção novamente.");
		}catch(BrigaderiaException e) {
			e.printStackTrace();
			return buildErrorResponse(e.getMessage());
		}catch(SQLException e){
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao cancelar a ordem de produção. Entre em contato com o administrador do sistema.");
		}
	}
	
	@DELETE
	@Path("/deletarOrdem/{numero}")
	@Consumes("application/*")
	
	public Response deletar (@PathParam("numero") int numero) throws BrigaderiaException{
		try {
			OrdemProducaoService service = new OrdemProducaoService();
			service.deletarOrdem(numero);
			return buildResponse("Ordem de produção deletada com sucesso!");
		}catch(BrigaderiaException e){
			e.printStackTrace();
			return buildErrorResponse(e.getMessage());
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse(ERROINESPERADO);
		}
	}
	
}