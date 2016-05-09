package br.com.brigaderia.validacoes;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.ProdutosNaoInformadosException;
import br.com.brigaderia.objetos.PedidoVenda;

public class ValidaPedidoVenda {
	
	public void validar(PedidoVenda pedidoVenda) throws BrigaderiaException {

		if (pedidoVenda.getItemPedidoVenda().isEmpty()) {
			throw new ProdutosNaoInformadosException();
		}
	}
}