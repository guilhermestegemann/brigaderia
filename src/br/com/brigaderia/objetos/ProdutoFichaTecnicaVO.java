package br.com.brigaderia.objetos;

import java.io.Serializable;

public class ProdutoFichaTecnicaVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Produto produto;
	private FichaTecnica fichaTecnica;
	
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public FichaTecnica getFichaTecnica() {
		return fichaTecnica;
	}
	public void setFichaTecnica(FichaTecnica fichaTecnica) {
		this.fichaTecnica = fichaTecnica;
	}
}