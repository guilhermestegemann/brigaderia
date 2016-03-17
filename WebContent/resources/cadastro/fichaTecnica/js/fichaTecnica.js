BRIGADERIA.fichaTecnica = new Object();

$(document).ready(function(){
	
	var ingredientes;
	
	BRIGADERIA.fichaTecnica.listarIngredientes = function () { 
		BRIGADERIA.fichaTecnicaService.listarIngredientes({
			success: function(data) {
				var html = "";
				ingredientes = data;

				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigo + "'>" + data[i].descricao + "</option>";
				}
				$("#ingrediente").append(html);
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		});
	};
	
	BRIGADERIA.fichaTecnica.exibirFormulario = function () {
		BRIGADERIA.fichaTecnica.listarIngredientes();
	};
	
	BRIGADERIA.fichaTecnica.incluirIngrediente = function () {
		
		if ($("#ingrediente").val() == "") {
			bootbox.alert("Selecione o Ingrediente!");
		}else if (($("#qtdeIngrediente").val() == "") || ($("#qtdeIngrediente").val() <= 0)) {
			bootbox.alert("Quantida inválida.");
		}else{
			var html = "";
			var codigo = $("#ingrediente").val();
			var descricao = "";
			var un = "";
			var qtde = $("#qtdeIngrediente").val();
			ingredientes.lancado = 0;
			console.log(ingredientes);
			for (var i = 0; i < ingredientes.length; i++) {
				if ((ingredientes[i].codigo == codigo) && (un == "")) {
					un = ingredientes[i].un;
					descricao = ingredientes[i].descricao;
				}
			}
			if (un != "") {
				html =  "<tr>"
						  + "<td>" + codigo + "</td>"
						  + "<td>" + descricao + "</td>"
						  + "<td>" + un + "</td>"
						  + "<td>" + qtde + "</td>"
					  + "</tr>";
				$("#ingredientesFichaTecnica tbody").append(html);
				$("#ingrediente option:selected").remove();
				$("#qtdeIngrediente").val("");
			}
		}
	};
});//fecha ready