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
		
		if(BRIGADERIA.validarDataFiltro.validar(dataInicio, dataFim)){
			BRIGADERIA.gerenciarPedidoCompra.exibirPedidos (undefined, dataInicio, dataFim);
		}
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
					  + "<td><button class='btn btn-primary btn-sm' type='button' onclick='BRIGADERIA.gerenciarPedidoCompra.visualizarPedido(" + listaDePedidos[i].numero + ")' aria-hidden='true'>Visualizar</button>"
					  	 +	"<button class='btn btn-danger btn-sm' type='button' onclick='BRIGADERIA.gerenciarPedidoCompra.deletarPedido(" + listaDePedidos[i].numero + ")' aria-hidden='true'>Excluir</button>  </td>"
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
	
	BRIGADERIA.gerenciarPedidoCompra.lancarPedido = function() {
		$("#conteudo").load("faturamento/pedidoCompra/pedidoCompra.html", function (){
		BRIGADERIA.pedidoCompra.exibirFormulario();
		});
	};
	
	BRIGADERIA.gerenciarPedidoCompra.visualizarPedido = function(numero) {
		$("#conteudo").load("faturamento/pedidoCompra/pedidoCompraView.html", function (){
			BRIGADERIA.pedidoCompra.exibirEdicao(numero);
		});	
	};
	
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
	}
});







