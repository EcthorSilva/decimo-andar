document.addEventListener('DOMContentLoaded', () => {
    const addImagemBtn = document.getElementById('addImagemBtn');
    const imagemInputContainer = document.getElementById('imagemInputContainer');
    const cepInput = document.getElementById('cep');
    const logradouroInput = document.getElementById('endereco');

     // Função para consultar o CEP
     async function consultarCEP() {
         const cep = cepInput.value.replace(/\D/g, '');

         if (cep.length !== 8) {
             alert('Por favor, digite um CEP válido com 8 dígitos.');
             return;
         }

         try {
             const response = await fetch(`https://viacep.com.br/ws/${cep}/json/`);
             const data = await response.json();

             if (data.erro) {
                 alert('CEP não encontrado.');
             } else {
                 logradouroInput.value = data.logradouro;
                 console.log(data.logradouro);
             }
         } catch (error) {
             console.error('Erro ao consultar o CEP:', error);
             alert('Ocorreu um erro ao consultar o CEP. Por favor, tente novamente.');
         }
     }

     // Event listener para consultar o CEP ao pressionar "Enter"
     cepInput.addEventListener('keypress', async (event) => {
         if (event.key === 'Enter') {
             event.preventDefault();
             console.log("passei aqui chamada!!");
             await consultarCEP();
         }
     });


    function verificarCampos(){
        var tipoImovel = document.getElementById("tipoImovel").value.trim();
        var endereco = document.getElementById("endereco").value.trim();
        var metrosQuadrados = document.getElementById("metrosQuadrados").value.trim();
        var numQuartos = document.getElementById("numQuartos").value.trim();
        var numBanheiros = document.getElementById("numBanheiros").value.trim();
        var cep = document.getElementById('cep').value.trim();
        var descricaoImovel = document.getElementById("descricaoImovel").value.trim();

        console.log("Campos Verificados!!");

        if(tipoImovel === "" || endereco === "" || metrosQuadrados === "" || numBanheiros === "" || numQuartos === "" || cep === "" || descricaoImovel === ""){
           console.log("Todos os campos são obrigatórios.");
           alert("Todos os campos são obrigatórios.");
           return null; // Retorna null em caso de campos em branco
        }

        return {
            tipoImovel: tipoImovel,
            endereco: endereco,
            metrosQuadrados: metrosQuadrados,
            numQuartos: numQuartos,
            numBanheiros: numBanheiros,
            cep: cep,
            descricaoImovel: descricaoImovel
        }
    }

    document.querySelector('.btn-primary').addEventListener("click", function() {
           console.log("Botão clicado!");

           // Chama a função para verificar os campos
           var dados = verificarCampos();

           if (dados) {
               console.log("Dados do formulário a serem enviados:");
               console.log(JSON.stringify(dados));

               // Enviar dados para a servlet usando fetch
               fetch('/create-imovel', {
                   method: 'POST',
                   headers: {
                       'Content-Type': 'application/json',
                   },
                   body: JSON.stringify(dados),
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
                   //alert('Erro ao enviar os dados: ' + error.message);
               });
           }
       });
    });