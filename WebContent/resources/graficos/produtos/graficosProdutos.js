BRIGADERIA.graficos = new Object();


$(document).ready(function(){
	var tipoGraficoProduto = "";
	
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
		}else if (tipoGraficoProduto === "rdbtnMaisVendido"){
			BRIGADERIA.graficos.porProduto("maisVendido");
		}else{
			BRIGADERIA.graficos.porProduto("menosVendido");
		};
	});

});
