package br.com.brigaderia.rest;

import java.sql.SQLException;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.service.GraficoService;

@Path("graficos")

public class Graficos extends UtilRest{

	static final String ERROINESPERADO = "Ocorreu um erro inesperado. Entre em contato com o administrador do sistema.";
	


	
	@GET
	@Path("/vendaAnual/{ano}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response vendaAnual(@PathParam("ano") int ano) throws BrigaderiaException{
		try {
			GraficoService service = new GraficoService();
			return this.buildResponse(service.vendaAnual(ano));
		}catch (SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao gerar o gráfico. Entre em contato com o administrador do sistema.");
		}
	}
	
	@GET
	@Path("/porProduto/{dataInicio}/{dataFim}/{orderBy}/{limit}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	
	public Response porProduto (@PathParam("dataInicio") String dataInicio, 
			                       @PathParam("dataFim") String dataFim,
			                       @PathParam("orderBy") String orderBy,
			                       @PathParam("limit") int limit) throws BrigaderiaException{
		
		try {
			
			GraficoService service = new GraficoService();
			return buildResponse(service.gerarPorProduto(dataInicio, dataFim, orderBy, limit));
		}catch(SQLException e) {
			e.printStackTrace();
			return buildErrorResponse("Ocorreu um erro ao gerar o gráfico de venda por produto. Entre em contato com o administrador do sistema.");
		}
	}
}