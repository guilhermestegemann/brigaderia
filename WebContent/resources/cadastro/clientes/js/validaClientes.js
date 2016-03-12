BRIGADERIA.validaClientes = new Object();

	BRIGADERIA.validaClientes.validar = function(newCliente) {
		retorno = "";
		var expEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
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
		
		if (newCliente.email != "") {
			if (!expEmail.test(newCliente.email)) {
				if (retorno == "") {
					retorno += "Email inválido";
				}else{
					retorno += ", Email inválido";
				}
			}
		}
		
		if (retorno != "") {
			retorno += ".";
		}
		return retorno;
	}