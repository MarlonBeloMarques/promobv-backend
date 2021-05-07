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

   ### Como configurar suas Variáveis de Ambiente
   Abaixo veremos como configurar as variávies de ambiente para que você possa rodar com sucesso a aplicação.
   
   #### USER_EMAIL
   Adicione o nome do seu e-mail, se seu e-mail é `marlonmarqsbr@gmail.com` então adicione somente, `marlonmarqsbr`.
   
   #### USER_PASSWORD
   Adicione a senha do `USER_EMAIL`.
   
   #### EMAIL
   Adicione o seu e-mail completo.
   
   #### AWS_ACCESS_KEY_ID
   Para as configurações da AWS é necessário primeiro ter uma conta na AWS, então acesse [aqui](https://portal.aws.amazon.com/billing/signup#/start) para realizar o cadastro.
   Após realizar o acesso, navegue até *Identity and Access Management (IAM)* > *Users*, e crie um usuário. Ao fim salve as credenciais para que possa usa-las nas demais etapas da AWS.
   Agora com as credenciais em mãos, você pode usar o **access_key_id**.
   
   #### AWS_SECRET_ACCESS_KEY
   Se você realizou a etapa anterior da AWS_ACCESS_KEY_ID, basta pegar no arquivo baixado das credenciais o **secret_access_key**.
   
   #### S3_BUCKET
   Agora que você tem uma conta na AWS, basta ir em *Service* > *Storage* > *S3*, e crie um bucket com o nome `promobv-dev` por exemplo. Esse será o seu **S3_BUCKET**.
   
   #### S3_REGION
   No momomento de criação do bucket você seleciona a região, mas você também pode encontrar em seus buckets **AWS REGION**, esse será o seu **S3_REGION**.
   
   #### GOOGLE_CLIENT_ID
   Para você conseguir realizar login com o Google com Oauth2, primeiro é preciso criar um projeto no Google Cloud Developer. Então faça o login no [Google Developer](https://developers.google.com/) e crie um projeto com o nome `promobv` por exemplo.
   Após ter o projeto criado, Vá em *Tela de consentimento OAuth* > *Prepare-se para a verificação*, adicione apenas algumas informações importantes:
   * Nome do app
   * E-mail para suporte do usuário
   * Dominios Autorizados (Somente se não for localhost)

   Em seguida vá em *Credenciais* > *Criar credenciais* > *ID do Cliente OAuth*, selecione tipo **Aplicativo da Web** e adicione as URIS de origem autorizadas e URIS de redirecionamento autorizados.
   
   Para o nosso caso, basta somente as URIS de redirecionamento autorizados, segue exemplo abaixo:
      
   * http://localhost:8080/oauth2/callback/google
   
   Após as credenciais serem criadas, você já terá acess ao ID do cliente, então adicione em **GOOGLE_CLIENT_ID**.
   
   #### GOOGLE_CLIENT_SECRET
   Após concluida a etapa anterior, basta obter a Chave secreta do cliente e adicionar em **GOOGLE_CLIENT_SECRET**.
   
   #### FACEBOOK_CLIENT_ID
   Para você conseguir realizar login com o Facebook com Oauth2, primeiro é preciso criar um projeto no [Facebook Developers](https://developers.facebook.com/), indo em *Meus Aplicativos* > *Criar aplicativo*, selecione Consumidor e dê um nome ao aplicativo como `promobv` por exemplo.
   Após isso, vá em *Configurações* > *Básico*, e insira:
   * Email de contato
   * Url de política de privacidade
   * Url dos termos de serviço
   * Categoria
   * Informações de contato do encarregado da proteção dos dados
   * Domínios do aplicativo (Exemplo: localhost)
   
   Em Produtos, adicione **Login do Facebook**, em seguida vá em configurações do Login do Facebook e faça o seguinte:
   * Login no OAuth do cliente (Sim)
   * Login do OAuth na Web (Sim)
   * Login de dispositivos (Sim)

   Por fim, volte em *Configurações* > *Básico* e pegue o **ID do Aplicativo** e adicione em **FACEBOOK_CLIENT_ID**.
   
   #### FACEBOOK_CLIENT_SECRET
   Após feito as etapas anteriores, basta obter a **Chave Secreta do Aplicativo** em *Configurações* > *Básico* e adicionar em **FACEBOOK_CLIENT_SECRET**.

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


