package br.com.brigaderia.validacoes;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.ValidaClientesException;
import br.com.brigaderia.objetos.Cliente;

public class ValidaCliente {
	
	public void validarCliente (Cliente cliente) throws BrigaderiaException {
		
		if ((cliente.getNome().equals("")) || (cliente.getBairro() == 0) || (cliente.getCidade() == 0)){
			throw new ValidaClientesException();
		}
		ValidacoesGerais validacoes = new ValidacoesGerais();
		try {
			validacoes.validarCpf(cliente.getCpf());
		}catch (BrigaderiaException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
