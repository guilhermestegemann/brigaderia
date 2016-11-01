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
				var status = "";
				var btnFaturar = "";
				var btnEditar = "";
				var btnExcluir = "";
				var showBtnCancelar = "Cancelar";
				var showBtnEditar = "";
				for (var i = 0; i < listaDePedidos.length; i++) {
					
					if (listaDePedidos[i].emissao != null) {
						listaDePedidos[i].emissao = BRIGADERIA.convertData.dateToStr(listaDePedidos[i].emissao);
					}
					
					if (listaDePedidos[i].faturado == "N") {
						status = "Pendente";
						showBtnEditar = "Editar";
					}else{
						status = "Faturado";
						btnFaturar = "disabled='disabled'";
						showBtnEditar = "Visualizar";
					}
					
					if (listaDePedidos[i].cancelado == "S") { 
						status = "Cancelado";
						showBtnCancelar = "Descancelar";
					}
					
					if (listaDePedidos[i].produzido == "S") {
						if (status == "Pendente") {
							status = "Produzido";
						}
					}
					
					if (status != "Pendente") {
						btnExcluir = "disabled='disabled'";
					}
					
					html += "<tr>"
					  + "<td>" + listaDePedidos[i].numero + "</td>"
					  + "<td>" + listaDePedidos[i].cliente + "</td>"
					  + "<td>" + listaDePedidos[i].emissao + "</td>"
					  + "<td>" + "R$ " + parseFloat(listaDePedidos[i].total).toFixed(2) + "</td>"
					  + "<td>" + status + "</td>"
					  +"<td><button class='btn btn-primary btn-sm' type='button' onclick='BRIGADERIA.gerenciarPedidoVenda.visualizarPedido(" + listaDePedidos[i].numero + "," + "\"" + listaDePedidos[i].faturado + "\"" +")' aria-hidden='true'>"+showBtnEditar+"</button>"
					  + "<button class='btn btn-success btn-sm'" + btnFaturar + "type='button' onclick='BRIGADERIA.gerenciarPedidoVenda.faturarPedido(" + listaDePedidos[i].numero + ")' aria-hidden='true'>Faturar</button>"
					  + "<button class='btn btn-warning btn-sm' type='button' onclick='BRIGADERIA.gerenciarPedidoVenda.controlaCancelamento(" + listaDePedidos[i].numero + "," + "\"" + listaDePedidos[i].cancelado + "\"" + ")' aria-hidden='true'>"+showBtnCancelar+"</button>"
					  + "<button class='btn btn-danger btn-sm'" + btnExcluir + "type='button' onclick='BRIGADERIA.gerenciarPedidoVenda.deletarPedido(" + listaDePedidos[i].numero + ")' aria-hidden='true'>Excluir</button></td>"
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
	
	BRIGADERIA.gerenciarPedidoVenda.faturarPedido = function (numero) {
		
		bootbox.confirm({ 
			size: 'medium',
			message: "Deseja realmente faturar o pedido selecionado?", 
			callback: function(confirma){
				if (confirma) {
					BRIGADERIA.pedidoVendaService.faturarPedido({
						numero : numero,
						success: function (successo) {
							bootbox.alert(successo.replace("\n","<br>"));
							BRIGADERIA.gerenciarPedidoVenda.buscar();
						},
						error: function(err) {
							bootbox.alert(err.responseText);
						}
					})
				}
			}
		});
	}
	
	BRIGADERIA.gerenciarPedidoVenda.controlaCancelamento = function (numero, cancelado) {
		var acao = "";
		if (cancelado == "S"){
			acao = "descancelar";
		}else{
			acao = "cancelar";
		}
		bootbox.confirm({ 
			size: 'medium',
			message: "Deseja realmente " + acao + " o pedido selecionado?", 
			callback: function(confirma){
				if (confirma) {
					if (cancelado == "N") {
						BRIGADERIA.pedidoVendaService.cancelarPedido({
							numero : numero,
							success: function (successo) {
								bootbox.alert(successo);
								BRIGADERIA.gerenciarPedidoVenda.buscar();
							},
							error: function(err) {
								bootbox.alert(err.responseText);
							}
						});
					}else{
						BRIGADERIA.pedidoVendaService.descancelarPedido({
							numero : numero,
							success: function (successo) {
								bootbox.alert(successo);
								BRIGADERIA.gerenciarPedidoVenda.buscar();
							},
							error: function(err) {
								bootbox.alert(err.responseText);
							}
						});
					}
				}
			}
		});
	}
	
	BRIGADERIA.gerenciarPedidoVenda.deletarPedido = function (numero) {
		
		bootbox.confirm({ 
			size: 'medium',
			message: "Deseja realmente excluir o pedido?", 
			callback: function(confirma){
				if (confirma) {
					BRIGADERIA.pedidoVendaService.deletar({
						numero : numero,
						success: function (sucesso) {
							bootbox.alert(sucesso);
							BRIGADERIA.gerenciarPedidoVenda.buscar();
						},
						error: function(err) {
							bootbox.alert(err.responseText);
						}
					})
				}
			}
		});
	}
	
	$(".filtro").on('change',function(){
		BRIGADERIA.gerenciarPedidoVenda.buscar();
	});
	
	BRIGADERIA.pedidoVenda.listarClientes("#clienteFiltro");
});