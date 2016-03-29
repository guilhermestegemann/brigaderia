package br.com.brigaderia.rest; 

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.objetos.ProdutoFichaTecnicaVO;
import br.com.brigaderia.service.ProdutoFichaTecnicaService;

@Path("produtoFichaTecnica")

public class ProdutoFichaTecnicaRest extends UtilRest{
	
	static final String ERROINESPERADO = "Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.";
	
	@POST
	@Path ("/adicionar")
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response adicionar(String param) throws BrigaderiaException {
		try {
			ProdutoFichaTecnicaVO prodFicha = new ObjectMapper().readValue(param, ProdutoFichaTecnicaVO.class);
			ProdutoFichaTecnicaService service = new ProdutoFichaTecnicaService();
			service.adicionar(prodFicha);		
			return buildResponse("Produto e Ficha Técnica cadastrados com sucesso!");
		}catch (BrigaderiaException e) {
			return buildErrorResponse(e.getMessage());
		}catch(Exception e) {
			return buildErrorResponse(e.getMessage());
		}
	}
	
	@PUT
	@Path("/atualizar")
	@Consumes("application/*")
	public Response atualizar (String prodFichaEditado) throws BrigaderiaException {
		try {
			ProdutoFichaTecnicaVO prodFicha = new ObjectMapper().readValue(prodFichaEditado, ProdutoFichaTecnicaVO.class);
			ProdutoFichaTecnicaService service = new ProdutoFichaTecnicaService();
			service.atualizarProdutoFichaTecnica(prodFicha);
			return buildResponse("Produto e Ficha Técnica editados com sucesso.");
		}catch (BrigaderiaException e){
			return buildErrorResponse(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return buildErrorResponse(ERROINESPERADO);
		}
	}
	
}
