package br.com.brigaderia.objetos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PedidoVenda implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int numero;
	private int cliente;
	private Date dataEmissao;
	private Date dataFaturado;
	private String faturado;
	private String produzido;
	private String cancelado;
	private double total;
	private int ordemProducao;
	private List<ItemPedidoVenda> itemPedidoVenda;
	
	public int getOrdemProducao() {
		return ordemProducao;
	}
	public void setOrdemProducao(int ordemProducao) {
		this.ordemProducao = ordemProducao;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getCliente() {
		return cliente;
	}
	public void setCliente(int cliente) {
		this.cliente = cliente;
	}
	public Date getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public Date getDataFaturado() {
		return dataFaturado;
	}
	public void setDataFaturado(Date dataFaturado) {
		this.dataFaturado = dataFaturado;
	}
	public String getFaturado() {
		return faturado;
	}
	public void setFaturado(String faturado) {
		this.faturado = faturado;
	}
	public String getProduzido() {
		return produzido;
	}
	public void setProduzido(String produzido) {
		this.produzido = produzido;
	}
	public String getCancelado() {
		return cancelado;
	}
	public void setCancelado(String cancelado) {
		this.cancelado = cancelado;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public List<ItemPedidoVenda> getItemPedidoVenda() {
		return itemPedidoVenda;
	}
	public void setItemPedidoVenda(List<ItemPedidoVenda> itemPedidoVenda) {
		this.itemPedidoVenda = itemPedidoVenda;
	}
	
	
	

}
