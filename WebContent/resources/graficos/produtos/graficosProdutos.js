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
		debugger;
		var data = "";
		$("#graficoProduto").remove();
		$("#divCanvas").append('<canvas  id="graficoProduto" width=1350 height="550"></canvas>');
		//var canvas = document.getElementById("graficoProduto");
		var contexto = $("#graficoProduto");
		//var context = canvas.getContext('2d');
		//context.restore();
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
			        	backgroundColor: [
			        	    "#FF6384",
	                    	"#36A2EB",
	                    	"#FFCE56"
	        	       ],
	        	       hoverBackgroundColor: [
                            "#FF6384",
                            "#36A2EB",
                            "#FFCE56"
                       ]
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
