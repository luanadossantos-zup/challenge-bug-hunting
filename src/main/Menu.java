package main;

import model.Video;
import service.VideoService;
import strategy.SearchStrategy;

import java.text.SimpleDateFormat;
import java.util.*;

public class Menu {

    public int menuInicialDeEscolha(Scanner scanner) {
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

        return  opcao;
    }

    public void adicionarVideo (Scanner scanner, VideoService videoService) {
        String titulo = "";
        String descricao = "";
        int duracao = 0;
        String categoria = "";
        String dataStr;
        int escolhaCategoria;
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
                System.out.println("1 - Filme");
                System.out.println("2 - Serie");
                System.out.println("3 - Documentário");
                System.out.println("4 - Curta-metragem");
                System.out.println("5 - Desenho");
                System.out.println("6 - Anime");


                System.out.println(" ");
                escolhaCategoria = scanner.nextInt();


            } catch (Exception e) {
                System.out.println(" -- Digite um número de 1 a 5! --");
                break;
            }

            categoria = switch (escolhaCategoria) {
                case 1 -> "Filme";
                case 2 -> "Série";
                case 3 -> "Documentário";
                case 4 -> "Curta-metragem";
                case 5 -> "Desenho";
                case 6 -> "Anime";
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

    }

    public void listarVideos(VideoService videoService) {
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
    }

