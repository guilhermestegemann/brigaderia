package br.com.brigaderia.validacoes;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.FichaTecnicaSemIngredienteException;
import br.com.brigaderia.exception.QtdeProdutoFichaTecnicaInválida;
import br.com.brigaderia.objetos.FichaTecnica;

public class ValidaFichaTecnica {
	
	public void validar(FichaTecnica fichaTecnica) throws BrigaderiaException {
		if (fichaTecnica.getQtdeProduto() <= 0) {
			throw new QtdeProdutoFichaTecnicaInválida();
		}
		if (fichaTecnica.getIngredientes().isEmpty()) {
			throw new FichaTecnicaSemIngredienteException();
		}
	}
}