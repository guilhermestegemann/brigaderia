BRIGADERIA.validaProdutos = new Object();

	BRIGADERIA.validaProdutos.validar = function(produto) {
		retorno = "";
		var expUnMedida = /^[A-Za-z]{2}$/;
		var expNumeros = /^[0-9]+$/;
		if (produto.tipoItem == "") {
			retorno += "Tipo Item"; 
		}
		
		if (produto.descricao =="") {
			if (retorno == "") {
				retorno += "Descrição";
			}else{
				retorno += ", Descrição";
			}	
		}
		
		if (produto.qtdeEntrada != "") {
			if (!expNumeros.test(produto.qtdeEntrada)) {
				if (retorno == "") {
					retorno += "Quantidade entrada inválida";
				}else{
					retorno += ", Quantidade entrada inválida";
				}
			}
		}else {
			if (retorno == "") {
				retorno += "Quantidade de entrada não informada";
			}else{
				retorno += ", Quantidade de entrada não informada";
			}
		}
		
		if (produto.unEntrada != "") {
			if (!expUnMedida.test(produto.unEntrada)) {
				if (retorno == "") {
					retorno += "Unidade de entrada inválida";
				}else{
					retorno += ", Unidade de entrada inválida";
				}
			}
		}else {
			if (retorno == "") {
				retorno += "Unidade de entrada não informada";
			}else{
				retorno += ", Unidade de entrada não informada";
			}
		}
		
		if (produto.estoque < 0) {
			if (retorno == "") {
				retorno += "Estoque menor que zero";
			}else{
				retorno += ", Estoque menor que zero";
			}	
		}
		
		if (produto.unEstoque != "") {
			if (!expUnMedida.test(produto.unEstoque)) {
				if (retorno == "") {
					retorno += "Unidade de estoque inválida";
				}else{
					retorno += ", Unidade de estoque inválida";
				}
			}
		}else {
			if (retorno == "") {
				retorno += "Unidade de estoque não informada";
			}else{
				retorno += ", Unidade de estoque não informada";
			}
		}
		
		if (produto.margem == "") {
			if (retorno == "") {
				retorno += "Margem não informada";
			}else{
				retorno += ", Margem não informada";
			}
		}
		
		if (produto.valorVenda < 0) {
			if (retorno == "") {
				retorno += "Valor venda menor que zero";
			}else{
				retorno += ", Valor venda menor que zero";
			}
		}else if (produto.valorVenda == ""){
			if (retorno == "") {
				retorno += "Valor venda não informado";
			}else{
				retorno += ", Valor venda não informado";
			}
		}
		
		if (retorno != "") {
			retorno += ".";
		}
		return retorno;
	}