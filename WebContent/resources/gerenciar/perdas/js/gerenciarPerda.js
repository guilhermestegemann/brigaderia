BRIGADERIA.gerenciarPerda = new Object();

$(document).ready( function () {
	
	$("#subConteudo").text(""); //inicia div vazia
	
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
		
		BRIGADERIA.gerenciarPerda.exibirPerdas (undefined, dataInicio, dataFim);
	};
	
	BRIGADERIA.gerenciarPerda.exibirPerdas = function(listaDePerdas, dataInicio, dataFim) {
		
		BRIGADERIA.perdaService.listar({
			dataInicio: dataInicio,
			dataFim: dataFim,
			success : function (listaDePerdas) {
				
				var html = "";
				
				for (var i = 0; i < listaDePerdas.length; i++) {
					
					if (listaDePerdas[i].data != null) {
						listaDePerdas[i].data = BRIGADERIA.convertData.dateToStr(listaDePerdas[i].data);
					}
					
					
					html += "<tr>"
					  + "<td>" + listaDePerdas[i].numero + "</td>"
					  + "<td>" + listaDePerdas[i].data + "</td>"
					  + "<td>" + "R$ " + parseFloat(listaDePerdas[i].total).toFixed(2) + "</td>"
					  + "<td><a href='#'><i class='glyphicon glyphicon-edit' onclick='BRIGADERIA.gerenciarPerda.visualizarPerda(" + listaDePerdas[i].numero + ")' aria-hidden='true'></i></a>"
					  	 +	"<a href='#'><i class='glyphicon glyphicon-remove-sign' onclick='BRIGADERIA.gerenciarPerda.deletarPerda(" + listaDePerdas[i].numero + ")' aria-hidden='true'></i></a>  </td>"
					  + "</tr>";
				}
				
				$("#resultadoPerda tbody").html(html);
			
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
	
	BRIGADERIA.gerenciarPerda.lancarPerda = function() {
		$("#conteudo").load("resources/movimento/perdas/perdas.html", function (){
		BRIGADERIA.perda.exibirFormulario();
		});
	};
	
	BRIGADERIA.gerenciarPerda.visualizarPerda = function(numero) {
		$("#conteudo").load("resources/movimento/perdas/perdasView.html", function (){
			BRIGADERIA.perda.exibirEdicao(numero);
		});	
	};
		
	BRIGADERIA.gerenciarPerda.deletarPerda = function (numero) {
		
		bootbox.confirm({ 
			size: 'medium',
			message: "Deseja realmente deletar o lançamento de perda?", 
			callback: function(confirma){
				if (confirma) {
					BRIGADERIA.perdaService.deletar({
						numero : numero,
						success: function (sucesso) {
							bootbox.alert(sucesso);
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
});







