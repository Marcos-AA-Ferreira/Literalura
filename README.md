<h1 align="center">Desafio Literalura</h1>

<p align="center">
 <img src="https://img.shields.io/badge/Java-21%2B-blue" alt="Java"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Maven-4.0.0-purple" alt="Maven"/>
  <img src="https://img.shields.io/badge/PostgreSQL-16.4-blue" alt="PostgreSQL"/>
  <img src="https://img.shields.io/badge/jackson-2.15.2-red" alt="Jackson"/>
  <img src="https://img.shields.io/badge/API-Gutendex-orange" alt="Gutendex API"/>
  <img src="https://img.shields.io/badge/IDE-IntelliJ%20IDEA-pink" alt="IntelliJ IDEA"/>
</p>

<p align="center">
  <img loading="lazy" src="http://img.shields.io/static/v1?label=STATUS&message=CONCLUIDO&color=GREEN&style=for-the-badge"/>
</p>

## Descrição

Esse desafio visa consolidar os conhecimentos obtidos na primeira parte da formação em Java e Spring Framework. Durante essa etapa, aprendemos a consumir dados por meio de uma API e 
utilizamos a API Gutendex, que fornece informações sobre diversos livros nacionais e internacionais, incluindo o nome do(a) autor(a) e o número de downloads.

O projeto consistiu no desenvolvimento de uma aplicação back-end que, no futuro, pode evoluir para uma API que extrai dados de um banco de dados SGL, utilizando o PostgreSQL como base de dados.
A aplicação foi desenvolvida no IntelliJ IDEA e configurada como um projeto Maven por meio do "Spring Initializr".

## Funcionalidades do projeto

- `Cadastrar Livros presente na API Gutendex`: O sistema permite o usuário buscar por um livro na API Gutendex, que traz os dados do livro (Titulo da Obra, Idioma, Numero de Download e Dados do Autor(a)) e adicionar seus dados a uma tabela Livros;
- `Cadastrar Autores presente na API Gutendex`: Após o cadastro do Livro, os dados do autor também são salvos em uma tabela associada a tabela Livros, denominada Autores;
- `Exiber os Livros Cadastrado no Banco de Dados`: Buscar os dados dos livros da tabela Livro e exibe para o usuário;
- `Exiber os Autores Cadastrado no Banco de Dados`: Buscar os dados dos autores da tabela Autores e exibe para o usuário;
- `Filtrar Autores que Estava Vivos em Determinado Ano`: Aplicar um filtro que selecionar os autores vivos e nascido em determinado ano;
- `Filtrar Livros por Idiomas`: Aplicar um filtro que traz somente os livros e um determinado idiomas;
- `Exiber os Top 10 Mais Baixado`: Exiber os 10 livros mais baixado com base nos dados no banco de dados.

## Técnicas e tecnologias utilizadas

- ``Java 21``
- ``InteliJ IDEA``
- ``Maven``
- ``Jackson``
