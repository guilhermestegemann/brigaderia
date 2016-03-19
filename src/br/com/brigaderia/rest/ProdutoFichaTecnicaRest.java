package br.com.brigaderia.rest; 

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.objetos.ProdutoFichaTecnicaVO;

@Path("produtoFichaTecnica")

public class ProdutoFichaTecnicaRest extends UtilRest{
	
	static final String ERROINESPERADO = "Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.";
	
	@POST
	@Path ("/adicionar")
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response adicionar(String param) throws BrigaderiaException {
		try {
			ProdutoFichaTecnicaVO prodFicha = new ObjectMapper().readValue(param, ProdutoFichaTecnicaVO.class);
			
			return buildResponse("Produto e Ficha Técnica cadastrados com sucesso!");
		}catch (Exception e) {
			return buildErrorResponse(e.getMessage());
		}
	}
	
}
