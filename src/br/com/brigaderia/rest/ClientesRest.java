package br.com.brigaderia.rest;

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

import br.com.brigaderia.exception.ClienteComPedidoException;
import br.com.brigaderia.exception.CpfInvalidoException;
import br.com.brigaderia.exception.ValidaClientesException;
import br.com.brigaderia.objetos.Cliente;
import br.com.brigaderia.service.ClienteService;


@Path("clientes")

public class ClientesRest extends UtilRest{
	
	
	@POST
	@Path ("/adicionar")
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response adicionar(String param) {
		
		try {
			Cliente cliente = new ObjectMapper().readValue(param, Cliente.class);
			ClienteService service = new ClienteService();
			service.adicionarCliente(cliente);
			return super.buildResponse("Cliente cadastrado com sucesso");
		}catch (ValidaClientesException e){
			return this.buildErrorResponse(e.getMessage());
		}catch(CpfInvalidoException e) {
			return this.buildErrorResponse(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse("Ocorreu algum erro!");
		}
	}
	
	@GET
	@Path("/buscarClientes/{valorBusca}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	
	public Response buscarClientes (@PathParam("valorBusca") String valorBusca) {
		
		try {
			
			ClienteService service = new ClienteService();
			return this.buildResponse(service.buscarClientesVO(valorBusca));
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@DELETE
	@Path("/deletar/{codigo}")
	@Consumes("application/*")
	
	public Response deletar (@PathParam("codigo") int codigo) {
		try {
			ClienteService service = new ClienteService();
			service.deletarCliente(codigo);
			return this.buildResponse("Cliente deletado!");
		}catch (ClienteComPedidoException e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/buscarClientePeloCodigo/{codigo}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscarClientePeloCodigo(@PathParam("codigo")int codigo) {
		try {
			ClienteService service = new ClienteService(); 
			return this.buildResponse(service.buscarClientePeloCodigo(codigo));
		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@PUT
	@Path("/atualizar")
	@Consumes("application/*")
	public Response atualizar (String clienteEditado) {
		try {
			Cliente cliente = new ObjectMapper().readValue(clienteEditado, Cliente.class);
			ClienteService service = new ClienteService();
			service.atualizarCliente(cliente);
			return this.buildResponse("Cliente editado com sucesso.");
		}catch (ValidaClientesException e){
			return this.buildErrorResponse(e.getMessage());
		}catch (CpfInvalidoException e) {
			return this.buildErrorResponse(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

}










