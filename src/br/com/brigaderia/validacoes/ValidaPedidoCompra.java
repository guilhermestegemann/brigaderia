package br.com.brigaderia.validacoes;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.ProdutosNaoInformadosException;
import br.com.brigaderia.objetos.PedidoCompra;

public class ValidaPedidoCompra {
	
	public void validar(PedidoCompra pedidoCompra) throws BrigaderiaException {

		if (pedidoCompra.getItemPedidoCompra().isEmpty()) {
			throw new ProdutosNaoInformadosException();
		}
	}
}

