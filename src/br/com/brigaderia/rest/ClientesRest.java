package br.com.brigaderia.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.jdbc.JDBCClienteDAO;
import br.com.brigaderia.jdbcinterface.ClienteDAO;
import br.com.brigaderia.objetos.Cliente;
import br.com.brigaderia.objetos.DadosClientesVO;


@Path("clientes")

public class ClientesRest extends UtilRest{
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response listar() {
		return super.buildResponse(new ArrayList<Cliente>());
	}
	
	@POST
	@Path ("/adicionar")
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response adicionar(String param) {
		try {
			Cliente cliente = new ObjectMapper().readValue(param, Cliente.class);
			Date d = new Date();
			cliente.setDataCadastro(d);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			ClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
			boolean retorno = jdbcCliente.cadastrar(cliente);
			conec.fecharConexao();
			
			if (retorno) {
				return super.buildResponse("Cliente cadastrado com sucesso");
			}else{
				return super.buildErrorResponse("Erro ao cadastrar cliente!");
			}
		}catch (Exception e){
			e.printStackTrace();
			return super.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/buscarClientes/{valorBusca}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	
	public Response buscarClientes (@PathParam("valorBusca") String valorBusca) {
		
		try {
			List<DadosClientesVO> dadosClientesVO = new ArrayList<DadosClientesVO>();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			ClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
			dadosClientesVO = jdbcCliente.buscarClientes(valorBusca);
			conec.fecharConexao();
			
			return this.buildResponse(dadosClientesVO);
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
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			ClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
			jdbcCliente.deletar(codigo);
			return this.buildResponse("Cliente deletado!");
		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/buscarClientePeloCodigo/{codigo}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscarClientePeloCodigo(@PathParam("codigo")int codigo) {
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			ClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
			Cliente cliente = jdbcCliente.buscarPeloCodigo(codigo);
			return this.buildResponse(cliente);
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
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			ClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
			jdbcCliente.atualizar(cliente);
			conec.fecharConexao();
			return this.buildResponse("Cliente editado com sucesso.");
		}catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

}










