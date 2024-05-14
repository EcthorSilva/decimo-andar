document.addEventListener('DOMContentLoaded', () => {
    const cepInput = document.getElementById('cep');
    const logradouroInput = document.getElementById('endereco');
    const termosCheckbox = document.getElementById('form2Example3cg');
    const fileInput = document.getElementById('imputFile');

    // Função para consultar o CEP e preencher automaticamente os campos de endereço
    async function consultarCEP() {
        const cep = cepInput.value.replace(/\D/g, '');

        if (cep.length !== 8) {
            exibirErro('Por favor, digite um CEP válido com 8 dígitos.');
            return;
        }

        try {
            const response = await fetch(`https://viacep.com.br/ws/${cep}/json/`);
            const data = await response.json();

            if (data.erro) {
                exibirErro('CEP não encontrado.');
            } else {
                logradouroInput.value = data.logradouro;
                document.getElementById('cidade').value = data.localidade;
                document.getElementById('uf').value = data.uf;
            }
        } catch (error) {
            console.error('Erro ao consultar o CEP:', error);
            exibirErro('Ocorreu um erro ao consultar o CEP. Por favor, tente novamente.');
        }
    }

    // Event listener para consultar o CEP ao pressionar "Enter"
    cepInput.addEventListener('keypress', async (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
            await consultarCEP();
        }
    });

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
        var fotos = fileInput.files;

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
            descricaoImovel: descricaoImovel,
            fotos: fotos
        }
    }

    document.getElementById('enviaranuncio').addEventListener("click", function () {
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
            console.log(dados);

            // Enviar dados para a servlet usando fetch
            let formData = new FormData();
            formData.append('imovel', new Blob([JSON.stringify(dados)], { type: 'application/json' }));

            for (let i = 0; i < dados.fotos.length; i++) {
                formData.append('fotos', dados.fotos[i]);
            }

            fetch('/create-imovel', {
                method: 'POST',
                body: formData,
            })
                .then(response => {
                    if (response.ok) {
                        console.log("DADOS ENVIADOS COM SUCESSO");
                        window.location.href = "/pages/profile.html";
                    } else {
                        console.log("Erro no backend...");
                        return response.text();
                    }
                })
                .catch(error => {
                    console.log('Erro ao enviar os dados:', error);
                    alert('Erro ao enviar os dados: ' + error.message);
                });
        }
    });
});
