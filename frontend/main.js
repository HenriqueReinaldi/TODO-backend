const input_nome_novo_quadro = document.getElementById("input-nome");
const area_quadros = document.getElementById("area-quadros");

const area_tarefas = document.getElementById("area-tarefas");

var modo_listagem = "L_STATUS"; //   src/....../terminal/modo_listagem



function init(){
    // CARREGAR QUADROS QUANDO FRONT CARREGAR...
}
init();

function limpar_area_tarefas(){
    let remocao = document.getElementsByClassName("temp-tarefa");
    for (let el of remocao) el.remove();
}

function usar_quadro(e) {
    limpar_area_tarefas();
    // CÓDIGO DE CARREGAR QUADRO DO BACKEND
}




input_nome_novo_quadro.onkeydown = (e) => {
    if (e.key !== "Enter") return;
    if (input_nome_novo_quadro.value === " ")return;

    let el = document.createElement("button")

    el.innerHTML += input_nome_novo_quadro.value;
    el.quadro = novo_quadro();
    el.quadro.nome = input_nome_novo_quadro.value;
    el.addEventListener("click", () => { usar_quadro(el) });

    area_quadros.append(el);
    input_nome_novo_quadro.value = "";
};



function novo_quadro(){
    //reflete src/main/java/../classes/Quadro_de_tarefas.java

    return {
        nome : "",
        lista_de_tarefas : [],
    }
}

function nova_tarefa(){
    //reflete src/main/java/../classes/Tarefa.java

    return {
        nome : "",
        desc : "",
        data_termino : "",
        prioridade : 1,
        categoria : "",
        status : "TODO",
        alarmes : [],
    }
}