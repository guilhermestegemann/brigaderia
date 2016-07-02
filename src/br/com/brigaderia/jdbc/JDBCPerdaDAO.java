package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.exception.ProdutoVinculadoEmPerdaException;
import br.com.brigaderia.ferramentas.ConversorDecimal;
import br.com.brigaderia.jdbcinterface.PerdaDAO;
import br.com.brigaderia.objetos.ItemPedidoVenda;
import br.com.brigaderia.objetos.ItemPerda;
import br.com.brigaderia.objetos.PedidoCompra;
import br.com.brigaderia.objetos.Perda;

public class JDBCPerdaDAO implements PerdaDAO{
	
	private Connection conexao;

	public JDBCPerdaDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public int adicionarPerda(Perda perda) throws SQLException {
		
		String comando = "INSERT INTO PERDA (DATA, TOTAL) VALUES (?,?)";
		PreparedStatement p;
		ConversorDecimal cd = new ConversorDecimal();

		p = this.conexao.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS);
		p.setDate(1, new java.sql.Date(perda.getData().getTime()));
		p.setString(2, cd.convertDoubleString(perda.getTotal()));
		p.execute();
		ResultSet generatedKeys = p.getGeneratedKeys();
        if (generatedKeys.next()) {
        	perda.setNumero(generatedKeys.getInt(1));
        }
        else {
            throw new SQLException("Erro ao recuperar chave inserida! (Pedido Compra)");
        }
		return perda.getNumero();
	}
	
	public void adicionarProdutos(int numeroPerda, int codProduto, float qtde, float unitario, float total) throws SQLException{
		String comando = "INSERT INTO ITEMPERDA (NUMERO, PRODUTO, QTDE, UNITARIO, TOTAL) VALUES (?,?,?,?,?)";
		PreparedStatement p;
		try{
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, numeroPerda);
			p.setInt(2, codProduto);
			p.setFloat(3, qtde);
			p.setFloat(4, unitario);
			p.setFloat(5, total);
			p.execute();
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<ItemPerda> buscarItensPerda(int numero) throws SQLException  {
		
		List<ItemPerda> listItemPerda = new ArrayList<ItemPerda>();
		String comando = "SELECT IP.PRODUTO, P.DESCRICAO, P.UNESTOQUE, P.ESTOQUE, IP.QTDE, IP.UNITARIO, IP.TOTAL "
				       + "FROM ITEMPERDA IP "
				       + "INNER JOIN PRODUTO P ON P.CODIGO = IP.PRODUTO "
				       + "WHERE IP.NUMERO = " + numero;
		ItemPerda itemPerda = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			itemPerda = new ItemPerda();
			itemPerda.setCodigoProduto(rs.getInt("PRODUTO"));
			itemPerda.setDescricao(rs.getString("DESCRICAO"));
			itemPerda.setUnEstoque(rs.getString("UNESTOQUE"));
			itemPerda.setEstoque(rs.getFloat("ESTOQUE"));
			itemPerda.setQtde(rs.getFloat("QTDE"));
			itemPerda.setUnitario(rs.getFloat("UNITARIO"));
			itemPerda.setTotal(rs.getFloat("TOTAL"));
			listItemPerda.add(itemPerda);
		}
		return listItemPerda;
	}
	
	public List<Perda> buscarPerdas (String dataInicio, String dataFim) throws SQLException {
		
		String comando = "SELECT * FROM PERDA ";
		if ((!dataInicio.equals("null") && !dataInicio.equals("")) && (!dataFim.equals("null") && !dataFim.equals(""))) {
			comando += "WHERE PERDA.DATA BETWEEN '" + dataInicio + "' AND '" + dataFim + "'";
		}
		comando += " ORDER BY DATA DESC";
		
		List<Perda> listPerda= new ArrayList<Perda>();
		Perda perda = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			perda = new Perda();
			perda.setNumero(rs.getInt("NUMERO"));
			perda.setData(rs.getDate("DATA"));
			perda.setTotal(rs.getDouble("TOTAL"));
			listPerda.add(perda);
		}
		return listPerda;
	}
	
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmPerdaException  {
		String comando = "SELECT PRODUTO FROM ITEMPERDA  WHERE ITEMPERDA.PRODUTO = " + codigo + " LIMIT 1";
		
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		if (rs.next()) {
			throw new ProdutoVinculadoEmPerdaException();
		}
	}
}
