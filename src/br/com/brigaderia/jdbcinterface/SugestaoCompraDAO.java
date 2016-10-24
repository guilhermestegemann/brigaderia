package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.objetos.SugestaoCompraVO;

public interface SugestaoCompraDAO {
	
	public List<SugestaoCompraVO> gerarSugestao(int cliente, String dataInicio, String dataFim) throws SQLException;

}
