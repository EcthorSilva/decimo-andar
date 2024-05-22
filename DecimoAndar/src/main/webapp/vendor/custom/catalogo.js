console.log('Catalogo.js carregado com sucesso!');

document.addEventListener("DOMContentLoaded", function () {
   // Função para carregar os imóveis do usuário via AJAX
   function loadImoveis() {
       fetch('/list-imoveis')
           .then(response => response.json())
           .then(data => {
               renderImoveis(data);
           })
           .catch(error => {
               console.error('Erro ao carregar imóveis:', error);
           });
   }

   // Função para renderizar os imóveis na página
   function renderImoveis(imoveis) {
       var container = document.getElementById('imoveisContainer');

       // Limpa o conteúdo atual
       container.innerHTML = '';

       // Itera sobre os imóveis e cria os cards
       var rowCount = Math.ceil(imoveis.length / 3); // Calcula o número de linhas necessárias

       for (var i = 0; i < rowCount; i++) {
           // Cria uma linha
           var row = document.createElement('div');
           row.className = 'row justify-content-center';

           // Adiciona os cards nesta linha
           for (var j = 0; j < 3; j++) {
               var index = i * 3 + j;
               if (index < imoveis.length) {
                   var imovel = imoveis[index];
                   var card = createCard(imovel);
                   row.appendChild(card);
               }
           }

           container.appendChild(row);
       }
   }

   // Função para criar um card de imóvel
   function createCard(imovel) {
       var cardCol = document.createElement('div');
       cardCol.className = 'col-md-4 mb-3';

       var cardAnchor = document.createElement('a');
       cardAnchor.href = '#';
       cardAnchor.className = 'text-decoration-none text-dark shadow-lg';

       var card = document.createElement('div');
       card.className = 'card h-100';

       var cardImage = document.createElement('img');
       cardImage.className = 'img-fluid';
       cardImage.src = imovel.imagem;
       cardImage.alt = 'Imagem do Imóvel';
       card.appendChild(cardImage);

       var cardBody = document.createElement('div');
       cardBody.className = 'card-body';

       var cardTitle = document.createElement('h5');
       cardTitle.className = 'card-title px-2';
       cardTitle.textContent = `R$ ${imovel.preco}`;
       cardBody.appendChild(cardTitle);

       var cardBody2 = document.createElement('div');
       cardBody2.className = 'card-body p-0 pb-0 d-flex align-items-end';

       var geoIcon = document.createElement('i');
       geoIcon.className = 'bi bi-geo-alt pb-3 px-2';
       cardBody2.appendChild(geoIcon);

       var locationText = document.createElement('p');
       locationText.className = 'text-black-50 text-light px-';
       locationText.textContent = imovel.endereco;
       cardBody2.appendChild(locationText);

       cardBody.appendChild(cardBody2);

       var cardBody3 = document.createElement('div');
       cardBody3.className = 'card-body pt-0 pb-0 d-flex align-items-end';

       var exclamationIcon = document.createElement('i');
       exclamationIcon.className = 'bi bi-exclamation-circle pb-3 px-2';
       cardBody3.appendChild(exclamationIcon);

       var tipoLabel = document.createElement('p');
       tipoLabel.className = 'text-black-50 text-light me-md-2';
       tipoLabel.textContent = 'Tipo:';
       cardBody3.appendChild(tipoLabel);

       var tipoText = document.createElement('p');
       tipoText.textContent = imovel.tipo;
       cardBody3.appendChild(tipoText);

       cardBody.appendChild(cardBody3);

       card.appendChild(cardBody);
       cardAnchor.appendChild(card);
       cardCol.appendChild(cardAnchor);

       return cardCol;
   }

   // Chama a função para carregar os imóveis ao carregar a página
   loadImoveis();

   // Função para carregar os detalhes do imóvel quando o botão "Ver" é clicado
   function loadPropertyDetails(propertyId) {
       fetch(`/property-details?id=${propertyId}`)
           .then(response => response.json())
           .then(data => {
               // Implemente aqui a lógica para exibir os detalhes do imóvel
               console.log('Detalhes do imóvel:', data);
           })
           .catch(error => {
               console.error('Erro ao carregar detalhes do imóvel:', error);
           });
   }

   // Adiciona o evento de clique para carregar detalhes do imóvel quando o botão "Ver" é clicado
   document.addEventListener('click', function (event) {
       if (event.target && event.target.classList.contains('btn-ver')) {
           var propertyId = event.target.dataset.imovelId;
           loadPropertyDetails(propertyId);
       }
   });
});
