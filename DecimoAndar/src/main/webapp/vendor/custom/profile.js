document.addEventListener("DOMContentLoaded", function () {
    var updateUserDataForm = document.getElementById('updateUserDataForm');

    updateUserDataForm.addEventListener('submit', function (event) {
        event.preventDefault();

        var telefone = document.getElementById('telefone').value;
        var dataNascimento = document.getElementById('datanasci').value;

        var dados = {
            telefone: telefone,
            dataNascimento: dataNascimento
        };

        fetch('/update-user-data', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(dados),
        })
        .then(response => {
            if (response.ok) {
                console.log("Dados de usuário atualizados com sucesso!");
                // Fechar o modal após atualização bem-sucedida
                $('#modalId').modal('hide');
                // Atualizar os dados na interface do usuário
                obterDadosUsuario();
            } else {
                console.log("Erro ao atualizar os dados do usuário.");
                return response.text();
            }
        })
        .catch(error => {
            console.error('Erro ao atualizar os dados do usuário:', error);
        });
    });

    // Função para obter os dados do usuário
    function obterDadosUsuario() {
        fetch('/profile')
        .then(response => response.json())
        .then(userData => {
            atualizarPerfil(userData);
        })
        .catch(error => {
            console.error('Erro ao obter os dados do usuário:', error);
        });
    }

    // Função para atualizar o perfil na página
    function atualizarPerfil(userData) {
        document.getElementById('nome').textContent = userData.name;
        document.getElementById('nome2').textContent = userData.name;
        document.getElementById('email').textContent = userData.email;
        document.getElementById('tel').textContent = userData.telefone;
        document.getElementById('datanascimento').textContent = userData.dataNascimento;
    }

    // Chamada da função para obter os dados do usuário quando a página é carregada
    obterDadosUsuario();

    // Direcionando Click do btn "Anunciar" na Header.
    document.getElementById("AnunciarHeaderProfile").addEventListener("click", function() {
        window.location.href = "/pages/imovel.html";
    });
});


// para pegar os anuncios do usuario
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
                                                    <button type="button" class="btn btn-outline-secondary px-3 me-md-2">Ver</button>
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
});
