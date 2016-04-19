package br.com.brigaderia.rest;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.service.TipoItemService;

@Path("tipoitem")

public class TipoItemRest extends UtilRest{
	
	static final String ERROINESPERADO = "Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.";
	
	@GET
	@Path("/buscar")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscar() throws BrigaderiaException{
			
		try {
			TipoItemService service = new TipoItemService();
			return this.buildResponse(service.buscar());
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao buscar tipo item. Entre em contato com o administrador do sistema.");
		}
	}
}
