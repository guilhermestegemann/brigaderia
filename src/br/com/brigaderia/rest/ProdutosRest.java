package br.com.brigaderia.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
	
	public Response adicionar(String param) throws BrigaderiaException {
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
}
