package br.com.brigaderia.objetos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrdemProducao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int numero;
	private Date horaInicio;
	private Date horaFim;
	private Date duracao;
	private Date data;
	private String emProducao;
	private String produzida;
	private String cancelada;
	private List<ItemOrdemProducao> itemOrdemProducao;
	
	public List<ItemOrdemProducao> getItemOrdemProducao() {
		return itemOrdemProducao;
	}
	public void setItemOrdemProducao(List<ItemOrdemProducao> itemOrdemProducao) {
		this.itemOrdemProducao = itemOrdemProducao;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public Date getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}
	public Date getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(Date horaFim) {
		this.horaFim = horaFim;
	}
	public Date getDuracao() {
		return duracao;
	}
	public void setDuracao(Date duracao) {
		this.duracao = duracao;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getEmProducao() {
		return emProducao;
	}
	public void setEmProducao(String emProducao) {
		this.emProducao = emProducao;
	}
	public String getProduzida() {
		return produzida;
	}
	public void setProduzida(String produzida) {
		this.produzida = produzida;
	}
	public String getCancelada() {
		return cancelada;
	}
	public void setCancelada(String cancelada) {
		this.cancelada = cancelada;
	}
	
	

}