    public void pesquisarVideoPorTitulo (Scanner scanner, SearchStrategy searchStrategy, VideoService videoService) {
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

        }
        for (Video video : resultadosBuscaVideo) {
            System.out.println(video);
            System.out.println("------------------------------");
        }
        System.out.println(" ");
    }

    public void editarVideo(Scanner scanner, SearchStrategy searchStrategy, VideoService videoService) {
        System.out.println(" ");
        System.out.println("========== 4. Editar informações de vídeo =============");
        System.out.print("Digite o título para busca: ");
        String tituloEdicao = scanner.nextLine();
        System.out.println(" ");

        List<Video> resultadosEdicao = searchStrategy.search(videoService.listVideos(), tituloEdicao);
        if (resultadosEdicao.isEmpty()) {
            System.out.println("Nenhum vídeo encontrado com o título especificado.");

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
            System.out.println("1 - Filme");
            System.out.println("2 - Série");
            System.out.println("3 - Documentário");
            System.out.println("4 - Curta-metragem");
            System.out.println("5 - Desenho");
            System.out.println("6 - Anime");

            int novaCategoriaEscolha = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha
            String novaCategoria = switch (novaCategoriaEscolha) {
                case 1 -> "Filme";
                case 2 -> "Série";
                case 3 -> "Documentário";
                case 4 -> "Curta-metragem";
                case 5 -> "Desenho";
                case 6 -> "Anime";
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
    }

    public void excluirVideo(Scanner scanner, SearchStrategy searchStrategy, VideoService videoService) {
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
    }

    public void filtrarVideosPorCategoria (Scanner scanner, VideoService videoService) {
        int escolhaFiltroCategoria = 0;
        System.out.println(" ");
        System.out.println("========== 6. Filtrar vídeos por categoria ==============");
        System.out.println(" ");
        List<Video> videosFiltroCategoria = videoService.listVideos();

        do {

            try{
                System.out.println("Digite o número correspondente a categoria de vídeo que você quer listar: ");

                System.out.println("1 - Filme");
                System.out.println("2 - Serie");
                System.out.println("3 - Documentario");
                System.out.println("4 - Curta-metragem");
                System.out.println("5 - Desenho");
                System.out.println("6 - Anime");

                escolhaFiltroCategoria = scanner.nextInt();

            } catch (Exception e) {
                break;
            }

            System.out.println(" ");

            switch (escolhaFiltroCategoria) {
                case 1 :
                    int quantiaDeFilme = 0;
                    System.out.println("Listando vídeos de Filme:");
                    System.out.println(" ");
                    for (Video video : videosFiltroCategoria) {
                        if (video.getCategoria().equalsIgnoreCase("Filme")) {
                            System.out.println("- " + video);
                            quantiaDeFilme = quantiaDeFilme + 1;

                        }
                    }

                    if (quantiaDeFilme == 0) {
                        System.out.println("-- Nenhum vídeo de Filme encontrado --");
                    }
                    break;

                case 2 :
                    int quantiaDeSerie = 0;
                    System.out.println("Listando vídeos de Serie:");
                    System.out.println(" ");
                    for (Video video : videosFiltroCategoria) {
                        if (video.getCategoria().toLowerCase().equalsIgnoreCase("Serie")) {
                            System.out.println("- " + video);
                            quantiaDeSerie =  quantiaDeSerie + 1;

                        }
                    }

                    if (quantiaDeSerie == 0) {
                        System.out.println("-- Nenhum vídeo de Serie encontrado -- ");
                    }
                    break;

                case 3 :
                    int quantiaDeDocumentario = 0;
                    System.out.println("Listando vídeos de Documentario:");
                    System.out.println(" ");
                    for (Video video : videosFiltroCategoria) {
                        if (video.getCategoria().equalsIgnoreCase("Documentario")) {
                            System.out.println("- " + video);
                            quantiaDeDocumentario = quantiaDeDocumentario + 1;

                        }
                    }

                    if (quantiaDeDocumentario == 0) {
                        System.out.println("-- Nenhum vídeo de Documentario encontrado -- ");
                    }
                    break;

                case 4 :
                    int quantiaDeCurta = 0;
                    System.out.println("Listando vídeos de Curta-metragem:");
                    System.out.println(" ");
                    for (Video video : videosFiltroCategoria) {
                        if (video.getCategoria().equalsIgnoreCase("Curta-metragem")) {
                            System.out.println("- " + video);
                            quantiaDeCurta = quantiaDeCurta + 1;

                        }
                    }

                    if (quantiaDeCurta == 0) {
                        System.out.println("-- Nenhum vídeo de Curta-metragem encontrado --");
                    }
                    break;

                case 5:
                    int quantiaDeDesenho = 0;
                    System.out.println("Listando vídeos de Desenho:");
                    System.out.println(" ");
                    for (Video video : videosFiltroCategoria) {
                        if (video.getCategoria().equalsIgnoreCase("Desenho")) {
                            quantiaDeDesenho = quantiaDeDesenho + 1;
                            System.out.println("- " + video);
                        }
                    }

                    if (quantiaDeDesenho == 0) {
                        System.out.println("-- Nenhum vídeo de Desenho encontrado --");
                    }
                    break;

                case 6 :
                    int quantiaDeAnime = 0;
                    System.out.println("Listando vídeos de Anime:");
                    System.out.println(" ");
                    for (Video video : videosFiltroCategoria) {
                        if (video.getCategoria().equalsIgnoreCase("Anime")) {
                            System.out.println("- " + video);
                            quantiaDeAnime = quantiaDeAnime + 1;
                        }
                    }

                    if (quantiaDeAnime == 0) {
                        System.out.println("-- Nenhum vídeo de Anime encontrado --");
                    }

                    break;

            }

        } while (escolhaFiltroCategoria < 1 || escolhaFiltroCategoria > 6);
    }

    public void ordenarVideosPorDataDePublicacao (VideoService videoService) {
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
    }

    public void relatorioDeEstatisticas (VideoService videoService) {
        System.out.println(" ");
        System.out.println("========== 8. Relatório de Estatísticas ==============");
        System.out.println(" ");
        int quantiaDeVideosTotal = 0;
        int duracaoTotalVideos = 0;
        int totalFilme = 0;
        int totalSerie = 0;
        int totalDocumentario = 0;
        int totalCurtametragem = 0;
        int totalDesenho = 0;
        int totalAnime = 0;

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
        //Filme
        for (Video video : videosEstatistica) {
            if (video.getCategoria().equalsIgnoreCase("Filme")) {
                totalFilme = totalFilme + 1;
            }
        }
        System.out.println("Filme: " + totalFilme + " vídeo(s);");

        //Série
        for (Video video : videosEstatistica) {
            if (video.getCategoria().equalsIgnoreCase("Série")) {
                totalSerie = totalSerie + 1;
            }
        }
        System.out.println("Série: " + totalSerie + " vídeo(s);");

        //Documentário
        for (Video video : videosEstatistica) {
            if (video.getCategoria().equalsIgnoreCase("Documentário")) {
                totalDocumentario = totalDocumentario + 1;
            }
        }
        System.out.println("Documentário: " + totalDocumentario + " vídeo(s);");

        //Curta-metragem
        for (Video video : videosEstatistica) {
            if (video.getCategoria().equalsIgnoreCase("Curta-metragem")) {
                totalCurtametragem = totalCurtametragem + 1;
            }
        }
        System.out.println("Curta-metragem: " + totalCurtametragem + " vídeo(s);");


        //Desenho
        for (Video video : videosEstatistica) {
            if (video.getCategoria().equalsIgnoreCase("Desenho")) {
                totalDesenho = totalDesenho + 1;
            }
        }
        System.out.println("Desenho: " + totalDesenho + " vídeo(s);");

        //Anime
        for (Video video : videosEstatistica) {
            if (video.getCategoria().equalsIgnoreCase("Anime")) {
                totalAnime = totalAnime + 1;
            }
        }
        System.out.println("Anime: " + totalAnime + " vídeo(s);");


        if (videosEstatistica.isEmpty()) {
            System.out.println(" -- Nenhum vídeo encontrado! -- ");
        }
        System.out.println(" ");
    }
}
