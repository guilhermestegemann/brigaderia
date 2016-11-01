package br.com.brigaderia.objetos;

import java.io.Serializable;

public class VendaPorProdutoVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int codigo;
	private String nome;
	private float qtde;
	private float valorMedio;
	private float custo;
	private float total;
	
	public float getCusto() {
		return custo;
	}
	public void setCusto(float custo) {
		this.custo = custo;
	}
	public float getValorMedio() {
		return valorMedio;
	}
	public void setValorMedio(float valorMedio){
		this.valorMedio = valorMedio;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public float getQtde() {
		return qtde;
	}
	public void setQtde(float qtde) {
		this.qtde = qtde;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	
	
	
}
