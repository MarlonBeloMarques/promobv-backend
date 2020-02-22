# PromoBV - Modelo Conceitual

O modelo conceitual consiste em criar a estrutura do back-end, tais como requisições, repositorios, classes de dominio etc.

### Estrutura

O projeto está estruturado conforme abaixo:

    +---src
        +---main
            +---java/com/marlonmarqs/promobv
                |   PromoBvApplication.java
                |
                +---domain
                    |   Categoria.java
                    |   GaleriaDeImagens.java
                    |   Notificacao.java
                    |   Promocao.java
                    |   Usuario.java
                    |
                    +---enums
                        |   TipoNotificacao.java
                        |   TipoPerfil.java
                        |
                +---repository
                        |   CategoriaRepository.java
                        |   GaleriaDeImagensRepository.java
                        |   NotificacaoRepository.java
                        |   PromocaoRepository.java
                        |   UsuarioRepository.java
                        |
                +---resources
                        |   CategoriaResource.java
                        |   NotificacaoResource.java
                        |   PromocaoResource.java
                        |   UsuarioResource.java
                        |
                        +---exceptions
                            |   ResourceExceptionHandler.java
                            |   StandardError.java
                            |
                +---service
                        |   CategoriaService.java
                        |   NotificacaoService.java
                        |   PromocaoService.java
                        |   UsuarioService.java
                        |
                        +---exceptions
                            |   ObjectNotFoundEception.java
                            |
            +---resources
                |   application.properties
                |
                
Pode-se observar que o projeto é composto pelas pastas:

* Domain - Destinada as classes de dominio.
* Enums - Os tipos presentes.
* Repository - Os repositorios.
* Service - Onde contém as regras e comunicação com repository.
* Resources - Comunicação através de requisições HTTP.
* Exception - excessões.

### Necessário

* XAMPP - Iniciar as portas Apache e MySQL.
* MVN - Instalar variáveis de Ambiente.
* Java - JDK/JRE

### Inicialização 

Na pasta do projeto execute o seguinte comando: 

```sh
$ mvn spring-boot:run
```
### Observação

Para inserção de objetos no banco de dados, basta seguir os exemplos no arquivo `PromoBvApplication.java`.

Os caminhos para requisições podem ser encontradas na pasta resources, para que possa ser feita uma requisição,
pode-se utilizar o [Postman](https://www.postman.com/downloads/)

### Docker

Para realizar consultas, inserções, atualizações etc. Tenha instalado o Docker em sua maquina e realize os seguintes comando no caminho raiz do projeto.

1. Use o MySQL Image publicado pelo [Docker Hub](https://hub.docker.com/_/mysql/) para executar o container mysql
* docker pull mysql
* docker run -p 3306:3306 --name mysql-standalone -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=test -e MYSQL_USER=sa -e MYSQL_PASSWORD=password -d mysql:5.6

2. Usando o Dockerfile, crie a imagem do Docker. No diretório Dockerfile execute: 
* docker build . -t users-mysql

3. Execute a imagem do Docker (users-mysql): 
* docker run -p 8086:8086 --name users-mysql --link mysql-standalone:mysql -d users-mysql

Para consultar as tabelas no banco dedados, basta instalar o [MySQL Woekbench](https://www.mysql.com/products/workbench/) e criar uma conexão no localhost na porta 3306 com o seguinte usuario: "sa" e senha : "password"


