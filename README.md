<!--
*** Arquivo de Descrição de Funcionamento e Configuração do Projeto. 
*** Se você tiver alguma sugestão que possa melhorá-lo ainda mais dê um fork no repositório e crie uma Pull Request ou abra uma Issue com a tag "sugestão".
-->

<!-- PROJECT SHIELDS -->


[![GitHub issues](https://img.shields.io/github/issues-raw/rocketseat/react-native-template-rocketseat-advanced.svg)](https://github.com/andreramoslj/rottenmangos/issues)
[![GitHub last commit](https://img.shields.io/github/last-commit/rocketseat/react-native-template-rocketseat-advanced.svg)](https://github.com/andreramoslj/rottenmangos/commits/master)


<!-- PROJECT LOGO -->
<br />
<p align="center">

  <h3 align="center">Rotten Mangos </h3>
</p>

<!-- TABLE OF CONTENTS -->

## Tabela de Conteúdo

- [Tabela de Conteúdo](#tabela-de-conte%C3%BAdo)
- [Sobre o Projeto](#sobre-o-projeto)
  - [Tecnologias Utilizadas](### Tecnologias Utilizadas)
- [Começando](#come%C3%A7ando)
  - [Pré-requisitos](#pr%C3%A9-requisitos)
  - [Estrutura de Arquivos](#estrutura-de-arquivos)
  - [Subindo no Intellij](#instala%C3%A7%C3%A3o)
    - [Possiveis Problemas](#passo-adicional-no-android)
  - [Funcionamento da Aplicação](#edi%C3%A7%C3%A3o)
  - [Acessando Swagger](#publica%C3%A7%C3%A3o)
  - [Acessando Bancod de Dados em Memória](#publica%C3%A7%C3%A3o)
- [Contato](#contato)

<!-- ABOUT THE PROJECT -->

## Sobre o Projeto

Este projeto visa a tirar métricas de Produtores com mais ou com menos intervalo de inicações para o Prêmio Raspberry Awards.
O trófeu Raspberry Awards é uma premiação que acontece anualmente para premiar os piores filmes.
O Projeto é uma API REST feita em Spring Boot para importar um arquivo que contém os nomeados e vencedores de vários anos da premiação.

### Tecnologias Utilizadas

Abaixo segue o que foi utilizado na criação deste template:

- [Spring Boot](https://spring.io/projects/spring-boot) - O Spring Boot é um framework Java open source que tem como objetivo facilitar esse processo em aplicações Java;
- [Swagger](https://swagger.io/) - Swagger é uma linguagem de descrição de interface para descrever APIs RESTful expressas usando JSON. O Swagger é usado junto com um conjunto de ferramentas de software de código aberto para projetar, construir, documentar e usar serviços da Web RESTful. 
- [h2 Database](https://www.h2database.com/html/main.html) - H2 é um banco de dados relacional escrito em Java. Ele pode ser integrado em aplicativos Java ou executado no modo cliente-servidor.
<!-- GETTING STARTED -->


## Começando

Para conseguir rodar o projeto, siga os passos abaixo.


### Pré-requisitos

Antes de seguirmos para as configurações e uso do projeto, é ideal que você tenha a versão do Java Development Kit (jdk11) instalada e configurado na sua máquina 
[JDK11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)


### Estrutura de Arquivos

A estrutura de arquivos está da seguinte maneira:

```bash
rottenmangos
├── src/
│   ├── main/
        └── java/* (Classes principais de funcionamento da Aplicação)
        └── resources/
             └── application.properties (Arquivo com parametros de execução da api)
             └── data.sql (Arquivo de Sql para criação das tabelas em Memória)
             └── Insomnia_2021-11-07 (Collection para importaçãõ no Insomnia/Postman)
             └──  movielist.csv (Arquivo de Exemplo a ser importado)
│   ├──  test/
        └── java/* (Arquivos referentes a Testes de integração)
```


### Subindo no Intellij

1. Abra o Inteliij e vá em File > new > Project from Existin Sources 

2. Seleciona a opção 'Maven' e clique em Finish

https://vaadin.com/docs/v14/flow/tutorials/in-depth-course/project-setup 




#### Possiveis Problemas

Caso haja incompatibilidade de versões das libs do projeto, renomeie a sua pasta .m2/ para baixar as dependencias atualizadas. 


#### Funcionamento da Aplicação

* Ao iniciar a aplicaçao os dados de um arquivo csv são importados e salvos no Banco de dados em Memoria
* É possível importar mais arquivos csv no formato especificado. (MESMO FORMATO DO ARQUIVO DE TESTE EM resources/) utilizando o endpoint POST /api/v1/process/import
** Informar O Arquivo e o Nome de descrição.
* O endpoint vai iniciar o processo de forma ASSINCRONA. 
* É possível analisar o status do Arquivo GET /api/v1/raspberry-files
* Se for importado com sucesso, os registros de indicações serão salvos na tabela RASPBERRY_INDICATION (Possivel acessar o banco de dados e conferir)
* Depois de importado o arquivo e salvo os registros, acessar o GET api/v1/raspberry-indications/intervals para ter uma lista dos Produtores com menor ou maior intervalo entre indicação
** PARAMETRO OBRIGATÓRIO: orderBy (MIN (menor intervalo) / MAX (maior intervalo) )




#### Acessando Swagger

Para acessar a documentação dos endpoints basta subir o projeto e acessar 
http://localhost:8070/api/swagger-ui.html#

É possível fazer testes e utilizar os endpoints pela própria página.


#### Acessando Bancod de Dados em Memória

Para acessar o console do h2 basta subir a api e acessar http://localhost:8070/api/h2-console

Driver class: org.h2.Driver
jdbc url: jdbc:h2:mem:rottemmango
username / pass : ardr / ardr

Clicar em 'Connect' e terá acesso ao gerenciamento do database.


## Contato

André Ramos - [Github](https://github.com/andreramoslj) - **andreramoslj@gmail.com**
