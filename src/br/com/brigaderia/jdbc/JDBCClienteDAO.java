package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;






import br.com.brigaderia.jdbcinterface.ClienteDAO;
import br.com.brigaderia.objetos.Cliente;
import br.com.brigaderia.objetos.DadosClientesVO;

public class JDBCClienteDAO implements ClienteDAO{
	
	private Connection conexao;

	public JDBCClienteDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public boolean cadastrar (Cliente cliente) {
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
			p.setDate(10, new java.sql.Date(cliente.getAniversario().getTime()));
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
			return false;
		}return true;
	}
	
	public List<DadosClientesVO> buscarClientes (String valorBusca) {
		
		String comando = "SELECT CLIENTE.CODIGO AS CODIGOCLIENTE, CLIENTE.NOME AS NOMECLIENTE, CIDADE.NOME AS NOMECIDADE, "
					   + "ESTADO.UF AS UFESTADO, BAIRRO.NOME AS NOMEBAIRRO, PEDIDO.EMISSAO AS ULTIMAVENDA "
					   + "FROM CLIENTE "
				       + "INNER JOIN CIDADE ON CIDADE.CODIGO = CLIENTE.CIDADE "
					   + "INNER JOIN ESTADO ON ESTADO.CODIGO = CIDADE.ESTADO "
					   + "INNER JOIN BAIRRO ON BAIRRO.CODIGO = CLIENTE.BAIRRO "
				       + "LEFT JOIN PEDIDO ON PEDIDO.CLIENTE = CLIENTE.CODIGO AND PEDIDO.FATURADO = 'S' AND PEDIDO.CANCELADO = 'N' ";
		
		if (!valorBusca.equals("null") && !valorBusca.equals("")) {
			comando += "WHERE CLIENTE.NOME LIKE '" + valorBusca + "%'";
		}
		
		List<DadosClientesVO> listDadosClientes = new ArrayList<DadosClientesVO>();
		DadosClientesVO dadosClientesVO = null;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			dadosClientesVO = new DadosClientesVO();
			while(rs.next()) {
				dadosClientesVO = new DadosClientesVO();
				int codCliente = rs.getInt("CODIGOCLIENTE");
				String nomeCliente = rs.getString("NOMECLIENTE");
				String nomeCidade = rs.getString("NOMECIDADE");
				String uf = rs.getString("UFESTADO");
				String nomeBairro = rs.getString("NOMEBAIRRO");
				Date ultimaVenda = rs.getDate("ULTIMAVENDA");
				
				dadosClientesVO.setCodigo(codCliente);
				dadosClientesVO.setNome(nomeCliente);
				dadosClientesVO.setCidade(nomeCidade);
				dadosClientesVO.setUf(uf);
				dadosClientesVO.setBairro(nomeBairro);
				dadosClientesVO.setUltimaVenda(ultimaVenda);
				listDadosClientes.add(dadosClientesVO);
			}
		}catch(Exception e){
			e.printStackTrace();
		}return listDadosClientes;
		
	}
	
	public boolean deletar (int codigo) {
		String comando = "DELETE FROM CLIENTE WHERE CLIENTE.CODIGO = " + codigo;
		Statement p;
		
		try {
			p = this.conexao.createStatement();
			p.execute(comando);
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Cliente buscarPeloCodigo (int codigo) {
		String comando = "SELECT * FROM CLIENTE WHERE CLIENTE.CODIGO = " + codigo;
		Cliente cliente = new Cliente();
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()) {
				int codigoCliente = rs.getInt("codigo");
				String nome = rs.getString("nome");
				String rg = rs.getString("rg");
				String cpf = rs.getString("cpf");
				String endereco = rs.getString("endereco");
				String numero = rs.getString("numero");
				String complemento = rs.getString("complemento");
				int cep = rs.getInt("cep");
				int bairro = rs.getInt("bairro");
				int cidade = rs.getInt("cidade");
				Date aniversario = rs.getDate("aniversario");
				Date dataCadastro = rs.getDate("datacadastro");
				String email = rs.getString("email");
				Long telefone = rs.getLong("telefone");
				Long celular = rs.getLong("celular");
				
				cliente.setCodigo(codigoCliente);
				cliente.setNome(nome);
				cliente.setRg(rg);
				cliente.setCpf(cpf);
				cliente.setEndereco(endereco);
				cliente.setNumero(numero);
				cliente.setComplemento(complemento);
				cliente.setCep(cep);
				cliente.setBairro(bairro);
				cliente.setCidade(cidade);
				cliente.setAniversario(aniversario);
				cliente.setDataCadastro(dataCadastro);
				cliente.setEmail(email);
				cliente.setTelefone(telefone);
				cliente.setCelular(celular);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return cliente;
	}
	
	public boolean atualizar (Cliente cliente) {
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
	    	p.setDate(10, new java.sql.Date(cliente.getAniversario().getTime()));
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
	    	return false;
	    }
	    return true;
	}

}











