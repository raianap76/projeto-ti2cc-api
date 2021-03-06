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
  let url = `http://localhost:6789/usuario/${index}`;
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
  let data = fazGet("http://localhost:6789/usuario");
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
  console.log(openModalEditar);
  if (!openModalEditar) {
    if (isValidFields()) {
      const client = {
        user_id: document.getElementById("user_id").value,
        tipo_usuario: document.getElementById("tipo_usuario").value,
        senha: document.getElementById("senha").value,
      };
      let url = "http://localhost:6789/usuario";
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
    const client = {
      user_id: document.getElementById("user_id").value,
      tipo_usuario: document.getElementById("tipo_usuario").value,
      senha: document.getElementById("senha").value,
    };
    let url = `http://localhost:6789/usuario/${client.user_id}`;
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
        <td>${client.user_id}</td>
        <td>${client.tipo_usuario}</td>
        <td>${client.senha}</td>
        <td>
            <button type="button" class="button green" id="edit-${index}">Editar</button>
            <button type="button" class="button red" id="delete-${index}" >Excluir</button>
        </td>
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
  document.getElementById("tipo_usuario").value = client.tipo_usuario;
  document.getElementById("senha").value = client.senha;
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
        `Deseja realmente excluir o usuario ${client.user_id}`
      );
      if (response) {
        deleteClient(client1.user_id);
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
