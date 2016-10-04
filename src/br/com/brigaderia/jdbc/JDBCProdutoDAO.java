package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.jdbcinterface.ProdutoDAO;
import br.com.brigaderia.objetos.IngredientesVO;
import br.com.brigaderia.objetos.Produto;

public class JDBCProdutoDAO implements ProdutoDAO{
	
	private Connection conexao;
	
	public JDBCProdutoDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public List<IngredientesVO> buscarIngredientes () throws  SQLException {
		
		String comando = "SELECT CODIGO, DESCRICAO, UNESTOQUE FROM PRODUTO WHERE PRODUTO.TIPOITEM = 2 AND PRODUTO.ATIVO = 'S' ";
		List<IngredientesVO> listIngredientes = new ArrayList<IngredientesVO>();
		IngredientesVO ingredientes = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			ingredientes = new IngredientesVO();
			ingredientes.setCodigo(rs.getInt("codigo"));
			ingredientes.setDescricao(rs.getString("descricao"));
			ingredientes.setUn(rs.getString("unestoque"));
			
			listIngredientes.add(ingredientes);
		}
		return listIngredientes;
	}
	
	public int adicionar(Produto produto) throws SQLException  {
		
		String comando = "INSERT INTO PRODUTO (DESCRICAO, ESTOQUE, UNESTOQUE, VALORCUSTO, MARGEM, VALORVENDA, TIPOITEM, "
				       + "DATACADASTRO, ATIVO, QTDEENTRADA, UNENTRADA) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement p;
	
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
		ResultSet generatedKeys = p.getGeneratedKeys();
        if (generatedKeys.next()) {
            produto.setCodigoProduto(generatedKeys.getInt(1));
        }
        else {
            throw new SQLException("Erro ao recuperar chave inserida! (Produto)");
        }
		return produto.getCodigoProduto();
	}
	
	public Produto buscarPeloCodigo (int codigo) throws SQLException {
		
		String comando = "SELECT * FROM PRODUTO WHERE PRODUTO.CODIGO = " + codigo;
		Produto produto = new Produto();
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			produto.setCodigoProduto(rs.getInt("codigo"));
			produto.setDescricao(rs.getString("descricao"));
			produto.setEstoque(rs.getFloat("estoque"));
			produto.setUnEstoque(rs.getString("unestoque"));
			produto.setValorCusto(rs.getFloat("valorcusto"));
			produto.setMargem(rs.getFloat("margem"));
			produto.setValorVenda(rs.getFloat("valorVenda"));
			produto.setTipoItem(rs.getInt("tipoitem"));
			produto.setDataCadastro(rs.getDate("datacadastro"));
			produto.setAtivo(rs.getString("ativo"));
			produto.setQtdeEntrada(rs.getFloat("qtdeentrada"));
			produto.setUnEntrada(rs.getString("unentrada"));
		}
		return produto;
	}
	
	public void deletar(int codigo) throws SQLException {
		
		String comando = "DELETE FROM PRODUTO WHERE PRODUTO.CODIGO = " + codigo;
		Statement p;
		p = this.conexao.createStatement();
		p.execute(comando);	
	}
	
	public List<Produto> buscarProdutos (String valorBusca, String ativo, String tipoItem) throws SQLException {
		
		String comando = "SELECT * FROM PRODUTO ";
		if ((!valorBusca.equals("")) && (!valorBusca.equals("null") && !valorBusca.equals(""))) {
			comando += "WHERE PRODUTO.ATIVO = '" + ativo + "'"
					+ "AND PRODUTO.DESCRICAO LIKE '" + valorBusca + "%'";
		}else{
			comando += "WHERE PRODUTO.ATIVO = '" + ativo + "'";
		}
		if (!tipoItem.equals("0")) {
			comando += " AND PRODUTO.TIPOITEM IN ( " + tipoItem + ")";
		}
		
		List<Produto> listProdutos = new ArrayList<Produto>();
		Produto produto = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			produto = new Produto();
			produto.setCodigoProduto(rs.getInt("CODIGO"));
			produto.setDescricao(rs.getString("DESCRICAO"));
			produto.setEstoque(rs.getFloat("ESTOQUE"));
			produto.setUnEstoque(rs.getString("UNESTOQUE"));
			produto.setValorCusto(rs.getFloat("VALORCUSTO"));
			produto.setMargem(rs.getFloat("MARGEM"));
			produto.setValorVenda(rs.getFloat("VALORVENDA"));
			produto.setTipoItem(rs.getInt("TIPOITEM"));
			produto.setQtdeEntrada(rs.getFloat("QTDEENTRADA"));
			produto.setUnEntrada(rs.getString("UNENTRADA"));
			listProdutos.add(produto);
		}
		return listProdutos;
	}
	
	public void atualizar (Produto produto) throws SQLException {
		
		String comando = "UPDATE PRODUTO SET " 
							+ "PRODUTO.DESCRICAO = ?, "
							+ "PRODUTO.UNESTOQUE = ?, "
							+ "PRODUTO.MARGEM = ?, "
							+ "PRODUTO.VALORVENDA = ?, "
							+ "PRODUTO.ATIVO = ?, "
							+ "PRODUTO.QTDEENTRADA = ?, "
							+ "PRODUTO.UNENTRADA = ? "
					   + "WHERE PRODUTO.CODIGO = " + produto.getCodigoProduto();
	    
		PreparedStatement p;
    	p = this.conexao.prepareStatement(comando);
    	p.setString(1, produto.getDescricao());
    	p.setString(2, produto.getUnEstoque());
    	p.setFloat(3, produto.getMargem());
    	p.setFloat(4, produto.getValorVenda());
    	p.setString(5, produto.getAtivo());
    	p.setFloat(6, produto.getQtdeEntrada());
    	p.setString(7, produto.getUnEntrada());
    	p.executeUpdate();
	    
	}
	
	public void atualizarEstoque(int codProduto, float custo, float margem) throws SQLException  {
		
		String update = "UPDATE PRODUTO SET PRODUTO.VALORCUSTO = " + custo
					  + ", PRODUTO.MARGEM = + " + margem
					  + " WHERE PRODUTO.CODIGO = " + codProduto;		
		
		PreparedStatement p = this.conexao.prepareStatement(update);
		p.execute();
	}
	
public void atualizarCusto(int codProduto, float custo) throws SQLException  {
		
		String update = "UPDATE PRODUTO SET PRODUTO.VALORCUSTO = " + custo
					  + " WHERE PRODUTO.CODIGO = " + codProduto;		
		
		PreparedStatement p = this.conexao.prepareStatement(update);
		p.execute();
	}
	
	public void movimentaEstoque (int codProduto, float qtde) throws SQLException  {
		String update = "UPDATE PRODUTO SET PRODUTO.ESTOQUE = ESTOQUE + " + qtde + " WHERE PRODUTO.CODIGO = " + codProduto;

		PreparedStatement p = this.conexao.prepareStatement(update);
		p.execute();
	}
}