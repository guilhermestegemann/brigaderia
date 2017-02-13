BRIGADERIA.graficos = new Object();


$(document).ready(function(){
	var tipoGraficoProduto = "";
	
	BRIGADERIA.graficoService.vendaAnual({
		success: function(data){
			var etiquetas = [];
			var dados = [];
			
			for ( var i = 0; i < data.length; i++) {
				dados.push(data[i].total);
				etiquetas.push(data[i].mes);
			}
			BRIGADERIA.graficos.vendaAnual(dados, etiquetas)
		}
	});
	
	BRIGADERIA.graficos.vendaAnual = function(dados, etiquetas){
		var contexto = $("#grafico");
		var  options = {
				responsive: false,
				beginAtZero: true
		}
		var data = {
			    labels: etiquetas,
			    datasets: [
			        {
			            label: "Venda",
			            fill: false,
			            lineTension: 0.0,
			            backgroundColor: "rgba(75,192,192,0.4)",
			            borderColor: "rgba(75,192,192,1)",
			            borderCapStyle: 'butt',
			            borderWidth: 2,
			            borderDash: [],
			            borderDashOffset: 0.0,
			            borderJoinStyle: 'miter',
			            pointBorderColor: "rgba(75,192,192,1)",
			            pointBackgroundColor: "#red",
			            pointBorderWidth: 15,
			            pointHoverRadius: 15,
			            pointHoverBackgroundColor: "rgba(75,192,192,1)",
			            pointHoverBorderColor: "rgba(220,220,220,1)",
			            pointHoverBorderWidth: 2,
			            pointRadius: 10,
			            pointHitRadius: 10,
			            data: dados,
			            spanGaps: true,
			        }
			    ]
			};
		var myLineChart = new Chart(contexto, {
		    type: 'line',
		    data: data,
		    options: options
		});
	};
	
	
	//Radio
	BRIGADERIA.graficos.eventoRadio = function (radio){
		tipoGraficoProduto = radio.id;
	};
	
	//Produto
	$("#gerarGrafico").on("click",function(){
		if(tipoGraficoProduto === ""){
			bootbox.alert({
				message: "Selecione um tipo de relatório.",
				 size: 'small'
			});
		};
	});

});
