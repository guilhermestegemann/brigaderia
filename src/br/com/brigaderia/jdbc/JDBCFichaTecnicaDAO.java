package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.IngredienteVinculadoEmFichaTecnicaException;
import br.com.brigaderia.exception.SomarCustoFichaTecnicaException;
import br.com.brigaderia.jdbcinterface.FichaTecnicaDAO;
import br.com.brigaderia.objetos.FichaTecnica;
import br.com.brigaderia.objetos.ItemFichaTecnicaVO;


public class JDBCFichaTecnicaDAO implements FichaTecnicaDAO{
	
	private Connection conexao;
	
	public JDBCFichaTecnicaDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public int adicionar(FichaTecnica fichaTecnica) throws SQLException  {
		String comando = "INSERT INTO FICHATECNICA (PRODUTO, QTDE, TOTALCUSTO, PROCEDIMENTO) VALUES (?,?,?,?)";
		PreparedStatement p;
		p = this.conexao.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS);
		p.setInt(1, fichaTecnica.getProduto());
		p.setFloat(2, fichaTecnica.getQtdeProduto());
		p.setFloat(3, fichaTecnica.getCustoTotal());
		p.setString(4, fichaTecnica.getProcedimento());
		p.execute();
		ResultSet generatedKeys = p.getGeneratedKeys(); 
        if (generatedKeys.next()) {
        	fichaTecnica.setCodigoFichaTecnica(generatedKeys.getInt(1));
        }
        else {
            throw new SQLException("Erro ao recuperar chave inserida! (Ficha Técnica)");
        }			
		return fichaTecnica.getCodigoFichaTecnica();
	}
	
	public void adicionarIngredientes(int codFichaTecnica, int codIngrediente, float qtde) throws SomarCustoFichaTecnicaException, SQLException {
		String comando = "INSERT INTO ITEMFICHATECNICA (FICHATECNICA, INGREDIENTE, QTDE) VALUES (?,?,?)";
		PreparedStatement p;
		try {
			this.conexao.setAutoCommit(false);
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, codFichaTecnica);
			p.setInt(2, codIngrediente);
			p.setFloat(3, qtde);
			p.execute();
			somarFichaTecnica(codFichaTecnica);
			this.conexao.commit();
		}catch (SQLException e) {
			e.printStackTrace();
			this.conexao.rollback();
			throw new SomarCustoFichaTecnicaException();
		}
	}
	
	private void somarFichaTecnica(int codFichaTecnica) throws SQLException  {
		String comando = "SELECT SUM(CAST(ITEMFICHATECNICA.QTDE * PRODUTO.VALORCUSTO AS DECIMAL(10,2))) TOTALCUSTO "
					   + "FROM ITEMFICHATECNICA "
					   + "INNER JOIN PRODUTO ON PRODUTO.CODIGO = ITEMFICHATECNICA.INGREDIENTE "
					   + "WHERE ITEMFICHATECNICA.FICHATECNICA = " + codFichaTecnica;
		String updateFichaTecnica = "UPDATE FICHATECNICA SET FICHATECNICA.TOTALCUSTO = ? "
					  + "WHERE FICHATECNICA.CODIGO = " + codFichaTecnica;
		float total = 0;
		
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			total = rs.getFloat("TOTALCUSTO");
		}
		PreparedStatement p = this.conexao.prepareStatement(updateFichaTecnica);
		p.setFloat(1, total);
		p.execute();			
	}
	
	public FichaTecnica buscarPeloCodigoProduto (int codigoProduto) throws SQLException {
		String comando = "SELECT * FROM FICHATECNICA WHERE FICHATECNICA.PRODUTO = " + codigoProduto;
		FichaTecnica fichaTecnica = new FichaTecnica();
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			fichaTecnica.setCodigoFichaTecnica(rs.getInt("CODIGO"));
			fichaTecnica.setQtdeProduto(rs.getFloat("QTDE"));
			fichaTecnica.setCustoTotal(rs.getFloat("TOTALCUSTO"));
			fichaTecnica.setProcedimento(rs.getString("PROCEDIMENTO"));
			fichaTecnica.setIngredientes(buscarIngredientesPeloCodigo(fichaTecnica.getCodigoFichaTecnica()));
		}
		return fichaTecnica;
	}
	
	private List<ItemFichaTecnicaVO> buscarIngredientesPeloCodigo(int codigo) throws SQLException {
		List<ItemFichaTecnicaVO> listItemFichaTecnica = new ArrayList<ItemFichaTecnicaVO>();
		String comando = "SELECT ITEMFICHATECNICA.INGREDIENTE, PRODUTO.DESCRICAO, PRODUTO.UNESTOQUE, ITEMFICHATECNICA.QTDE "
				       + "FROM ITEMFICHATECNICA "
				       + "INNER JOIN PRODUTO ON PRODUTO.CODIGO = ITEMFICHATECNICA.INGREDIENTE "
				       + "WHERE ITEMFICHATECNICA.FICHATECNICA = " + codigo;
		ItemFichaTecnicaVO itemFichaTecnicaVO = null;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			itemFichaTecnicaVO = new ItemFichaTecnicaVO();
			itemFichaTecnicaVO.setCodigo(rs.getInt("ingrediente"));
			itemFichaTecnicaVO.setDescricao(rs.getString("descricao"));
			itemFichaTecnicaVO.setUn(rs.getString("unestoque"));
			itemFichaTecnicaVO.setQtde(rs.getFloat("qtde"));
			listItemFichaTecnica.add(itemFichaTecnicaVO);
		}
		return listItemFichaTecnica;
	}
	
	public void atualizar (FichaTecnica fichaTecnica) throws SQLException {
		String comando = "UPDATE FICHATECNICA SET " 
							+ "FICHATECNICA.QTDE = ?, "
							+ "FICHATECNICA.PROCEDIMENTO = ? "
					   + "WHERE FICHATECNICA.CODIGO = " + fichaTecnica.getCodigoFichaTecnica();
	    PreparedStatement p;
    	p = this.conexao.prepareStatement(comando);
    	p.setFloat(1, fichaTecnica.getQtdeProduto());
    	p.setString(2, fichaTecnica.getProcedimento());
    	p.executeUpdate();
	}
	
	public void deletarIngredientes(int codFicha) throws SQLException {
		String comando = "DELETE FROM ITEMFICHATECNICA WHERE ITEMFICHATECNICA.FICHATECNICA = " + codFicha;
		Statement p;
		p = this.conexao.createStatement();
		p.execute(comando);	
	}
	
	public void countIngredientes(int codigo) throws SQLException, IngredienteVinculadoEmFichaTecnicaException {
		String comando = "SELECT COUNT(*) AS QTDEINGREDIENTE FROM ITEMFICHATECNICA  WHERE ITEMFICHATECNICA.INGREDIENTE = " + codigo;
		int qtdeProduto = 0;
		Statement stmt = conexao.createStatement();
		ResultSet rs = stmt.executeQuery(comando);
		while(rs.next()) {
			qtdeProduto = rs.getInt("QTDEINGREDIENTE");
		}
		if (qtdeProduto > 0) {
			throw new IngredienteVinculadoEmFichaTecnicaException();
		}
	}
}
