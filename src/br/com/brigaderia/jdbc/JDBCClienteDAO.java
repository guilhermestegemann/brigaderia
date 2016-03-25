package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.CpfDuplicadoException;
import br.com.brigaderia.jdbcinterface.ClienteDAO;
import br.com.brigaderia.objetos.Cliente;
import br.com.brigaderia.objetos.DadosClientesVO;

public class JDBCClienteDAO implements ClienteDAO{
	
	private Connection conexao;

	public JDBCClienteDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public void cadastrar (Cliente cliente)  throws BrigaderiaException{
		String comando = "INSERT INTO CLIENTE (NOME, RG, CPF, ENDERECO, NUMERO, COMPLEMENTO, CEP, CIDADE, BAIRRO, ANIVERSARIO, "
				+ "EMAIL, TELEFONE, CELULAR, DATACADASTRO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, cliente.getNome());
			p.setString(2, cliente.getRg());
			p.setString(3, cliente.getCpf());
			p.setString(4, cliente.getEndereco());
			p.setString(5, cliente.getNumero());
			p.setString(6, cliente.getComplemento());
			p.setInt(7, cliente.getCep());
			p.setInt(8, cliente.getCidade());
			p.setInt(9, cliente.getBairro());
			if (cliente.getAniversario() == "") {
				p.setDate(10, null);
			}else{
				p.setDate(10, new java.sql.Date(cliente.getAniversarioDate().getTime()));
			}
			p.setString(11, cliente.getEmail());
			if (cliente.getTelefone() == null) {
				p.setLong(12, 0);
			}else{
				p.setLong(12, cliente.getTelefone());
			}
			if (cliente.getCelular() == null) {
				p.setLong(13, 0);
			}else{
				p.setLong(13, cliente.getCelular());
			}
			p.setDate(14, new java.sql.Date(cliente.getDataCadastro().getTime()));
			p.execute();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}
	}
	
	public List<DadosClientesVO> buscarClientes (String valorBusca) throws BrigaderiaException {
		
		String comando = "SELECT CLIENTE.CODIGO AS CODIGOCLIENTE, CLIENTE.NOME AS NOMECLIENTE, CIDADE.NOME AS NOMECIDADE, "
					   + "ESTADO.UF AS UFESTADO, BAIRRO.NOME AS NOMEBAIRRO, "
					   + "(SELECT MAX(PEDIDO.EMISSAO) FROM PEDIDO WHERE PEDIDO.CLIENTE = CLIENTE.CODIGO) AS ULTIMAVENDA "
					   + "FROM CLIENTE "
				       + "INNER JOIN CIDADE ON CIDADE.CODIGO = CLIENTE.CIDADE "
					   + "INNER JOIN ESTADO ON ESTADO.CODIGO = CIDADE.ESTADO "
					   + "INNER JOIN BAIRRO ON BAIRRO.CODIGO = CLIENTE.BAIRRO ";
		
		if (!valorBusca.equals("null") && !valorBusca.equals("")) {
			comando += "WHERE CLIENTE.NOME LIKE '" + valorBusca + "%'";
		}
		
		List<DadosClientesVO> listDadosClientes = new ArrayList<DadosClientesVO>();
		DadosClientesVO dadosClientesVO = null;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()) {
				dadosClientesVO = new DadosClientesVO();
				dadosClientesVO.setCodigo(rs.getInt("CODIGOCLIENTE"));
				dadosClientesVO.setNome(rs.getString("NOMECLIENTE"));
				dadosClientesVO.setCidade(rs.getString("NOMECIDADE"));
				dadosClientesVO.setUf(rs.getString("UFESTADO"));
				dadosClientesVO.setBairro(rs.getString("NOMEBAIRRO"));
				dadosClientesVO.setUltimaVenda(rs.getDate("ULTIMAVENDA")); 
				listDadosClientes.add(dadosClientesVO);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new BrigaderiaException();
		}
		return listDadosClientes;
	}
	
	public void deletar (int codigo) throws BrigaderiaException{
		String comando = "DELETE FROM CLIENTE WHERE CLIENTE.CODIGO = " + codigo;
		Statement p;
		
		try {
			p = this.conexao.createStatement();
			p.execute(comando);
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BrigaderiaException();	
		}
	}
	
	public Cliente buscarPeloCodigo (int codigo) throws BrigaderiaException{
		String comando = "SELECT * FROM CLIENTE WHERE CLIENTE.CODIGO = " + codigo;
		Cliente cliente = new Cliente();
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()) {
				cliente.setCodigo(rs.getInt("codigo"));
				cliente.setNome(rs.getString("nome"));
				cliente.setRg(rs.getString("rg"));
				cliente.setCpf(rs.getString("cpf"));
				cliente.setEndereco(rs.getString("endereco"));
				cliente.setNumero(rs.getString("numero"));
				cliente.setComplemento(rs.getString("complemento"));
				cliente.setCep(rs.getInt("cep"));
				cliente.setBairro(rs.getInt("bairro"));
				cliente.setCidade(rs.getInt("cidade"));
				cliente.setAniversarioDate(rs.getDate("aniversario"));
				cliente.setDataCadastro(rs.getDate("datacadastro"));
				cliente.setEmail(rs.getString("email"));
				cliente.setTelefone(rs.getLong("telefone"));
				cliente.setCelular(rs.getLong("celular"));
			}
		}catch (SQLException e) {
			throw new BrigaderiaException();
		}
		return cliente;
	}
	
	public void atualizar (Cliente cliente) throws BrigaderiaException {
		String comando = "UPDATE CLIENTE SET " 
							+ "CLIENTE.NOME = ?, "
							+ "CLIENTE.RG = ?, "
							+ "CLIENTE.CPF = ?, "
							+ "CLIENTE.ENDERECO = ?, "
							+ "CLIENTE.NUMERO = ?, "
							+ "CLIENTE.COMPLEMENTO = ?, "
							+ "CLIENTE.CEP = ?, "
							+ "CLIENTE.BAIRRO = ?, "
							+ "CLIENTE.CIDADE = ?, "
							+ "CLIENTE.ANIVERSARIO = ?, "
							+ "CLIENTE.EMAIL = ?, "
							+ "CLIENTE.TELEFONE = ?, "
							+ "CLIENTE.CELULAR = ? "
					   + "WHERE CLIENTE.CODIGO = " + cliente.getCodigo();
	    PreparedStatement p;
	    try {
	    	p = this.conexao.prepareStatement(comando);
	    	p.setString(1, cliente.getNome());
	    	p.setString(2, cliente.getRg());
	    	p.setString(3, cliente.getCpf());
	    	p.setString(4, cliente.getEndereco());
	    	p.setString(5, cliente.getNumero());
	    	p.setString(6, cliente.getComplemento());
	    	p.setInt(7, cliente.getCep());
	    	p.setInt(8, cliente.getBairro());
	    	p.setInt(9, cliente.getCidade());
	    	if (cliente.getAniversarioDate() != null) {
	    		p.setDate(10, new java.sql.Date(cliente.getAniversarioDate().getTime()));
	    	}else {
	    		p.setDate(10, null);
	    	}
	    	p.setString(11, cliente.getEmail());
			if (cliente.getTelefone() == null) {
				p.setLong(12, 0);
			}else{
				p.setLong(12, cliente.getTelefone());
			}
			if (cliente.getCelular() == null) {
				p.setLong(13, 0);
			}else{
				p.setLong(13, cliente.getCelular());
			}
	    	p.executeUpdate();
	    }catch (SQLException e) {
	    	e.printStackTrace();
	    	throw new BrigaderiaException();
	    }
	}
	
	public void verificarCpfDuplicado (String cpf) throws BrigaderiaException {
		String comando = "SELECT COUNT(*) AS QTDECLIENTE FROM CLIENTE WHERE CLIENTE.CPF = '" + cpf + "'";
		int qtdeClientes = 0;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()) {
				qtdeClientes = rs.getInt("QTDECLIENTE");
			}
			if (qtdeClientes > 0) {
				throw new CpfDuplicadoException();
			}
		}catch (BrigaderiaException e) {
			e.printStackTrace();
			throw e;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}
	}
	
	public void verificarCpfDuplicadoEdicao (String cpf, int codigo) throws BrigaderiaException {
		String comando = "SELECT COUNT(*) AS QTDECLIENTE FROM CLIENTE WHERE CLIENTE.CPF = '" + cpf + "' AND CLIENTE.CODIGO <> " + codigo;
		int qtdeClientes = 0;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()) {
				qtdeClientes = rs.getInt("QTDECLIENTE");
			}
			if (qtdeClientes > 0) {
				throw new CpfDuplicadoException();
			}
		}catch (BrigaderiaException e) {
			e.printStackTrace();
			throw e;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}
	}

}











