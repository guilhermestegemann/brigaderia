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
import br.com.brigaderia.jdbc.JDBCBairroDAO;
import br.com.brigaderia.jdbcinterface.BairroDAO;
import br.com.brigaderia.objetos.Bairro;

@Path("bairros")

public class BairrosRest extends UtilRest{
	
	
public BairrosRest() {
		
	}
	
	@GET
	@Path("/buscar")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscar() {
		
		try {
			List<Bairro> bairros = new ArrayList<Bairro>();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			BairroDAO jdbcBairro = new JDBCBairroDAO(conexao);
			bairros = jdbcBairro.buscar();
			conec.fecharConexao();
			
			return this.buildResponse(bairros);
		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
	}

}
