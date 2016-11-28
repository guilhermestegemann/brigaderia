$(document).ready(function(){	
	$("#formLogin").submit(function(e){
		if($("#usuario").val()==""){
			bootbox.alert({size:"small", message:"Informe o campo usuário!"});
			e.preventDefault();
		}else if ($("#senha").val()==""){
			bootbox.alert({size:"small", message:"Informe o campo senha!"});
			e.preventDefault();
		}else{
			var base64 = window.btoa($("#senha").val());
			$("#senha").val(base64);
			return;
		}
	});
	if (window.location.search){
		$("#retorno").text("Usuário e senha inválidos. Tente novamente.");
	}
	
});