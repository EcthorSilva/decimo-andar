console.log("custom-main.js carregado!");

document.addEventListener("DOMContentLoaded", function () {
    // Função para verificar se o cookie de sessão está presente
    function checkSessionCookie() {
        var cookies = document.cookie.split(';');
        for (var i = 0; i < cookies.length; i++) {
            var cookie = cookies[i].trim();
            // Verifica se o cookie começa com o prefixo correto
            if (cookie.startsWith('userCookie')) {
                return true; // O cookie de sessão está presente
            }
        }
        return false; // O cookie de sessão não está presente
    }

    // Verifica se o usuário está logado antes de permitir o acesso à página
    window.onload = function() {
        // Página de perfil
        if (window.location.pathname === '/pages/profile.html') {
            if (!checkSessionCookie()) {
                // Se o usuário não estiver logado, redireciona para a página de login
                window.location.href = '/pages/login.html';
            }
        }
    }

    function updateButtonVisibility() {
        var botao1 = document.querySelector('.botao1');
        var botao2 = document.querySelector('.botao2');

        if (botao1 && botao2) {  // Check if the elements exist
            if (checkSessionCookie()) {
                // Se o cookie de sessão estiver presente, mostra o botão 2 e esconde o botão 1
                botao1.classList.add('visually-hidden');
                botao2.classList.remove('visually-hidden');
            } else {
                // Se o cookie de sessão não estiver presente, mostra o botão 1 e esconde o botão 2
                botao1.classList.remove('visually-hidden');
                botao2.classList.add('visually-hidden');
            }
        }
    }

           // Função para excluir a conta do usuário
            function deleteAccount() {
                fetch('/delete-account', {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                })
                .then(response => {
                    if (response.ok) {
                        alert("Conta excluída com sucesso!");
                        window.location.href = "/pages/profile.html"; // Redirecionar para a página de login ou outra página apropriada
                    } else {
                        console.error("Erro ao excluir a conta.");
                        return response.text();
                    }
                })
                .catch(error => {
                    console.error('Erro ao excluir a conta:', error);
                });
            }

            // Função para obter os dados do usuário e verificar o telefone
            function verificarTelefoneHeader() {
                fetch('/profile')
                    .then(response => response.json())
                    .then(userData => {
                        if (userData.telefone && userData.telefone.trim() !== "") {
                            window.location.href = "/pages/imovel.html";
                        } else {
                            alert("Por favor, cadastre seu telefone antes de anunciar.");
                            window.location.href = "/pages/profile.html";
                        }
                    })
                    .catch(error => {
                        console.error('Erro ao obter os dados do usuário:', error);
                        alert("Erro ao verificar dados do usuário.");
                    });
            }

        // Adiciona o evento clique ao botão para chamar a função de exclusão do usuário.
        document.getElementById("bntExcluirConta").addEventListener("click", function() {
                    if (confirm("Tem certeza de que deseja excluir sua conta?")) {
                        deleteAccount();
                    }
                });

    // Event listener para o botão "Anuncie" na homepage
    var adButton = document.getElementById("AdButton");
    if (adButton) {
        adButton.addEventListener("click", function() {
            if (checkSessionCookie()) {
                window.location.href = "/pages/imovel.html";
            } else {  
                window.location.href = "/pages/login.html";
            }
        });
    }

// Event listener para o botão "Anunciar" na Header
var anunciarHeader = document.getElementById("AnunciarHeader");
if (anunciarHeader) {
    anunciarHeader.addEventListener("click", function() {
        if (checkSessionCookie()) {
            verificarTelefoneHeader();
        } else {
            // Armazena a intenção de anunciar na sessão ou localStorage antes de redirecionar
            sessionStorage.setItem('redirectAfterLogin', 'verificarTelefoneHeader');
            window.location.href = "/pages/login.html";
        }
    });
}

    updateButtonVisibility();

    // Adicionando a função de logout ao clique do botão de logout
    var logoutButton = document.getElementById("logoutButton");
    if (logoutButton) {
        logoutButton.addEventListener("click", function() {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "/logout", true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    // Atualiza a página
                    window.location.reload();
                }
            };
            xhr.send();
        });
    }
});
