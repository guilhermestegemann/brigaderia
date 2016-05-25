BRIGADERIA.gerenciarPedidoVenda = new Object();

$(document).ready( function () {
	
	$("#subConteudo").text(""); //inicia div vazia
	
	$('.dataFiltro').datepicker({
		format: 'dd/mm/yyyy'
	});
	
	BRIGADERIA.gerenciarPedidoVenda.buscar = function () {
		var dataInicio = $("#dataInicio").val();
		var dataFim = $("#dataFim").val();
		var faturado = $("#faturadoFiltro").val();
		var cancelado = $("#canceladoFiltro").val();
		var produzido = $("#produzidoFiltro").val();
		var codCliente = $("#clienteFiltro").val();
		if (dataInicio == "") {
			dataInicio = "null";
		}else{
			dataInicio = BRIGADERIA.convertData.strToDate(dataInicio);
		}
		if (dataFim == "") {
			dataFim = "null";
		}else{
			dataFim = BRIGADERIA.convertData.strToDate(dataFim);
		}
		
		
		BRIGADERIA.gerenciarPedidoVenda.exibirPedidos (undefined, dataInicio, dataFim, faturado, cancelado, produzido, codCliente);
	};
	
	BRIGADERIA.gerenciarPedidoVenda.exibirPedidos = function(listaDePedidos, dataInicio, dataFim, faturado, cancelado, produzido, codCliente) {
		
		BRIGADERIA.pedidoVendaService.listar({
			dataInicio: dataInicio,
			dataFim: dataFim,
			faturado: faturado,
			cancelado: cancelado,
			produzido: produzido,
			codCliente: codCliente,
			success : function (listaDePedidos) {
				
				var html = "";
				var faturado = "";
				var produzido = "";
				var cancelado = "";
				for (var i = 0; i < listaDePedidos.length; i++) {
					
					if (listaDePedidos[i].emissao != null) {
						listaDePedidos[i].emissao = BRIGADERIA.convertData.dateToStr(listaDePedidos[i].emissao);
					}
					
					if (listaDePedidos[i].faturado == "N") {
						faturado = "<input type='checkbox' name='faturado'>";
					}else{
						faturado = "<input type='checkbox' name='faturado' checked>";
					}
					
					if (listaDePedidos[i].cancelado == "N") {
						cancelado = "<input type='checkbox' name='cancelado'>";
					}else{
						cancelado = "<input type='checkbox' name='cancelado' checked>";
					}
					
					if (listaDePedidos[i].produzido == "N") {
						produzido = "<input type='checkbox' name='produzido'>";
					}else{
						produzido = "<input type='checkbox' name='produzido' checked>";
					}
					
					html += "<tr>"
					  + "<td>" + listaDePedidos[i].numero + "</td>"
					  + "<td>" + listaDePedidos[i].cliente + "</td>"
					  + "<td>" + listaDePedidos[i].emissao + "</td>"
					  + "<td>" + "R$ " + parseFloat(listaDePedidos[i].total).toFixed(2) + "</td>"
					  + "<td>" + faturado + "</td>"
					  + "<td>" + cancelado + "</td>"
					  + "<td>" + produzido + "</td>"
					  + "<td><a href='#'><i class='glyphicon glyphicon-edit' onclick='BRIGADERIA.gerenciarPedidoVenda.visualizarPedido(" + listaDePedidos[i].numero + "," + "\"" + listaDePedidos[i].faturado + "\"" +")' aria-hidden='true'></i></a>"
					  	 +	"<a href='#'><i class='glyphicon glyphicon-remove-sign' onclick='BRIGADERIA.gerenciarPedidoCompra.deletarPedido(" + listaDePedidos[i].numero + ")' aria-hidden='true'></i></a>  </td>"
					  + "</tr>";
				}
				
				$("#resultadoPedidoVenda tbody").html(html);
			
			},
			error : function(err) {
				console.log(err);
			} 
		});		   		
	};
	
	$("#buttonPesquisar").on('click', function(){
		BRIGADERIA.gerenciarPedidoVenda.buscar();
	});
	
	BRIGADERIA.gerenciarPedidoVenda.buscar();
	
	BRIGADERIA.gerenciarPedidoVenda.lancarPedido = function() {
		$("#conteudo").load("resources/faturamento/pedidoVenda/pedidoVenda.html", function (){
		BRIGADERIA.pedidoVenda.exibirFormulario();
		});
	};
	
	BRIGADERIA.gerenciarPedidoVenda.visualizarPedido = function(numero, faturado) {
		var url = "";
		if (faturado == "S") {
			url = "resources/faturamento/pedidoVenda/pedidoVendaView.html";
		}else{
			url = "resources/faturamento/pedidoVenda/pedidoVenda.html"
		}
		$("#conteudo").load(url, function (){
			BRIGADERIA.pedidoVenda.exibirEdicao(numero, faturado);
		});	
	};
/*	
	BRIGADERIA.gerenciarPedidoCompra.deletarPedido = function (numero) {
		
		bootbox.confirm({ 
			size: 'medium',
			message: "A exclusão de Pedido de Compra faz a saída dos itens no estoque. A quantidade de cada produto será verificada antes da exclusão. Deseja continuar?", 
			callback: function(confirma){
				if (confirma == true) {
					BRIGADERIA.pedidoCompraService.deletar({
						numero : numero,
						success: function (successo) {
							bootbox.alert(successo.replace("\n","<br>"));
							BRIGADERIA.gerenciarPedidoCompra.buscar();
						},
						error: function(err) {
							bootbox.alert(err.responseText);
						}
					})
				}
			}
		});
	}*/
	
	$(".filtro").on('change',function(){
		BRIGADERIA.gerenciarPedidoVenda.buscar();
	});
	
	BRIGADERIA.pedidoVenda.listarClientes("#clienteFiltro");
});