package com.alura.Literalura.repository;

import com.alura.Literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT l FROM Livro l LEFT JOIN FETCH l.autores")
    List<Livro> findAllWithAutores();

    @Query("SELECT DISTINCT l FROM Livro l JOIN FETCH l.autores a WHERE a.anoDeNascimento <= :anoEscolhido AND (a.anoDeFalecimento > :anoEscolhido OR a.anoDeFalecimento IS NULL)")
    List<Livro> findAutoresVivosEm(@Param("anoEscolhido") int anoEscolhido);

    @Query("SELECT DISTINCT l FROM Livro l JOIN FETCH l.autores WHERE l.idioma = :idioma")
    List<Livro> findLivrosByIdioma(String idioma);

    @Query("SELECT l FROM Livro l ORDER BY l.numeroDeDownload DESC LIMIT 5")
    List<Livro> findTop10();
}
