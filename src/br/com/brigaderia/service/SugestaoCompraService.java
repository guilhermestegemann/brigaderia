package br.com.brigaderia.service;


import java.sql.SQLException;
import java.util.List;


import br.com.brigaderia.objetos.DadosClientesVO;

public class SugestaoCompraService {
	
	public List<DadosClientesVO> listarClientes() throws SQLException {
		
		ClienteService cliService = new ClienteService();
		return cliService.buscarClientesVO("null");

	}

}
