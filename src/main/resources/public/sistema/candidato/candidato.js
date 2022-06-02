"use strict";
var openModalEditar = false;
const openModal = () =>
  document.getElementById("modal").classList.add("active");

const closeModal = () => {
  clearFields();
  document.getElementById("modal").classList.remove("active");
};

// CRUD - create read update delete
const deleteClient = (index) => {
  let url = `http://localhost:6788/candidato/${index}`;
  let request = new XMLHttpRequest();
  request.open("DELETE", url, false);
  request.send();
  console.log(request.responseText);
};

const updateClient = (index, client) => {
  const dbClient = readClient();
  dbClient[index] = client;
};

const fazGet = (url) => {
  let request = new XMLHttpRequest();
  request.open("GET", url, false);
  request.send();
  return request.responseText;
};
const readClient = () => {
  let data = fazGet("http://localhost:6788/candidato");
  console.log(data);
  let usuarios = JSON.parse(data);
  console.log(usuarios.data);
  return usuarios.data;
};

const isValidFields = () => {
  return document.getElementById("form").reportValidity();
};

//Interação com o layout

const clearFields = () => {
  const fields = document.querySelectorAll(".modal-field");
  fields.forEach((field) => (field.value = ""));
};

const saveClient = () => {
  const client = {
    user_id: document.getElementById("user_id").value,
    candidato_id: document.getElementById("candidato_id").value,
    nome: document.getElementById("nome").value,
    email: document.getElementById("email").value,
    profissao: document.getElementById("profissao").value,
    area_interesse: document.getElementById("area_interesse").value,
    foto_perfil: document.getElementById("foto_perfil").value,
    pais: document.getElementById("pais").value,
    genero: document.getElementById("genero").value,
    nacionalidade: document.getElementById("nacionalidade").value,
    data_nascimento: document.getElementById("data_nascimento").value,

  };
  if (!openModalEditar) {
    if (isValidFields()) {
      let url = "http://localhost:6788/candidato";
      console.log("Body=", client);
      let request = new XMLHttpRequest();
      request.open("POST", url, true);
      request.send(JSON.stringify(client));

      request.onload = function () {
        console.log(this.responseText);
        closeModal();
        updateTable();
      };
      console.log(request.responseText);
    }
  } else {
    let url = `http://localhost:6788/candidato/${client.candidato_id}`;
    console.log("Body=", client);
    let request = new XMLHttpRequest();
    request.open("PUT", url, true);
    request.send(JSON.stringify(client));

    request.onload = function () {
      console.log(this.responseText);
      closeModal();
      updateTable();
      this.openModalEditar = false;
    };
    console.log(request.responseText);
  }
};
const createRow = (client, index) => {
  const newRow = document.createElement("tr");
  newRow.innerHTML = `
        <td>
          <button type="button" class="button green" id="edit-${index}">Editar</button>
          <button type="button" class="button red" id="delete-${index}" >Excluir</button>
        </td>
        <td>${client.candidato_id}</td>
        <td>${client.user_id}</td>
        <td>${client.nome}</td>
        <td>${client.email}</td>
        <td>${client.profissao}</td>
        <td>${client.empresa_atual}</td>
        <td>${client.area_interesse}</td>
        <td>${client.foto_perfil}</td>
        <td>${client.pais}</td>
        <td>${client.genero}</td>
        <td>${client.nacionalidade}</td>
        <td>${client.data_nascimento}</td>
    `;
  document.querySelector("#tableClient>tbody").appendChild(newRow);
};

const clearTable = () => {
  const rows = document.querySelectorAll("#tableClient>tbody tr");
  rows.forEach((row) => row.parentNode.removeChild(row));
};

const updateTable = () => {
  const dbClient = readClient();
  if (dbClient) {
    clearTable();
    dbClient.forEach(createRow);
  }
};

const fillFields = (client) => {
  document.getElementById("user_id").value = client.user_id;
  document.getElementById("candidato_id").value = client.candidato_id;
  document.getElementById("nome").value = client.nome;
  document.getElementById("email").value = client.email;
  document.getElementById("profissao").value = client.profissao;
  document.getElementById("empresa_atual").value = client.empresa_atual;
  document.getElementById("area_interesse").value = client.area_interesse;
  document.getElementById("foto_perfil").value = client.foto_perfil;
  document.getElementById("pais").value = client.pais;
  document.getElementById("genero").value = client.genero;
  document.getElementById("nacionalidade").value = client.nacionalidade;
  document.getElementById("data_nascimento").value = client.data_nascimento;


};

const editClient = (index) => {
  const client = readClient()[index];
  client.index = index;
  fillFields(client);
  console.log(client);
  openModal();
};

const editDelete = (event) => {
  if (event.target.type == "button") {
    const [action, index] = event.target.id.split("-");
    const client1 = readClient()[index];
    if (action == "edit") {
      openModalEditar = true;
      editClient(index);
    } else {
      const client = readClient()[index];
      console.log(client);
      const response = confirm(
        `Deseja realmente excluir o candidato ${client.candidato_id}`
      );
      if (response) {
        deleteClient(client1.candidato_id);
        updateTable();
      }
    }
  }
};

updateTable();

// Eventos
document
  .getElementById("cadastrarCliente")
  .addEventListener("click", openModal);

document.getElementById("modalClose").addEventListener("click", closeModal);

document.getElementById("salvar").addEventListener("click", saveClient);

document
  .querySelector("#tableClient>tbody")
  .addEventListener("click", editDelete);

document.getElementById("cancelar").addEventListener("click", closeModal);
