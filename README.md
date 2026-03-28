# 🏦 Bank API - Sistema Bancário com Spring Boot

API RESTful para sistema bancário com funcionalidades completas de cadastro de usuários, autenticação JWT, gerenciamento de contas e transferências.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Funcionalidades](#funcionalidades)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do Ambiente](#configuração-do-ambiente)
- [Executando o Projeto](#executando-o-projeto)
- [Endpoints da API](#endpoints-da-api)
- [Exemplos de Uso](#exemplos-de-uso)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Contribuição](#contribuição)
- [Licença](#licença)
- [Contato](#contato)

## 🚀 Sobre o Projeto

Este é um projeto de API bancária desenvolvido em Java com Spring Boot, implementando funcionalidades essenciais de um sistema bancário como:

- Cadastro de usuários
- Autenticação com JWT
- Gerenciamento de contas bancárias
- Transferências entre contas
- Histórico de transações

O projeto foi desenvolvido como parte do meu portfólio para demonstrar conhecimentos em desenvolvimento backend com Java e Spring Boot.

## 🛠 Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 4.0.5** - Framework principal
- **Spring Security** - Autenticação e autorização
- **JWT (JSON Web Token)** - Tokens de autenticação
- **Spring Data JPA** - Persistência de dados
- **MySQL** - Banco de dados relacional
- **Maven** - Gerenciador de dependências
- **Lombok** - Redução de código boilerplate
- **Validation** - Validação de dados

## ✨ Funcionalidades

- ✅ Cadastro de usuários com validação de CPF e email
- ✅ Autenticação com JWT (JSON Web Token)
- ✅ Criação automática de conta bancária ao cadastrar usuário
- ✅ Consulta de saldo e dados da conta
- ✅ Depósitos em conta
- ✅ Transferências entre contas com validação de saldo
- ✅ Histórico completo de transações
- ✅ Tratamento global de exceções
- ✅ Validação de dados com Bean Validation

## 📦 Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:

- [JDK 21](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://www.mysql.com/downloads/)
- [Git](https://git-scm.com)
- [Postman](https://www.postman.com/) (opcional, para testar)

```bash
git clone https://github.com/seu-usuario/bank-api.git
cd bank-api
