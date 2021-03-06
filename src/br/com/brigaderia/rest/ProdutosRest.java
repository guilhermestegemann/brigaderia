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
import br.com.brigaderia.objetos.Produto;
import br.com.brigaderia.service.ProdutoService;

@Path("produtos")

public class ProdutosRest extends UtilRest{
	
	static final String ERROINESPERADO = "Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.";
	
	@POST
	@Path ("/adicionar")
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response adicionar(String param) {
		try {
			Produto produto = new ObjectMapper().readValue(param, Produto.class);
			ProdutoService service = new ProdutoService();
			service.adicionar(produto);		
			return buildResponse("Produto cadastrado com sucesso!");
		}catch (BrigaderiaException e) {
			return buildErrorResponse(e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			return buildErrorResponse(ERROINESPERADO);
		}
	}
	
	@GET
	@Path("/buscarProdutos/{valorBusca}/{ativo}/{tipoItem}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	
	public Response buscarProdutos (@PathParam("valorBusca") String valorBusca,
									@PathParam("ativo") String ativo,
									@PathParam("tipoItem") String tipoItem){
		try {
			ProdutoService service = new ProdutoService();
			return buildResponse(service.buscarProdutos(valorBusca, ativo, tipoItem));
		}catch(SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar produtos. Entre em contato com o administrador do sistema.");
		}
	}
	
	@GET
	@Path("/buscarProdutoPeloCodigo/{codigo}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscarProdutoPeloCodigo(@PathParam("codigo")int codigo){
		try {
			ProdutoService service = new ProdutoService(); 
			return buildResponse(service.buscarProdutoPeloCodigo(codigo));
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar clientes. Entre em contato com o administrador do sistema.");
		}
	}
	
	@PUT
	@Path("/atualizar")
	@Consumes("application/*")
	public Response atualizar (String produtoEditado) {
		try {
			Produto produto = new ObjectMapper().readValue(produtoEditado, Produto.class);
			ProdutoService service = new ProdutoService();
			service.atualizarProduto(produto);
			return buildResponse("Produto editado com sucesso.");
		}catch (BrigaderiaException e){
			return buildErrorResponse(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return buildErrorResponse(ERROINESPERADO);
		}
	}
	
	@DELETE
	@Path("/deletar/{codigo}/{tipoItem}")
	@Consumes("application/*")
	
	public Response deletar (@PathParam("codigo") int codigo, @PathParam("tipoItem") int tipoItem) {
		try {
			ProdutoService service = new ProdutoService();
			service.deletarProduto(codigo, tipoItem);
			return this.buildResponse("Produto deletado!");
		}catch (BrigaderiaException e) {
			return buildErrorResponse(e.getMessage());
		}catch (Exception e) {
			return buildErrorResponse(ERROINESPERADO);
		}
	}
}