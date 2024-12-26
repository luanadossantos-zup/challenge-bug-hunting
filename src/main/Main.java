package main;

import model.Video;
import repository.FileVideoRepository;
import service.VideoService;
import service.VideoServiceImpl;
import strategy.SearchStrategy;
import strategy.TitleSearchStrategy;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        VideoService videoService = new VideoServiceImpl(new FileVideoRepository("videos.txt"));
        SearchStrategy searchStrategy = new TitleSearchStrategy();
        Menu menu = new Menu();

        while (true) {
            int opcao = menu.menuInicialDeEscolha(scanner);

            switch (opcao) {

                case 1:
                //Adicionar video
                    menu.adicionarVideo(scanner, videoService);

                break;

                case 2:
                //Listar vídeos
                    menu.listarVideos(videoService);
                    break;

                case 3:
                //Pesquisar vídeo por título
                    menu.pesquisarVideoPorTitulo(scanner, searchStrategy, videoService);
                    break;

                case 4:
                //Editar vídeo
                // Permitir que o usuário edite as informações de um vídeo existente.
                    menu.editarVideo(scanner,searchStrategy,videoService);
                    break;

                case 5:
                //Excluir vídeo
                //Adicionar a opção de remover um vídeo do sistema.
                    menu.excluirVideo(scanner,searchStrategy,videoService);
                    break;

                case 6:
                //Filtrar vídeos por categoria
                //Listar apenas os vídeos de uma categoria específica
                    menu.filtrarVideosPorCategoria(scanner,videoService);
                    break;

                case 7:
                //Ordenar vídeos por data de publicação
                //Listar os vídeos em ordem cronológica
                    menu.ordenarVideosPorDataDePublicacao(videoService);
                    break;

                case 8:
                //Exibir relatório de estatísticas
                //Número total de vídeos.
                //Duração total de todos os vídeos.
                //Quantidade de vídeos por categoria.
                    menu.relatorioDeEstatisticas(videoService);
                    break;

                case 9:
                //Sair
                    System.out.println("Saindo do sistema...");
                    break;

            }

        }
    }
}