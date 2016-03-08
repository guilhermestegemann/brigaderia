BRIGADERIA.validaClientes = new Object();

	BRIGADERIA.validaClientes.validar = function(newCliente) {
		retorno = "";
		if (newCliente.nome == "") {
			retorno += "Nome"; 
		}
		
		if (newCliente.cidade =="") {
			if (retorno == "") {
				retorno += "Cidade";
			}else{
				retorno += ", Cidade";
			}	
		}
		
		if (newCliente.bairro =="") {
			if (retorno == "") {
				retorno += "Bairro";
			}else{
				retorno += ", Bairro";
			}
		}
		
		if (retorno != "") {
			retorno += ".";
		}
		return retorno;
	}