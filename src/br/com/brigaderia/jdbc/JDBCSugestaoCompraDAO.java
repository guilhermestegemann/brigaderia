package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.jdbcinterface.SugestaoCompraDAO;
import br.com.brigaderia.objetos.SugestaoCompraVO;



public class JDBCSugestaoCompraDAO implements SugestaoCompraDAO{
	
	private Connection conexao;
	
	public JDBCSugestaoCompraDAO (Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public List<SugestaoCompraVO> gerarSugestao(int cliente, String dataInicio, String dataFim) throws SQLException {
		String where = "";
		if ((!dataInicio.equals("null") && !dataInicio.equals("")) && (!dataFim.equals("null") && !dataFim.equals(""))) {
			where = " WHERE PEDIDO.EMISSAO BETWEEN '" + dataInicio + "' AND '" + dataFim + "' ";
		}
		if (cliente != 0) {
			if (where.equals("")) {
				where = " WHERE PEDIDO.CLIENTE = " + cliente;
			}else{
				where += "AND PEDIDO.CLIENTE = " + cliente;
			}
		}
		String sql = "SELECT DISTINCT " 
						+ "ITEMFICHATECNICA.INGREDIENTE, "
						+ "PRODUTO.DESCRICAO AS NOMEINGREDIENTE, "
						+ "PRODUTO.UNESTOQUE, "
						+ "ITEMFICHATECNICA.QTDE AS ITEMFICHAQTDE, "
						+ "FICHATECNICA.QTDE AS FICHAQTDE, "
						+ "SUM(ITEMPEDIDO.QTDE) AS QTDEPEDIDO, "
						+ "PRODUTO.ESTOQUE, "
						+ "PRODUTO.VALORCUSTO, "
						+ "(ITEMFICHATECNICA.QTDE * (SUM(ITEMPEDIDO.QTDE) / FICHATECNICA.QTDE)) AS NECESSARIO, "
						+ "((ITEMFICHATECNICA.QTDE * (SUM(ITEMPEDIDO.QTDE) / FICHATECNICA.QTDE)) - PRODUTO.ESTOQUE) AS SUGESTAO, "
						+ "CAST((((ITEMFICHATECNICA.QTDE * (SUM(ITEMPEDIDO.QTDE) / FICHATECNICA.QTDE)) - PRODUTO.ESTOQUE) * PRODUTO.VALORCUSTO) AS DECIMAL(7,2)) AS CUSTOSUGESTAO "
				   + "FROM ITEMFICHATECNICA "
				   + "INNER JOIN FICHATECNICA ON FICHATECNICA.CODIGO = ITEMFICHATECNICA.FICHATECNICA "
				   + "INNER JOIN ITEMPEDIDO ON ITEMPEDIDO.PRODUTO = FICHATECNICA.PRODUTO "
				   + "INNER JOIN PEDIDO ON PEDIDO.NUMERO = ITEMPEDIDO.NUMERO AND PEDIDO.CANCELADO = 'N' "
				   														  + "AND PEDIDO.FATURADO = 'N' "
				   														  + "AND PEDIDO.PRODUZIDO = 'N' "
				   														  + "AND PEDIDO.ORDEMPRODUCAO IS NULL "
				   + "INNER JOIN PRODUTO ON PRODUTO.CODIGO = ITEMFICHATECNICA.INGREDIENTE "
				   + where														  
				   + " GROUP BY 1 "
				   + "HAVING ((ITEMFICHATECNICA.QTDE * (SUM(ITEMPEDIDO.QTDE) / FICHATECNICA.QTDE)) - PRODUTO.ESTOQUE) > 0 ";
												   
		List<SugestaoCompraVO> listSugestao = new ArrayList<SugestaoCompraVO>();
		SugestaoCompraVO sugestao = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()){
			sugestao = new SugestaoCompraVO();
			sugestao.setIngrediente(rs.getInt("INGREDIENTE"));
			sugestao.setNomeIngrediente(rs.getString("NOMEINGREDIENTE"));
			sugestao.setUnEstoque(rs.getString("UNESTOQUE"));
			sugestao.setItemFichaQtde(rs.getFloat("ITEMFICHAQTDE"));
			sugestao.setFichaQtde(rs.getFloat("FICHAQTDE"));
			sugestao.setQtdePedido(rs.getFloat("QTDEPEDIDO"));
			sugestao.setEstoque(rs.getFloat("ESTOQUE"));
			sugestao.setValorCusto(rs.getFloat("VALORCUSTO"));
			sugestao.setQtdeNecessaria(rs.getFloat("NECESSARIO"));
			sugestao.setQtdeSugestao(rs.getFloat("SUGESTAO"));
			sugestao.setCustoSugestão(rs.getFloat("CUSTOSUGESTAO"));
			listSugestao.add(sugestao);
			}
		return listSugestao;
	}

}
