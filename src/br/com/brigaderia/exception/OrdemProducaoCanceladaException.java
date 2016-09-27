package br.com.brigaderia.exception;

public class OrdemProducaoCanceladaException extends BrigaderiaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public OrdemProducaoCanceladaException(){
		super("A Ordem de Produção está cancelada.");
	}
	public OrdemProducaoCanceladaException(String msg){
		super(msg);
	}
	public OrdemProducaoCanceladaException(Throwable t){
		super(t);
	}
}