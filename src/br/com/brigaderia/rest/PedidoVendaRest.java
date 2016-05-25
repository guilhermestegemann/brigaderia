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
import br.com.brigaderia.objetos.PedidoVenda;
import br.com.brigaderia.service.PedidoVendaService;

@Path("pedidoVenda")

public class PedidoVendaRest extends UtilRest{

	static final String ERROINESPERADO = "Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.";
	
	@POST
	@Path ("/adicionar")
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response adicionar(String param) {
		
		try {
			PedidoVenda pedidoVenda = new ObjectMapper().readValue(param, PedidoVenda.class);
			PedidoVendaService service = new PedidoVendaService();
			service.adicionarPedido(pedidoVenda);
			return buildResponse("Pedido de Compra lançado com sucesso.");
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
			PedidoVendaService service = new PedidoVendaService();
			return this.buildResponse(service.buscarProdutos());
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar produtos. Entre em contato com o administrador do sistema.");
		}
	}
	
	@GET
	@Path("/listarClientes")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response listarClientes(){
		
		try {
			PedidoVendaService service = new PedidoVendaService();
			return this.buildResponse(service.buscarClientes());
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar clientes. Entre em contato com o administrador do sistema.");
		}
	}
	
	@GET
	@Path("/buscarPedidoVenda/{dataInicio}/{dataFim}/{faturado}/{cancelado}/{produzido}/{codCliente}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	
	public Response buscarPedidos (@PathParam("dataInicio") String dataInicio, 
			                       @PathParam("dataFim") String dataFim,
			                       @PathParam("faturado") String faturado,
			                       @PathParam("cancelado") String cancelado,
			                       @PathParam("produzido") String produzido,
			                       @PathParam("codCliente") int codCliente) throws BrigaderiaException{
		
		try {
			
			PedidoVendaService service = new PedidoVendaService();
			return buildResponse(service.buscarPedidoVenda(dataInicio, dataFim, faturado, cancelado, produzido, codCliente));
		}catch(SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar pedidos de compra. Entre em contato com o administrador do sistema.");
		}
	}

	@GET
	@Path("/buscarPedidoPeloNumero/{numero}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscarPedidoPeloNumero(@PathParam("numero")int numero) throws BrigaderiaException{
		try {
			PedidoVendaService service = new PedidoVendaService(); 
			return buildResponse(service.buscarPedidoPeloNumero(numero));
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar pedidos de venda. Entre em contato com o administrador do sistema.");
		}
	}
/*	
	@DELETE
	@Path("/deletar/{numero}")
	@Consumes("application/*")
	
	public Response deletar (@PathParam("numero") int numero) throws BrigaderiaException{
		try {
			PedidoCompraService service = new PedidoCompraService();
			return this.buildResponse(service.deletarPedido(numero));
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse(ERROINESPERADO);
		}
	}*/
}