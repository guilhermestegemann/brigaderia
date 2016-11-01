package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.objetos.VendaPorProdutoVO;

public interface VendaPorProdutoDAO {
	
	public List<VendaPorProdutoVO> gerarPorProduto(String dataInicio, String dataFim, int produto, int numReg) throws SQLException;

}
