package br.com.brigaderia.rest;

import java.sql.SQLException;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.brigaderia.service.GraficoService;

@Path("graficos")

public class Graficos extends UtilRest{

	static final String ERROINESPERADO = "Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.";
	


	
	@GET
	@Path("/vendaAnual")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response vendaAnual(){
		try {
			GraficoService service = new GraficoService();
			return this.buildResponse(service.vendaAnual());
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao gerar o gráfico. Entre em contato com o administrador do sistema.");
		}
	}
}