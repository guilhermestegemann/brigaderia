package br.com.brigaderia.objetos;

import java.io.Serializable;

public class VendaPorClienteVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int codigo;
	private String nome;
	private int pedidos;
	private float valorMedioPedido;
	private float custo;
	private float total;
	
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
	public int getPedidos() {
		return pedidos;
	}
	public void setPedidos(int pedidos) {
		this.pedidos = pedidos;
	}
	public float getValorMedioPedido() {
		return valorMedioPedido;
	}
	public void setValorMedioPedido(float valorMedioPedido) {
		this.valorMedioPedido = valorMedioPedido;
	}
	public float getCusto() {
		return custo;
	}
	public void setCusto(float custo) {
		this.custo = custo;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	
	
	
	
	
}
