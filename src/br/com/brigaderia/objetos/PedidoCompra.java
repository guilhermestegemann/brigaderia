package br.com.brigaderia.objetos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PedidoCompra implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int numero;
	private Date data;
	private double total;
	private List<ItemPedidoCompra> itemPedidoCompra;
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public List<ItemPedidoCompra> getItemPedidoCompra() {
		return itemPedidoCompra;
	}
	public void setItemPedidoCompra(List<ItemPedidoCompra> itemPedidoCompra) {
		this.itemPedidoCompra = itemPedidoCompra;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
}