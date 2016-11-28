BRIGADERIA.gerenciarClientes = new Object();

$(document).ready(function() {
	

	
	BRIGADERIA.gerenciarClientes.buscar = function () {
		var ativo = $("#filtroAtivo").val();
		var valorBusca = $("#buscaCliente").val();
		if (valorBusca == "") {
			valorBusca = "null";
		}
		
		BRIGADERIA.gerenciarClientes.exibirClientes (undefined, valorBusca, ativo);
	};
	
	BRIGADERIA.gerenciarClientes.exibirClientes = function(listaDeClientes, valorBusca, ativo) {
		
		BRIGADERIA.clienteService.listar({
			valorBusca : valorBusca,
			ativo : ativo,
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
					  + "<td><button class='btn btn-primary btn-sm' type='button' onclick='BRIGADERIA.gerenciarClientes.editarCliente(" + listaDeClientes[i].codigo + ")' aria-hidden='true'>Editar</button>"
					  	 +	"<button class='btn btn-danger btn-sm' type='button' onclick='BRIGADERIA.gerenciarClientes.deletarCliente(" + listaDeClientes[i].codigo + ")' aria-hidden='true'>Excluir</button>  </td>"
					  + "</tr>";
				}
				
				$("#resultadoClientes tbody").html(html);
			
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
		$("#conteudo").load("cadastro/clientes/clientes.html", function (){
			BRIGADERIA.clientes.exibirEdicao(codigo);
		});	
	};
	
	BRIGADERIA.gerenciarClientes.cadastrarCliente = function () {
		$("#conteudo").load("cadastro/clientes/clientes.html", function (){
			BRIGADERIA.clientes.exibirFormulario();
		});
	};
	
	$("#filtroAtivo").on('change',function(){
		BRIGADERIA.gerenciarClientes.buscar();
	});
});