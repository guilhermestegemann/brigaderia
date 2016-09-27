BRIGADERIA.gerenciarOrdemProducao = new Object();

$(document).ready(function (){
	
	$("#subConteudo").text(""); //inicia div vazia
	
	$('.dataFiltro').datepicker({
		format: 'dd/mm/yyyy'
	});
	
	$('#filtroStatus').on('change', function(){
		BRIGADERIA.gerenciarOrdemProducao.buscar();
	})
	
	BRIGADERIA.gerenciarOrdemProducao.lancarOrdemProducao = function() {
		$("#conteudo").load("resources/movimento/ordemProducao/ordemProducao.html", function (){
			BRIGADERIA.ordemProducao.exibirFormulario();
		});
	};
	
	BRIGADERIA.gerenciarOrdemProducao.buscar = function () {
		
		var dataInicio = $("#dataInicio").val();
		var dataFim = $("#dataFim").val();
		var status = $("#filtroStatus").val();
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
		
		BRIGADERIA.gerenciarOrdemProducao.exibirOrdens (undefined, dataInicio, dataFim, status);
	};
	
	BRIGADERIA.gerenciarOrdemProducao.exibirOrdens = function(listaDeOrdens, dataInicio, dataFim, status) {
		
		BRIGADERIA.ordemProducaoService.listar({
			dataInicio: dataInicio,
			dataFim: dataFim,
			status: status,
			success : function (listaDeOrdens) {
				
				var html = "";
				var status = "";
				var textoBtnEditar = "Editar";
				var textoBtnProduzir = "Produzir";
				var tipoBtnProduzir = "success";
				var btnExcluir = "";
				var btnFinalizar = "disabled='disabled'";
				for (var i = 0; i < listaDeOrdens.length; i++) {
					
					if (listaDeOrdens[i].data != null) {
						listaDeOrdens[i].data = BRIGADERIA.convertData.dateToStr(listaDeOrdens[i].data);
					}
					status = "Pendente";
					if (listaDeOrdens[i].emProducao =="S"){
						status = "Em Produção";
						textoBtnProduzir = "Cancelar Produção";
						tipoBtnProduzir = "info";
						btnExcluir = "disabled='disabled'";
						btnFinalizar = "";
					}
					if (listaDeOrdens[i].produzida == "S"){
						status = "Produzida";
						textoBtnEditar = "Visualizar";
						btnExcluir = "disabled='disabled'";
						textoBtnProduzir = "Cancelar";
						tipoBtnProduzir = "warning";
					}
					if (listaDeOrdens[i].cancelada == "S"){
						status = "Cancelada";
					}
					if (listaDeOrdens[i].horaInicio == null){
						listaDeOrdens[i].horaInicio = "";
					}else{
						listaDeOrdens[i].horaInicio = BRIGADERIA.convertData.dateToStr(listaDeOrdens[i].horaInicio.substring(0,10)) + " " + listaDeOrdens[i].horaInicio.substring(11,19);
					}
					if (listaDeOrdens[i].horaFim == null){
						listaDeOrdens[i].horaFim = "";
					}else{
						listaDeOrdens[i].horaFim = BRIGADERIA.convertData.dateToStr(listaDeOrdens[i].horaFim.substring(0,10)) + " " + listaDeOrdens[i].horaFim.substring(11,19);
					}
					if (listaDeOrdens[i].duracao == null){
						listaDeOrdens[i].duracao = "";
					}
					
					html += "<tr>"
					  + "<td>" + listaDeOrdens[i].numero + "</td>"
					  + "<td>" + listaDeOrdens[i].data + "</td>"
					  + "<td>" + listaDeOrdens[i].horaInicio + "</td>"
					  + "<td>" + listaDeOrdens[i].horaFim + "</td>"
					  + "<td>" + listaDeOrdens[i].duracao + "</td>"
					  + "<td>" + status + "</td>"
					  + "<td><button class='btn btn-primary btn-sm' type='button' onclick='BRIGADERIA.gerenciarOrdemProducao.visualizarOrdemProducao(" + listaDeOrdens[i].numero + ","+ "\"" + listaDeOrdens[i].produzida + "\"" + ")' aria-hidden='true'>"+ textoBtnEditar + "</button>"
					     + "<button class='btn btn-"+tipoBtnProduzir+" btn-sm' type='button' onclick='BRIGADERIA.gerenciarOrdemProducao.controlaAcao(" + listaDeOrdens[i].numero + ","+ "\"" + listaDeOrdens[i].emProducao + "\"" + "," + "\"" + listaDeOrdens[i].produzida + "\"" + ")' aria-hidden='true'>"+ textoBtnProduzir + "</button>"
					     + "<button class='btn btn-success btn-sm' type='button'" + btnFinalizar + "onclick='BRIGADERIA.gerenciarOrdemProducao.finalizarProducao(" + listaDeOrdens[i].numero + ")' aria-hidden='true'>Finalizar Produção</button>"
					  	 + "<button class='btn btn-danger btn-sm' type='button'" + btnExcluir + "onclick='BRIGADERIA.gerenciarOrdemProducao.deletarOrdemProducao(" + listaDeOrdens[i].numero + ")' aria-hidden='true'>Excluir</button>  </td>"
					  + "</tr>";
				}
				
				$("#resultadoOrdemProducao tbody").html(html);
			
			},
			error : function(err) {
				console.log(err);
			} 
		});		   		
	};
	
	BRIGADERIA.gerenciarOrdemProducao.controlaAcao = function(numero, emProducao, produzida){
		var acao = "";
		var confirmacao = "";
		if ((emProducao == "N") && (produzida == "N")){
			acao = "produzir";
			confirmacao = "Deseja realmente iniciar a produção?";
		}else if (emProducao == "S") {
			acao = "cancelarProducao";
			confirmacao = "Deseja realmente cancelar a produção iniciada?";
		}else {
			acao = "cancelarProduzido";
			confirmacao = "Deseja realmente cancelar a produção concluída?";
		}
		bootbox.confirm({ 
			size: 'medium',
			message: confirmacao, 
			callback: function(confirma){
				if (confirma) {
					if (acao == "produzir") {
						BRIGADERIA.ordemProducaoService.produzir({
							numero : numero,
							success: function (successo) {
								bootbox.alert(successo.replace("\n","<br>"));
								BRIGADERIA.gerenciarOrdemProducao.buscar();
							},
							error: function(err) {
								bootbox.alert(err.responseText);
							}
						});
					}else if (acao == "cancelarProducao"){
						BRIGADERIA.ordemProducaoService.cancelarProducao({
							numero : numero,
							success: function (successo) {
								bootbox.alert(successo);
								BRIGADERIA.gerenciarOrdemProducao.buscar();
							},
							error: function(err) {
								bootbox.alert(err.responseText);
							}
						});
					}else {
						BRIGADERIA.ordemProducaoService.cancelarProduzido({
							numero : numero,
							success: function (successo) {
								bootbox.alert(successo);
								BRIGADERIA.gerenciarOrdemProducao.buscar();
							},
							error: function(err) {
								bootbox.alert(err.responseText);
							}
						});
					}
				}
			}
		});
	};
	
	BRIGADERIA.gerenciarOrdemProducao.finalizarProducao = function(numero){
		bootbox.confirm({ 
			size: 'medium',
			message: "Deseja realmente finalizar a produção?", 
			callback: function(confirma){
				if (confirma) {
					BRIGADERIA.ordemProducaoService.finalizarProducao({
						numero : numero,
						success: function (successo) {
							bootbox.alert(successo);
							BRIGADERIA.gerenciarOrdemProducao.buscar();
						},
						error: function(err) {
							bootbox.alert(err.responseText);
						}
					});
				}
			}
		});
	};
	
	BRIGADERIA.gerenciarOrdemProducao.visualizarOrdemProducao = function(numero, produzida) {
		var url = "";
		if (produzida == "S") {
			url = "resources/movimento/ordemProducao/ordemProducaoView.html";
		}else{
			url = "resources/movimento/ordemProducao/ordemProducao.html"
		}
		$("#conteudo").load(url, function (){
			BRIGADERIA.ordemProducao.exibirEdicao(numero, produzida);
		});	
	};
	
	$("#buttonPesquisar").on('click', function(){
		BRIGADERIA.gerenciarOrdemProducao.buscar();
	});
	
	BRIGADERIA.gerenciarOrdemProducao.buscar();
});

