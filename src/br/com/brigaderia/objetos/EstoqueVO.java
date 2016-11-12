package br.com.brigaderia.objetos;


public class EstoqueVO extends Produto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private float totalCusto;
	private float totalVenda;
	
	public float getTotalCusto() {
		return totalCusto;
	}
	public void setTotalCusto(float totalCusto) {
		this.totalCusto = totalCusto;
	}
	public float getTotalVenda() {
		return totalVenda;
	}
	public void setTotalVenda(float totalVenda) {
		this.totalVenda = totalVenda;
	}
}
