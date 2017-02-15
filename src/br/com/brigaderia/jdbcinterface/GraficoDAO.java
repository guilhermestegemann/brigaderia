package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.objetos.VendaAnualVO;
import br.com.brigaderia.objetos.VendaPorProdutoVO;

public interface GraficoDAO {
	
	public List<VendaAnualVO> vendaAnual(int ano) throws SQLException;
	public List<VendaPorProdutoVO> gerarPorProduto(String dataInicio, String dataFim, String orderBy, int numReg) throws SQLException;

}
