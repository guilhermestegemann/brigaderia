package br.com.brigaderia.objetos;

import java.io.Serializable;

public class SugestaoCompraVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int ingrediente;
	private String nomeIngrediente;
	private String unEstoque;
	private float itemFichaQtde;
	private float fichaQtde;
	private float qtdePedido;
	private float estoque;
	private float valorCusto;
	private float qtdeNecessaria;
	private float qtdeSugestao;
	private float custoSugestão;
	
	public String getUnEstoque() {
		return unEstoque;
	}
	public void setUnEstoque(String unEstoque) {
		this.unEstoque = unEstoque;
	}
	public int getIngrediente() {
		return ingrediente;
	}
	public void setIngrediente(int ingrediente) {
		this.ingrediente = ingrediente;
	}
	public float getItemFichaQtde() {
		return itemFichaQtde;
	}
	public void setItemFichaQtde(float itemFichaQtde) {
		this.itemFichaQtde = itemFichaQtde;
	}
	public float getFichaQtde() {
		return fichaQtde;
	}
	public void setFichaQtde(float fichaQtde) {
		this.fichaQtde = fichaQtde;
	}
	public float getQtdePedido() {
		return qtdePedido;
	}
	public void setQtdePedido(float qtdePedido) {
		this.qtdePedido = qtdePedido;
	}
	public float getEstoque() {
		return estoque;
	}
	public void setEstoque(float estoque) {
		this.estoque = estoque;
	}
	public float getValorCusto() {
		return valorCusto;
	}
	public void setValorCusto(float valorCusto) {
		this.valorCusto = valorCusto;
	}
	public float getQtdeNecessaria() {
		return qtdeNecessaria;
	}
	public void setQtdeNecessaria(float qtdeNecessaria) {
		this.qtdeNecessaria = qtdeNecessaria;
	}
	public float getQtdeSugestao() {
		return qtdeSugestao;
	}
	public void setQtdeSugestao(float qtdeSugestao) {
		this.qtdeSugestao = qtdeSugestao;
	}
	public float getCustoSugestão() {
		return custoSugestão;
	}
	public void setCustoSugestão(float custoSugestão) {
		this.custoSugestão = custoSugestão;
	}
	public String getNomeIngrediente() {
		return nomeIngrediente;
	}
	public void setNomeIngrediente(String nomeIngrediente) {
		this.nomeIngrediente = nomeIngrediente;
	}
	
}
