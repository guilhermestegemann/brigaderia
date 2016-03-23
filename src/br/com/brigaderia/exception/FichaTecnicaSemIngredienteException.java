package br.com.brigaderia.exception;



public class FichaTecnicaSemIngredienteException extends BrigaderiaException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public FichaTecnicaSemIngredienteException(){
		super("Os ingredientes da Ficha Técnica não foram informados.");
	}
	public FichaTecnicaSemIngredienteException(String msg){
		super(msg);
	}
	public FichaTecnicaSemIngredienteException(Throwable t){
		super(t);
	}
	

}
