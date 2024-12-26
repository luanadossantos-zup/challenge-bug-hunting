package VideoManager;

import model.Video;
import service.VideoService;
import strategy.SearchStrategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class VideoManager {


    public void listarVideos(VideoService videoService) {
        List<Video> videos = videoService.listVideos();
        for (Video video : videos) {
            System.out.println("- " + video);
        }
        if (videos.isEmpty()) {
            System.out.println(" -- Nenhum vídeo encontrado! -- ");
        }
        System.out.println(" ");
    }

    public void pesquisarVideoPorTitulo(SearchStrategy searchStrategy, VideoService videoService, String query) {
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

    public void filtrarVideoPorCategoria(Scanner scanner, VideoService videoService) {
        int escolhaFiltroCategoria = 0;
        List<Video> videosFiltroCategoria = videoService.listVideos();

        do {

            try{
                System.out.println("Digite o número correspondente a categoria de vídeo que você quer listar: ");

                System.out.println("1 - Filme");
                System.out.println("2 - Série");
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
                    System.out.println("Listando vídeos de Série:");
                    System.out.println(" ");
                    for (Video video : videosFiltroCategoria) {
                        if (video.getCategoria().toLowerCase().equalsIgnoreCase("Série")) {
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
                    System.out.println("Listando vídeos de Documentário:");
                    System.out.println(" ");
                    for (Video video : videosFiltroCategoria) {
                        if (video.getCategoria().equalsIgnoreCase("Documentário")) {
                            System.out.println("- " + video);
                            quantiaDeDocumentario = quantiaDeDocumentario + 1;

                        }
                    }

                    if (quantiaDeDocumentario == 0) {
                        System.out.println("-- Nenhum vídeo de Documentário encontrado -- ");
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

    public void ordenarVideoPorDataDePublicacao(VideoService videoService) {
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

    public void relatorioEstatisticas (VideoService videoService) {
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
