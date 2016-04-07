package br.com.brigaderia.exception;

public class AtualizarEstoqueException extends BrigaderiaException{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AtualizarEstoqueException(){
		super("Erro ao atualizar o estoque. Entre em contato com o administrador do sistema");
	}
	public AtualizarEstoqueException(String msg){
		super(msg);
	}
	public AtualizarEstoqueException(Throwable t){
		super(t);
	}
	

}