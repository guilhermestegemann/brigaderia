package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.jdbcinterface.VendasDAO;
import br.com.brigaderia.objetos.VendaPorClienteVO;
import br.com.brigaderia.objetos.VendaPorProdutoVO;



public class JDBCVendasDAO implements VendasDAO{
	
	private Connection conexao;
	
	public JDBCVendasDAO (Connection conexao) {
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
	
	public List<VendaPorClienteVO> gerarPorCliente(String dataInicio, String dataFim, int cliente, int numReg) throws SQLException {
		String where = "";
		
		if ((!dataInicio.equals("null") && !dataInicio.equals("")) && (!dataFim.equals("null") && !dataFim.equals(""))) {
			where = " AND PEDIDO.EMISSAO BETWEEN '" + dataInicio + "' AND '" + dataFim + "' ";
		}
		if (cliente != 0) {
			where += "AND PEDIDO.CLIENTE = " + cliente;
		}
		String sql = "SELECT PEDIDO.CLIENTE, "
				   + "CLIENTE.NOME, "
				   + "COUNT(PEDIDO.NUMERO) AS PEDIDOS, "
				   + "PEDIDO.TOTAL / COUNT(PEDIDO.NUMERO) AS VALORMEDIOPEDIDO, "
				   + "SUM(PEDIDO.TOTAL) AS TOTAL, "
				   + "SUM(ITEMPEDIDO.TOTALCUSTO) AS CUSTO "
				   + "FROM PEDIDO "
				   + "INNER JOIN CLIENTE ON CLIENTE.CODIGO = PEDIDO.CLIENTE "
				   + "INNER JOIN ITEMPEDIDO ON ITEMPEDIDO.NUMERO = PEDIDO.NUMERO "
				   + "WHERE PEDIDO.CANCELADO = 'N' "
				   + "AND PEDIDO.FATURADO = 'S' "
				   + where														  
				   + " GROUP BY 1 ";
		if (numReg > 0) {
			sql += "LIMIT " + numReg;
		}
		List<VendaPorClienteVO> listVenda = new ArrayList<VendaPorClienteVO>();
		VendaPorClienteVO venda = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()){
			venda = new VendaPorClienteVO();
			venda.setCodigo(rs.getInt("CLIENTE"));
			venda.setNome(rs.getString("NOME"));
			venda.setPedidos(rs.getInt("PEDIDOS"));
			venda.setValorMedioPedido(rs.getFloat("VALORMEDIOPEDIDO"));
			venda.setTotal(rs.getFloat("TOTAL"));
			venda.setCusto(rs.getFloat("CUSTO"));
			listVenda.add(venda);
			}
		return listVenda;
	}

}
