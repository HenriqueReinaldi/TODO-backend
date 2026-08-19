package org.henrique;

import org.henrique.permanencia.Permanencia;
import org.henrique.terminal.Terminal;

public class Main {
    static void main() {
        String path = "/home/carlos/Documents/projetos/projetosJAVA/permanencia";

        /*

        String path = "/home/carlos/Documents/projetos/projetosJAVA/permanencia";
        salvaria todas os quadros de tarefas dentro da pasta permanencia.
        
        
        /!\ É de importante que o caminho fornecido exista.
        
        */

        Permanencia perm = new Permanencia(path);

        Terminal term = new Terminal(perm);
        term.iniciar();
    }
}
