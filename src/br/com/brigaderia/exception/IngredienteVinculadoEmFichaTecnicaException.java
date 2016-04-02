package br.com.brigaderia.exception;

public class IngredienteVinculadoEmFichaTecnicaException extends BrigaderiaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IngredienteVinculadoEmFichaTecnicaException(){
		super("Esse produto possui vínculo com Ficha Técnica! Por isso não será deletado!");
	}
	public IngredienteVinculadoEmFichaTecnicaException(String msg){
		super(msg);
	}
	public IngredienteVinculadoEmFichaTecnicaException(Throwable t){
		super(t);
	}
	

}