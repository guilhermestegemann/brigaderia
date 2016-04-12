BRIGADERIA.gerenciarPedidoCompra = new Object();

$(document).ready( function () {
	
	$("#subConteudo").text(""); //inicia div vazia
	
	$('.dataFiltro').datepicker({
		format: 'dd/mm/yyyy'
	});
	
	BRIGADERIA.gerenciarPedidoCompra.buscar = function () {
		var dataInicio = $("#dataInicio").val();
		var dataFim = $("#dataFim").val();
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
		
		BRIGADERIA.gerenciarPedidoCompra.exibirPedidos (undefined, dataInicio, dataFim);
	};
	
	BRIGADERIA.gerenciarPedidoCompra.exibirPedidos = function(listaDePedidos, dataInicio, dataFim) {
		
		BRIGADERIA.pedidoCompraService.listar({
			dataInicio: dataInicio,
			dataFim: dataFim,
			success : function (listaDePedidos) {
				
				var html = "";
				
				for (var i = 0; i < listaDePedidos.length; i++) {
					
					if (listaDePedidos[i].data != null) {
						listaDePedidos[i].data = BRIGADERIA.convertData.dateToStr(listaDePedidos[i].data);
					}
					
					
					html += "<tr>"
					  + "<td>" + listaDePedidos[i].numero + "</td>"
					  + "<td>" + listaDePedidos[i].data + "</td>"
					  + "<td>" + "R$ " + parseFloat(listaDePedidos[i].total).toFixed(2) + "</td>"
					  + "<td><a href='#'><i class='glyphicon glyphicon-edit' onclick='BRIGADERIA.gerenciarPedidoCompra.visualizarPedido(" + listaDePedidos[i].numero + ")' aria-hidden='true'></i></a>"
					  	 +	"<a href='#'><i class='glyphicon glyphicon-remove-sign' onclick='BRIGADERIA.gerenciarPedidoCompra.deletarPedido(" + listaDePedidos[i].numero + ")' aria-hidden='true'></i></a>  </td>"
					  + "</tr>";
				}
				
				$("#resultadoPedidoCompra tbody").html(html);
			
			},
			error : function(err) {
				console.log(err);
			} 
		});		   		
	};
	
	$("#buttonPesquisar").on('click', function(){
		BRIGADERIA.gerenciarPedidoCompra.buscar();
	});
	
	BRIGADERIA.gerenciarPedidoCompra.buscar();
	
	BRIGADERIA.gerenciarPedidoCompra.lancarPedido = function() {
		$("#conteudo").load("resources/faturamento/pedidoCompra/pedidoCompra.html", function (){
		BRIGADERIA.pedidoCompra.exibirFormulario();
		});
	};
	
	BRIGADERIA.gerenciarPedidoCompra.visualizarPedido = function(numero) {
		$("#conteudo").load("resources/faturamento/pedidoCompra/pedidoCompraView.html", function (){
			BRIGADERIA.pedidoCompra.exibirEdicao(numero);
		});	
	};
});