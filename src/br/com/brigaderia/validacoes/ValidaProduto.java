package br.com.brigaderia.validacoes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.exception.CamposObrigatoriosException;
import br.com.brigaderia.exception.EstoqueNegativoException;
import br.com.brigaderia.exception.QtdeEntradaInvalidaException;
import br.com.brigaderia.exception.UnMedidaInvalidaException;
import br.com.brigaderia.objetos.Produto;

public class ValidaProduto {
	
	public void validarProduto (Produto produto) throws BrigaderiaException {
		
		
		
		try {
		
			
			if ((produto.getTipoItem() == 0) || (produto.getDescricao().equals(""))) {
				throw new CamposObrigatoriosException();
			}
			
			
			if (produto.getQtdeEntrada() <= 0) {
				throw new QtdeEntradaInvalidaException();
			}
			if (produto.getEstoque() < 0) {
				throw new EstoqueNegativoException();
			}
			
			String regExUnMedida = "[a-zA-Z]{2}";
			Pattern pattern = Pattern.compile(regExUnMedida, Pattern.CASE_INSENSITIVE);
	        Matcher unEntrada = pattern.matcher(produto.getUnEntrada());
	        Matcher unEStoque = pattern.matcher(produto.getUnEstoque());
	        if ((!unEntrada.matches()) ||(!unEStoque.matches())) {
	        	throw new UnMedidaInvalidaException();
	        }
	        
		}catch(BrigaderiaException e) {
			e.printStackTrace();
			throw e;
		}
		
        
	}
}
