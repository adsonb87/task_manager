# Task Manager (Lista de Tarefas) - Spring Boot

Este é um repositório para um aplicativo de gerenciamento de tarefas (to-do list) desenvolvido com o Spring Boot.

## Pré-requisitos

Certifique-se de ter os seguintes componentes instalados antes de executar o aplicativo:

- Java Development Kit (JDK) 17 ou superior
- Maven
- MySQL

## Configuração do Banco de Dados

O aplicativo utiliza o MySQL como banco de dados. Certifique-se de ter um servidor MySQL configurado e atualize as informações de conexão no arquivo `application.properties`.

Caso seja necessário, altere o `spring.datasource.username` e `spring.datasource.password` de acordo com suas credenciais do mySql.

Também lembre de criar um Schema com o nome task_manager.

## Executando o Aplicativo

Para executar o aplicativo, siga as etapas abaixo:

1. Clone este repositório para o seu ambiente local.
2. Navegue até o diretório raiz do projeto.
3. Encontre o arquivo `TaskManagerApplication.java` e execute-o clicando em __run__.

Após a execução bem-sucedida, você poderá acessar o aplicativo em `http://localhost:8080`.

## 📚 Documentação (endpoints)

Documentação da API para o task manager.

### Login

Efetua o login de um usuário registrado.

| Método | Funcionalidade                          | URL                         |
| ------ | --------------------------------------- | --------------------------- |
| `POST` | Realiza o login do usuário na aplicação | <http://localhost:8080/api/auth/login> |

- __URL:__ `/api/auth/login`
- __Método:__ `POST`
- __Corpo da Requisição:__

```json
  {
    "email": "usuario@example.com",
    "senha": "senha123"
  }
```

<details>
  <summary>A resposta da requisição é a seguinte:</summary>

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "nome": "Nome do Usuário",
  "sobrenome": "Sobrenome do Usuário",
  "email": "usuario@example.com",
  "token": null
}
```

</details>
