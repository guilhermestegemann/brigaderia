BRIGADERIA.gerenciarPerda = new Object();

$(document).ready( function () {
	
/*	$("#subConteudo").text(""); //inicia div vazia
	
	$('.dataFiltro').datepicker({
		format: 'dd/mm/yyyy'
	});
	
	BRIGADERIA.gerenciarPerda.buscar = function () {
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
		
		BRIGADERIA.gerenciarPerda.exibirPedidos (undefined, dataInicio, dataFim);
	};
	
	BRIGADERIA.gerenciarPerda.exibirPedidos = function(listaDePedidos, dataInicio, dataFim) {
		
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
					  + "<td><a href='#'><i class='glyphicon glyphicon-edit' onclick='BRIGADERIA.gerenciarPerda.visualizarPedido(" + listaDePedidos[i].numero + ")' aria-hidden='true'></i></a>"
					  	 +	"<a href='#'><i class='glyphicon glyphicon-remove-sign' onclick='BRIGADERIA.gerenciarPerda.deletarPedido(" + listaDePedidos[i].numero + ")' aria-hidden='true'></i></a>  </td>"
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
		BRIGADERIA.gerenciarPerda.buscar();
	});
	
	BRIGADERIA.gerenciarPerda.buscar();
*/	
	BRIGADERIA.gerenciarPerda.lancarPerda = function() {
		$("#conteudo").load("resources/movimento/perdas/perdas.html", function (){
		BRIGADERIA.perda.exibirFormulario();
		});
	};
	
/*	BRIGADERIA.gerenciarPerda.visualizarPedido = function(numero) {
		$("#conteudo").load("resources/faturamento/pedidoCompra/pedidoCompraView.html", function (){
			BRIGADERIA.pedidoCompra.exibirEdicao(numero);
		});	
	};
	
	BRIGADERIA.gerenciarPerda.deletarPedido = function (numero) {
		
		bootbox.confirm({ 
			size: 'medium',
			message: "A exclusão de Pedido de Compra faz a saída dos itens no estoque. A quantidade de cada produto será verificada antes da exclusão. Deseja continuar?", 
			callback: function(confirma){
				if (confirma == true) {
					BRIGADERIA.pedidoCompraService.deletar({
						numero : numero,
						success: function (successo) {
							bootbox.alert(successo.replace("\n","<br>"));
							BRIGADERIA.gerenciarPerda.buscar();
						},
						error: function(err) {
							bootbox.alert(err.responseText);
						}
					})
				}
			}
		});
	}
	*/
});







