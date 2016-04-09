package br.com.brigaderia.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.objetos.PedidoCompra;
import br.com.brigaderia.service.PedidoCompraService;

@Path("pedidoCompra")

public class PedidoCompraRest extends UtilRest{

	static final String ERROINESPERADO = "Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.";
	
	@POST
	@Path ("/adicionar")
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response adicionar(String param) throws BrigaderiaException {
		
		try {
			PedidoCompra pedidoCompra = new ObjectMapper().readValue(param, PedidoCompra.class);
			PedidoCompraService service = new PedidoCompraService();
			service.adicionarPedido(pedidoCompra);
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
	public Response listarProdutos() throws BrigaderiaException{
		
		try {
			PedidoCompraService service = new PedidoCompraService();
			return this.buildResponse(service.buscarProdutos());
		}catch (BrigaderiaException e) {
			return buildErrorResponse(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(ERROINESPERADO);
		}
	}
	
	@GET
	@Path("/buscarClientes/{dataInicio}/{dataFim}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	
	public Response buscarPedidos (@PathParam("dataInicio") String dataInicio, @PathParam("dataFim") String dataFim) throws BrigaderiaException{
		
		try {
			
			PedidoCompraService service = new PedidoCompraService();
			return buildResponse(service.buscarPedidoCompra(dataInicio, dataFim));
		}catch (BrigaderiaException e) {
			return buildErrorResponse(e.getMessage());
		}catch(Exception e) {
			return buildErrorResponse(ERROINESPERADO);
		}
	}
}
