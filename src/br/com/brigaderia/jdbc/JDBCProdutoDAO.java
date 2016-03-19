package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbcinterface.ProdutoDAO;
import br.com.brigaderia.objetos.IngredientesVO;
import br.com.brigaderia.objetos.Produto;

public class JDBCProdutoDAO implements ProdutoDAO{
	
	private Connection conexao;
	
	public JDBCProdutoDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public List<IngredientesVO> buscarIngredientes () throws BrigaderiaException {
		String comando = "SELECT CODIGO, DESCRICAO, UNESTOQUE FROM PRODUTO WHERE TIPOITEM = 2 ";
		List<IngredientesVO> listIngredientes = new ArrayList<IngredientesVO>();
		IngredientesVO ingredientes = null;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()) {
				ingredientes = new IngredientesVO();
				ingredientes.setCodigo(rs.getInt("codigo"));
				ingredientes.setDescricao(rs.getString("descricao"));
				ingredientes.setUn(rs.getString("unestoque"));
				
				listIngredientes.add(ingredientes);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}return listIngredientes;
	}
	
	public int adicionar(Produto produto) throws BrigaderiaException {
		String comando = "INSERT INTO PRODUTO (DESCRICAO, ESTOQUE, UNESTOQUE, VALORCUSTO, MARGEM, VALORVENDA, TIPOITEM, "
				       + "DATACADASTRO, ATIVO, QTDEENTRADA, UNENTRADA) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS);
			p.setString(1, produto.getDescricao());
			p.setFloat(2, produto.getEstoque());
			p.setString(3, produto.getUnEstoque());
			p.setFloat(4, produto.getValorCusto());
			p.setFloat(5, produto.getMargem());
			p.setFloat(6, produto.getValorVenda());
			p.setInt(7, produto.getTipoItem());
			p.setDate(8, new java.sql.Date(produto.getDataCadastro().getTime()));
			p.setString(9, produto.getAtivo());
			p.setFloat(10, produto.getQtdeEntrada());
			p.setString(11, produto.getUnEntrada());
			p.execute();
			try (ResultSet generatedKeys = p.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                produto.setCodigo(generatedKeys.getInt(1));
	            }
	            else {
	                throw new SQLException("Erro ao criar pedido. ID failed.");
	            }
			} 
			
			return produto.getCodigo();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}
	}

}
