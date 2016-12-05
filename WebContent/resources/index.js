function carregarConteudo (url) {
	$("#conteudo").load(url);
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

