package main;

import model.Video;
import repository.FileVideoRepository;
import service.VideoService;
import service.VideoServiceImpl;
import strategy.SearchStrategy;
import strategy.TitleSearchStrategy;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String titulo = "";
        String descricao = "";
        int duracao = 0;
        String categoria = "";
        String dataStr;
        int escolhaCategoria;
        Scanner scanner = new Scanner(System.in);
        VideoService videoService = new VideoServiceImpl(new FileVideoRepository("videos.txt"));
        SearchStrategy searchStrategy = new TitleSearchStrategy();

        while (true) {
            System.out.println(" ");
            System.out.println("========================================================");
            System.out.println("========== Sistema de Gerenciamento de Vídeos ==========");
            System.out.println("========================================================");
            System.out.println("1. Adicionar vídeo");
            System.out.println("2. Listar vídeos");
            System.out.println("3. Pesquisar vídeo por título");
            System.out.println("4. Editar vídeo");
            System.out.println("5. Excluir vídeo");
            System.out.println("6. Filtrar vídeo por categoria");
            System.out.println("7. Ordenar vídeo por data de publicação");
            System.out.println("8. Exibir relatório de estatísticas");
            System.out.println("9. Sair");
            System.out.println("========================================================");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            System.out.println("========================================================");

            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {

                case 1:
                //Adicionar video
                    System.out.println(" ");
                    System.out.println("============= 1. Adicionar vídeo ===============");
                    do {

                        try {
                            System.out.print("Digite o título do vídeo: ");
                            titulo = scanner.nextLine();
                            System.out.println(" ");

                            if (titulo.isBlank()) {
                                System.out.println(" --- Por favor, escreva um título! --- ");
                                System.out.println(" ");
                                continue;
                            }
                            if (titulo.charAt(0) >= '0' && titulo.charAt(0) <= '9') {
                                System.out.println(" --- Por favor, digite um nome válido, não números! --- ");
                                System.out.println(" ");
                                continue;
                            }

                        } catch (Exception e) {
                            break;

                        }

                    } while (titulo.isBlank() || titulo.charAt(0) >= '0' && titulo.charAt(0) <= '9');

                    do {
                        try {
                            System.out.print("Digite a descrição do vídeo: ");
                            descricao = scanner.nextLine();
                            System.out.println(" ");

                            if (descricao.isBlank()) {
                                System.out.println(" --- Por favor, escreva uma descrição! --- ");
                                System.out.println(" ");
                                continue;
                            }
                            if (descricao.charAt(0) >= '0' && descricao.charAt(0) <= '9') {
                                System.out.println(" --- Por favor, digite um nome válido, não números! ---");
                                System.out.println(" ");
                                continue;
                            }


                        } catch (Exception e) {
                            break;
                        }

                    } while (descricao.isBlank() || descricao.charAt(0) >= '0' && descricao.charAt(0) <= '9');


                    do {
                        try {
                            System.out.print("Digite a duração do vídeo (em minutos): ");
                            duracao = scanner.nextInt();
                            System.out.println(" ");

                            if (duracao <= 0) {
                                System.out.println(" --- Por favor, digite apenas números positivos! --- ");
                                System.out.println(" ");
                            }

                        } catch (Exception e) {
                            System.out.println(" --- Digite apenas números! --- ");
                            System.out.println(" ");
                            break;
                        }

                    } while (duracao <= 0);

                    scanner.nextLine();

                    do {
                        try {
                            // Consumir a quebra de linha
                            System.out.println("Selecione a categoria do vídeo: ");
                            System.out.println("1 - Aventura");
                            System.out.println("2 - Ação");
                            System.out.println("3 - Suspense");
                            System.out.println("4 - Comédia");
                            System.out.println("5 - Romance");
                            System.out.println("6 - Drama");


                            System.out.println(" ");
                            escolhaCategoria = scanner.nextInt();


                        } catch (Exception e) {
                            System.out.println(" -- Digite um número de 1 a 5! --");
                            break;
                        }

                        categoria = switch (escolhaCategoria) {
                            case 1 -> "Aventura";
                            case 2 -> "Ação";
                            case 3 -> "Suspense";
                            case 4 -> "Comédia";
                            case 5 -> "Romance";
                            case 6 -> "Drama";
                            default -> categoria;
                        };
                    } while (escolhaCategoria <1 || escolhaCategoria> 6 );

                    scanner.nextLine();

                    do {
                        System.out.print("Digite a data de publicação (dd/MM/yyyy): ");
                        dataStr = scanner.nextLine();
                        System.out.println(" ");

                        try {

                            if (dataStr.isBlank()) {
                                System.out.println(" --- Por favor, escreva a data da publicação! --- ");
                                System.out.println(" ");
                                continue;
                            }

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date dataPublicacao = sdf.parse(dataStr);
                            Video video = new Video(titulo, descricao, duracao, categoria, dataPublicacao);
                            videoService.addVideo(video);
                            System.out.println("Vídeo adicionado com sucesso!");
                        } catch (Exception e) {
                            System.out.println("Data com formato inválido! Tente novamente.");
                            break;
                        }

                    } while (dataStr.isBlank() || dataStr.length() != 10);
                    break;

                case 2:
                //Listar vídeos
                    System.out.println(" ");
                    System.out.println("============= 2. Listar vídeos =================");
                    System.out.println(" ");
                    List<Video> videos = videoService.listVideos();
                    for (Video video : videos) {
                        System.out.println("- " + video);
                    }
                    if (videos.isEmpty()) {
                        System.out.println(" -- Nenhum vídeo encontrado! -- ");
                    }
                    System.out.println(" ");
                    break;


                case 3:
                //Pesquisar vídeo por título
                    System.out.println(" ");
                    System.out.println("=========== 3. Pesquisar vídeo por título ==============");
                    System.out.print("Digite o título para busca: ");
                    String query = scanner.nextLine();
                    System.out.println(" ");
                    System.out.println("Resultados da busca:");
                    System.out.println(" ");
                    List<Video> resultadosBuscaVideo = searchStrategy.search(videoService.listVideos(), query);

                    if (resultadosBuscaVideo.isEmpty()) {
                        System.out.println(" -- Nenhum vídeo encontrado com o título especificado! -- ");
                        break;
                    }
                    for (Video video : resultadosBuscaVideo) {
                        System.out.println(video);
                        System.out.println("------------------------------");
                    }
                    System.out.println(" ");
                    break;

                case 4:
                //Editar vídeo
                // Permitir que o usuário edite as informações de um vídeo existente.
                    System.out.println(" ");
                    System.out.println("========== 4. Editar informações de vídeo =============");
                    System.out.print("Digite o título para busca: ");
                    String tituloEdicao = scanner.nextLine();
                    System.out.println(" ");

                    List<Video> resultadosEdicao = searchStrategy.search(videoService.listVideos(), tituloEdicao);
                    if (resultadosEdicao.isEmpty()) {
                        System.out.println("Nenhum vídeo encontrado com o título especificado.");
                        break;
                    }



                    Video videoParaEditar = resultadosEdicao.get(0);
                    Video videoAnteriorParaDeletar = resultadosEdicao.get(0);

                    System.out.println("Vídeo encontrado:");
                    System.out.println(videoParaEditar);
                    System.out.println("------------------------------");

                    // Permitir edição dos campos
                    System.out.print("Deseja alterar o título? (S/N): ");
                    String resposta = scanner.nextLine();
                    if (resposta.equalsIgnoreCase("S")) {
                        System.out.print("Novo título: ");
                        String novoTitulo = scanner.nextLine();
                        videoParaEditar = new Video(novoTitulo, videoParaEditar.getDescricao(), videoParaEditar.getDuracao(), videoParaEditar.getCategoria(), videoParaEditar.getDataPublicacao());
                    }

                    System.out.print("Deseja alterar a descrição? (S/N): ");
                    resposta = scanner.nextLine();
                    if (resposta.equalsIgnoreCase("S")) {
                        System.out.print("Nova descrição: ");
                        String novaDescricao = scanner.nextLine();
                        videoParaEditar = new Video(videoParaEditar.getTitulo(), novaDescricao, videoParaEditar.getDuracao(), videoParaEditar.getCategoria(), videoParaEditar.getDataPublicacao());
                    }

                    System.out.print("Deseja alterar a duração? (S/N): ");
                    resposta = scanner.nextLine();
                    if (resposta.equalsIgnoreCase("S")) {
                        System.out.print("Nova duração (em minutos): ");
                        int novaDuracao = scanner.nextInt();
                        scanner.nextLine(); // Consumir quebra de linha
                        videoParaEditar = new Video(videoParaEditar.getTitulo(), videoParaEditar.getDescricao(), novaDuracao, videoParaEditar.getCategoria(), videoParaEditar.getDataPublicacao());
                    }

                    System.out.print("Deseja alterar a categoria? (S/N): ");
                    resposta = scanner.nextLine();
                    if (resposta.equalsIgnoreCase("S")) {
                        System.out.println("Selecione a nova categoria:");
                        System.out.println("1 - Aventura");
                        System.out.println("2 - Ação");
                        System.out.println("3 - Suspense");
                        System.out.println("4 - Comédia");
                        System.out.println("5 - Romance");
                        System.out.println("6 - Drama");

                        int novaCategoriaEscolha = scanner.nextInt();
                        scanner.nextLine(); // Consumir quebra de linha
                        String novaCategoria = switch (novaCategoriaEscolha) {
                            case 1 -> "Aventura";
                            case 2 -> "Ação";
                            case 3 -> "Suspense";
                            case 4 -> "Comédia";
                            case 5 -> "Romance";
                            case 6 -> "Drama";
                            default -> videoParaEditar.getCategoria();
                        };
                        videoParaEditar = new Video(videoParaEditar.getTitulo(), videoParaEditar.getDescricao(), videoParaEditar.getDuracao(), novaCategoria, videoParaEditar.getDataPublicacao());
                    }

                    System.out.print("Deseja alterar a data de publicação? (S/N): ");
                    resposta = scanner.nextLine();
                    if (resposta.equalsIgnoreCase("S")) {
                        System.out.print("Nova data de publicação (dd/MM/yyyy): ");
                        String novaDataStr = scanner.nextLine();
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date novaDataPublicacao = sdf.parse(novaDataStr);
                            videoParaEditar = new Video(videoParaEditar.getTitulo(), videoParaEditar.getDescricao(), videoParaEditar.getDuracao(), videoParaEditar.getCategoria(), novaDataPublicacao);
                        } catch (Exception e) {
                            System.out.println("Formato de data inválido. Alteração ignorada.");
                        }
                    }


                    videoService.deleteVideo(videoAnteriorParaDeletar);
                    videoService.addVideo(videoParaEditar);
                    System.out.println("Informações do vídeo atualizadas com sucesso!");
                    break;


                case 5:
                //Excluir vídeo
                //Adicionar a opção de remover um vídeo do sistema.
                    String escolha = "";
                    System.out.println(" ");
                    System.out.println("========== 5. Excluir um vídeo ==============");
                    System.out.print("Digite o título do vídeo que você quer deletar: ");
                    String buscaVideoExclusao = scanner.nextLine();
                    System.out.println(" ");

                    List<Video> resultados = searchStrategy.search(videoService.listVideos(), buscaVideoExclusao);
                    System.out.println("Resultados da busca:");
                    System.out.println(" ");
                    if (resultados.isEmpty()) {
                        System.out.println(" -- Nenhum vídeo encontrado com o título especificado! -- ");
                    }

                    Video videoParaDeletar = resultados.get(0);
                    System.out.println(videoParaDeletar);

                    System.out.println(" ");
                    do {
                        try {
                            System.out.print("Deseja deletar o vídeo? (S/N):");

                            escolha = scanner.nextLine();
                            if (escolha.equalsIgnoreCase("s")) {
                                videoService.deleteVideo(videoParaDeletar);
                                System.out.println("Vídeo deletado com sucesso!");
                                break;
                            }


                        }catch (Exception e) {
                            System.out.println(" -- Por favor, S para Sim ou N para Não -- ");
                            break;
                        }


                    } while (escolha.isBlank());
                    break;




                case 6:
                //Filtrar vídeos por categoria
                //Listar apenas os vídeos de uma categoria específica
                    int escolhaFiltroGenero = 0;
                    System.out.println(" ");
                    System.out.println("========== 6. Filtrar vídeos por categoria ==============");
                    System.out.println(" ");
                    List<Video> videosFiltroGenero = videoService.listVideos();

                    do {

                        try{
                            System.out.println("Digite o número correspondente a categoria de vídeo que você quer listar: ");

                            System.out.println("1 - Aventura");
                            System.out.println("2 - Ação");
                            System.out.println("3 - Suspense");
                            System.out.println("4 - Comédia");
                            System.out.println("5 - Romance");
                            System.out.println("6 - Drama");

                            escolhaFiltroGenero = scanner.nextInt();

                        } catch (Exception e) {
                            break;
                        }

                        System.out.println(" ");

                        switch (escolhaFiltroGenero) {
                            case 1 :
                                int quantiaDeAventura = 0;
                                System.out.println("Listando vídeos de Aventura:");
                                System.out.println(" ");
                                for (Video video : videosFiltroGenero) {
                                    if (video.getCategoria().equalsIgnoreCase("Aventura")) {
                                        System.out.println("- " + video);
                                        quantiaDeAventura = quantiaDeAventura + 1;

                                    }
                                }

                                if (quantiaDeAventura == 0) {
                                    System.out.println("-- Nenhum vídeo de Aventura encontrado --");
                                }
                                break;

                            case 2 :
                                int quantiaDeAcao = 0;
                                System.out.println("Listando vídeos de Ação:");
                                System.out.println(" ");
                                for (Video video : videosFiltroGenero) {
                                    if (video.getCategoria().toLowerCase().equalsIgnoreCase("Ação")) {
                                        System.out.println("- " + video);
                                        quantiaDeAcao =  quantiaDeAcao + 1;

                                    }
                                }

                                if (quantiaDeAcao == 0) {
                                    System.out.println("-- Nenhum vídeo de Ação encontrado -- ");
                                }
                                break;

                            case 3 :
                                int quantiaDeSuspense = 0;
                                System.out.println("Listando vídeos de Suspense:");
                                System.out.println(" ");
                                for (Video video : videosFiltroGenero) {
                                    if (video.getCategoria().equalsIgnoreCase("Suspense")) {
                                        System.out.println("- " + video);
                                        quantiaDeSuspense = quantiaDeSuspense + 1;

                                    }
                                }

                                if (quantiaDeSuspense == 0) {
                                    System.out.println("-- Nenhum vídeo de Suspense encontrado -- ");
                                }
                                break;

                            case 4 :
                                int quantiaDeComedia = 0;
                                System.out.println("Listando vídeos de Comédia:");
                                System.out.println(" ");
                                for (Video video : videosFiltroGenero) {
                                    if (video.getCategoria().equalsIgnoreCase("Comédia")) {
                                        System.out.println("- " + video);
                                        quantiaDeComedia = quantiaDeComedia + 1;

                                    }
                                }

                                if (quantiaDeComedia == 0) {
                                    System.out.println("-- Nenhum vídeo de Comédia encontrado --");
                                }
                                break;

                            case 5:
                                int quantiaDeRomance = 0;
                                System.out.println("Listando vídeos de Romance:");
                                System.out.println(" ");
                                for (Video video : videosFiltroGenero) {
                                    if (video.getCategoria().equalsIgnoreCase("Romance")) {
                                        quantiaDeRomance = quantiaDeRomance + 1;
                                        System.out.println("- " + video);
                                    }
                                }

                                if (quantiaDeRomance == 0) {
                                    System.out.println("-- Nenhum vídeo de Romance encontrado --");
                                }
                                break;

                            case 6 :
                                int quantiaDeDrama = 0;
                                System.out.println("Listando vídeos de Drama:");
                                System.out.println(" ");
                                for (Video video : videosFiltroGenero) {
                                    if (video.getCategoria().equalsIgnoreCase("Drama")) {
                                        System.out.println("- " + video);
                                        quantiaDeDrama = quantiaDeDrama + 1;
                                    }
                                }

                                if (quantiaDeDrama == 0) {
                                    System.out.println("-- Nenhum vídeo de Drama encontrado --");
                                }

                                break;

                        }

                    } while (escolhaFiltroGenero < 1 || escolhaFiltroGenero > 6);
                    break;



                case 7:
                //Ordenar vídeos por data de publicação
                //Listar os vídeos em ordem cronológica
                    System.out.println(" ");
                    System.out.println("========== 7. Filtrar vídeos por data de publicação ==============");
                    System.out.println(" ");
                    List<Video> videosFiltroData = videoService.listVideos();

                    Collections.sort(videosFiltroData, new Comparator<Video>() {
                        @Override
                        public int compare(Video o1, Video o2) {
                            return o1.getDataPublicacao().compareTo(o2.getDataPublicacao());

                        }
                    });

                    for (Video video : videosFiltroData) {
                        System.out.println(video);
                    }
                    break;

                case 8:
                //Exibir relatório de estatísticas
                //Número total de vídeos.
                //Duração total de todos os vídeos.
                //Quantidade de vídeos por categoria.
                    System.out.println("========== 8. Relatório de Estatísticas ==============");
                    System.out.println(" ");
                    int quantiaDeVideosTotal = 0;
                    int duracaoTotalVideos = 0;
                    int totalAventura = 0;
                    int totalAcao = 0;
                    int totalSuspense = 0;
                    int totalComedia = 0;
                    int totalRomance = 0;
                    int totalDrama = 0;

                    List<Video> videosEstatistica = videoService.listVideos();

                    //Quantia de videos
                    for (Video video : videosEstatistica) {

                       quantiaDeVideosTotal = quantiaDeVideosTotal +1;

                    }
                    System.out.println("Quantia total de vídeos: " + quantiaDeVideosTotal);


                    //Duração total dos vídeos
                    for (Video video : videosEstatistica) {

                        duracaoTotalVideos = duracaoTotalVideos + video.getDuracao();

                    }
                    System.out.println("Duração total de todos os vídeos: " + duracaoTotalVideos + " minutos.");


                    //Quantidade de vídeos por categoria
                    //Aventura
                    for (Video video : videosEstatistica) {
                        if (video.getCategoria().equalsIgnoreCase("Aventura")) {
                            totalAventura = totalAventura + 1;
                        }
                    }
                    System.out.println("Aventura: " + totalAventura + " vídeos;");

                    //Ação
                    for (Video video : videosEstatistica) {
                        if (video.getCategoria().equalsIgnoreCase("Ação")) {
                            totalAcao = totalAcao + 1;
                        }
                    }
                    System.out.println("Ação: " + totalAcao + " vídeos;");

                    //Suspense
                    for (Video video : videosEstatistica) {
                        if (video.getCategoria().equalsIgnoreCase("Suspense")) {
                            totalSuspense = totalSuspense + 1;
                        }
                    }
                    System.out.println("Suspense: " + totalSuspense + " vídeos;");

                    //Comedia
                    for (Video video : videosEstatistica) {
                        if (video.getCategoria().equalsIgnoreCase("Comédia")) {
                            totalComedia = totalComedia + 1;
                        }
                    }
                    System.out.println("Comédia: " + totalComedia + " vídeos;");


                    //Romance
                    for (Video video : videosEstatistica) {
                        if (video.getCategoria().equalsIgnoreCase("Romance")) {
                            totalRomance = totalRomance + 1;
                        }
                    }
                    System.out.println("Romance: " + totalRomance + " vídeos;");

                    //Drama
                    for (Video video : videosEstatistica) {
                        if (video.getCategoria().equalsIgnoreCase("Drama")) {
                            totalDrama = totalDrama + 1;
                        }
                    }
                    System.out.println("Drama: " + totalDrama + " vídeos;");


                    if (videosEstatistica.isEmpty()) {
                        System.out.println(" -- Nenhum vídeo encontrado! -- ");
                    }
                    System.out.println(" ");
                    break;

                case 9:
                //Sair
                    System.out.println("Saindo do sistema...");
                    break;


            }

        }
    }
}