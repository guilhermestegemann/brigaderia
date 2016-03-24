BRIGADERIA.validaProdutos = new Object();

	BRIGADERIA.validaProdutos.validar = function(newProduto) {
		retorno = "";
		var expUnMedida = /^[A-Za-z]{2}$/;
		var expNumeros = /^[0-9]+$/;
		if (newProduto.tipoItem == "") {
			retorno += "Tipo Item"; 
		}
		
		if (newProduto.descricao =="") {
			if (retorno == "") {
				retorno += "Descrição";
			}else{
				retorno += ", Descrição";
			}	
		}
		
		if (newProduto.qtdeEntrada != "") {
			if (!expNumeros.test(newProduto.qtdeEntrada)) {
				if (retorno == "") {
					retorno += "Quantidade entrada inválida";
				}else{
					retorno += ", Quantidade entrada inválida";
				}
			}
		}else {
			if (retorno == "") {
				retorno += "Quantidade de entrada";
			}else{
				retorno += ", Quantidade de entrada";
			}
		}
		
		if (newProduto.unEntrada != "") {
			if (!expUnMedida.test(newProduto.unEntrada)) {
				if (retorno == "") {
					retorno += "Unidade de entrada inválida";
				}else{
					retorno += ", Unidade de entrada inválida";
				}
			}
		}else {
			if (retorno == "") {
				retorno += "Unidade de entrada";
			}else{
				retorno += ", Unidade de entrada não";
			}
		}
		
		if (newProduto.estoque < 0) {
			if (retorno == "") {
				retorno += "Estoque menor que zero";
			}else{
				retorno += ", Estoque menor que zero";
			}	
		}
		
		if (newProduto.unEstoque != "") {
			if (!expUnMedida.test(newProduto.unEstoque)) {
				if (retorno == "") {
					retorno += "Unidade de estoque inválida";
				}else{
					retorno += ", Unidade de estoque inválida";
				}
			}
		}else {
			if (retorno == "") {
				retorno += "Unidade de estoque";
			}else{
				retorno += ", Unidade de estoque";
			}
		}
		
		if (newProduto.margem != "") {
			if (!expNumeros.test(newProduto.margem)) {
				if (retorno == "") {
					retorno += "Margem inválida";
				}else{
					retorno += ", Margem inválida";
				}
			}
		}
		
		if (newProduto.valorVenda != "") {
			if (!expNumeros.test(newProduto.valorVenda)) {
				if (retorno == "") {
					retorno += "Valor venda inválido";
				}else{
					retorno += ", Valor venda inválido";
				}
			}
		}else {
			if (retorno == "") {
				retorno += "Valor venda";
			}else{
				retorno += ", Valor venda";
			}
		}
		
		if (retorno != "") {
			retorno += ".";
		}
		return retorno;
	}