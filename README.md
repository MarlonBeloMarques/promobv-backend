# PromoBV - Back-End

O PromoBV é uma aplicação onde o usuário pode compartilhar suas promoções de diversas categorias. Esse é um projeto completo desde o back-end ao front-end(mobile). Aqui você encontrará os detalhes para rodar localmente a aplicação back-end ou em um docker. Para que possa ser totalmente util o back-end, você pode encontrar o projeto da aplicação mobile [aqui](https://github.com/MarlonBeloMarques/promobv-mobile) e realizar a comunicação com o back-end. Para os detalhes da comunicação você pode encontrar no readme do projeto mobile.

Esse é um projeto que foi idealizado pela [Larissa Pantoja](https://github.com/Pantoja49).

## O que você pode fazer?

* Cadastro e Acesso em duas etapas por e-mail
* Alterar senha em duas etapas por e-mail
* Autenticação e Autorização por token
* Acesso por google ou facebook utilizando Oauth2
* Alterar informações do usuário
* Adicionar promoções
* Alterar promoções
* Deletar promoções
* Curtir promoções
* Denunciar promoções
* Buscar todas promoções
* Buscar promoções por categoria
* Compartilhar promoções
* Adicionar categorias
* Buscar categorias
* E muito mais...

## O Projeto é composto pelas seguintes pastas:

* config - Configurações da aplicação.
* domain - Classes de dominio.
    * enums - Tipos presentes.
* dto - Objetos para transferência de dados.
* event - Eventos.
* filters - Configurações de filtragem.
* repository - Repositórios usados.
* resources - Comunicação via requisições HTTP.
    * exceptions - Exceções da camada de comunicação.
* security - Configurações gerais de segurança.
    * exceptions - Exceções de seguraça.
    * oauth2 - Configurações de acesso por redes sociais.
        * user - Informações por tipo de acesso.
    * utils - Utilitários.   
* service - Contém as regras de serviço e comunicação com o Repository.
    * exceptions - Exceções de serviço.
    * validation - Validação dos dtos.
        * utils - Utilitários.

## Ambientes
   Você pode rodar a aplicação conforme o ambiente que deseja, para rodar localmente recomendasse utilizar o ambiente de dev, para rodar em um docker, utilizar ambiente de docker e assim sucessivamente. Como posso fazer isso?
  
  ### Alterando o ambiente da aplicação:
  Navegue até:
  
      +---src
        +---main
            +---resources
                |   application.properties
                |   application-dev.properties
                |   application-prod.properties
                |   application-test.properties
                |   application-docker.properties

   Após encontrar a pasta resources, abra o application.properties e altere a linha `spring.profiles.active`e adicione o ambiente de configuração que deseja.
   
   Exemplo:
   
      spring.profiles.active=dev
      
   ### Variáveis de Ambiente
   Para usufruir de todo o poder do back-end, como login com Oauth2, autenticação em duas etapas, comunicação com AWS S3 etc. Você precisar adicionar as váriaveis de ambiente da sua configuração (Verifique mais a baixo como você pode configurar suas variáveis de ambiente).
   
   As variáveis de ambientes necessárias para rodar a aplicação com sucesso, são:
   * USER_EMAIL
   * USER_PASSWORD
   * EMAIL
   * AWS_ACCESS_KEY_ID
   * AWS_SECRET_ACCESS_KEY
   * S3_BUCKET
   * S3_REGION
   * GOOGLE_CLIENT_ID
   * GOOGLE_CLIENT_SECRET
   * FACEBOOK_CLIENT_ID
   * FACEBOOK_CLIENT_SECRET

## Necessário

* XAMPP - Iniciar as portas Apache e MySQL.
* MVN - Instalar variáveis de Ambiente.
* Java - JDK/JRE

## Inicialização 

Na pasta do projeto execute o seguinte comando: 

```sh
$ mvn spring-boot:run
```
## Observação

Para inserção de objetos no banco de dados, basta seguir os exemplos no arquivo `PromoBvApplication.java`.

Os caminhos para requisições podem ser encontradas na pasta resources, para que possa ser feita uma requisição,
pode-se utilizar o [Postman](https://www.postman.com/downloads/)

## Docker

Para realizar consultas, inserções, atualizações etc. Tenha instalado o Docker em sua maquina e realize os seguintes comando no caminho raiz do projeto.

1. Use o MySQL Image publicado pelo [Docker Hub](https://hub.docker.com/_/mysql/) para executar o container mysql
* docker pull mysql
* docker run -p 3306:3306 --name mysql-standalone -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=promobv -e MYSQL_USER=sa -e MYSQL_PASSWORD=password -d mysql

2. Usando o Dockerfile, crie a imagem do Docker. No diretório Dockerfile execute: 
* docker build . -t promobv

3. Execute a imagem do Docker (promobv): 
* docker run -p 8086:8086 -e USER_EMAIL='' -e USER_PASSWORD='' -e EMAIL='' -e AWS_ACCESS_KEY_ID='' -e AWS_SECRET_ACCESS_KEY='' -e S3_BUCKET='' -e S3_REGION='' -e GOOGLE_CLIENT_ID='' -e GOOGLE_CLIENT_SECRET='' -e FACEBOOK_CLIENT_ID='' -e FACEBOOK_CLIENT_SECRET='' --name promobv --link mysql-standalone:mysql -d promobv

Para consultar as tabelas no banco dedados, basta instalar o [MySQL Woekbench](https://www.mysql.com/products/workbench/) e criar uma conexão no localhost na porta 3306 com o seguinte usuario: "sa" e senha : "password"


