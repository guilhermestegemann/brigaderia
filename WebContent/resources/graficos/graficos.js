BRIGADERIA.graficos = new Object();

$(document).ready(function(){
	var myDate = new Date();
	var anoAtual = myDate.getYear();
	anoAtual < 1000 ? anoAtual += 1900 : anoAtual;
	$("#ano").val(anoAtual);
	
	$("#ano").on('change', function(){
		$("#grafico").remove();
		$("#divCanvasGraficoAnual").append('<canvas  id="grafico" width=1350 height="550"></canvas>');
		BRIGADERIA.graficos.buscarDadosGraficoAnual($("#ano").val());
	});
	
	BRIGADERIA.graficos.buscarDadosGraficoAnual = function(ano){
		BRIGADERIA.graficoService.vendaAnual({
			ano: ano,
			success: function(data){
				var etiquetas = [];
				var dados = [];
				
				for ( var i = 0; i < data.length; i++) {
					dados.push(parseFloat(data[i].total).toFixed(2));
					etiquetas.push(data[i].mes);
				}
				BRIGADERIA.graficos.vendaAnual(dados, etiquetas)
			}
		});
	};

	
	
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
			            pointBorderWidth: 1,
			            pointHoverRadius: 15,
			            pointHoverBackgroundColor: "rgba(75,192,192,1)",
			            pointHoverBorderColor: "rgba(220,220,220,1)",
			            pointHoverBorderWidth: 2,
			            pointRadius: 10,
			            pointHitRadius: 1,
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
	
	BRIGADERIA.graficos.buscarDadosGraficoAnual(anoAtual);
});
