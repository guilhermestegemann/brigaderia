package br.com.brigaderia.rest;



import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.jdbc.JDBCCidadeDAO;
import br.com.brigaderia.jdbcinterface.CidadeDAO;
import br.com.brigaderia.objetos.Cidade;





@Path("cidades")

public class CidadesRest extends UtilRest{
	
	public CidadesRest() {
		
	}
	
	@GET
	@Path("/buscar")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscar() {
		
		try {
			List<Cidade> cidades = new ArrayList<Cidade>();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			CidadeDAO jdbcCidade = new JDBCCidadeDAO(conexao);
			cidades = jdbcCidade.buscar();
			conec.fecharConexao();
			
			return this.buildResponse(cidades);
		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
	}

}
