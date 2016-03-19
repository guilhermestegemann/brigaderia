package br.com.brigaderia.objetos;

import java.io.Serializable;
import java.util.List;

public class FichaTecnica implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float custoTotal;
	private float qtdeProduto;
	private String procedimento;
	private List<itemFichaTecnicaVO> ingredientes;
	
	
	public List<itemFichaTecnicaVO> getIngredientes() {
		return ingredientes;
	}
	public void setIngredientes(List<itemFichaTecnicaVO> ingredientes) {
		this.ingredientes = ingredientes;
	}
	public float getCustoTotal() {
		return custoTotal;
	}
	public void setCustoTotal(float custoTotal) {
		this.custoTotal = custoTotal;
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
