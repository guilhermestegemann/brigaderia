package br.com.brigaderia.service;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbc.JDBCClienteDAO;
import br.com.brigaderia.jdbcinterface.ClienteDAO;
import br.com.brigaderia.objetos.Cliente;

public class ClienteService {
	
	public void adicionarCliente (Cliente cliente) {
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			Date dataCadastro = new Date();
			cliente.setDataCadastro(dataCadastro);
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String aniversarioStr = cliente.getAniversario();
			Date dataAniversario = (Date)formatter.parse(aniversarioStr);
			cliente.setAniversarioDate(dataAniversario);
			ClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
			jdbcCliente.cadastrar(cliente);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			conec.fecharConexao();
		}
	}
	
	public Cliente buscarClientePeloCodigo (int codigo) throws BrigaderiaException{
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
			Cliente cliente = jdbcCliente.buscarPeloCodigo(codigo);
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date dataAniversario = cliente.getAniversarioDate();
			cliente.setAniversario((String)formatter.format(dataAniversario));
			
			return cliente;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}finally{
			conec.fecharConexao();
		}
		
		
	}
}
