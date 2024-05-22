document.addEventListener("DOMContentLoaded", function () {

   // Função para carregar os imóveis via AJAX
   function loadImoveis() {
       fetch('/property') // Endpoint para listar os imóveis
           .then(response => {
               if (!response.ok) {
                   throw new Error('Erro ao carregar imóveis.');
               }
               return response.json();
           })
           .then(data => {
               renderImoveis(data);
           })
           .catch(error => {
               console.error('Erro ao carregar imóveis:', error);
               var container = document.getElementById('imoveisContainer');
               if (container) {
                   container.innerHTML = '<p>Erro ao carregar imóveis. Por favor, tente novamente mais tarde.</p>';
               }
           });
   }

   // Função para renderizar os imóveis na página
   function renderImoveis(imoveis) {
       var container = document.getElementById('imoveisContainer');

       // Verifica se o container existe
       if (!container) {
           console.error('Elemento #imoveisContainer não encontrado.');
           return;
       }

       // Limpa o conteúdo atual
       container.innerHTML = '';

       // Itera sobre os imóveis e cria os cards
       var rowCount = Math.ceil(imoveis.length / 3); // Calcula o número de linhas necessárias

       for (var i = 0; i < rowCount; i++) {
           // Cria uma linha
           var row = document.createElement('div');
           row.className = 'row';

           // Adiciona os cards nesta linha
           for (var j = 0; j < 3; j++) {
               var index = i * 3 + j;
               if (index < imoveis.length) {
                   var imovel = imoveis[index];
                   var card = createCard(imovel);
                   var col = document.createElement('div');
                   col.className = 'col-md-4 mb-3';
                   col.appendChild(card);
                   row.appendChild(col);
               }
           }

           container.appendChild(row);
       }
   }

   // Função para criar um card de imóvel
   function createCard(imovel) {
       var cardAnchor = document.createElement('a');
       cardAnchor.href = '#';
       cardAnchor.className = 'text-decoration-none text-dark shadow-lg';
       cardAnchor.setAttribute('data-imovel-id', imovel.id); // Define o ID do imóvel

       cardAnchor.addEventListener('click', function (event) {
           event.preventDefault(); // Evita o comportamento padrão de redirecionamento
           var propertyId = this.getAttribute('data-imovel-id');
           loadPropertyDetails(propertyId);
       });

       var card = document.createElement('div');
       card.className = 'card h-100';

       // Ajuste para obter a primeira imagem, se disponível
       var imagePath = imovel.imagePaths && imovel.imagePaths.length > 0 ? imovel.imagePaths[0] : '';

       var cardImage = document.createElement('img');
       cardImage.className = 'card-img-top img-fluid';
       cardImage.style.height = '200px';  // Ajuste para altura fixa
       cardImage.style.objectFit = 'cover';  // Ajuste para object-fit cover
       cardImage.src = imagePath; // Ajuste para obter a imagem corretamente
       cardImage.alt = 'Imagem do Imóvel';
       card.appendChild(cardImage);

       var cardBody = document.createElement('div');
       cardBody.className = 'card-body';

       var cardTitle = document.createElement('h5');
       cardTitle.className = 'card-title px-2';
       cardTitle.textContent = `R$ ${imovel.valor}`;
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
       tipoText.textContent = imovel.tipoVenda; // Ajuste para obter o tipo de venda
       cardBody3.appendChild(tipoText);

       cardBody.appendChild(cardBody3);

       card.appendChild(cardBody);
       cardAnchor.appendChild(card);

       return cardAnchor;
   }

   // Chama a função para carregar os imóveis ao carregar a página
   loadImoveis();

   // Função para carregar os detalhes do imóvel quando o botão "Ver" é clicado
   function loadPropertyDetails(propertyId) {
       fetch(`/property?id=${propertyId}`)
           .then(response => response.json())
           .then(data => {
               // Redireciona para a página de anúncio com o ID do imóvel
               window.location.href = `/pages/anuncio.html?id=${propertyId}`;
           })
           .catch(error => {
               console.error('Erro ao carregar detalhes do imóvel:', error);
           });
   }

});
