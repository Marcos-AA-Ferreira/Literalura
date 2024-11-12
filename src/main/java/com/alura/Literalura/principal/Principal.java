package com.alura.Literalura.principal;

import com.alura.Literalura.model.Autor;
import com.alura.Literalura.model.Livro;
import com.alura.Literalura.model.RLivros;
import com.alura.Literalura.model.RespostaAPI;
import com.alura.Literalura.repository.LivroRepository;
import com.alura.Literalura.service.ConsumoApi;
import com.alura.Literalura.service.ConverteDados;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/";

    private LivroRepository repositorio;

    public Principal(LivroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        System.out.println("Bem vindo(a) ao Literalura📚\nComo posso ajudar? ");
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    
                    Escolha uma opção abaixo:
                    
                    1 - Buscar Livro na Web
                    2 - Listar Livros Registrados
                    3 - Listar Autores Registrados
                    4 - Buscar Autores Vivos em Determinado Ano
                    5 - Buscar Livro por Idioma
                    6 - Livros Mais Baixados
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    listaLivroNoRegistro();
                    break;
                case 3:
                    listaAutoresNoRegistro();
                    break;
                case 4:
                    autorVivosEm();
                    break;
                case 5:
                    buscarLivroPorIdioma();
                    break;
                case 6:
                    top10MaisBaixados();
                    break;
                case 0:
                    System.out.println("Saindo do Sistema...");
                    System.out.println("Obrigado por usar Literalura\nAté a próxima 👋");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    // ****************** Exibição de Livro e Autor de uma forma mais bonita *******************
    private String exibirLivro(Livro livro) {
        String autores = livro.getAutores().stream()
                .map(Autor::getNome)  // Obtém apenas o nome de cada autor
                .collect(Collectors.joining("; "));
        return String.format("Título da Obra: %s\nIdioma: %s\nNumero de Downloads: %d\nAutor(a): %s\n",
                livro.getTitulo(),
                livro.getIdioma(),
                livro.getNumeroDeDownload(),
                autores);
    }

    private String exibirAutor(Autor autor) {
        return String.format("Nome: %s, Ano de Nascimento: %d e Ano de Falecimento: %s\n",
                autor.getNome(),
                autor.getAnoDeNascimento(),
                autor.getAnoDeFalecimento());
    }
    // ********************************************************************************************


    private void buscarLivroWeb() {
        try {
            System.out.println("\nDigite o nome do livro para busca:");
            var nomeLivro = leitura.nextLine();

            var json = consumo.obterDados(ENDERECO + "?search=" + nomeLivro.replace(" ", "%20").toLowerCase());
            RespostaAPI respostaAPI = conversor.obterDados(json, RespostaAPI.class);

            List<RLivros> livros = conversor.obterDadosCparaR(respostaAPI::getResults, RLivros.class);
            List<Livro> livrolist = conversor.obterDadosRParaC(livros);

            for (Livro livro : livrolist) {
                try {
                    repositorio.save(livro);
                    System.out.println("\nNovo livro adicionado ao banco de dados: ");
                    System.out.println(exibirLivro(livro));
                } catch (Exception e) {
                    System.err.println("\n⚠️⚠️⚠️Erro ao processar livro: " + nomeLivro + " já existente no banco de dados⚠️⚠️⚠️");
                }
            }
        } catch (Exception e) {
            System.err.println("\n⚠️⚠️⚠️Erro ao buscar livro na Web ou processar dados⚠️⚠️⚠️");
        }
    }

    private void listaLivroNoRegistro() {
        System.out.println("\n---- Lista de livros registrados ----");

        List<Livro> livrosRepositorio = repositorio.findAllWithAutores();
        if (livrosRepositorio.isEmpty()) {
            System.out.println("\nNenhum livro registrado no banco de dados.");
        } else {
            livrosRepositorio.forEach(livro -> System.out.println(exibirLivro(livro)));
        }
        System.out.println("-------------------------------------");
    }

    private void listaAutoresNoRegistro() {
        System.out.println("\n---- Lista de autores registrados ----");
        List<Livro> livrosComAutores = repositorio.findAllWithAutores();

        livrosComAutores.forEach(livro -> livro.getAutores()
                .forEach(autor -> System.out.println(exibirAutor(autor))));

        System.out.println("--------------------------------------");
    }

    private void autorVivosEm() {
        System.out.println("\nQual ano você deseja verificar? ");
        var anoEscolhido = leitura.nextInt();
        leitura.nextLine();

        List<Livro> livrosComAutoresVivos = repositorio.findAutoresVivosEm(anoEscolhido);

        if (livrosComAutoresVivos == null || livrosComAutoresVivos.isEmpty()) {
            System.out.println("Nenhum autor(a) encontrado vivo no ano de " + anoEscolhido + ".");
            return;
        }

        System.out.println("\n---- Autores vivos em " + anoEscolhido + " ----\n");
        livrosComAutoresVivos.forEach(livro -> livro.getAutores()
                .forEach(autor -> System.out.println(exibirAutor(autor))));
        System.out.println("--------------------------------------");
    }

    private void buscarLivroPorIdioma() {
        String escolhaDeIdioma = """
            
            Digite a abreviação para selecionar o idioma:
            pt - português
            en - inglês
            es - espanhol
            fr - francês
            
            """;
        System.out.println(escolhaDeIdioma);
        var idiomaEscolhido = leitura.nextLine();

        List<Livro> livrosPorIdioma = repositorio.findLivrosByIdioma(idiomaEscolhido.toLowerCase());

        System.out.println("\n---- Livros no idioma: " + idiomaEscolhido + " ----");
        livrosPorIdioma.forEach(livro -> System.out.println(exibirLivro(livro)));
        System.out.println("--------------------------------------");
    }


    private void top10MaisBaixados() {
        System.out.println("\n---- Top Livros mais baixado ----");
        List<Livro> maisBaixado = repositorio.findTop10();
        maisBaixado.forEach(livro -> System.out.println(exibirLivro(livro)));
        System.out.println("\n----------------------------------");
    }
}