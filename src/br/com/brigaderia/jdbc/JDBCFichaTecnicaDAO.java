package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.jdbcinterface.FichaTecnicaDAO;
import br.com.brigaderia.objetos.FichaTecnica;


public class JDBCFichaTecnicaDAO implements FichaTecnicaDAO{
	
	private Connection conexao;
	
	public JDBCFichaTecnicaDAO(Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public int adicionar(FichaTecnica fichaTecnica) throws BrigaderiaException {
		String comando = "INSERT INTO FICHATECNICA (PRODUTO, QTDE, TOTALCUSTO, PROCEDIMENTO) VALUES (?,?,?,?)";
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS);
			p.setInt(1, fichaTecnica.getProduto());
			p.setFloat(2, fichaTecnica.getQtdeProduto());
			p.setFloat(3, fichaTecnica.getCustoTotal());
			p.setString(4, fichaTecnica.getProcedimento());
			p.execute();
			try (ResultSet generatedKeys = p.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	fichaTecnica.setCodigo(generatedKeys.getInt(1));
	            }
	            else {
	                throw new SQLException("Erro ao recuperar chave inserida! (Ficha Técnica)");
	            }
			} 
			
			return fichaTecnica.getCodigo();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}
	}
	
	public void adicionarIngredientes(int codFichaTecnica, int codIngrediente, float qtde) throws BrigaderiaException {
		String comando = "INSERT INTO ITEMFICHATECNICA (FICHATECNICA, INGREDIENTE, QTDE) VALUES (?,?,?)";
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, codFichaTecnica);
			p.setInt(2, codIngrediente);
			p.setFloat(3, qtde);
			p.execute();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}
		
	}
}
