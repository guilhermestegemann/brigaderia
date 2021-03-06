package br.com.brigaderia.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



import br.com.brigaderia.bd.conexao.Conexao;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.ClienteComPedidoException;
import br.com.brigaderia.exception.DataNascimentoInvalidaException;
import br.com.brigaderia.jdbc.JDBCClienteDAO;
import br.com.brigaderia.jdbc.JDBCPedidoVendaDAO;
import br.com.brigaderia.jdbcinterface.ClienteDAO;
import br.com.brigaderia.jdbcinterface.PedidoVendaDAO;
import br.com.brigaderia.objetos.Cliente;
import br.com.brigaderia.objetos.DadosClientesVO;
import br.com.brigaderia.validacoes.ValidaCliente;

public class ClienteService {
	
	public void adicionarCliente (Cliente cliente) throws ParseException, BrigaderiaException, SQLException{
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ValidaCliente validaCliente = new ValidaCliente();
			ClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
			if (!cliente.getCpf().equals("")) {
				jdbcCliente.verificarCpfDuplicado(cliente.getCpf());
			}
			validaCliente.validarCliente(cliente);
			cliente.setDataCadastro(new Date());
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if (cliente.getAniversario() != "") {
				Date dataAniversario = (Date)formatter.parse(cliente.getAniversario());
				if (dataAniversario.after(new Date())) {
					throw new DataNascimentoInvalidaException();
				}else{
					cliente.setAniversarioDate(dataAniversario);
				}
			}
			jdbcCliente.cadastrar(cliente);
		}finally {
			conec.fecharConexao();
		}
	}
	
	public Cliente buscarClientePeloCodigo (int codigo) throws SQLException{
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
			Cliente cliente = jdbcCliente.buscarPeloCodigo(codigo);
			if (cliente.getAniversarioDate() != null) {
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				cliente.setAniversario((String)formatter.format(cliente.getAniversarioDate()));
			}
			return cliente;
		}finally{
			conec.fecharConexao();
		}	
	}
	
	public void atualizarCliente (Cliente cliente) throws BrigaderiaException, SQLException, ParseException  {
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
			if (!cliente.getCpf().equals("")){
				jdbcCliente.verificarCpfDuplicadoEdicao(cliente.getCpf(), cliente.getCodigo());
			}
			ValidaCliente validaCliente = new ValidaCliente();
			validaCliente.validarCliente(cliente);
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if (cliente.getAniversario() != "") {
				Date dataAniversario = (Date)formatter.parse(cliente.getAniversario());
				if (dataAniversario.after(new Date())) {
					throw new DataNascimentoInvalidaException();
				}else{
					cliente.setAniversarioDate(dataAniversario);
				}
			}
			jdbcCliente.atualizar(cliente);
		}finally{
			conec.fecharConexao();
		}
	}
	
	public void deletarCliente (int codigo) throws ClienteComPedidoException, SQLException {
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			PedidoVendaDAO jdbcPedidoVenda = new JDBCPedidoVendaDAO(conexao);
			jdbcPedidoVenda.verificaPedidoCliente(codigo);
			ClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
			jdbcCliente.deletar(codigo);
		}finally {
			conec.fecharConexao();
		}
	}
	
	public List<DadosClientesVO> buscarClientesVO (String valorBusca, String ativo) throws SQLException {
		
		Conexao conec = new Conexao();
		try {
			Connection conexao = conec.abrirConexao();
			ClienteDAO jdbcCliente = new JDBCClienteDAO(conexao);
			return jdbcCliente.buscarClientes(valorBusca, ativo);
		}finally{
			conec.fecharConexao();
		}
	}
}
