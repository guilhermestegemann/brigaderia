package br.com.brigaderia.jdbcinterface;

import java.util.List;

import br.com.brigaderia.exception.CpfDuplicadoException;
import br.com.brigaderia.objetos.Cliente;
import br.com.brigaderia.objetos.DadosClientesVO;

public interface ClienteDAO {
	
	public void cadastrar (Cliente cliente);
	public List<DadosClientesVO> buscarClientes (String valorBusca);
	public boolean deletar (int codigo);
	public Cliente buscarPeloCodigo(int codigo);
	public boolean atualizar (Cliente cliente);
	public void verificarCpfDuplicado (String cpf) throws CpfDuplicadoException;
	public void verificarCpfDuplicadoEdicao (String cpf, int id) throws CpfDuplicadoException;
}
