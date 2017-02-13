function carregarConteudo (url) {
	$("#conteudo").load(url);
	$("#grafico").remove("");
}

function carregarConteudoCancel (url) {

	bootbox.confirm({ 
		size: 'medium',
		message: "Possíveis edições podem ser perdidas! Deseja realmente cancelar?", 
		callback: function(confirma){
			if (confirma == true) {
				$("#conteudo").load(url);
			}
		}
	});
}

//function paginaInicial(){
//	$("#conteudo").text("");
//	BRIGADERIA.graficos.grafico1();
//}

