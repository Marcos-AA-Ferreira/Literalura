package com.alura.Literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RAutor(
        @JsonProperty("name")String name,
        @JsonProperty("birth_year") int anoDeNascimento,
        @JsonProperty("death_year") int anoDeFalecimento
) {}
