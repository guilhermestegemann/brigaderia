BRIGADERIA.graficosProdutos = new Object();


$(document).ready(function(){
	var tipoGraficoProduto = "";

	
	$('.dataFiltro').datepicker({
		format: 'dd/mm/yyyy'
	});
	
	//Radio
	BRIGADERIA.graficosProdutos.eventoRadio = function (radio){
		tipoGraficoProduto = radio.id;
	};
	
	//Produto
	
	$("#gerarGrafico").on("click",function(){
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
		if(tipoGraficoProduto === ""){
			bootbox.alert({
				message: "Selecione um tipo de relatório.",
				 size: 'small'
			});
		}else if ($("#numRegistro").val()<0){
			bootbox.alert({
				message: "Número de registro não pode ser menor que zero.",
				size: 'medium'
			});
		}else if(BRIGADERIA.validarDataFiltro.validar(dataInicio, dataFim)){
			if (tipoGraficoProduto === "rdbtnMaisVendido"){
				BRIGADERIA.graficoService.porProduto({
					dataInicio : dataInicio,
					dataFim    : dataFim,
					orderBy    : "desc",
					limit      : $("#numRegistro").val() != "" ? $("#numRegistro").val() : 0,
					success    : function(dados){
						BRIGADERIA.graficosProdutos.gerarGrafico(dados);
					}
				});
			}else{
				BRIGADERIA.graficoService.porProduto({
					dataInicio : dataInicio,
					dataFim    : dataFim,
					orderBy    : "null",
					limit      : $("#numRegistro").val() != "" ? $("#numRegistro").val() : 0,
				    success    : function(dados){
				    	BRIGADERIA.graficosProdutos.gerarGrafico(dados);
					}		
				});
			};
		}
	});
	
	BRIGADERIA.graficosProdutos.gerarGrafico = function(data){
		var etiquetas = [];
		var dados = [];
		for ( var i = 0; i < data.length; i++) {
			dados.push(data[i].total);
			etiquetas.push(data[i].nome);
		}
		BRIGADERIA.graficosProdutos.porProduto(dados, etiquetas);
	};
	
	BRIGADERIA.graficosProdutos.porProduto = function(dados, etiquetas){
		var cores = ["#0b3372","#db7241","#5b3f32","#d3ac4a","#2b2624","#65d145","#9cbaa4","#4256a5","#89a52c","#40ed6e","#3f4c14","#194224",
		             "#6a8471","#515652","#212f66","#3d61ed","#878c75","#38c45e","#42eded","#8e8491","#3e88ef","#718093","#1e2d2d","#c63ced",
		             "#12243d","#233144","#4c1414","#4c175b","#ccc6ce","#f74040","#512727"];
		var data = "";
		$("#graficoProduto").remove();
		$("#divCanvas").append('<canvas  id="graficoProduto" width=1350 height="550"></canvas>');
		var contexto = $("#graficoProduto");
		
		var  options = {
				responsive: false,
				animation:{
		            animateScale:true
		        }
		}
		data = {
			    labels: etiquetas,
			    datasets: [
			        {
			        	data: dados,
			        	backgroundColor: cores,
	        	       hoverBackgroundColor: cores
			        }
			    ]
			};
		
		var myPieChart = new Chart(contexto, {
		    type: 'pie',
		    data: data,
		    options: options
		});
		
	};
});
