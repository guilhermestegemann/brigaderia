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
				var shoBtnEditar = "Editar";
				for (var i = 0; i < listaDeOrdens.length; i++) {
					
					if (listaDeOrdens[i].data != null) {
						listaDeOrdens[i].data = BRIGADERIA.convertData.dateToStr(listaDeOrdens[i].data);
					}
					status = "Pendente";
					if (listaDeOrdens[i].emProducao =="S"){
						status = "Em Produção";
					}
					if (listaDeOrdens[i].produzida == "S"){
						status = "Produzida";
						shoBtnEditar = "Visualizar"
					}
					if (listaDeOrdens[i].cancelada == "S"){
						status = "Cancelada";
					}
					if (listaDeOrdens[i].horaInicio == null){
						listaDeOrdens[i].horaInicio = "";
					}
					if (listaDeOrdens[i].horaFim == null){
						listaDeOrdens[i].horaFim = "";
					}
					if (listaDeOrdens[i].duracao == null){
						listaDeOrdens[i].duracao = "";
					}
					
					html += "<tr>"
					  + "<td>" + listaDeOrdens[i].numero + "</td>"
					  + "<td>" + listaDeOrdens[i].data + "</td>"
					  + "<td>" + listaDeOrdens[i].duracao + "</td>"
					  + "<td>" + listaDeOrdens[i].horaFim + "</td>"
					  + "<td>" + listaDeOrdens[i].duracao + "</td>"
					  + "<td>" + status + "</td>"
					  + "<td><button class='btn btn-primary btn-sm' type='button' onclick='BRIGADERIA.gerenciarOrdemProducao.visualizarOrdemProducao(" + listaDeOrdens[i].numero + ","+ "\"" + listaDeOrdens[i].produzida + "\"" + ")' aria-hidden='true'>"+ shoBtnEditar + "</button>"
					  	 +	"<button class='btn btn-danger btn-sm' type='button' onclick='BRIGADERIA.gerenciarOrdemProducao.deletarOrdemProducao(" + listaDeOrdens[i].numero + ")' aria-hidden='true'>Excluir</button>  </td>"
					  + "</tr>";
				}
				
				$("#resultadoOrdemProducao tbody").html(html);
			
			},
			error : function(err) {
				console.log(err);
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

