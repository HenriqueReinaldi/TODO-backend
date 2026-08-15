### Sobre:

Esta é uma TODO-list simples feita em java.<br><br>
O programa permite o usuário a criar quadros de tarefas, cada um contendo uma lista de tarefas que pode ser observada e modificada.<br>


### Para executar:

    0. Necessário Java 8+ e GRADLE

    1. Na linha 8 de src/main/java/org/henrique/Main.java temos uma String que detém o caminho usado para salvar as listas. É necessário editá-la para um caminho de seu desejo.

    2. Execute na raiz do projeto o comando:

    ./gradlew run -q --console=plain


    ALTERNATIVAMENTE


    1. Abra o projeto no Intellij IDEA.
    2. Opções para rodar:
        2.a Vá para src/main/java/org/henrique/Main.java e clique no botão RUN do método main()

        2.b Use a hotkey de "rodar 'main'" (padrão: "SHIFT+F10")

### Solução

Como sugerido, utilizei o Backlog criado em K1-T2 (com algumas modificações para funcionar no terminal).<br>
Desse modo, o projeto é separado em 3 partes principais:<br>

    Classes: Contém as informações para instanciar objetos "Tarefa" e "Quadro de tarefas"

    Permanência: O sistema de permanência de dados mencionado no backlog
    
    Terminal: A interface de linha de comando do programa.

### Informações pertinentes:

Arquivo main do programa: src/main/java/org/henrique/Main.java<br>
[Tecnologias](https://www.youtube.com/watch?v=NjXweFUgY78): Java, Graddle, IntelliJ IDEA
