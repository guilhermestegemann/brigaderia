package br.com.brigaderia.objetos;

import java.io.Serializable;
import java.util.List;

public class FichaTecnica implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int codigoFichaTecnica;
	private int produto;
	private float custoTotal;
	private float qtdeProduto;
	private String procedimento;
	private List<ItemFichaTecnicaVO> ingredientes;
	
	public float getCustoTotal() {
		return custoTotal;
	}
	public void setCustoTotal(float custoTotal) {
		this.custoTotal = custoTotal;
	}
	public int getCodigoFichaTecnica() {
		return codigoFichaTecnica;
	}
	public void setCodigoFichaTecnica(int codigoFichaTecnica) {
		this.codigoFichaTecnica = codigoFichaTecnica;
	}
	public int getProduto() {
		return produto;
	}
	public void setProduto(int produto) {
		this.produto = produto;
	}
	public List<ItemFichaTecnicaVO> getIngredientes() {
		return ingredientes;
	}
	public void setIngredientes(List<ItemFichaTecnicaVO> ingredientes) {
		this.ingredientes = ingredientes;
	}
	
	public float getQtdeProduto() {
		return qtdeProduto;
	}
	public void setQtdeProduto(float qtdeProduto) {
		this.qtdeProduto = qtdeProduto;
	}
	public String getProcedimento() {
		return procedimento;
	}
	public void setProcedimento(String procedimento) {
		this.procedimento = procedimento;
	}
}