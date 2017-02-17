function format ( d ) {

	var html = "<table>" +
	              "<thead>" +
                     "<tr>" +
                        "<th>Código</th>" +
                        "<th>Nome</th>" +
                        "<th>Quantidade</th>" +
                        "<th>UN</th>" +
                        "<th>Unitário</th>" +
                        "<th>Total</th>" +
                     "</tr>" +
                  "</thead>";
	
	for (var i = 0; i < d.itemPedidoVenda.length; i++){
		html += "<tr>";
		html += "<td>" + d.itemPedidoVenda[i].codigoProduto + "</td>" +
		        "<td>" + d.itemPedidoVenda[i].descricao + "</td>" +
		        "<td>" + d.itemPedidoVenda[i].qtde + "</td>" +
		        "<td>" + d.itemPedidoVenda[i].unEstoque + "</td>" +
		        "<td>" + parseFloat(d.itemPedidoVenda[i].unitario).toFixed(2) + "</td>" +
		        "<td>" + parseFloat(d.itemPedidoVenda[i].total).toFixed(2) + "</td>";
		html += "</tr><tfoot><tr><td></td></tr></tfoot>";
	}
	html += "</table>";
	return html;
}


BRIGADERIA.ordemProducao = new Object();

$(document).ready(function (){
	
	var table; //DataTable
	var produtos;
	var produtoArray = []; //utilizado ao inserir produtos no formulario.
	var pedidos = [];
	
	$("#produtos").on('change',function(){
		for (var i = 0; i < produtos.length; i++) {
			if (produtos[i].codigoProduto == $("#produtos").val()) {
				$("#unEstoque").val(produtos[i].unEstoque);
			}else if ($("#produtos").val() == ""){
				$("#unEstoque").val("");
			}
		}
	});
	
	BRIGADERIA.ordemProducao.listarProdutos = function () { 
		BRIGADERIA.ordemProducaoService.listarProdutos({
			success: function(data) {
				var html = "";
				produtos = data;//atribui ao objeto global o valor retornado da lista. Utilizado pra edição/inserção de itens da Ordem
				for (var i = 0; i < data.length; i++) {
					html += "<option value='" + data[i].codigoProduto + "'>" + data[i].descricao + "</option>";
				}
				$("#produtos").append(html);
			},
			error : function (err) {
				bootbox.alert(err.responseText);
			}
		});
	};
	
	BRIGADERIA.ordemProducao.exibirFormulario = function () {
		BRIGADERIA.ordemProducao.listarProdutos();
	};
	
	BRIGADERIA.ordemProducao.incluirProduto = function () {
		var expNumeros = /^[0-9]+$/;
		if ($("#produtos").val() == "") {
			bootbox.alert("Selecione o Produto!");
		}else if (!expNumeros.test($("#qtdeProduto").val().replace(",","").replace(".",""))) {
			bootbox.alert("Quantidade inválida.");
		}else if (parseFloat($("#qtdeProduto").val().replace(",",".")) == 0){
			bootbox.alert("Quantidade deve ser maior que zero.");
		}else{
			var html = "";
			var descricao = "";
			var unEstoque = "";
			
			for (var i = 0; i < produtos.length; i++) {
		
				if ((produtos[i].codigoProduto == $("#produtos").val()) && (unEstoque == "")) {
					unEstoque = produtos[i].unEstoque;
					descricao = produtos[i].descricao; 
				}
			}
			
			if (unEstoque != "") {
				html =  "<tr>"
						  + "<td >" + $("#produtos").val() + "</td>"
						  + "<td>" + descricao + "</td>"
						  + "<td>" + $("#qtdeProduto").val().replace(",",".") + "</td>"
						  + "<td>" + unEstoque + "</td>"
						  + "<td><button class='btn btn-primary btn-sm' type='button' onclick='BRIGADERIA.ordemProducao.editarProduto(this"+ "," + $("#produtos").val() + "," + "\"" + descricao + "\"" + "," + parseFloat($("#qtdeProduto").val().replace(",",".")) + "," + "\"" + unEstoque + "\"" + ")' aria-hidden='true'>Editar</button>"
						  	  + "<button class='btn btn-danger btn-sm' type='button' onclick='BRIGADERIA.ordemProducao.deletarProduto(this"+ "," + $("#produtos").val() + "," + "\"" + descricao + "\"" + ")' aria-hidden='true'>Excluir</button></td>"
					  + "</tr>";
				
				var prod = {
						codigoProduto: $("#produtos").val(),
						qtde: $("#qtdeProduto").val()
				} ;
				
				produtoArray.push(prod);
				$("#itensOrdemProducao tbody").append(html);
				$("#produtos option:selected").remove();
				$("#qtdeProduto").val("");
				$("#unEstoque").val("");
			}
		}
	};
	
	BRIGADERIA.ordemProducao.editarProduto = function (handler, codigo, descricao, qtde, unEstoque) {
		
		$("#qtdeProduto").val(parseFloat(qtde));
		$("#unEstoque").val(unEstoque);
		
		for (var i = 0; i < produtoArray.length; i++) {
			if (produtoArray[i].codigoProduto == codigo) {
				produtoArray.splice(i, 1);
			}
		}
		
		$(handler).closest('tr').remove();//exclui o tr mais proximo.
		$('#produtos').append('<option value="' + codigo + '" selected="unselected">' + descricao + '</option>');
	};
	
	BRIGADERIA.ordemProducao.deletarProduto = function (handler, codigo, descricao) {
		
		bootbox.confirm({ 
			size: 'small',
			message: "Deseja deletar o produto?", 
			callback: function(confirma){
				if (confirma) {
					for (var i = 0; i < produtoArray.length; i++) {
						if (produtoArray[i].codigoProduto == codigo) {
							produtoArray.splice(i, 1);
						}
					}
					
					$(handler).closest('tr').remove();//exclui o tr mais proximo.
					$('#produtos').append('<option value="' + codigo + '">' + descricao + '</option>');
				}
			}
		});
	};
	
	BRIGADERIA.ordemProducao.adicionar = function () {
		
		var newOrdemProducao = {
				itemOrdemProducao : produtoArray
		};
		if (newOrdemProducao.itemOrdemProducao == "") {
			bootbox.alert("Produtos não inseridos");
		}else{
			BRIGADERIA.ordemProducaoService.adicionar(newOrdemProducao);
		}
	};
	
	BRIGADERIA.ordemProducao.salvarEdicao = function () {
		var ordemProducao = {
				numero : $("#numero").val(),
				itemOrdemProducao : produtoArray
		}
		if(ordemProducao.itemOrdemProducao == ""){
			bootbox.alert("Produtos não inseridos");
		}else{
			BRIGADERIA.ordemProducaoService.editarOrdemProducao(ordemProducao);
		}
	}
	
	BRIGADERIA.ordemProducao.exibirEdicao = function(numero, produzida) {
		if (produzida == "N") {
			BRIGADERIA.ordemProducao.listarProdutos();
		}
		BRIGADERIA.ordemProducaoService.buscarOrdemPeloNumero({
			numero : numero,
			async: false,
			success : function (ordem) {
				$("#numero").val(ordem.numero);
				$("#data").val(BRIGADERIA.convertData.dateToStr(ordem.data));
				itemOrdemVO = {};
				itemOrdemVO = ordem.itemOrdemProducao;
				for (var i = 0; i < itemOrdemVO.length; i++ ) {
					var html = ""
						html =  "<tr class='itemOrdemProducao'>"
							  + "<td >" + itemOrdemVO[i].codigoProduto + "</td>"
							  + "<td>" + itemOrdemVO[i].descricao + "</td>"
							  + "<td>" + itemOrdemVO[i].qtde + "</td>"
							  + "<td>" + itemOrdemVO[i].unEstoque + "</td>";
							  if (ordem.produzida == "S") {
								  html += "<td></td><td></td>";
							  }else{
								  html += "<td><button class='btn btn-primary btn-sm' type='button' onclick='BRIGADERIA.ordemProducao.editarProduto(this"+ "," + itemOrdemVO[i].codigoProduto + "," + "\"" + itemOrdemVO[i].descricao + "\"" + "," + itemOrdemVO[i].qtde + "," + "\"" + itemOrdemVO[i].unEstoque + "\"" + ")' aria-hidden='true'>Editar</button>"
							  	  + "<button class='btn btn-danger btn-sm' type='button' onclick='BRIGADERIA.ordemProducao.deletarProduto(this"+ "," + itemOrdemVO[i].codigoProduto + "," + "\"" + itemOrdemVO[i].descricao + "\"" + ")' aria-hidden='true'>Excluir</button></td>";
							  }
							  
						  html += "</tr>";
						produtoArray.push(itemOrdemVO[i]);
						$("#itensOrdemProducao tbody").append(html);
						$("#produtos option[value='"+ itemOrdemVO[i].codigoProduto +"']").remove();
				}
				$("#btnSalvar").attr("onclick", "BRIGADERIA.ordemProducao.salvarEdicao()");
			}
		});	
	};
	
	BRIGADERIA.ordemProducao.modal = function(){
		if (table === undefined){
			table = $('#pedidos').DataTable( {
				"language": {
					"lengthMenu" : "Exibindo _MENU_ registros",
					"search": "Pesquisar",
					"info": "Exibindo _PAGE_ de _PAGES_ páginas",
					"next": "teste",
					"paginate": {
			            previous:   "Anterior",
			            next:       "Próximo"
			        },
				},
		        data: pedidos,
		        "columns": [
		            {
		                "className":      'details-control',
		                "orderable":      false,
		                "data":           null,
		                "defaultContent": ''
		            },
		            { 
		            	"data": "numero",
		            	"className": 'dt-body-right'
		            },
		            { 
		            	"data": "dataEmissao",
		            	"className": 'dt-body-center'
		            },
		            { 
		            	"data": "nomeCliente",
		            	"className": 'dt-body-center'
		            },
		            { 
		            	"data": "total",
		                "className": 'dt-body-right'
		            }
		        ],
		        "order": [[1, 'asc']]
		    } );
			
			$('#pedidos tbody').on('click', 'td.details-control', function () {
		        var tr = $(this).closest('tr');
		        var row = table.row( tr );
		 
		        if ( row.child.isShown() ) {
		            // This row is already open - close it
		            row.child.hide();
		            tr.removeClass('shown');
		        }
		        else {
		            // Open this row
		            row.child( format(row.data()) ).show();
		            tr.addClass('shown');
		        }
		    });
		}
		
	};
	
});