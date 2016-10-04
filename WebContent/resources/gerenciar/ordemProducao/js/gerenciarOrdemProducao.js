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
				var btnEditar = "";
				var btnIniciarProducao = "";
				var btnFinalizar = "";
				var btnCancelarInicio = "";
				var btnCancelarFinalizada = "";
				var btnExcluir = "";
				var btnVisualizar = "";
				
				for (var i = 0; i < listaDeOrdens.length; i++) {
					//inicia botões vazios
					btnEditar = "";
					btnIniciarProducao = "";
					btnFinalizar = "";
					btnCancelarInicio = "";
					var btnCancelarFinalizada = "";
					btnExcluir = "";
					btnVisualizar = "";
					
					if (listaDeOrdens[i].data != null) {
						listaDeOrdens[i].data = BRIGADERIA.convertData.dateToStr(listaDeOrdens[i].data);
					}
					
					status = "Pendente";
					btnEditar = "<button class='btn btn-primary btn-sm' type='button' onclick='BRIGADERIA.gerenciarOrdemProducao.visualizarOrdemProducao(" + listaDeOrdens[i].numero + ","+ "\"" + listaDeOrdens[i].produzida + "\"" + ")' aria-hidden='true'>Editar</button>";
					btnIniciarProducao = "<button class='btn btn-success btn-sm' type='button' onclick='BRIGADERIA.gerenciarOrdemProducao.iniciarProducao(" + listaDeOrdens[i].numero + ")' aria-hidden='true'>Iniciar Produção</button>";
					btnExcluir = "<button class='btn btn-danger btn-sm' type='button' onclick='BRIGADERIA.gerenciarOrdemProducao.deletarOrdemProducao(" + listaDeOrdens[i].numero + ")' aria-hidden='true'>Excluir</button>";
					
					if (listaDeOrdens[i].emProducao =="S"){
						status = "Em Produção";
						btnExcluir = "";
						btnIniciarProducao = "";
						btnFinalizar = "<button class='btn btn-success btn-sm' type='button' onclick='BRIGADERIA.gerenciarOrdemProducao.finalizarProducao(" + listaDeOrdens[i].numero + ")' aria-hidden='true'>Finalizar Produção</button>";
						btnCancelarInicio = "<button class='btn btn-warning btn-sm' type='button' onclick='BRIGADERIA.gerenciarOrdemProducao.cancelarInicio(" + listaDeOrdens[i].numero + ")' aria-hidden='true'>Cancelar Início</button>"; 
					}
					if (listaDeOrdens[i].produzida == "S"){
						status = "Produzida";
						btnExcluir = "";
						btnEditar = "";
						btnProduzir = "";
						btnCancelarInicio = "";
						btnIniciarProducao = "";
						btnCancelarFinalizada = "<button class='btn btn-warning btn-sm' type='button' onclick='BRIGADERIA.gerenciarOrdemProducao.cancelarFinalizada(" + listaDeOrdens[i].numero + ")' aria-hidden='true'>Cancelar</button>";
						btnVisualizar = "<button class='btn btn-primary btn-sm' type='button' onclick='BRIGADERIA.gerenciarOrdemProducao.visualizarOrdemProducao(" + listaDeOrdens[i].numero + ","+ "\"" + listaDeOrdens[i].produzida + "\"" + ")' aria-hidden='true'>Visualizar</button>";
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
					  + "<td>"
					  	 + btnEditar
					  	 + btnVisualizar
					     + btnIniciarProducao
					     + btnFinalizar
					     + btnCancelarInicio
					     + btnCancelarFinalizada
					     + btnExcluir
					  + "</td>"
					  + "</tr>";
				}
				$("#resultadoOrdemProducao tbody").html(html);
			
			},
			error : function(err) {
				console.log(err);
			} 
		});		   		
	};
	
	BRIGADERIA.gerenciarOrdemProducao.iniciarProducao = function(numero){
		bootbox.confirm({ 
			size: 'medium',
			message: "Deseja realmente iniciar a produção?", 
			callback: function(confirma){
				if (confirma) {
					BRIGADERIA.ordemProducaoService.iniciarProducao({
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
	
	BRIGADERIA.gerenciarOrdemProducao.cancelarInicio = function(numero){
		bootbox.confirm({ 
			size: 'medium',
			message: "Deseja realmente cancelar o inicio da produção?", 
			callback: function(confirma){
				if (confirma) {
					BRIGADERIA.ordemProducaoService.cancelarInicio({
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
	
	BRIGADERIA.gerenciarOrdemProducao.deletarOrdemProducao = function(numero){
		bootbox.confirm({ 
			size: 'medium',
			message: "Deseja realmente deletar a ordem de produção?", 
			callback: function(confirma){
				if (confirma) {
					BRIGADERIA.ordemProducaoService.deletarOrdem({
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
	
	BRIGADERIA.gerenciarOrdemProducao.cancelarFinalizada = function(numero){
		bootbox.confirm({ 
			size: 'medium',
			message: "Deseja realmente cancelar a produção?", 
			callback: function(confirma){
				if (confirma) {
					BRIGADERIA.ordemProducaoService.cancelarFinalizada({
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

