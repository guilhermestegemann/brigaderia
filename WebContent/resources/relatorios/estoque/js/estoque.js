BRIGADERIA.estoque = new Object();

$(document).ready( function(){
	
	
	BRIGADERIA.estoque.gerarRelatorio = function(){
		$("#resultadoEstoque tbody").text("");
		BRIGADERIA.estoqueService.gerarRelatorio({
			tipoItem : $("#tipoItemFiltro").val(),
			ativo : $("#filtroAtivo").val() === "" ? 'null': $("#filtroAtivo").val(),
			success : function(estoque){
				if (estoque.length == 0){
					bootbox.alert("A consulta não retornou nenhuma informação, verifique os filtros aplicados e tente novamente.");
				}else{
					
					var html = "";				
					for (var i = 0; i < estoque.length; i++){
						html += "<tr>"
							+ "<td>"+ estoque[i].codigoProduto +"</td>"
							+ "<td>"+ estoque[i].descricao +"</td>"
							+ "<td>"+ estoque[i].estoque +"</td>"
							+ "<td>R$ "+ parseFloat(estoque[i].valorCusto).toFixed(2) +"</td>"
							+ "<td>R$ "+ parseFloat(estoque[i].totalCusto).toFixed(2) +"</td>"
							+ "<td>R$ "+ parseFloat(estoque[i].valorVenda).toFixed(2) +"</td>"
							+ "<td>R$ "+ parseFloat(estoque[i].totalVenda).toFixed(2) +"</td>"
							+ "<td>"+ parseFloat(estoque[i].margem).toFixed(2) +"%</td>"
						+"</tr>";
					};
					$("#resultadoEstoque tbody").html(html);
				}
			},
			error : function(error){
				bootbox.alert(error.responseText);
			}
		})
	};
	
	
	
	BRIGADERIA.produtos.listarTipoItem("#tipoItemFiltro");
});