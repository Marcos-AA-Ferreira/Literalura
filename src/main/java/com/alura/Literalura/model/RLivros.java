package com.alura.Literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RLivros(
        @JsonProperty("title") String titulo,
        @JsonProperty("authors") List<RAutor> autores,
        @JsonProperty("languages") List<String> idioma,
        @JsonProperty("download_count") int numeroDeDownload
) {}
