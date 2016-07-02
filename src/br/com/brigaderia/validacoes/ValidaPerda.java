package br.com.brigaderia.validacoes;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.ProdutosNaoInformadosException;
import br.com.brigaderia.objetos.Perda;

public class ValidaPerda {
	
	public void validar(Perda perda) throws BrigaderiaException {

		if (perda.getItemPerda().isEmpty()) {
			throw new ProdutosNaoInformadosException();
		}
	}
}