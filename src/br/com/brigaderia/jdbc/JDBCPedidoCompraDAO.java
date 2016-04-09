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
import br.com.brigaderia.objetos.PedidoCompra;

public class JDBCPedidoCompraDAO implements PedidoCompraDAO {
	
	private Connection conexao;

	public JDBCPedidoCompraDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public int adicionarPedido(PedidoCompra pedidoCompra) throws BrigaderiaException {
		String comando = "INSERT INTO COMPRA (DATA, TOTAL) VALUES (?,?)";
		PreparedStatement p;
		ConversorDecimal cd = new ConversorDecimal();
		try {
			p = this.conexao.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS);
			p.setDate(1, new java.sql.Date(pedidoCompra.getData().getTime()));
			p.setString(2, cd.convertDoubleString(pedidoCompra.getTotal()));
			System.out.println(cd.convertDoubleString(pedidoCompra.getTotal()));
			p.execute();
			try (ResultSet generatedKeys = p.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	pedidoCompra.setNumero(generatedKeys.getInt(1));
	            }
	            else {
	                throw new SQLException("Erro ao recuperar chave inserida! (Pedido Compra)");
	            }
			} 
			
			return pedidoCompra.getNumero();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}
	}
	
	public void adicionarProdutos(int numeroPedido, int codProduto, float qtde, float unitario, float total) throws BrigaderiaException, SQLException {
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
	
	public void atualizarEstoque(int codProduto, float qtde, float unitario) throws SQLException {
		String sqlProduto = "SELECT PRODUTO.VALORCUSTO, PRODUTO.ESTOQUE "
						  + "FROM PRODUTO "
						  + "WHERE PRODUTO.CODIGO = " + codProduto;
		
		float valorCusto = 0;
		float estoque = 0;
		String updateProduto = "";
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(sqlProduto);
			while(rs.next()) {
				valorCusto = rs.getFloat("VALORCUSTO");
				estoque = rs.getFloat("ESTOQUE");
			}
			//Custo
			if (estoque <= 0) {
				if(unitario > 0) {
					updateProduto = "UPDATE PRODUTO SET PRODUTO.VALORCUSTO = " + unitario
								  + ", PRODUTO.ESTOQUE = ESTOQUE + " + qtde
								  + " WHERE PRODUTO.CODIGO = " + codProduto;
				}
			}else{
				float custoMedio =((valorCusto * estoque) + (unitario * qtde))/(estoque + qtde);
				updateProduto = "UPDATE PRODUTO SET PRODUTO.VALORCUSTO = " + custoMedio
				              + ", PRODUTO.ESTOQUE = ESTOQUE + " + qtde
				              + " WHERE PRODUTO.CODIGO = " + codProduto;
			}//Fim Custo
			
			PreparedStatement p = this.conexao.prepareStatement(updateProduto);
			p.execute();
		}catch (SQLException e) {
			throw e;
		}
	}
	
	public void countProdutos(int codigo) throws BrigaderiaException {
		String comando = "SELECT COUNT(*) AS QTDEPRODUTO FROM ITEMCOMPRA  WHERE ITEMCOMPRA.PRODUTO = " + codigo;
		int qtdeProduto = 0;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()) {
				qtdeProduto = rs.getInt("QTDEPRODUTO");
			}
			if (qtdeProduto > 0) {
				throw new ProdutoVinculadoEmPedidoCompraException();
			}
		}catch (BrigaderiaException e) {
			e.printStackTrace();
			throw e;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}
	}
	
	public List<PedidoCompra> buscarPedidos (String dataInicio, String dataFim) throws BrigaderiaException {
		
		String comando = "SELECT * FROM COMPRA ";
		
		if ((!dataInicio.equals("null") && !dataInicio.equals("")) && (!dataFim.equals("null") && !dataFim.equals(""))) {
			comando += "WHERE COMPRA.DATA BETWEEN '" + dataInicio + "' AND '" + dataFim + "'";
		}
		
		List<PedidoCompra> listPedidoCompra = new ArrayList<PedidoCompra>();
		PedidoCompra pedidoCompra = null;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()) {
				pedidoCompra = new PedidoCompra();
				pedidoCompra.setNumero(rs.getInt("NUMERO"));
				pedidoCompra.setData(rs.getDate("DATA"));
				pedidoCompra.setTotal(rs.getDouble("TOTAL"));
				pedidoCompra.setAtualizado(rs.getString("ATUALIZADO"));
				listPedidoCompra.add(pedidoCompra);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new BrigaderiaException();
		}
		return listPedidoCompra;
	}
}
