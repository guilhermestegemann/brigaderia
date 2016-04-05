package br.com.brigaderia.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.service.PedidoCompraService;

@Path("pedidoCompra")

public class PedidoCompraRest extends UtilRest{

	static final String ERROINESPERADO = "Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.";
	
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
}
