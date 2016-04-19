package br.com.brigaderia.objetos;

import java.io.Serializable;
import java.sql.Date;

public class DadosClientesVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int codigo;
	private String nome;
	private String cidade;
	private String uf;
	private String bairro;
	private Date ultimaVenda;	
	
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
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
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public Date getUltimaVenda() {
		return ultimaVenda;
	}
	public void setUltimaVenda(Date ultimaVenda) {
		this.ultimaVenda = ultimaVenda;
	}
}