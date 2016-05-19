package br.com.brigaderia.objetos;

import java.io.Serializable;
import java.util.Date;

public class PedidoVendaVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int numero;
	private String cliente;
	private Date emissao;
	private double total;
	private String faturado;
	private String cancelado;
	private String produzido;
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public Date getEmissao() {
		return emissao;
	}
	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getFaturado() {
		return faturado;
	}
	public void setFaturado(String faturado) {
		this.faturado = faturado;
	}
	public String getCancelado() {
		return cancelado;
	}
	public void setCancelado(String cancelado) {
		this.cancelado = cancelado;
	}
	public String getProduzido() {
		return produzido;
	}
	public void setProduzido(String produzido) {
		this.produzido = produzido;
	}
	
	
	

}
