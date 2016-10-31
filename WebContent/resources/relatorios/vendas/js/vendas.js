BRIGADERIA.vendas = new Object();

$(document).ready(function(){
	$("#subConteudo").text(""); //inicia div vazia
	
	$('.dataFiltro').datepicker({
		format: 'dd/mm/yyyy'
	});
	
	BRIGADERIA.vendas.eventoRadio = function (radio) {
		console.log(radio);
		if (radio.id == "rdbtnProdutos") {
			$("#clientes-produtos option").remove();
			$("#clientes-produtos").val("");
			$("#clientes-produtos").append("<option value='0'>Selecione o produto</option>");
		}else{
			$("#clientes-produtos option").remove();
			$("#clientes-produtos").val("");
			$("#clientes-produtos").append("<option value='0'>Selecione o cliente</option>");
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
	
/*	BRIGADERIA.sugestaoCompra.gerarSugestao = function (){
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
		BRIGADERIA.sugestaoCompraService.gerarSugestao({
			cliente : $("#clientes").val(),
			dataInicio : dataInicio,
			dataFim : dataFim,
			success : function(sugestao){
				var totalSugestao = 0;
				var html = "";
				for (var i = 0; i < sugestao.length; i++){
					html += "<tr>"
						+ "<td>"+ sugestao[i].ingrediente +"</td>"
						+ "<td>"+ sugestao[i].nomeIngrediente +"</td>"
						+ "<td>"+ sugestao[i].unEstoque +"</td>"
						+ "<td>"+ parseFloat(sugestao[i].qtdeNecessaria).toFixed(3) +"</td>"
						+ "<td>"+ parseFloat(sugestao[i].estoque).toFixed(3) +"</td>"
						+ "<td>"+ parseFloat(sugestao[i].qtdeSugestao).toFixed(3) +"</td>"
						+ "<td>"+ "R$ " + sugestao[i].valorCusto +"</td>"
						+ "<td>"+ "R$ " + parseFloat(sugestao[i].valorCusto * sugestao[i].qtdeSugestao).toFixed(2) +"</td>"
					+"</tr>";
					totalSugestao = parseFloat(totalSugestao) + parseFloat(sugestao[i].valorCusto) * parseFloat(sugestao[i].qtdeSugestao);
				};
				$("#resultadoSugestaoCompra tbody").html(html);
				$("#totalSugestao").text("Total: R$ " + parseFloat(totalSugestao).toFixed(2));
			},
			error : function(error){
				bootbox.alert(error.responseText);
			}
		})
	}
	*/
	
});