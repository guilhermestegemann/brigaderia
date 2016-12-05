package br.com.brigaderia.objetos;

public class ItemPedidoVenda extends Produto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float qtde;
	private float unitario;
	private float total;
	
	public float getUnitario() {
		return unitario;
	}
	public void setUnitario(float unitario) {
		this.unitario = unitario;
	}

	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public float getQtde() {
		return qtde;
	}
	public void setQtde(float qtde) {
		this.qtde = qtde;
	}
}