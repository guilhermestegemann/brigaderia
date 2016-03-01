BRIGADERIA.gerenciarClientes = new Object();

$(document).ready(function() {
	

	
	BRIGADERIA.gerenciarClientes.buscar = function () {
		
		var valorBusca = $("#buscaCliente").val();
		if (valorBusca == "") {
			valorBusca = "null";
		}
		
		BRIGADERIA.gerenciarClientes.exibirClientes (undefined, valorBusca);
	};
	
	BRIGADERIA.gerenciarClientes.exibirClientes = function(listaDeClientes, valorBusca) {
		
		BRIGADERIA.clienteService.listar({
			valorBusca : valorBusca,
			success : function (listaDeClientes) {
				
				var html = "";
				
				for (var i = 0; i < listaDeClientes.length; i++) {
					
					if (listaDeClientes[i].ultimaVenda != null) {
						listaDeClientes[i].ultimaVenda = BRIGADERIA.convertData.dateToStr(listaDeClientes[i].ultimaVenda);
					}else{
						listaDeClientes[i].ultimaVenda = "Sem Compra";
					}
					
					html += "<tr>"
					  + "<td>" + listaDeClientes[i].codigo + "</td>"
					  + "<td>" + listaDeClientes[i].nome + "</td>"
					  + "<td>" + listaDeClientes[i].cidade + " - " + listaDeClientes[i].uf + "</td>"
					  + "<td>" + listaDeClientes[i].bairro + "</td>"
					  + "<td>" + listaDeClientes[i].ultimaVenda + "</td>"
					  + "<td><a href='#'><i class='glyphicon glyphicon-edit' onclick='BRIGADERIA.gerenciarClientes.editarCliente(" + listaDeClientes[i].codigo + ")' aria-hidden='true'></i></a>"
					  	 +	"<a href='#'><i class='glyphicon glyphicon-remove-sign' onclick='BRIGADERIA.gerenciarClientes.deletarCliente(" + listaDeClientes[i].codigo + ")' aria-hidden='true'></i></a>  </td>"
					  + "</tr>";
				}
				
				$("tbody").html(html);
				
				},
				error : function(err) {
					console.log(err);
				} 
		});		   		
	};
	
	$("#buttonPesquisar").on('click', function(){
		BRIGADERIA.gerenciarClientes.buscar();
	});
	
	BRIGADERIA.gerenciarClientes.buscar();
	
	BRIGADERIA.gerenciarClientes.deletarCliente = function (codigo) {
		
		bootbox.confirm({ 
			size: 'small',
			message: "Deseja deletar o Cliente?", 
			callback: function(confirma){
				if (confirma == true) {
					BRIGADERIA.clienteService.deletar({
						codigo : codigo,
						success: function (successo) {
							bootbox.alert(successo);
							BRIGADERIA.gerenciarClientes.buscar();
						},
						error: function(err) {
							bootbox.alert(err.responseText);
						}
					})
				}
			}
		});
	}
	
	BRIGADERIA.gerenciarClientes.editarCliente = function (codigo) {
		$("#conteudo").load("resources/cadastro/clientes.html", function (){
			BRIGADERIA.clientes.exibirEdicao(codigo);
		});	
	};
	
	BRIGADERIA.gerenciarClientes.cadastrarCliente = function () {
		$("#conteudo").load("resources/cadastro/clientes.html", function (){
			BRIGADERIA.clientes.exibirFormulario();
		});
	};
});