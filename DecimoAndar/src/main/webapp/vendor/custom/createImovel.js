document.addEventListener('DOMContentLoaded', () => {
    const termosCheckbox = document.getElementById('form2Example3cg');
    const fileInput = document.getElementById('imputFile');

    function exibirErro(mensagem) {
        var errorDiv = document.querySelector('.alert-danger');
        errorDiv.textContent = mensagem;
        errorDiv.classList.remove('visually-hidden');

        setTimeout(function () {
            errorDiv.classList.add('visually-hidden');
        }, 3000);
    }

    function verificarCampos() {
        var tipoImovel = document.getElementById("tipoImovel").value.trim();
        var tipoVenda = document.getElementById("tipoVenda").value.trim();
        var valor = document.getElementById("valor").value.trim();
        var endereco = document.getElementById("endereco").value.trim();
        var numero = document.getElementById("numero").value.trim();
        var cidade = document.getElementById("cidade").value.trim();
        var uf = document.getElementById("uf").value.trim();
        var cep = document.getElementById('cep').value.trim();
        var numQuartos = document.getElementById("numQuartos").value.trim();
        var numBanheiros = document.getElementById("numBanheiros").value.trim();
        var metrosQuadrados = document.getElementById("metrosQuadrados").value.trim();
        var descricaoImovel = document.getElementById("descricaoImovel").value.trim();

        if (tipoImovel === "" || tipoVenda === "" || valor === "" || endereco === "" || numero === "" || cidade === "" || uf === "" || cep === "" || numQuartos === "" || numBanheiros === "" || metrosQuadrados === "" || descricaoImovel === "") {
            exibirErro("Todos os campos são obrigatórios.");
            return null; // Retorna null em caso de campos em branco
        }

        return {
            tipoImovel: tipoImovel,
            tipoVenda: tipoVenda,
            valor: valor,
            endereco: endereco,
            numero: numero,
            cidade: cidade,
            uf: uf,
            cep: cep,
            numQuartos: numQuartos,
            numBanheiros: numBanheiros,
            metrosQuadrados: metrosQuadrados,
            descricaoImovel: descricaoImovel
        }
    }

    document.getElementById('enviaranuncio').addEventListener("click", async function () {
        console.log("Botão clicado!");

        // Verifica se o cliente concordou com os termos
        if (!termosCheckbox.checked) {
            exibirErro("Por favor, leia e concorde com os termos e condições.");
            return;
        }

        // Chama a função para verificar os campos
        var dados = verificarCampos();

        if (dados) {
            console.log("Dados do formulário a serem enviados:");
            console.log(JSON.stringify(dados));

            // Enviar apenas os dados do formulário para a servlet usando fetch
            const enviarDados = fetch('/create-imovel', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(dados),
            });

            // Enviar apenas as imagens para a servlet usando fetch
            const formData = new FormData();
            for (let i = 0; i < fileInput.files.length; i++) {
                formData.append('fotos', fileInput.files[i]);
            }
            const enviarImagens = fetch('/upload-imagem', {
                method: 'POST',
                body: formData,
            });

            // Aguardar o envio das duas requisições simultaneamente
            try {
                await Promise.all([enviarDados, enviarImagens]);
                console.log("DADOS E IMAGENS ENVIADOS COM SUCESSO");
                // Redirecionar para a página de perfil após o envio
                window.location.href = "/pages/profile.html";
            } catch (error) {
                console.log('Erro ao enviar os dados e imagens:', error);
                alert('Erro ao enviar os dados e imagens: ' + error.message);
            }
        }
    });
});
