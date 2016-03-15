BRIGADERIA.fichaTecnica = new Object();

$(document).ready(function(){
	
	BRIGADERIA.fichaTecnica.listarIngredientes = function () { 
		BRIGADERIA.fichaTecnicaService.listarIngredientes({
			success: function(data) {
				var html = "";
				
				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigo + "'>" + data[i].descricao + "</option>";
				}
				$("#tipoItem").append(html);
			}
		});
	};
	
	BRIGADERIA.fichaTecnica.listarIngredientes();
});//fecha ready