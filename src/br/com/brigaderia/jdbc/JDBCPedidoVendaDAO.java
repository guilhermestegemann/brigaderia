package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.brigaderia.exception.ClienteComPedidoException;
import br.com.brigaderia.exception.ProdutoVinculadoEmPedidoVendaException;
import br.com.brigaderia.ferramentas.ConversorDecimal;
import br.com.brigaderia.jdbcinterface.PedidoVendaDAO;
import br.com.brigaderia.objetos.ItemPedidoVenda;
import br.com.brigaderia.objetos.PedidoVenda;
import br.com.brigaderia.objetos.PedidoVendaVO;

public class JDBCPedidoVendaDAO implements PedidoVendaDAO{
	
	private Connection conexao;

	public JDBCPedidoVendaDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public int adicionarPedido(PedidoVenda pedidoVenda) throws SQLException {
		
		String comando = "INSERT INTO PEDIDO (CLIENTE, EMISSAO, FATURADO, PRODUZIDO, CANCELADO, TOTAL) "
				       + "VALUES (?,?,?,?,?,?)";
		PreparedStatement p;
		ConversorDecimal cd = new ConversorDecimal();

		p = this.conexao.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS);
		p.setInt(1, pedidoVenda.getCliente());
		p.setDate(2, new java.sql.Date(pedidoVenda.getDataEmissao().getTime()));
		p.setString(3, pedidoVenda.getFaturado());
		p.setString(4, pedidoVenda.getProduzido());
		p.setString(5, pedidoVenda.getCancelado());
		p.setString(6, cd.convertDoubleString(pedidoVenda.getTotal()));
		p.execute();
		ResultSet generatedKeys = p.getGeneratedKeys();
        if (generatedKeys.next()) {
        	pedidoVenda.setNumero(generatedKeys.getInt(1));
        }
        else {
            throw new SQLException("Erro ao recuperar chave inserida! (Pedido Venda)");
        }
		return pedidoVenda.getNumero();
	}
	
	public void editarPedido (PedidoVenda pedidoVenda) throws SQLException {
		
		String comando = "UPDATE PEDIDO SET " 
							+ "PEDIDO.CLIENTE = ?, "
							+ "PEDIDO.TOTAL = ? "
					   + "WHERE PEDIDO.NUMERO = " + pedidoVenda.getNumero();
	    PreparedStatement p;
    	p = this.conexao.prepareStatement(comando);
    	p.setInt(1, pedidoVenda.getCliente());
    	p.setDouble(2, pedidoVenda.getTotal());
    	p.executeUpdate();
	}
	
	public void adicionarProdutos(int numeroPedido, int codProduto, float qtde, float unitario, float total) throws SQLException{
		float custo = 0;
		float totalCusto = 0;
		String sqlCusto = "SELECT VALORCUSTO FROM PRODUTO WHERE CODIGO = " + codProduto;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(sqlCusto);
		if (rs.next()) {
			custo = rs.getFloat("VALORCUSTO");
		}
		totalCusto = custo * qtde;
		String comando = "INSERT INTO ITEMPEDIDO (NUMERO, PRODUTO, QTDE, UNITARIO, TOTAL, CUSTO, TOTALCUSTO) "
				       + " VALUES (?,?,?,?,?,?,?)";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, numeroPedido);
			p.setInt(2, codProduto);
			p.setFloat(3, qtde);
			p.setFloat(4, unitario);
			p.setFloat(5, total);
			p.setFloat(6, custo);
			p.setFloat(7, totalCusto);
			p.execute();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void verificaPedidoCliente (int codigo) throws SQLException, ClienteComPedidoException  {
		String comando = "SELECT CLIENTE FROM PEDIDO  WHERE PEDIDO.CLIENTE = " + codigo + " LIMIT 1";
		
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		if (rs.next()) {
			throw new ClienteComPedidoException();
		}
	}
	
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmPedidoVendaException  {
		String comando = "SELECT PRODUTO FROM ITEMPEDIDO  WHERE ITEMPEDIDO.PRODUTO = " + codigo + " LIMIT 1";
		
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		if (rs.next()) {
			throw new ProdutoVinculadoEmPedidoVendaException();
		}
	}
	
	public List<PedidoVendaVO> buscarPedidos (String dataInicio, String dataFim, String faturado, String cancelado,
			                                  String produzido, int codCliente) throws SQLException {
		String condicao = "";
		String comando = "SELECT PEDIDO.NUMERO, CLIENTE.NOME AS NOMECLIENTE, PEDIDO.EMISSAO, PEDIDO.TOTAL, "
				       + "PEDIDO.FATURADO, PEDIDO.CANCELADO, PEDIDO.PRODUZIDO "
				       + "FROM PEDIDO "
				       + "INNER JOIN CLIENTE ON CLIENTE.CODIGO = PEDIDO.CLIENTE ";
		if ((!dataInicio.equals("null") && !dataInicio.equals("")) && (!dataFim.equals("null") && !dataFim.equals(""))) {
			condicao += "WHERE PEDIDO.EMISSAO BETWEEN '" + dataInicio + "' AND '" + dataFim + "' ";
		}
		if (condicao.equals("")) {
			condicao = "WHERE PEDIDO.FATURADO = '" + faturado + "' AND PEDIDO.CANCELADO = '" + cancelado + "' "
					  + "AND PEDIDO.PRODUZIDO = '" + produzido + "' ";
		}else{
			condicao += "AND PEDIDO.FATURADO = '" + faturado + "' AND PEDIDO.CANCELADO = '" + cancelado + "' "
					  + "AND PEDIDO.PRODUZIDO = '" + produzido + "' ";
		}
		if(codCliente != 0) {
			condicao += "AND PEDIDO.CLIENTE = " + codCliente;
		}
		comando += condicao +" ORDER BY PEDIDO.NUMERO DESC";
		
		List<PedidoVendaVO> listPedidoVendaVO = new ArrayList<PedidoVendaVO>();
		PedidoVendaVO pedidoVendaVO = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			pedidoVendaVO = new PedidoVendaVO();
			pedidoVendaVO.setNumero(rs.getInt("NUMERO"));
			pedidoVendaVO.setCliente(rs.getString("NOMECLIENTE"));
			pedidoVendaVO.setEmissao(rs.getDate("EMISSAO"));
			pedidoVendaVO.setTotal(rs.getDouble("TOTAL"));
			pedidoVendaVO.setFaturado(rs.getString("FATURADO"));
			pedidoVendaVO.setCancelado(rs.getString("CANCELADO"));
			pedidoVendaVO.setProduzido(rs.getString("PRODUZIDO"));
			listPedidoVendaVO.add(pedidoVendaVO);
		}
		return listPedidoVendaVO;
	}
	
	public PedidoVenda buscarPeloNumero (int numero) throws SQLException {
		
		String comando = "SELECT P.NUMERO, P.CLIENTE, C.NOME AS NOMECLIENTE, P.EMISSAO, P.DATAFATURADO, P.FATURADO, P.PRODUZIDO, "
				       + "P.CANCELADO, P.TOTAL, P.ORDEMPRODUCAO "
				       + "FROM PEDIDO P "
				       + "INNER JOIN CLIENTE C ON C.CODIGO = P.CLIENTE "
				       + "WHERE P.NUMERO = " + numero;
		PedidoVenda pedidoVenda = new PedidoVenda();
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			pedidoVenda.setNumero(rs.getInt("NUMERO"));
			pedidoVenda.setCliente(rs.getInt("CLIENTE"));
			pedidoVenda.setNomeCliente(rs.getString("NOMECLIENTE"));
			pedidoVenda.setDataEmissao(rs.getDate("EMISSAO"));
			pedidoVenda.setDataFaturado(rs.getDate("DATAFATURADO"));
			pedidoVenda.setFaturado(rs.getString("FATURADO"));
			pedidoVenda.setProduzido(rs.getString("PRODUZIDO"));
			pedidoVenda.setCancelado(rs.getString("CANCELADO"));
			pedidoVenda.setTotal(rs.getDouble("TOTAL"));
			pedidoVenda.setOrdemProducao(rs.getInt("ORDEMPRODUCAO"));
		}
		return pedidoVenda;
	}
	
	public List<ItemPedidoVenda> buscarItensPedido(int numero) throws SQLException  {
		
		List<ItemPedidoVenda> listItemPedidoVenda = new ArrayList<ItemPedidoVenda>();
		String comando = "SELECT IP.PRODUTO, P.DESCRICAO, P.UNESTOQUE, P.ESTOQUE, IP.QTDE, IP.UNITARIO, P.VALORVENDA, IP.TOTAL "
				       + "FROM ITEMPEDIDO IP "
				       + "INNER JOIN PRODUTO P ON P.CODIGO = IP.PRODUTO "
				       + "WHERE IP.NUMERO = " + numero;
		ItemPedidoVenda itemPedidoVenda = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			itemPedidoVenda = new ItemPedidoVenda();
			itemPedidoVenda.setCodigoProduto(rs.getInt("PRODUTO"));
			itemPedidoVenda.setDescricao(rs.getString("DESCRICAO"));
			itemPedidoVenda.setUnEstoque(rs.getString("UNESTOQUE"));
			itemPedidoVenda.setEstoque(rs.getFloat("ESTOQUE"));
			itemPedidoVenda.setQtde(rs.getFloat("QTDE"));
			itemPedidoVenda.setUnitario(rs.getFloat("UNITARIO"));
			itemPedidoVenda.setValorVenda(rs.getFloat("VALORVENDA"));
			itemPedidoVenda.setTotal(rs.getFloat("TOTAL"));
			listItemPedidoVenda.add(itemPedidoVenda);
		}
		return listItemPedidoVenda;
	}
	
	public boolean pedidoFaturado (int numero) throws SQLException {
		String comando = "SELECT PEDIDO.FATURADO FROM PEDIDO WHERE PEDIDO.NUMERO = " + numero + " AND PEDIDO.FATURADO = 'S' LIMIT 1";
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		if (rs.next()) {
			return true;
		}else{
			return false;
		}
	}
	
	public void faturarPedido (int numero, Date dataFaturado) throws SQLException {
		String comando = "UPDATE PEDIDO SET PEDIDO.FATURADO = 'S', PEDIDO.CANCELADO = 'N',  PEDIDO.DATAFATURADO = ?"
				       + " WHERE PEDIDO.NUMERO = " + numero;
		PreparedStatement p;
		p = this.conexao.prepareStatement(comando);
		p.setDate(1, new java.sql.Date (dataFaturado.getTime()));
		p.executeUpdate();
	}
	
	public void deletarProdutos(int numero) throws SQLException {
		
		String comando = "DELETE FROM ITEMPEDIDO WHERE ITEMPEDIDO.NUMERO = " + numero;
		Statement p;
		p = this.conexao.createStatement();
		p.execute(comando);	
	}
	
	public void cancelarPedido(int numero) throws SQLException {
		String comando = "UPDATE PEDIDO SET PEDIDO.CANCELADO = 'S' WHERE PEDIDO.NUMERO = " + numero;
		PreparedStatement p = this.conexao.prepareStatement(comando);
		p.executeUpdate();
	}
	
	public void descancelarPedido(int numero) throws SQLException {
		String comando = "UPDATE PEDIDO SET PEDIDO.CANCELADO = 'N', PEDIDO.FATURADO = 'N' WHERE PEDIDO.NUMERO = " + numero;
		PreparedStatement p = this.conexao.prepareStatement(comando);
		p.executeUpdate();
	}
	
	public void deletarPedido (int numero) throws SQLException {
		String comando = "DELETE FROM PEDIDO WHERE PEDIDO.NUMERO = " + numero;
		Statement p = this.conexao.createStatement();
		p.execute(comando);
	}
	
	public List<PedidoVenda> buscarPedidosImportacao() throws SQLException {
		String comando = "SELECT PEDIDO.NUMERO, PEDIDO.CLIENTE, CLIENTE.NOME AS NOMECLIENTE, PEDIDO.EMISSAO, PEDIDO.TOTAL "
				       + "FROM PEDIDO "
				       + "INNER JOIN CLIENTE ON CLIENTE.CODIGO = PEDIDO.CLIENTE "
				       + "WHERE PEDIDO.FATURADO = 'N' "
				       + "AND PEDIDO.CANCELADO = 'N' "
				       + "AND PEDIDO.PRODUZIDO = 'N' "
				       + "AND PEDIDO.ORDEMPRODUCAO IS NULL";
		PedidoVenda pedidoVenda = null;
		List<PedidoVenda> listaPedidos = new ArrayList<>();
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while (rs.next()) {
			pedidoVenda = new PedidoVenda();
			pedidoVenda.setNumero(rs.getInt("NUMERO"));
			pedidoVenda.setCliente(rs.getInt("CLIENTE"));
			pedidoVenda.setNomeCliente(rs.getString("NOMECLIENTE"));
			pedidoVenda.setDataEmissao(rs.getDate("EMISSAO"));
			pedidoVenda.setTotal(rs.getDouble("TOTAL"));
			pedidoVenda.setItemPedidoVenda(buscarItensPedido(pedidoVenda.getNumero()));
			listaPedidos.add(pedidoVenda);
		}
		return listaPedidos;
	}
}







