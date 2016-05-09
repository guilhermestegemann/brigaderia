package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.brigaderia.exception.ClienteComPedidoException;
import br.com.brigaderia.exception.ProdutoVinculadoEmPedidoVendaException;
import br.com.brigaderia.ferramentas.ConversorDecimal;
import br.com.brigaderia.jdbcinterface.PedidoVendaDAO;
import br.com.brigaderia.objetos.PedidoVenda;

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
	
	public void adicionarProdutos(int numeroPedido, int codProduto, float qtde, float unitario, float total) {
		
		String comando = "INSERT INTO ITEMPEDIDO (NUMERO, PRODUTO, QTDE, UNITARIO, TOTAL) VALUES (?,?,?,?,?)";
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
}