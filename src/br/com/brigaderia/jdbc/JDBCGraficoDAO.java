package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.jdbcinterface.GraficoDAO;
import br.com.brigaderia.objetos.VendaAnualVO;
import br.com.brigaderia.objetos.VendaPorProdutoVO;



public class JDBCGraficoDAO implements GraficoDAO{
	
	private Connection conexao;
	
	public JDBCGraficoDAO (Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public List<VendaAnualVO> vendaAnual() throws SQLException {
		
		String sql = "SELECT * FROM VIEW_VENDAANUAL";
												   
		List<VendaAnualVO> listVendaAnual = new ArrayList<VendaAnualVO>();
		VendaAnualVO venda = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()){
			venda = new VendaAnualVO();
			venda.setMes(rs.getString("MES"));
			venda.setTotal(rs.getFloat("TOTAL"));
			listVendaAnual.add(venda);
			}
		return listVendaAnual;
	}
	
	public List<VendaPorProdutoVO> gerarPorProduto(String dataInicio, String dataFim, String orderBy, int numReg) throws SQLException {
		String sql = " SELECT PRODUTO.DESCRICAO, SUM(ITEMPEDIDO.TOTAL) AS TOTAL " +
	                 " FROM ITEMPEDIDO " +
				     " INNER JOIN PRODUTO ON PRODUTO.CODIGO = ITEMPEDIDO.PRODUTO " +
	                 " INNER JOIN PEDIDO ON PEDIDO.NUMERO = ITEMPEDIDO.NUMERO AND PEDIDO.FATURADO = 'S' " +
				                                                            " AND PEDIDO.CANCELADO = 'N' ";
		if ((!dataInicio.equals("null") && !dataInicio.equals("")) && (!dataFim.equals("null") && !dataFim.equals(""))) {
			sql += " WHERE PEDIDO.EMISSAO BETWEEN '" + dataInicio + "' AND '" + dataFim + "' ";
		}
		sql+= " GROUP BY 1 ";
		sql+= " ORDER BY TOTAL ";
		if (orderBy.equals("desc")){
			sql += orderBy;
		}
		if (numReg > 0) {
			sql += " LIMIT " + numReg;
		}
		List<VendaPorProdutoVO> listVenda = new ArrayList<VendaPorProdutoVO>();
		VendaPorProdutoVO venda = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()){
			venda = new VendaPorProdutoVO();
			venda.setNome(rs.getString("DESCRICAO"));
			venda.setTotal(rs.getFloat("TOTAL"));
			listVenda.add(venda);
			}
		return listVenda;
	}

}
