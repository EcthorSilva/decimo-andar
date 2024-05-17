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
