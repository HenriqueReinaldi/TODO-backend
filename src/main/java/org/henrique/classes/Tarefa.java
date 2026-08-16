package org.henrique.classes;

import java.util.List;

public class Tarefa {
    public String nome;
    public String desc;
    public String data_termino;
    public int prioridade;
    public String categoria;
    public String status;

    public List<String> alarmes;

    public Tarefa(String nome, String desc, String data_termino, int prioridade, String categoria, String status, List<String> alarmes) {
        this.nome = nome;
        this.desc = desc;
        this.data_termino = data_termino;
        this.prioridade = prioridade;
        this.categoria = categoria;
        this.status = status;
        this.alarmes = alarmes;
    }

    public void show_information(){
        System.out.println("    >" + nome + "\n" +
                            "      :" + desc + "\n" +
                            "      :" + data_termino + "\n" +
                            "      :" + prioridade + "\n" +
                            "      :" + categoria + "\n" +
                            "      :" + status + "\n"
        );
    }

    public String get_attr(String nome){
        return switch (nome) {
            case "desc" -> desc;
            case "data_termino" -> data_termino;
            case "prioridade" -> String.valueOf(prioridade);
            case "categoria" -> categoria;
            case "status" -> status;
            default -> "";
        };
    }

    public List<String> get_list_attr(String nome){
       return switch (nome){
           case "alarmes" -> alarmes;
           default -> null;
       };
    }
}
