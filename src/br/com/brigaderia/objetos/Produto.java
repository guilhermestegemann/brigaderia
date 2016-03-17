package br.com.brigaderia.objetos;

import java.io.Serializable;
import java.sql.Date;

public class Produto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int tipoItem;
	private String descricao;
	private float qtdeEntrada;
	private String unEntrada;
	private float valorCusto;
	private float estoque;
	private String unEstoque;
	private float margem;
	private float valorVenda;
	private Date dataCadastro;
	
	
	public int getTipoItem() {
		return tipoItem;
	}
	public void setTipoItem(int tipoItem) {
		this.tipoItem = tipoItem;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public float getQtdeEntrada() {
		return qtdeEntrada;
	}
	public void setQtdeEntrada(float qtdeEntrada) {
		this.qtdeEntrada = qtdeEntrada;
	}
	public String getUnEntrada() {
		return unEntrada;
	}
	public void setUnEntrada(String unEntrada) {
		this.unEntrada = unEntrada;
	}
	public float getValorCusto() {
		return valorCusto;
	}
	public void setValorCusto(float valorCusto) {
		this.valorCusto = valorCusto;
	}
	public float getEstoque() {
		return estoque;
	}
	public void setEstoque(float estoque) {
		this.estoque = estoque;
	}
	public String getUnEstoque() {
		return unEstoque;
	}
	public void setUnEstoque(String unEstoque) {
		this.unEstoque = unEstoque;
	}
	public float getMargem() {
		return margem;
	}
	public void setMargem(float margem) {
		this.margem = margem;
	}
	public float getValorVenda() {
		return valorVenda;
	}
	public void setValorVenda(float valorVenda) {
		this.valorVenda = valorVenda;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	
	
}
