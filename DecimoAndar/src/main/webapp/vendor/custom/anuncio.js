console.log("Anúncio.js carregado com sucesso!");

function loadPropertyDetails(propertyId) {
    $.ajax({
        type: "GET",
        url: `/property-details?id=${propertyId}`,
        dataType: "json",
        success: function (data) {
            // Exibe os detalhes do imóvel no console
            console.log("Detalhes do Imóvel:", data);

            // Atualiza as imagens no carousel
            var carouselImages = $("#carouselImages");
            carouselImages.empty();
            data.imagePaths.forEach(function (imagePath, index) {
                var activeClass = index === 0 ? 'active' : '';
                var item = `
                    <div class="carousel-item ${activeClass}">
                        <img src="${imagePath}" class="d-block w-100" alt="Imagem do Imóvel">
                    </div>
                `;
                carouselImages.append(item);
            });

            // Atualiza o valor
            var valor = parseFloat(data.valor); // Converte para número
            $(".card-title").text("R$ " + valor.toFixed(2));

            // Atualiza os detalhes no resumo
            $(".bedrooms").text(data.numQuartos + " Quartos");
            $(".bathrooms").text(data.numBanheiros + " Banheiros");
            $(".area").text(data.metrosQuadrados + " m²");

            // Atualiza os detalhes no card de resumo 3
            $("#tipoImovel").text("Tipo: " + data.tipoImovel);
            $("#tipoVenda").text("Tipo de anúncio: " + data.tipoVenda);

            // Atualiza o endereço
            $("#logradouro").text("Logradouro: " + data.endereco);
            $("#numero").text("Número: " + data.numero);
            $("#cidade").text("Cidade: " + data.cidade);
            $("#estado").text("Estado: " + data.uf);
            $("#cep").text("CEP: " + data.cep);

            // Atualiza a descrição do imóvel
            $("#descricao").text(data.descricaoImovel);

            // Atualiza o iframe do mapa
            var iframe = `<iframe class="mt-5 border-0 rounded-3 shadow-sm"
                                src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3654.1713889822545!2d-46.7040323183628!3d-23.669828123870488!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x94ce515bb231b5ed%3A0x327b78892baef8e6!2zU2VuYWMgTmHDp8O1ZXMgVW5pZGFz!5e0!3m2!1spt-BR!2sbr!4v1716145780152!5m2!1spt-BR!2sbr"
                                width="600" height="250" style="border:0;" allowfullscreen="" loading="lazy"
                                referrerpolicy="no-referrer-when-downgrade"></iframe>`;
            $("#mapa").html(iframe);

            // Atualiza o link do botão de WhatsApp
            var whatsappLink = `https://wa.me/+55${data.phoneNumber}`;
            $("#botaochamar a").attr("href", whatsappLink);
        },
        error: function (xhr, status, error) {
            console.error("Erro ao carregar detalhes do imóvel:", error);
        }
    });
}

// Obtém o parâmetro id da URL e carrega os detalhes do imóvel
$(document).ready(function () {
    var urlParams = new URLSearchParams(window.location.search);
    var propertyId = urlParams.get('id');
    if (propertyId) {
        loadPropertyDetails(propertyId);
    } else {
        console.error("ID do imóvel não encontrado na URL");
    }
});
