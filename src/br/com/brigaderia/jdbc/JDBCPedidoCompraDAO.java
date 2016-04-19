package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.exception.AtualizarEstoqueException;
import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.ProdutoVinculadoEmPedidoCompraException;
import br.com.brigaderia.ferramentas.ConversorDecimal;
import br.com.brigaderia.jdbcinterface.PedidoCompraDAO;
import br.com.brigaderia.objetos.ItemPedidoCompra;
import br.com.brigaderia.objetos.PedidoCompra;

public class JDBCPedidoCompraDAO implements PedidoCompraDAO {
	
	private Connection conexao;

	public JDBCPedidoCompraDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public int adicionarPedido(PedidoCompra pedidoCompra) throws SQLException {
		String comando = "INSERT INTO COMPRA (DATA, TOTAL) VALUES (?,?)";
		PreparedStatement p;
		ConversorDecimal cd = new ConversorDecimal();

		p = this.conexao.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS);
		p.setDate(1, new java.sql.Date(pedidoCompra.getData().getTime()));
		p.setString(2, cd.convertDoubleString(pedidoCompra.getTotal()));
		p.execute();
		ResultSet generatedKeys = p.getGeneratedKeys();
        if (generatedKeys.next()) {
        	pedidoCompra.setNumero(generatedKeys.getInt(1));
        }
        else {
            throw new SQLException("Erro ao recuperar chave inserida! (Pedido Compra)");
        }
		return pedidoCompra.getNumero();
	}
	
	public void adicionarProdutos(int numeroPedido, int codProduto, float qtde, float unitario, float total) throws AtualizarEstoqueException {
		String comando = "INSERT INTO ITEMCOMPRA (NUMERO, PRODUTO, QTDE, UNITARIO, TOTAL) VALUES (?,?,?,?,?)";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, numeroPedido);
			p.setInt(2, codProduto);
			p.setFloat(3, qtde);
			p.setFloat(4, unitario);
			p.setFloat(5, total);
			p.execute();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new AtualizarEstoqueException();
		}
	}
	
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmPedidoCompraException {
		String comando = "SELECT COUNT(*) AS QTDEPRODUTO FROM ITEMCOMPRA  WHERE ITEMCOMPRA.PRODUTO = " + codigo;
		int qtdeProduto = 0;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			qtdeProduto = rs.getInt("QTDEPRODUTO");
		}
		if (qtdeProduto > 0) {
			throw new ProdutoVinculadoEmPedidoCompraException();
		}
		
	}
	
		

	public List<PedidoCompra> buscarPedidos (String dataInicio, String dataFim) throws SQLException {
		
		String comando = "SELECT * FROM COMPRA ";
		
		if ((!dataInicio.equals("null") && !dataInicio.equals("")) && (!dataFim.equals("null") && !dataFim.equals(""))) {
			comando += "WHERE COMPRA.DATA BETWEEN '" + dataInicio + "' AND '" + dataFim + "'";
		}
		
		comando += " ORDER BY DATA DESC";
		
		List<PedidoCompra> listPedidoCompra = new ArrayList<PedidoCompra>();
		PedidoCompra pedidoCompra = null;
	
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			pedidoCompra = new PedidoCompra();
			pedidoCompra.setNumero(rs.getInt("NUMERO"));
			pedidoCompra.setData(rs.getDate("DATA"));
			pedidoCompra.setTotal(rs.getDouble("TOTAL"));
			listPedidoCompra.add(pedidoCompra);
		}
		return listPedidoCompra;
	}
	
	public PedidoCompra buscarPeloNumero (int numero) throws SQLException {
		String comando = "SELECT * FROM COMPRA WHERE COMPRA.NUMERO = " + numero;
		PedidoCompra pedidoCompra = new PedidoCompra();
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			pedidoCompra.setNumero(rs.getInt("NUMERO"));
			pedidoCompra.setData(rs.getDate("DATA"));
			pedidoCompra.setTotal(rs.getDouble("TOTAL"));
		}
		return pedidoCompra;
	}
	
	public List<ItemPedidoCompra> buscarItensPedido(int numero) throws SQLException  {
		List<ItemPedidoCompra> listItemPedidoCompra = new ArrayList<ItemPedidoCompra>();
		String comando = "SELECT ITEMCOMPRA.PRODUTO, PRODUTO.DESCRICAO, PRODUTO.ESTOQUE, PRODUTO.UNENTRADA, ITEMCOMPRA.QTDE, ITEMCOMPRA.UNITARIO, ITEMCOMPRA.TOTAL "
				       + "FROM ITEMCOMPRA "
				       + "INNER JOIN PRODUTO ON PRODUTO.CODIGO = ITEMCOMPRA.PRODUTO "
				       + "WHERE ITEMCOMPRA.NUMERO = " + numero;
		ItemPedidoCompra itemPedidoCompra = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			itemPedidoCompra = new ItemPedidoCompra();
			itemPedidoCompra.setCodigoProduto(rs.getInt("PRODUTO"));
			itemPedidoCompra.setDescricao(rs.getString("DESCRICAO"));
			itemPedidoCompra.setEstoque(rs.getFloat("ESTOQUE"));
			itemPedidoCompra.setUnEntrada(rs.getString("UNENTRADA"));
			itemPedidoCompra.setQtde(rs.getFloat("QTDE"));
			itemPedidoCompra.setUnitario(rs.getFloat("UNITARIO"));
			itemPedidoCompra.setTotal(rs.getFloat("TOTAL"));
			listItemPedidoCompra.add(itemPedidoCompra);
		}
		return listItemPedidoCompra;
	}
	
	public void deletarPedido(int numero) throws SQLException {
		String comando = "DELETE FROM COMPRA WHERE COMPRA.NUMERO = " + numero;
		Statement p;
		p = this.conexao.createStatement();
		p.execute(comando);	
	}
	
}
