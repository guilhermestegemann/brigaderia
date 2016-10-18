BRIGADERIA.sugestaoCompra = new Object();

$(document).ready(function(){
	$("#subConteudo").text(""); //inicia div vazia
	
	$('.dataFiltro').datepicker({
		format: 'dd/mm/yyyy'
	});
	
	BRIGADERIA.sugestaoCompra.listarClientes = function (){
		BRIGADERIA.sugestaoCompraService.listarClientes({
			success: function(data) {
				var html = "";
				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigo + "'>" + data[i].nome + "</option>";
				}
				$("#clientes").append(html);
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		});
	}
	
	BRIGADERIA.sugestaoCompra.listarClientes();
});