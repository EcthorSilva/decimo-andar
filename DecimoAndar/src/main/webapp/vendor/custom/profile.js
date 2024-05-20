$(document).ready(function() {
    // Função para carregar os imóveis do usuário via AJAX
    function loadImoveis() {
        $.ajax({
            type: "GET",
            url: "/list-imoveis",
            dataType: "json",
            success: function(data) {
                // Limpa o carrossel antes de adicionar os novos cards
                $("#carouselExampleIndicators2 .carousel-inner").empty();

                // Itera sobre os imóveis recebidos e adiciona os cards ao carrossel
                for (var i = 0; i < data.length; i += 3) {
                    var active = i === 0 ? 'active' : '';
                    var items = data.slice(i, i + 3);
                    var item = `
                        <div class="carousel-item ${active}">
                            <div class="row">
                                ${items.map(imovel => `
                                    <div class="col-md-4 mb-3">
                                        <div class="card">
                                            <img class="img-fluid" src="${imovel.imagePaths[0]}" alt="Imagem do Imóvel">
                                            <div class="card-body">
                                                <h6 class="card-title">${imovel.endereco}</h6>
                                                <p class="card-text"><strong>CEP: </strong>${imovel.cep}</p>
                                                <div class="d-grid gap-2 d-md-flex justify-content-md-start">
                                                    <a href="/property-details?id=${imovel.id}" class="btn btn-outline-secondary px-3 me-md-2">Ver</a>
                                                    <button type="button" class="btn btn-outline-danger px-3"><i class="bi bi-trash3"></i> Excluir</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                `).join('')}
                            </div>
                        </div>
                    `;
                    $("#carouselExampleIndicators2 .carousel-inner").append(item);
                }

                // Atualiza o carrossel
                $('#carouselExampleIndicators2').carousel();
            },
            error: function() {
                alert("Erro ao carregar imóveis.");
            }
        });
    }

    // Chama a função para carregar os imóveis ao carregar a página
    loadImoveis();

    // Função para carregar detalhes do imóvel
    function loadPropertyDetails(propertyId) {
        $.ajax({
            type: "GET",
            url: `/property-details?id=${propertyId}`,
            dataType: "json",
            success: function(data) {
                // Atualiza a interface com os detalhes do imóvel
                console.log("Detalhes do Imóvel:", data);

                // Exemplo de como você pode acessar os dados recebidos:
                console.log("ID do Imóvel:", data.id);
                console.log("Tipo de Imóvel:", data.tipoImovel);
                console.log("Tipo de Venda:", data.tipoVenda);
                console.log("Valor:", data.valor);
                console.log("Endereço:", data.endereco);
                console.log("Número:", data.numero);
                console.log("Cidade:", data.cidade);
                console.log("UF:", data.uf);
                console.log("CEP:", data.cep);
                console.log("Número de Quartos:", data.numQuartos);
                console.log("Número de Banheiros:", data.numBanheiros);
                console.log("Metros Quadrados:", data.metrosQuadrados);
                console.log("Descrição do Imóvel:", data.descricaoImovel);
                console.log("Caminhos das Imagens:", data.imagePaths);

                // Aqui você pode manipular os dados recebidos do imóvel
            }
        });
    }

    // Exemplo de chamada para carregar detalhes do imóvel
    loadPropertyDetails(1); // Substitua 1 pelo ID do imóvel desejado

});
