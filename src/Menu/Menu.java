package Menu;

import FileHandler.FileHandler;
import VideoManager.VideoManager;
import service.VideoService;
import strategy.SearchStrategy;
import java.util.*;

public class Menu {

    VideoManager videoManager = new VideoManager();
    FileHandler fileHandler = new FileHandler();

    public int menuInicialDeEscolha(Scanner scanner) {
        System.out.println(" ");
        System.out.println("================================================================================");
        System.out.println("===================== Sistema de Gerenciamento de Vídeos =======================");
        System.out.println("================================================================================");
        System.out.println("1. Adicionar vídeo");
        System.out.println("2. Listar vídeos");
        System.out.println("3. Pesquisar vídeo por título");
        System.out.println("4. Editar vídeo");
        System.out.println("5. Excluir vídeo");
        System.out.println("6. Filtrar vídeo por categoria");
        System.out.println("7. Ordenar vídeo por data de publicação");
        System.out.println("8. Exibir relatório de estatísticas");
        System.out.println("9. Sair");
        System.out.println("================================================================================");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        System.out.println("================================================================================");

        scanner.nextLine(); // Consumir a quebra de linha

        return  opcao;
    }

    public void adicionarVideo (Scanner scanner, VideoService videoService) {

        System.out.println(" ");
        System.out.println("============= 1. Adicionar vídeo ===============");

        fileHandler.adicionarVideo(scanner, videoService);

    }

    public void listarVideos(VideoService videoService) {
        System.out.println(" ");
        System.out.println("============================ 2. Listar vídeos ==================================");
        System.out.println(" ");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("--- Título --- Descrição --- Duração (minutos) --- Categoria --- Data publicação");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(" ");
        videoManager.listarVideos(videoService);
    }

    public void pesquisarVideoPorTitulo (Scanner scanner, SearchStrategy searchStrategy, VideoService videoService) {
        System.out.println(" ");
        System.out.println("====================== 3. Pesquisar vídeo por título ===========================");
        System.out.print("Digite o título para busca: ");
        String query = scanner.nextLine();
        System.out.println(" ");
        System.out.println("Resultados da busca:");
        System.out.println(" ");

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("--- Título --- Descrição --- Duração (minutos) --- Categoria --- Data publicação");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(" ");

        videoManager.pesquisarVideoPorTitulo(searchStrategy,videoService, query);
    }

    public void editarVideo(Scanner scanner, SearchStrategy searchStrategy, VideoService videoService) {
        System.out.println(" ");
        System.out.println("===================== 4. Editar informações de vídeo ==========================");



        fileHandler.editarVideo(scanner,searchStrategy,videoService);
    }

    public void excluirVideo(Scanner scanner, SearchStrategy searchStrategy, VideoService videoService) {
        System.out.println(" ");
        System.out.println("========================== 5. Excluir um vídeo =================================");
        System.out.print("Digite o título do vídeo que você quer deletar: ");
        String buscaVideoExclusao = scanner.nextLine();
        System.out.println(" ");

        fileHandler.excluirVideo(scanner, searchStrategy, videoService, buscaVideoExclusao);


    }

    public void filtrarVideosPorCategoria (Scanner scanner, VideoService videoService) {

        System.out.println(" ");
        System.out.println("===================== 6. Filtrar vídeos por categoria =========================");
        System.out.println(" ");

        videoManager.filtrarVideoPorCategoria(scanner, videoService);
    }

    public void ordenarVideosPorDataDePublicacao (VideoService videoService) {
        System.out.println(" ");
        System.out.println("=================== 7. Filtrar vídeos por data de publicação ===================");
        System.out.println(" ");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("--- Título --- Descrição --- Duração (minutos) --- Categoria --- Data publicação");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(" ");

        videoManager.ordenarVideoPorDataDePublicacao(videoService);
    }

    public void relatorioDeEstatisticas (VideoService videoService) {
        System.out.println(" ");
        System.out.println("===================== 8. Relatório de Estatísticas ===========================");
        System.out.println(" ");

        videoManager.relatorioEstatisticas(videoService);
    }
}
