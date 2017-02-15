BRIGADERIA.validarDataFiltro = new Object();

BRIGADERIA.validarDataFiltro.validar = function (dataInicio, dataFim) {
	var retorno = true;
	if ((dataInicio != "null") || (dataFim != "null")){
		if (dataInicio == "null"){
			bootbox.alert("Favor informar a data início.");
			retorno = false;
		}else if (dataFim == "null"){
			bootbox.alert("Favor informar a data fim.");
			retorno = false;
		}else if(dataInicio > dataFim){
			bootbox.alert("Data início não pode ser maior que a data fim.");
			retorno = false;
		}
	} 
	return retorno;
}