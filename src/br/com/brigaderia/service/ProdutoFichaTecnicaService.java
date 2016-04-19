package br.com.brigaderia.service;

import java.sql.SQLException;

import br.com.brigaderia.exception.BrigaderiaException;
import br.com.brigaderia.objetos.FichaTecnica;
import br.com.brigaderia.objetos.Produto;
import br.com.brigaderia.objetos.ProdutoFichaTecnicaVO;

public class ProdutoFichaTecnicaService {
	
	
	public void adicionar (ProdutoFichaTecnicaVO prodFicha) throws SQLException, BrigaderiaException{
		
		Produto produto = prodFicha.getProduto();
		FichaTecnica fichaTecnica = prodFicha.getFichaTecnica();
		ProdutoService serviceProduto = new ProdutoService();
		FichaTecnicaService serviceFichaTecnica = new FichaTecnicaService();
		fichaTecnica.setProduto(serviceProduto.adicionar(produto));
		try {
			serviceFichaTecnica.adicionar(fichaTecnica);
		}catch(BrigaderiaException e){
			serviceProduto.deletarProduto(fichaTecnica.getProduto());
			throw e;
		}
	}
	
	public void atualizarProdutoFichaTecnica (ProdutoFichaTecnicaVO prodFicha) throws BrigaderiaException, SQLException {
		
		Produto produto = prodFicha.getProduto();
		FichaTecnica fichaTecnica = prodFicha.getFichaTecnica();
		ProdutoService serviceProduto = new ProdutoService();
		FichaTecnicaService serviceFichaTecnica = new FichaTecnicaService();
		serviceProduto.atualizarProduto(produto);
		serviceFichaTecnica.atualizarFichaTecnica(fichaTecnica);
	}
}
