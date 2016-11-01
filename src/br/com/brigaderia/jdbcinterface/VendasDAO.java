package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.objetos.VendaPorClienteVO;
import br.com.brigaderia.objetos.VendaPorProdutoVO;

public interface VendasDAO {
	
	public List<VendaPorProdutoVO> gerarPorProduto(String dataInicio, String dataFim, int produto, int numReg) throws SQLException;
	public List<VendaPorClienteVO> gerarPorCliente(String dataInicio, String dataFim, int cliente, int numReg) throws SQLException;

}
