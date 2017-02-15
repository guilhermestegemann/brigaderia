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
	
	public List<VendaAnualVO> vendaAnual(int ano) throws SQLException {
		
		String sql = 
	"SELECT " + 
        "'Janeiro' AS MES, " +
        "(CASE " +
            "WHEN (SUM(PEDIDO.TOTAL) IS NOT NULL) THEN SUM(PEDIDO.TOTAL) " +
            "ELSE 0 " +
        "END) AS TOTAL " +
    "FROM " +
        "PEDIDO " +
    "WHERE " +
        "((EXTRACT(MONTH FROM PEDIDO.EMISSAO) = 1) " +
            "AND (EXTRACT(YEAR FROM PEDIDO.EMISSAO) = "+ano+") " +
            "AND (PEDIDO.FATURADO = 'S') " +
            "AND (PEDIDO.CANCELADO = 'N')) " +
    "UNION SELECT " +
        "'Fevereiro' AS MES, " +
        "(CASE " +
            "WHEN (SUM(PEDIDO.TOTAL) IS NOT NULL) THEN SUM(PEDIDO.TOTAL) " +
            "ELSE 0 " +
        "END) AS TOTAL " +
    "FROM " +
    	"PEDIDO " +
    "WHERE " +
        "((EXTRACT(MONTH FROM PEDIDO.EMISSAO) = 2) " +
            "AND (EXTRACT(YEAR FROM PEDIDO.EMISSAO) = "+ano+") " +
            "AND (PEDIDO.FATURADO = 'S') " +
            "AND (PEDIDO.CANCELADO = 'N')) " +
    "UNION SELECT " +
        "'Março' AS MES, " +
        "(CASE " +
            "WHEN (SUM(PEDIDO.TOTAL) IS NOT NULL) THEN SUM(PEDIDO.TOTAL) " +
            "ELSE 0 " +
        "END) AS TOTAL " +
    "FROM " +
    	"PEDIDO " +
    "WHERE " +
        "((EXTRACT(MONTH FROM PEDIDO.EMISSAO) = 3) " +
            "AND (EXTRACT(YEAR FROM PEDIDO.EMISSAO) = "+ano+") " +
            "AND (PEDIDO.FATURADO = 'S') " +
            "AND (PEDIDO.CANCELADO = 'N')) " +
    "UNION SELECT " +
        "'Abril' AS MES, " +
        "(CASE " +
            "WHEN (SUM(PEDIDO.TOTAL) IS NOT NULL) THEN SUM(PEDIDO.TOTAL) " +
            "ELSE 0 " +
        "END) AS TOTAL " +
    "FROM " +
    	"PEDIDO " +
    "WHERE " +
        "((EXTRACT(MONTH FROM PEDIDO.EMISSAO) = 4) " +
            "AND (EXTRACT(YEAR FROM PEDIDO.EMISSAO) = "+ano+") " +
            "AND (PEDIDO.FATURADO = 'S') " +
            "AND (PEDIDO.CANCELADO = 'N')) " +
    "UNION SELECT " +
        "'Maio' AS MES," +
        "(CASE " +
            "WHEN (SUM(PEDIDO.TOTAL) IS NOT NULL) THEN SUM(PEDIDO.TOTAL) " +
            "ELSE 0 " +
        "END) AS TOTAL " +
    "FROM " +
    	"PEDIDO " +
    "WHERE " +
        "((EXTRACT(MONTH FROM PEDIDO.EMISSAO) = 5) " +
            "AND (EXTRACT(YEAR FROM PEDIDO.EMISSAO) = "+ano+") " +
            "AND (PEDIDO.FATURADO = 'S') " + 
            "AND (PEDIDO.CANCELADO = 'N')) "  +
    "UNION SELECT " +
        "'Junho' AS MES, " +
        "(CASE " +
            "WHEN (SUM(PEDIDO.TOTAL) IS NOT NULL) THEN SUM(PEDIDO.TOTAL) " +
            "ELSE 0 " +
        "END) AS TOTAL " +
    "FROM " +
    	"PEDIDO " +
    "WHERE " +
        "((EXTRACT(MONTH FROM PEDIDO.EMISSAO) = 6) " +
            "AND (EXTRACT(YEAR FROM PEDIDO.EMISSAO) = "+ano+") " +
            "AND (PEDIDO.FATURADO = 'S') " +
            "AND (PEDIDO.CANCELADO = 'N')) "  +
    "UNION SELECT " +
        "'Julho' AS MES, " +
        "(CASE " +
            "WHEN (SUM(PEDIDO.TOTAL) IS NOT NULL) THEN SUM(PEDIDO.TOTAL) " +
            "ELSE 0 " +
        "END) AS TOTAL " +
    "FROM " +
    	"PEDIDO " +
    "WHERE " +
        "((EXTRACT(MONTH FROM PEDIDO.EMISSAO) = 7) " +
            "AND (EXTRACT(YEAR FROM PEDIDO.EMISSAO) = "+ano+") " +
            "AND (PEDIDO.FATURADO = 'S') " +
            "AND (PEDIDO.CANCELADO = 'N')) "  +
    "UNION SELECT " +
        "'Agosto' AS MES, " +
        "(CASE " +
            "WHEN (SUM(PEDIDO.TOTAL) IS NOT NULL) THEN SUM(PEDIDO.TOTAL) " +
            "ELSE 0 " +
        "END) AS TOTAL " +
    "FROM " +
    	"PEDIDO " +
    "WHERE " +
        "((EXTRACT(MONTH FROM PEDIDO.EMISSAO) = 8) " +
            "AND (EXTRACT(YEAR FROM PEDIDO.EMISSAO) = "+ano+") " +
            "AND (PEDIDO.FATURADO = 'S') " +
            "AND (PEDIDO.CANCELADO = 'N')) "  +
    "UNION SELECT " +
        "'Setembro' AS MES, " +
        "(CASE " +
            "WHEN (SUM(PEDIDO.TOTAL) IS NOT NULL) THEN SUM(PEDIDO.TOTAL) " +
            "ELSE 0 " +
        "END) AS TOTAL " +
    "FROM " +
    	"PEDIDO " +
    "WHERE " +
        "((EXTRACT(MONTH FROM PEDIDO.EMISSAO) = 9) " +
            "AND (EXTRACT(YEAR FROM PEDIDO.EMISSAO) = "+ano+") " +
            "AND (PEDIDO.FATURADO = 'S') " +
            "AND (PEDIDO.CANCELADO = 'N')) "  +
    "UNION SELECT " +
        "'Outubro' AS MES, " +
        "(CASE " +
            "WHEN (SUM(PEDIDO.TOTAL) IS NOT NULL) THEN SUM(PEDIDO.TOTAL) " +
            "ELSE 0 " +
        "END) AS TOTAL " +
    "FROM " +
    	"PEDIDO " +
    "WHERE " +
        "((EXTRACT(MONTH FROM PEDIDO.EMISSAO) = 10) " +
            "AND (EXTRACT(YEAR FROM PEDIDO.EMISSAO) = "+ano+") " +
            "AND (PEDIDO.FATURADO = 'S') " +
            "AND (PEDIDO.CANCELADO = 'N')) "  +
    "UNION SELECT " +
        "'Novembro' AS MES, " +
        "(CASE " +
            "WHEN (SUM(PEDIDO.TOTAL) IS NOT NULL) THEN SUM(PEDIDO.TOTAL) " +
            "ELSE 0 " +
        "END) AS TOTAL " +
    "FROM " +
    	"PEDIDO " +
    "WHERE " +
        "((EXTRACT(MONTH FROM PEDIDO.EMISSAO) = 11) " +
            "AND (EXTRACT(YEAR FROM PEDIDO.EMISSAO) = "+ano+") " +
            "AND (PEDIDO.FATURADO = 'S') " +
            "AND (PEDIDO.CANCELADO = 'N')) " + 
    "UNION SELECT "  +
        "'Dezembro' AS MES, " +
        "(CASE " +
            "WHEN (SUM(PEDIDO.TOTAL) IS NOT NULL) THEN SUM(PEDIDO.TOTAL) " +
            "ELSE 0 " +
        "END) AS TOTAL " +
    "FROM " +
    	"PEDIDO " +
    "WHERE " +
        "((EXTRACT(MONTH FROM PEDIDO.EMISSAO) = 12) " +
            "AND (EXTRACT(YEAR FROM PEDIDO.EMISSAO) = "+ano+") " +
            "AND (PEDIDO.FATURADO = 'S') " +
            "AND (PEDIDO.CANCELADO = 'N'))";
												   
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
