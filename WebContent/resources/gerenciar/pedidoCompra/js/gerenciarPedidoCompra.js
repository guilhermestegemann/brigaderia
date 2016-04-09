BRIGADERIA.gerenciarPedidoCompra = new Object();

$(document).ready( function () {
	
	$("#subConteudo").text(""); //inicia div vazia
	
	$('.dataFiltro').datepicker({
		format: 'dd/mm/yyyy'
	});
	
	BRIGADERIA.gerenciarPedidoCompra.buscar = function () {
		var dataInicio = BRIGADERIA.convertData.strToDate($("#dataInicio").val());
		var dataFim = BRIGADERIA.convertData.strToDate($("#dataFim").val());
		if (dataInicio == "undefined-undefined-") {
			dataInicio = "null";
		}
		if (dataFim == "undefined-undefined-") {
			dataFim = "null";
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
						listaDePedidos[i].data = BRIGADERIA.convertData.dateToStr(listaDePedidos[i].ultimaVenda);
					}
					
					html += "<tr>"
					  + "<td>" + listaDePedidos[i].numero + "</td>"
					  + "<td>" + listaDePedidos[i].data + "</td>"
					  + "<td>" + listaDePedidos[i].total + "</td>"
					  + "<td>" + listaDePedidos[i].atualizado + "</td>"
					  + "<td><a href='#'><i class='glyphicon glyphicon-edit' onclick='BRIGADERIA.gerenciarPedidoCompra.editarPedido(" + listaDePedidos[i].codigo + ")' aria-hidden='true'></i></a>"
					  	 +	"<a href='#'><i class='glyphicon glyphicon-remove-sign' onclick='BRIGADERIA.gerenciarPedidoCompra.deletarPedido(" + listaDePedidos[i].codigo + ")' aria-hidden='true'></i></a>  </td>"
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
	
});