package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.jdbcinterface.VendaPorProdutoDAO;
import br.com.brigaderia.objetos.VendaPorProdutoVO;



public class JDBCVendaPorProdutoDAO implements VendaPorProdutoDAO{
	
	private Connection conexao;
	
	public JDBCVendaPorProdutoDAO (Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public List<VendaPorProdutoVO> gerarPorProduto(String dataInicio, String dataFim, int produto, int numReg) throws SQLException {
		String where = "";
		
		if ((!dataInicio.equals("null") && !dataInicio.equals("")) && (!dataFim.equals("null") && !dataFim.equals(""))) {
			where = " AND PEDIDO.EMISSAO BETWEEN '" + dataInicio + "' AND '" + dataFim + "' ";
		}
		if (produto != 0) {
			where += "AND ITEMPEDIDO.PRODUTO = " + produto;
		}
		String sql = "SELECT ITEMPEDIDO.PRODUTO, "
				   + "PRODUTO.DESCRICAO, "
				   + "SUM(ITEMPEDIDO.QTDE) AS QTDE, "
				   + "SUM(ITEMPEDIDO.TOTAL) / SUM(ITEMPEDIDO.QTDE) AS VALORMEDIO, "
				   + "SUM(ITEMPEDIDO.TOTAL) AS TOTAL, "
				   + "(SUM(ITEMPEDIDO.CUSTO) * SUM(ITEMPEDIDO.QTDE))  AS CUSTO "
				   + "FROM ITEMPEDIDO "
				   + "INNER JOIN PRODUTO ON PRODUTO.CODIGO = ITEMPEDIDO.PRODUTO "
				   + "INNER JOIN PEDIDO ON PEDIDO.NUMERO = ITEMPEDIDO.NUMERO "
				   + "WHERE PEDIDO.CANCELADO = 'N' "
				   + "AND PEDIDO.FATURADO = 'S' "
				   + where														  
				   + " GROUP BY 1 ";
		if (numReg > 0) {
			sql += "LIMIT " + numReg;
		}
		List<VendaPorProdutoVO> listVenda = new ArrayList<VendaPorProdutoVO>();
		VendaPorProdutoVO venda = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()){
			venda = new VendaPorProdutoVO();
			venda.setCodigo(rs.getInt("PRODUTO"));
			venda.setNome(rs.getString("DESCRICAO"));
			venda.setQtde(rs.getFloat("QTDE"));
			venda.setValorMedio(rs.getFloat("VALORMEDIO"));
			venda.setTotal(rs.getFloat("TOTAL"));
			venda.setCusto(rs.getFloat("CUSTO"));
			listVenda.add(venda);
			}
		return listVenda;
	}

}
