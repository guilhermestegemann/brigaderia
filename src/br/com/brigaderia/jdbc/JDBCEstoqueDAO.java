package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.jdbcinterface.EstoqueDAO;
import br.com.brigaderia.objetos.EstoqueVO;

public class JDBCEstoqueDAO implements EstoqueDAO{
	
	private Connection conexao;
	
	public JDBCEstoqueDAO (Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public List<EstoqueVO> gerarRelatorio (String tipoItem, String ativo) throws SQLException {
		String sql = "SELECT "
						+ "PRODUTO.CODIGO, "
						+ "PRODUTO.DESCRICAO, "
					    + "PRODUTO.ESTOQUE, "
						+ "PRODUTO.VALORCUSTO, "
					    + "(PRODUTO.VALORCUSTO * PRODUTO.ESTOQUE) AS TOTALCUSTO, "
						+ "PRODUTO.VALORVENDA, "
					    + "(PRODUTO.VALORVENDA * PRODUTO.ESTOQUE) AS TOTALVENDA, "
						+ "PRODUTO.MARGEM "
					    + "FROM PRODUTO ";
		if(!tipoItem.equals("0")){
			sql += " WHERE PRODUTO.TIPOITEM = " + tipoItem;
		}
		if(!ativo.equals("null")){
			if(!tipoItem.equals("0")){
				sql += " AND PRODUTO.ATIVO = '" + ativo + "'";
			}else{
				sql += " WHERE PRODUTO.ATIVO = '" + ativo + "'";
			}
		}
		sql += " ORDER BY ESTOQUE DESC";
		List<EstoqueVO> listEstoque = new ArrayList<EstoqueVO>();
		EstoqueVO estoque = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()){
			estoque = new EstoqueVO();
			estoque.setCodigoProduto(rs.getInt("CODIGO"));
			estoque.setDescricao(rs.getString("DESCRICAO"));
			estoque.setEstoque(rs.getFloat("ESTOQUE"));
			estoque.setValorCusto(rs.getFloat("VALORCUSTO"));
			estoque.setTotalCusto(rs.getFloat("TOTALCUSTO"));
			estoque.setValorVenda(rs.getFloat("VALORVENDA"));
			estoque.setTotalVenda(rs.getFloat("TOTALVENDA"));
			estoque.setMargem(rs.getFloat("MARGEM"));
			listEstoque.add(estoque);
			}
		return listEstoque;
	}
}
