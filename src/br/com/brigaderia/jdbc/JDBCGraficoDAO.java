package br.com.brigaderia.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.brigaderia.jdbcinterface.GraficoDAO;
import br.com.brigaderia.objetos.VendaAnualVO;



public class JDBCGraficoDAO implements GraficoDAO{
	
	private Connection conexao;
	
	public JDBCGraficoDAO (Connection conexao) {
		super();
		this.conexao = conexao;
	}
	
	public List<VendaAnualVO> vendaAnual() throws SQLException {
		
		String sql = "SELECT * FROM VIEW_VENDAANUAL";
												   
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

}
