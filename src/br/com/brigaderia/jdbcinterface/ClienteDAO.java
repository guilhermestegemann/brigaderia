package br.com.brigaderia.jdbcinterface;

import java.util.List;



import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.objetos.Cliente;
import br.com.brigaderia.objetos.DadosClientesVO;

public interface ClienteDAO {
	
	public void cadastrar (Cliente cliente) throws BrigaderiaException;
	public List<DadosClientesVO> buscarClientes (String valorBusca) throws BrigaderiaException;
	public void deletar (int codigo) throws BrigaderiaException;
	public Cliente buscarPeloCodigo(int codigo) throws BrigaderiaException;
	public void atualizar (Cliente cliente) throws BrigaderiaException;
	public void verificarCpfDuplicado (String cpf) throws BrigaderiaException;
	public void verificarCpfDuplicadoEdicao (String cpf, int id) throws BrigaderiaException;
}
