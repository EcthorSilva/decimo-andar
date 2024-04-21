// Função para validar o formato do e-mail usando regex
function validarEmail(email) {
    var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

// Função para verificar o login
document.getElementById("loginButton").addEventListener("click", function () {
    var email = document.getElementById("e-mail").value;
    var password = document.getElementById("password").value;
    var errorDiv = document.querySelector('.alert-danger');

    // Limpa a mensagem de erro ao tentar fazer login novamente
    setTimeout(function () {
        errorDiv.classList.add('visually-hidden');
    }, 3000);

    // Verifica se os campos de e-mail e senha estão preenchidos
    if (email.trim() === "" || password.trim() === "") {
        errorDiv.textContent = "Todos os campos são obrigatórios.";
        errorDiv.classList.remove('visually-hidden');
    } else if (!validarEmail(email)) {
        errorDiv.textContent = "Por favor, insira um e-mail válido.";
        errorDiv.classList.remove('visually-hidden');
    } else {
        // LOGICA PARA VERIFICAR AS CREDENCIAS DO USUARIO
    }
});

// Adiciona event listener para ocultar a mensagem de erro quando os campos de email ou senha recebem foco
document.getElementById("e-mail").addEventListener("focus", function () {
    document.querySelector('.alert-danger').classList.add('visually-hidden');
});

document.getElementById("password").addEventListener("focus", function () {
    document.querySelector('.alert-danger').classList.add('visually-hidden');
});