package br.com.brigaderia.validacoes;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.ProdutosNaoInformadosException;
import br.com.brigaderia.objetos.OrdemProducao;

public class ValidaOrdemProducao {
	
	public void validar(OrdemProducao ordemProducao) throws BrigaderiaException {

		if (ordemProducao.getItemOrdemProducao().isEmpty()) {
			throw new ProdutosNaoInformadosException();
		}
	}
}