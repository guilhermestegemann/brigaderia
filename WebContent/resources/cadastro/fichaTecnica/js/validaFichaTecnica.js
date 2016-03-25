BRIGADERIA.validaFichaTecnica = new Object();

	BRIGADERIA.validaFichaTecnica.validar = function(newFichaTecnica) {
		retorno = "";
		var expNumeros = /^[0-9]+$/;
		if (newFichaTecnica.qtdeProduto != "") {
			if (!expNumeros.test(newFichaTecnica.qtdeProduto)) {
				if (retorno == "") {
					retorno += "Quantidade do produto inválida";
				}else{
					retorno += ", Quantidade do produto inválida";
				}
			}
		}else {
			if (retorno == "") {
				retorno += "Quantidade do produto não informada";
			}else{
				retorno += ", Quantidade do produto não informada";
			}
		}
		
		if (newFichaTecnica.qtdeProduto <= 0) {
			if (retorno == "") {
				retorno += "Quantidade do produto deve ser maior que zero";
			}else{
				retorno += ", Quantidade do produto deve ser maior que zero";
			}	
		}
		
		if (newFichaTecnica.ingredientes == "") {
			if (retorno == "") {
				retorno += "Ingredientes não informados";
			}else{
				retorno += ", Ingredientes não informados";
			}
		}
		
		if (retorno != "") {
			retorno += ".";
		}
		return retorno;
	}