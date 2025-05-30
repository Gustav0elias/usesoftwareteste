# Sistema de Biblioteca Simples

Este projeto é uma aplicação Java simples que simula um sistema básico de biblioteca. Ele permite:

* Listar livros disponíveis.
* Emprestar livros para alunos e professores.
* Devolver livros emprestado.

O projeto utiliza Maven para gerenciamento de dependências e build.

## Tecnologias

* Java 17
* Maven
* JUnit e Mockito (para testes)

## Como Construir e Rodar

Para construir o projeto, navegue até o diretório `usesoftware` no seu terminal e execute o seguinte comando Maven:

```bash
mvn clean install
```

Após a construção bem-sucedida, você pode rodar a aplicação principal com o seguinte comando, ainda no diretório `usesoftware`:

```bash
mvn exec:java -Dexec.mainClass="com.testetecnico.Main"
```

Este comando executará a classe `Main` que demonstra as funcionalidades básicas do sistema. 