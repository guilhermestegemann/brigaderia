BRIGADERIA.pedidoCompra = new Object();

$(document).ready( function () {
	
	var produtos;
	var produtoArray = []; //utilizado ao inserir ingrediente no formulário
	
	$("#subConteudo").text(""); //inicia div vazia
	
	$("#produtos").on('change',function(){
		for (var i = 0; i < produtos.length; i++) {
			if (produtos[i].codigoProduto == $("#produtos").val()) {
				$("#unEntrada").val(produtos[i].unEntrada);
			}
		}
		
	});
	
	$("#qtdeProduto").on('blur', function(){
		if ($("#unitario").val() != "") {
			$("#totalItemPedidoCompra").val($("#unitario").val().replace(",",".") * ($("#qtdeProduto").val().replace(",",".")));
		}
		if ($("#totalItemPedidoCompra").val() != "") {
			$("#totalItemPedidoCompra").val(parseFloat($("#totalItemPedidoCompra").val()).toFixed(2));
		}
	});
	
	$("#unitario").on('blur', function(){
		if ($("#unitario").val() != "") {
			$("#totalItemPedidoCompra").val(parseFloat($("#unitario").val().replace(",",".") * $("#qtdeProduto").val().replace(",",".")).toFixed(2));
		}
		if ($("#unitario").val().replace(",",".") > 0) {
			$("#unitario").val(parseFloat($("#unitario").val().replace(",",".")).toFixed(2));
		}
	});

	BRIGADERIA.pedidoCompra.listarProdutos = function () { 
		BRIGADERIA.pedidoCompraService.listarProdutos({
			success: function(data) {
				var html = "";
				produtos = data;//atribui ao objeto global o valor retornado da lista. Utilizado pra edição/inserção de itens de pedido
				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigoProduto + "'>" + data[i].descricao + "</option>";
				}
				$("#produtos").append(html);
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		});
	};
	
	BRIGADERIA.pedidoCompra.exibirFormulario = function () {
		BRIGADERIA.pedidoCompra.listarProdutos();
		$("#totalPedidoCompra").val("0.00");
	};
	
	BRIGADERIA.pedidoCompra.incluirProduto = function () {
		var expNumeros = /^[0-9]+$/;
		if ($("#produtos").val() == "") {
			bootbox.alert("Selecione o Produto!");
		}else if (!expNumeros.test($("#qtdeProduto").val().replace(",","").replace(".",""))) {
			bootbox.alert("Quantida inválida.");
		}else if (parseFloat($("#qtdeProduto").val().replace(",",".")) == 0){
			bootbox.alert("Quantidade deve ser maior que zero.");
		}else if (($("#unitario").val() == "") || ($("#unitario").val() <= 0)) {
			bootbox.alert("Unitário inválido.");
		}else{
			var html = "";
			var descricao = "";
			var unEntrada = "";
			
			for (var i = 0; i < produtos.length; i++) {
		
				if ((produtos[i].codigoProduto == $("#produtos").val()) && (unEntrada == "")) {
					unEntrada = produtos[i].unEntrada;
					descricao = produtos[i].descricao; 
				}
			}
			
			if (unEntrada != "") {
				html =  "<tr class='itemPedidoCompra'>"
						  + "<td >" + $("#produtos").val() + "</td>"
						  + "<td>" + descricao + "</td>"
						  + "<td>" + unEntrada + "</td>"
						  + "<td>" + $("#qtdeProduto").val().replace(",",".") + "</td>"
						  + "<td>" + parseFloat($("#unitario").val().replace(",",".")).toFixed(2) + "</td>"
						  + "<td>" + parseFloat($("#totalItemPedidoCompra").val()).toFixed(2) + "</td>"
						  + "<td><button class='btn btn-primary btn-sm' type='button' onclick='BRIGADERIA.pedidoCompra.editarProduto(this"+ "," + $("#produtos").val() + "," + "\"" + descricao + "\"" + "," + parseFloat($("#qtdeProduto").val().replace(",",".")) + "," + "\"" + unEntrada + "\"" + "," + parseFloat($("#unitario").val()) + "," + parseFloat($("#totalItemPedidoCompra").val()) +")' aria-hidden='true'>Editar</button>"
						  	  + "<button class='btn btn-danger btn-sm' type='button' onclick='BRIGADERIA.pedidoCompra.deletarProduto(this"+ "," + $("#produtos").val() + "," + "\"" + descricao + "\"" + "," + parseFloat($("#totalItemPedidoCompra").val()) +")' aria-hidden='true'>Excluir</button></td>"
					  + "</tr>";
				$("#totalPedidoCompra").val(parseFloat(parseFloat($("#totalItemPedidoCompra").val()) + parseFloat($("#totalPedidoCompra").val())).toFixed(2)) // soma o total do pedido.
				var prod = {
						codigoProduto: $("#produtos").val(),
						qtde: $("#qtdeProduto").val().replace(",","."),
						unitario: $("#unitario").val(),
						total: $("#totalItemPedidoCompra").val()
				} ;
				
				produtoArray.push(prod);
				$("#itensPedidoCompra tbody").append(html);
				$("#produtos option:selected").remove();
				$("#qtdeProduto").val("");
				$("#unitario").val("");
				$("#totalItemPedidoCompra").val("");
				$("#unEntrada").val("");
			}
		}
	};
	
	BRIGADERIA.pedidoCompra.editarProduto = function (handler, codigo, descricao, qtde, unEntrada, unitario, totalItem) {
		
		$("#qtdeProduto").val(parseFloat(qtde));
		$("#unEntrada").val(unEntrada);
		$("#unitario").val(parseFloat(unitario));
		$("#totalItemPedidoCompra").val(totalItem);
		
		for (var i = 0; i < produtoArray.length; i++) {
			if (produtoArray[i].codigoProduto == codigo) {
				produtoArray.splice(i, 1);
			}
		}
		
		$(handler).closest('tr').remove();//exclui o tr mais proximo.
		$('#produtos').append('<option value="' + codigo + '" selected="unselected">' + descricao + '</option>');
		$("#totalPedidoCompra").val(parseFloat(parseFloat($("#totalPedidoCompra").val()) - parseFloat(totalItem)).toFixed(2)) //diminui do total do pedido.
	};
	
	BRIGADERIA.pedidoCompra.deletarProduto = function (handler, codigo, descricao, totalItem) {
		
		bootbox.confirm({ 
			size: 'small',
			message: "Deseja deletar o Ingrediente?", 
			callback: function(confirma){
				if (confirma) {
					for (var i = 0; i < produtoArray.length; i++) {
						if (produtoArray[i].codigoProduto == codigo) {
							produtoArray.splice(i, 1);
						}
					}
					
					$(handler).closest('tr').remove();//exclui o tr mais proximo.
					$('#produtos').append('<option value="' + codigo + '">' + descricao + '</option>');
					$("#totalPedidoCompra").val(parseFloat(parseFloat($("#totalPedidoCompra").val()) - parseFloat(totalItem)).toFixed(2)) //diminui do total do pedido.
				}
			}
		});
		
		
	};
	
	BRIGADERIA.pedidoCompra.adicionar = function() {
		
		var newPedido = {
			total : $("#totalPedidoCompra").val(),
			itemPedidoCompra: produtoArray
		};
		if (newPedido.itemPedidoCompra == "") {
			bootbox.alert("Produtos não inseridos");
		}else{
			BRIGADERIA.pedidoCompraService.adicionar(newPedido);
		}
	};
	
	BRIGADERIA.pedidoCompra.exibirEdicao = function(numero) {
		BRIGADERIA.pedidoCompraService.buscarPedidoPeloNumero({
			numero : numero,
			async: false,
			success : function (pedido) {
				$("#numero").val(pedido.numero);
				$("#data").val(BRIGADERIA.convertData.dateToStr(pedido.data));
				$("#totalPedidoCompra").val(parseFloat(pedido.total).toFixed(2));
				itemPedido = {};
				itemPedido = pedido.itemPedidoCompra;
				
				for (var i = 0; i < itemPedido.length; i++ ) {
					var html = ""
						html =  "<tr class='itemPedidoCompra'>"
							  + "<td >" + itemPedido[i].codigoProduto + "</td>"
							  + "<td>" + itemPedido[i].descricao + "</td>"
							  + "<td>" + itemPedido[i].unEntrada + "</td>"
							  + "<td>" + itemPedido[i].qtde + "</td>"
							  + "<td>" + parseFloat(itemPedido[i].unitario).toFixed(2) + "</td>"
							  + "<td>" + parseFloat(itemPedido[i].total).toFixed(2) + "</td>"
						  + "</tr>";		
						
						$("#itensPedidoCompra tbody").append(html);
				}
			}
		});	
	};
});