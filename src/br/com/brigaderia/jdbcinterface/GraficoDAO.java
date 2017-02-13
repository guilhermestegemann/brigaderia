package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.objetos.VendaAnualVO;

public interface GraficoDAO {
	
	public List<VendaAnualVO> vendaAnual() throws SQLException;

}
