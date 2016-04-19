package br.com.brigaderia.exception;

public class SomarCustoFichaTecnicaException extends BrigaderiaException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SomarCustoFichaTecnicaException(){
		super("Erro ao somar o custo total da Ficha Técnica. Favor comunicar o administrador do sistema.");
	}
	public SomarCustoFichaTecnicaException(String msg){
		super(msg);
	}
	public SomarCustoFichaTecnicaException(Throwable t){
		super(t);
	}
}