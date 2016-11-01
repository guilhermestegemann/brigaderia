BRIGADERIA.vendas = new Object();

$(document).ready(function(){
	var tipoRelatorio = "";
	
	$("#gerarRelatorio").on("click",function(){
		if(tipoRelatorio === ""){
			bootbox.alert({
				message: "Selecione um tipo de relatório.",
				 size: 'small'
			});
		};
	});
	
	$("#subConteudo").text(""); //inicia div vazia
	
	$('.dataFiltro').datepicker({
		format: 'dd/mm/yyyy'
	});
	
	BRIGADERIA.vendas.eventoRadio = function (radio) {
		tipoRelatorio = radio.id;
		if (radio.id == "rdbtnProdutos") {
			$("#clientes-produtos option").remove();
			$("#clientes-produtos").val("");
			$("#clientes-produtos").append("<option value='0'>Selecione o produto</option>");
			BRIGADERIA.vendas.listarProdutos();
			$("#gerarRelatorio").attr("onclick","BRIGADERIA.vendas.gerarPorProduto()");
		}else{
			$("#clientes-produtos option").remove();
			$("#clientes-produtos").val("");
			$("#clientes-produtos").append("<option value='0'>Selecione o cliente</option>");
			BRIGADERIA.vendas.listarClientes();
			$("#gerarRelatorio").attr("onclick", "BRIGADERIA.vendas.gerarPorCliente()");
		}
	}
	
	BRIGADERIA.vendas.listarClientes = function (){
		BRIGADERIA.vendasService.listarClientes({
			success: function(data) {
				var html = "";
				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigo + "'>" + data[i].nome + "</option>";
				}
				$("#clientes-produtos").append(html);
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		});
	}
	
	BRIGADERIA.vendas.listarProdutos = function () { 
		BRIGADERIA.vendasService.listarProdutos({
			success: function(data) {
				var html = "";
				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigoProduto + "'>" + data[i].descricao + "</option>";
				}
				$("#clientes-produtos").append(html);
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		});
	};
	
	BRIGADERIA.vendas.gerarPorProduto = function(){ 
		$("#resultadoVendas").text("");
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
		BRIGADERIA.vendasService.gerarPorProduto({
			produto : $("#clientes-produtos").val(),
			dataInicio : dataInicio,
			dataFim : dataFim,
			numRegistro : $("#numRegistro").val() === "" ? '0': $("#numRegistro").val(),
			success : function(vendasPorProduto){
				if (vendasPorProduto.length == 0){
					bootbox.alert("A consulta não retornou nenhuma informação, verifique os filtros aplicados e tente novamente.");
				}else{
					var totalVenda = 0;
					var html = "";
					var cabecalho = "<thead>"
									+"<tr>"
										+"<th>Código</th>"
										+"<th>Nome</th>"
										+"<th>Quantidade</th>"
										+"<th>Valor Médio</th>"
										+"<th>Total Custo</th>"
										+"<th>Margem</th>"
										+"<th>Total Venda</th>"
										+"<th>Venda/Total</th>"
									+"</tr>"
									+"</thead>"
									+"<tbody></tbody>";
					for (var i = 0; i < vendasPorProduto.length; i++){
						totalVenda = parseFloat(totalVenda) + parseFloat(vendasPorProduto[i].total);
					}
					for (var i = 0; i < vendasPorProduto.length; i++){
						html += "<tr>"
							+ "<td>"+ vendasPorProduto[i].codigo +"</td>"
							+ "<td>"+ vendasPorProduto[i].nome +"</td>"
							+ "<td>"+ parseFloat(vendasPorProduto[i].qtde).toFixed(2) +"</td>"
							+ "<td>"+ parseFloat(vendasPorProduto[i].valorMedio).toFixed(2) +"</td>"
							+ "<td>"+ parseFloat(vendasPorProduto[i].custo).toFixed(2) +"</td>"
							+ "<td>"+ parseFloat(((parseFloat(vendasPorProduto[i].total) - parseFloat(vendasPorProduto[i].custo)) / parseFloat(vendasPorProduto[i].custo)*100)).toFixed(2) +"%</td>"
							+ "<td>"+ parseFloat(vendasPorProduto[i].total).toFixed(2) +"</td>"
							+ "<td>"+ parseFloat((parseFloat(vendasPorProduto[i].total) / parseFloat(totalVenda)) * 100).toFixed(2) +"%</td>"
						+"</tr>";
					};
					$("#resultadoVendas").html(cabecalho);
					$("#resultadoVendas tbody").html(html);
					$("#totalVenda").text("Total: R$ " + parseFloat(totalVenda).toFixed(2));
				}
			},
			error : function(error){
				bootbox.alert(error.responseText);
			}
		})
	};
	
	
	
	BRIGADERIA.vendas.gerarPorCliente = function(){
		$("#resultadoVendas").text("");
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
		BRIGADERIA.vendasService.gerarPorCliente({
			cliente : $("#clientes-produtos").val(),
			dataInicio : dataInicio,
			dataFim : dataFim,
			numRegistro : $("#numRegistro").val() === "" ? '0': $("#numRegistro").val(),
			success : function(vendasPorCliente){
				if (vendasPorCliente.length == 0) {
					bootbox.alert("A consulta não retornou nenhuma informação, verifique os filtros aplicados e tente novamente.");
				}else{
					var totalVenda = 0;
					var html = "";
					var cabecalho = "<thead>"
									+"<tr>"
										+"<th>Código</th>"
										+"<th>Nome</th>"
										+"<th>Pedidos</th>"
										+"<th>Valor Médio Pedido</th>"
										+"<th>Total Custo</th>"
										+"<th>Margem</th>"
										+"<th>Total Venda</th>"
										+"<th>Venda/Total</th>"
									+"</tr>"
									+"</thead>"
									+"<tbody></tbody>";
					for (var i = 0; i < vendasPorCliente.length; i++){
						totalVenda = parseFloat(totalVenda) + parseFloat(vendasPorCliente[i].total);
					}
					for (var i = 0; i < vendasPorCliente.length; i++){
						html += "<tr>"
							+ "<td>"+ vendasPorCliente[i].codigo +"</td>"
							+ "<td>"+ vendasPorCliente[i].nome +"</td>"
							+ "<td>"+ vendasPorCliente[i].pedidos +"</td>"
							+ "<td>"+ parseFloat(vendasPorCliente[i].valorMedioPedido).toFixed(2) +"</td>"
							+ "<td>"+ parseFloat(vendasPorCliente[i].custo).toFixed(2) +"</td>"
							+ "<td>"+ parseFloat(((parseFloat(vendasPorCliente[i].total) - parseFloat(vendasPorCliente[i].custo)) / parseFloat(vendasPorCliente[i].custo)*100)).toFixed(2) +"%</td>"
							+ "<td>"+ parseFloat(vendasPorCliente[i].total).toFixed(2) +"</td>"
							+ "<td>"+ parseFloat((parseFloat(vendasPorCliente[i].total) / parseFloat(totalVenda)) * 100).toFixed(2) +"%</td>"
						+"</tr>";
					};
					$("#resultadoVendas").html(cabecalho);
					$("#resultadoVendas tbody").html(html);
					$("#totalVenda").text("Total: R$ " + parseFloat(totalVenda).toFixed(2));
				}
			},
			error : function(error){
				bootbox.alert(error.responseText);
			}
		})
	};
});