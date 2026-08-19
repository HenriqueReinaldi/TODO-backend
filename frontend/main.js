        const input_nome_novo_quadro = document.getElementById("input-nome");
        const area_quadros = document.getElementById("area-quadros");

        //tarefas
        const area_tarefas = document.getElementById("area-tarefas");
        const btn_salvar_adicionar = document.getElementById("btn-salvar-adicionar");
        const btn_deletar = document.getElementById("btn-deletar");

        const input_nome_tarefa = document.getElementById("input-nome-tarefa");
        const select_status = document.getElementById("select-status");
        const input_categoria = document.getElementById("input-categoria");
        const input_prioridade = document.getElementById("input-prioridade");
        const input_descricao = document.getElementById("input-descricao");
        const input_alarme = document.getElementById("input-alarme");
        const check_sim_alarme = document.getElementById("check-sim-alarme");

        //config
        const config_select_display = document.getElementById("select-display");



        //templates
        const template_container = document.getElementById("template-container");


        //status
        var modo_listagem = "L_STATUS"; //   src/....../terminal/modo_listagem
        var tarefa_selecionada = null;
        var quadro_selecionado = null;


//INICIALIZACAO

function init(){
    // CARREGAR QUADROS QUANDO FRONT CARREGAR...
}
init();

function usar_quadro(e) {
    // CÓDIGO DE CARREGAR QUADRO DO BACKEND
    limpar_area_tarefas();
    quadro_selecionado = e.quadro;
    tarefa_selecionada = null;
    mostrar_tarefas();
}

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

//==============

//displayingdaszda

function criar_tarefa_btn(t){
    let tarefa_btn = document.createElement("button");
    tarefa_btn.addEventListener("click", () => {
        tarefa_selecionada = t;
        select_status.value = t.status;
        input_categoria.value = t.categoria;
        input_prioridade.value = t.prioridade;
        input_descricao.value = t.desc;
        input_nome_tarefa.value = "";
        console.log(t);
    })
    tarefa_btn.innerHTML = t.nome;

    return tarefa_btn;
}

function limpar_area_tarefas(){
    let remocao = document.querySelectorAll(".temp-tarefa");
    for (let el of remocao) el.remove();
}

function mostrar_tarefas_status(){
    for (let tit of ["TODO", "DOING", "DONE"]) {
        const area = template_container.content.cloneNode(true);
        const tit_elemento = area.querySelector(".container-titulo");
        const container_tarefas = area.querySelector(".container-tarefas");
        tit_elemento.innerHTML = tit;

        for (let t of quadro_selecionado.lista_de_tarefas){
            if (t.status !== tit) continue;
            let btn = criar_tarefa_btn(t);
            container_tarefas.appendChild(btn);
        }

        area_tarefas.appendChild(area);
    }
}

function mostrar_tarefas_prioridade(){
    for (let tit of ["1", "2", "3", "4", "5"]) {
        const area = template_container.content.cloneNode(true);
        const tit_elemento = area.querySelector(".container-titulo");
        const container_tarefas = area.querySelector(".container-tarefas");
        tit_elemento.innerHTML = tit;

        for (let t of quadro_selecionado.lista_de_tarefas){
            if (String(t.prioridade) !== tit) continue;
            let btn = criar_tarefa_btn(t);
            container_tarefas.appendChild(btn);
        }

        area_tarefas.appendChild(area);
    }
}

function mostrar_tarefas_categoria(){
    let categorias = {};
    for (let t of quadro_selecionado.lista_de_tarefas){
        categorias[t.categoria] = 1;
    }

    for (let tit of Object.keys(categorias)) {
        const area = template_container.content.cloneNode(true);
        const tit_elemento = area.querySelector(".container-titulo");
        const container_tarefas = area.querySelector(".container-tarefas");
        tit_elemento.innerHTML = tit;

        for (let t of quadro_selecionado.lista_de_tarefas){
            if (String(t.categoria) !== tit) continue;
            let btn = criar_tarefa_btn(t);
            container_tarefas.appendChild(btn);
        }

        area_tarefas.appendChild(area);
    }
}

function mostrar_tarefas(){
    limpar_area_tarefas();
    if (quadro_selecionado == null) return;

    switch (modo_listagem){
        case "L_STATUS":
            mostrar_tarefas_status();
            break;
        case "L_PRIORIDADE":
            mostrar_tarefas_prioridade();
            break;
        case "L_CATEGORIA":
            mostrar_tarefas_categoria();
            break;
        case "L_FDS":
            break;
    }
}

//================

//interacao com documento
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

btn_salvar_adicionar.addEventListener("click", _ => {
    if (quadro_selecionado == null) return;

    let status = select_status.value;
    let categoria = input_categoria.value;
    let prioridade = parseInt(input_prioridade.value);
    let descricao = input_descricao.value;
    let alarme = input_alarme.value;
    let nome = input_nome_tarefa.value;

    let novo_alarme_check = check_sim_alarme.checked;

    if (nome !== ""){
        let tarefa = nova_tarefa();
        tarefa.status = status;
        tarefa.categoria = categoria;
        tarefa.prioridade = prioridade;
        tarefa.desc = descricao;
        tarefa.alarmes.push(alarme);
        tarefa.nome = nome;
        if (novo_alarme_check) tarefa.alarmes.push(alarme);
        quadro_selecionado.lista_de_tarefas.push(tarefa);
        mostrar_tarefas();
        return;
    }

    if (tarefa_selecionada == null) return;

    tarefa_selecionada.status = status;
    tarefa_selecionada.categoria = categoria;
    tarefa_selecionada.prioridade = prioridade;
    tarefa_selecionada.desc = descricao;
    if (novo_alarme_check) tarefa_selecionada.alarmes.push(alarme);
    mostrar_tarefas();
});

btn_deletar.addEventListener("click", _ => {
    if (quadro_selecionado == null) return;
    if (tarefa_selecionada == null) return;

    let pos = quadro_selecionado.lista_de_tarefas.indexOf(tarefa_selecionada);
    if (pos >= 0){
        quadro_selecionado.lista_de_tarefas.splice(pos, 1);
    }
    mostrar_tarefas();

})

config_select_display.onchange = ev => {
    modo_listagem = ev.target.value;
    mostrar_tarefas();
};

//================