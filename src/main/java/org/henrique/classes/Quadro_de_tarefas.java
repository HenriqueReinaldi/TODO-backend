package org.henrique.classes;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Quadro_de_tarefas {
    public String nome;
    public ArrayList<Tarefa> lista_de_tarefas;
    private String data_criacao;

    public void listar_tarefas(){
        for(Tarefa t : lista_de_tarefas){
            t.show_information();
        }
    }

    public void append_tarefa(Tarefa t){
        lista_de_tarefas.add(t);
    }

    public Quadro_de_tarefas(String nome){
        this.nome = nome;
        this.lista_de_tarefas = new ArrayList<>();
        this.data_criacao = LocalDateTime.now().toString();
    }
}
