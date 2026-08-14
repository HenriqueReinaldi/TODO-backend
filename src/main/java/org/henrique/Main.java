package org.henrique;

import org.henrique.permanencia.Permanencia;
import org.henrique.terminal.Terminal;

public class Main {
    static void main() {
        Permanencia perm = new Permanencia();
        Terminal term = new Terminal(perm);


        term.iniciar();
    }
}
