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
				retorno += "Quantidade de entrada não informada";
			}else{
				retorno += ", Quantidade de entrada não informada";
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
				retorno += "Unidade de entrada não informada";
			}else{
				retorno += ", Unidade de entrada não informada";
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
				retorno += "Unidade de estoque não informada";
			}else{
				retorno += ", Unidade de estoque não informada";
			}
		}
		
		if (newProduto.margem == "") {
			if (retorno == "") {
				retorno += "Margem não informada";
			}else{
				retorno += ", Margem não informada";
			}
		}
		
		if (newProduto.valorVenda < 0) {
			if (retorno == "") {
				retorno += "Valor venda menor que zero";
			}else{
				retorno += ", Valor venda menor que zero";
			}
		}else if (newProduto.valorVenda == ""){
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