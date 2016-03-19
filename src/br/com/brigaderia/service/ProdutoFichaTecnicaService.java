package br.com.brigaderia.service;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.objetos.FichaTecnica;
import br.com.brigaderia.objetos.Produto;
import br.com.brigaderia.objetos.ProdutoFichaTecnicaVO;

public class ProdutoFichaTecnicaService {
	
	
	public void adicionar (ProdutoFichaTecnicaVO prodFicha) throws BrigaderiaException{
		try {
			Produto produto = prodFicha.getProduto();
			FichaTecnica fichaTecnica = prodFicha.getFichaTecnica();
			ProdutoService serviceProduto = new ProdutoService();
			FichaTecnicaService serviceFichaTecnica = new FichaTecnicaService();
			fichaTecnica.setProduto(serviceProduto.adicionar(produto));
			serviceFichaTecnica.adicionar(fichaTecnica);
		}catch(BrigaderiaException e) {
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new BrigaderiaException();
		}
	}
}
