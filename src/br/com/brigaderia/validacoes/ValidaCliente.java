package br.com.brigaderia.validacoes;


import java.util.regex.Pattern;
import java.util.regex.Matcher;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.ValidaClientesException;
import br.com.brigaderia.objetos.Cliente;

public class ValidaCliente {
	
	public void validarCliente (Cliente cliente) throws BrigaderiaException {
		
		boolean email = true;;
		if (!cliente.getEmail().equals("")) {
			 String expEmail = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			 Pattern pattern = Pattern.compile(expEmail, Pattern.CASE_INSENSITIVE);
	         Matcher matcher = pattern.matcher(cliente.getEmail());
	         if (!matcher.matches()) {
	        	 email = false;
	         }
		}
		if ((cliente.getNome().equals("")) || (cliente.getBairro() == 0) || (cliente.getCidade() == 0) || (!email)){
			System.out.println(cliente.getNome());
			System.out.println(cliente.getBairro());
			System.out.println(cliente.getCidade());
			System.out.println(cliente.getEmail() + " nada");
			System.out.println(email);
			
			throw new ValidaClientesException();
		}
		
		ValidacoesGerais validacoes = new ValidacoesGerais();
		validacoes.validarCpf(cliente.getCpf());
	}
}
