package com.alura.Literalura.service;

import com.alura.Literalura.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ConverteDados implements IConverteDados {
    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> obterDadosCparaR(Supplier<Object> supplier, Class<T> tipoClasse) {
        try {
            // Chama o supplier para obter o valor desejado e converte para List<T>
            return mapper.convertValue(supplier.get(), mapper.getTypeFactory().constructCollectionType(List.class, tipoClasse));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Livro converterParaLivro(RLivros rLivro) {
        // Converte a lista de autores de RAutor para Autor
        List<Autor> autores = rLivro.autores().stream()
                .map(this::converterParaAutor)
                .collect(Collectors.toList());

        String idioma = rLivro.idioma().isEmpty() ? null : rLivro.idioma().get(0);

        Livro livro = new Livro();
        livro.setTitulo(rLivro.titulo());
        livro.setAutores(autores);
        livro.setIdioma(idioma);
        livro.setNumeroDeDownload(rLivro.numeroDeDownload());

        return livro;
    }

    public List<Livro> obterDadosRParaC(List<RLivros> rLivros) {
        List<Livro> livroList = new ArrayList<>();
        for (RLivros rLivro : rLivros) {
            livroList.add(converterParaLivro(rLivro));  // Converte cada RLivro e adiciona à lista
        }
        return livroList;
    }

    public Autor converterParaAutor(RAutor rAutor) {
        Autor autor = new Autor();
        autor.setNome(rAutor.name());
        autor.setAnoDeNascimento(rAutor.anoDeNascimento());
        autor.setAnoDeFalecimento(rAutor.anoDeFalecimento());

        return autor;
    }

}