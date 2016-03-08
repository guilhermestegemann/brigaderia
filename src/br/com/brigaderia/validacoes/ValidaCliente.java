package br.com.brigaderia.validacoes;

import br.com.brigaderia.exception.CpfInvalidoException;
import br.com.brigaderia.exception.ValidaClientesException;
import br.com.brigaderia.objetos.Cliente;

public class ValidaCliente {
	
	public void validarCliente (Cliente cliente) throws ValidaClientesException, CpfInvalidoException{
		
		if ((cliente.getNome().equals("")) || (cliente.getBairro() == 0) || (cliente.getCidade() == 0)){
			throw new ValidaClientesException();
		}
		ValidaCpf validaCpf = new ValidaCpf();
		try {
			validaCpf.validarCpf(cliente.getCpf());
		}catch (CpfInvalidoException e) {
			throw e;
		}
	}

}
