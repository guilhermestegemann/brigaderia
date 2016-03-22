package br.com.brigaderia.exception;

public class CamposObrigatoriosException extends BrigaderiaException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CamposObrigatoriosException(){
		super("Favor preencher os campos obrigatórios!");
	}
	public CamposObrigatoriosException(String msg){
		super(msg);
	}
	public CamposObrigatoriosException(Throwable t){
		super(t);
	}
	

}
