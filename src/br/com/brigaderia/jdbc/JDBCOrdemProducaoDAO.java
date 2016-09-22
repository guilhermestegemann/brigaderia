package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.exception.ProdutoVinculadoEmOrdemProducaoException;
import br.com.brigaderia.jdbcinterface.OrdemProducaoDAO;
import br.com.brigaderia.objetos.ItemOrdemProducao;
import br.com.brigaderia.objetos.OrdemProducao;

public class JDBCOrdemProducaoDAO implements OrdemProducaoDAO {
	
	private Connection conexao;

	public JDBCOrdemProducaoDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public void countProdutos(int codigo) throws SQLException, ProdutoVinculadoEmOrdemProducaoException {
		String comando = "SELECT PRODUTO FROM ITEMORDEMPRODUCAO  WHERE ITEMORDEMPRODUCAO.PRODUTO = " + codigo + " LIMIT 1";
		
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		if (rs.next()) {
			throw new ProdutoVinculadoEmOrdemProducaoException();
		}
	}
	
	public int adicionarOrdemProducao(OrdemProducao ordemProducao) throws SQLException {
		
		String comando = "INSERT INTO ORDEMPRODUCAO (DATA, EMPRODUCAO, PRODUZIDA, CANCELADA) VALUES (?,?,?,?)";
		PreparedStatement p;
		

		p = this.conexao.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS);
		p.setDate(1, new java.sql.Date(ordemProducao.getData().getTime()));
		p.setString(2, "N");
		p.setString(3, "N");
		p.setString(4, "N");
		p.execute();
		ResultSet generatedKeys = p.getGeneratedKeys();
        if (generatedKeys.next()) {
        	ordemProducao.setNumero(generatedKeys.getInt(1));
        }
        else {
            throw new SQLException("Erro ao recuperar chave inserida! (Ordem Producao)");
        }
		return ordemProducao.getNumero();
	}
	
	public void adicionarProdutos(int numeroOrdemProducao, int codProduto, float qtde) throws SQLException{
		String comando = "INSERT INTO ITEMORDEMPRODUCAO (ORDEMPRODUCAO, PRODUTO, QTDE) VALUES (?,?,?)";
		PreparedStatement p;
		try{
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, numeroOrdemProducao);
			p.setInt(2, codProduto);
			p.setFloat(3, qtde);
			p.execute();
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<OrdemProducao> buscarOrdens (String dataInicio, String dataFim, String status) throws SQLException {
		String condicao = "";
		switch (status) {
		case "pendente":
			condicao = "WHERE EMPRODUCAO = 'N' AND PRODUZIDA = 'N' AND CANCELADA = 'N' ";
			break;
		case "emProducao":
			condicao = "WHERE ORDEMPRODUCAO.EMPRODUCAO = 'S' ";
			break;
		case "produzida":
			condicao = "WHERE ORDEMPRODUCAO.PRODUZIDA = 'S' ";
			break;
		case "cancelada": 
			condicao = "WHERE ORDEMPRODUCAO.CANCELADA = 'S' ";
			break;
		default:
			break;
		}
		String comando = "SELECT * FROM ORDEMPRODUCAO " + condicao;
		if ((!dataInicio.equals("null") && !dataInicio.equals("")) && (!dataFim.equals("null") && !dataFim.equals(""))) {
			if (condicao.equals("")){
				comando += "WHERE ORDEMPRODUCAO.DATA BETWEEN '" + dataInicio + "' AND '" + dataFim + "'";
			}else{
				comando += "AND ORDEMPRODUCAO.DATA BETWEEN '" + dataInicio + "' AND '" + dataFim + "'";
			}
			
		}
		comando += " ORDER BY DATA DESC";
		
		List<OrdemProducao> listOrdem= new ArrayList<OrdemProducao>();
		OrdemProducao ordem = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			ordem = new OrdemProducao();
			ordem.setNumero(rs.getInt("NUMERO"));
			ordem.setData(rs.getDate("DATA"));
			ordem.setHoraInicio(rs.getDate("INICIO"));
			ordem.setHoraFim(rs.getDate("FIM"));
			ordem.setDuracao(rs.getDate("DURACAO"));
			ordem.setEmProducao(rs.getString("EMPRODUCAO"));
			ordem.setProduzida(rs.getString("PRODUZIDA"));
			ordem.setCancelada(rs.getString("CANCELADA"));
			listOrdem.add(ordem);
		}
		return listOrdem;
	}
	
	public OrdemProducao buscarPeloNumero (int numero) throws SQLException {
		
		String comando = "SELECT * FROM ORDEMPRODUCAO WHERE ORDEMPRODUCAO.NUMERO = " + numero;
		OrdemProducao ordem = new OrdemProducao();
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			ordem.setNumero(rs.getInt("NUMERO"));
			ordem.setHoraInicio(rs.getDate("INICIO"));
			ordem.setHoraFim(rs.getDate("FIM"));
			ordem.setDuracao(rs.getDate("DURACAO"));
			ordem.setData(rs.getDate("DATA"));
			ordem.setEmProducao(rs.getString("EMPRODUCAO"));
			ordem.setProduzida(rs.getString("PRODUZIDA"));
			ordem.setCancelada(rs.getString("CANCELADA"));
		}
		return ordem;
	}
	
	public List<ItemOrdemProducao> buscarItensOrdem(int numero) throws SQLException  {
		
		List<ItemOrdemProducao> listItemOrdem = new ArrayList<ItemOrdemProducao>();
		String comando = "SELECT IO.PRODUTO, P.DESCRICAO, P.UNESTOQUE, P.ESTOQUE, IO.QTDE "
				       + "FROM ITEMORDEMPRODUCAO IO "
				       + "INNER JOIN PRODUTO P ON P.CODIGO = IO.PRODUTO "
				       + "WHERE IO.ORDEMPRODUCAO = " + numero;
		ItemOrdemProducao itemOrdem = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			itemOrdem = new ItemOrdemProducao();
			itemOrdem.setCodigoProduto(rs.getInt("PRODUTO"));
			itemOrdem.setDescricao(rs.getString("DESCRICAO"));
			itemOrdem.setUnEstoque(rs.getString("UNESTOQUE"));
			itemOrdem.setEstoque(rs.getFloat("ESTOQUE"));
			itemOrdem.setQtde(rs.getFloat("QTDE"));
			listItemOrdem.add(itemOrdem);
		}
		return listItemOrdem;
	}
	
	public void deletarProdutos (int numero) throws SQLException {
		String comando = "DELETE FROM ITEMORDEMPRODUCAO WHERE ITEMORDEMPRODUCAO.ORDEMPRODUCAO = " + numero;
		Statement stmt = this.conexao.createStatement();
		stmt.execute(comando);
	}
	
	
}