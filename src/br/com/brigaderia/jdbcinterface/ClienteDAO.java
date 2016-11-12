package br.com.brigaderia.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.brigaderia.exception.CpfDuplicadoException;
import br.com.brigaderia.objetos.Cliente;
import br.com.brigaderia.objetos.DadosClientesVO;

public interface ClienteDAO {
	
	public void cadastrar (Cliente cliente) throws SQLException;
	public List<DadosClientesVO> buscarClientes (String valorBusca, String ativo) throws SQLException;
	public void deletar (int codigo) throws SQLException;
	public Cliente buscarPeloCodigo(int codigo) throws SQLException;
	public void atualizar (Cliente cliente) throws SQLException;
	public void verificarCpfDuplicado (String cpf) throws SQLException, CpfDuplicadoException;
	public void verificarCpfDuplicadoEdicao (String cpf, int id) throws SQLException, CpfDuplicadoException;
}
