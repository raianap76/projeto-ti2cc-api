"use strict";
var openModalEditar = false;
const openModal = () =>
  document.getElementById("modal").classList.add("active");

const closeModal = () => {
  clearFields();
  document.getElementById("modal").classList.remove("active");
};
var dados = {};
const fazGet = (url) => {
  let request = new XMLHttpRequest();
  request.open("GET", url, false);
  request.send();
  return request.responseText;
};
const readClient = () => {
  let data = fazGet("http://localhost:6788/sistemaInteligente");
  console.log(data);
  let usuarios = JSON.parse(data);
  usuarios = JSON.parse(usuarios.data)
  console.log(usuarios.result);
  document.getElementById("poeResultado").value = `
  class: ${usuarios.result[0]['class']}
  tempo: ${usuarios.result[0]['tempo']},
  valence: ${usuarios.result[0]['valence']},
  liveness: ${usuarios.result[0]['liveness']},
  acousticness: ${usuarios.result[0]['acousticness']},
  danceability: ${usuarios.result[0]['danceability']},
  energy: ${usuarios.result[0]['energy']},
  speechiness: ${usuarios.result[0]['speechiness']},
  instrumentalness: ${usuarios.result[0]['instrumentalness']},
  Scored Probabilities_calmo: ${usuarios.result[0]['Scored Probabilities_calmo']},
  Scored Probabilities_correr: ${usuarios.result[0]['Scored Probabilities_correr']},
  Scored Probabilities_dormir: ${usuarios.result[0]['Scored Labels']},
  Scored Probabilities_energetico: ${usuarios.result[0]['Scored Probabilities_dormir']},
  Scored Probabilities_feliz:${usuarios.result[0]['Scored Probabilities_feliz']},
  Scored Probabilities_foco: ${usuarios.result[0]['Scored Probabilities_foco']},
  Scored Probabilities_gaming: ${usuarios.result[0]['Scored Probabilities_gaming']},
  Scored Probabilities_triste: ${usuarios.result[0]['Scored Probabilities_triste']},
  Scored Labels: ${usuarios.result[0]['Scored Labels']}`
  


  // usuarios.result[0]['Scored Labels'];
  console.log("teste")
  dados = usuarios.result
  return usuarios.result;
};

const createRow = (client, index) => {
  const newRow = document.createElement("tr");
  newRow.innerHTML = `
        <td>
          <button type="button" class="button green" id="edit-${index}">Editar</button>
          <button type="button" class="button red" id="delete-${index}" >Excluir</button>
        </td>
        <td>${client.user_id}</td>
        <td>${client.candidato_id}</td>
        <td>${client.titulo}</td>
        <td>${client.tipo_emprego}</td>
        <td>${client.nome_empresa}</td>
        <td>${client.localidade}</td>
        <td>${client.data_inicio}</td>
        <td>${client.data_fim}</td>
        <td>${client.setor}</td>
        <td>${client.descricao}</td>
    `;
  document.querySelector("#tableClient>tbody").appendChild(newRow);
};

document.getElementById("chamadaSistema").addEventListener("click", readClient);
