package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.objetos.EstoqueVO;

public interface EstoqueDAO {
	
	public List<EstoqueVO> gerarRelatorio(String tipoItem, String ativo) throws SQLException;
}
