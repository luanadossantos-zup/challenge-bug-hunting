package main;

import model.Video;
import repository.FileVideoRepository;
import service.VideoService;
import service.VideoServiceImpl;
import strategy.SearchStrategy;
import strategy.TitleSearchStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String titulo = "";
        String descricao = "";
        int duracao = 0;
        String categoria = "";
        String dataStr;
        Scanner scanner = new Scanner(System.in);
        VideoService videoService = new VideoServiceImpl(new FileVideoRepository("videos.txt"));
        SearchStrategy searchStrategy = new TitleSearchStrategy();

        while (true) {
            System.out.println("===========================================");
            System.out.println("\n=== Sistema de Gerenciamento de Vídeos ===");
            System.out.println("===========================================");
            System.out.println("1. Adicionar vídeo");
            System.out.println("2. Listar vídeos");
            System.out.println("3. Pesquisar vídeo por título");
            System.out.println("4. Editar vídeo");
            System.out.println("5. Excluir vídeo");
            System.out.println("6. Filtrar vídeo por categoria");
            System.out.println("7. Ordenar vídeo por data de publicação");
            System.out.println("8. Exibir relatório de estatísticas");
            System.out.println("9. Sair");
            System.out.println("===========================================");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            System.out.println("===========================================");

            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {

                case 1:
                //Adicionar video
                    System.out.println(" ");
                    System.out.println("========== 1. Adicionar vídeo ==============");
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
                            System.out.print("Digite a categoria do vídeo: ");
                            categoria = scanner.nextLine();
                            System.out.println(" ");


                            if (categoria.isBlank()) {
                                System.out.println(" --- Por favor, escreva o nome da categoria! --- ");
                                System.out.println(" ");
                                continue;
                            }
                            if (categoria.charAt(0) >= '0' && categoria.charAt(0) <= '9') {
                                System.out.println(" --- Por favor, digite um nome válido, não números! ---");
                                System.out.println(" ");
                                continue;
                            }

                        } catch (Exception e) {
                            break;
                        }
                    } while (categoria.isBlank() || categoria.charAt(0) >= '0' && categoria.charAt(0) <= '9');


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
                    System.out.println("========== 2. Listar vídeos ==============");
                    List<Video> videos = videoService.listVideos();
                    for (Video video : videos) {
                        System.out.println(video);
                    }
                    break;


                case 3:
                //Pesquisar vídeo por título
                    System.out.print("Digite o título para busca: ");
                    String query = scanner.nextLine();
                    List<Video> resultados = searchStrategy.search(videoService.listVideos(), query);
                    for (Video video : resultados) {
                        System.out.println(video);
                    }
                    break;

                case 4:
                //Editar vídeo
                // Permitir que o usuário edite as informações de um vídeo existente.


                case 5:
                //Excluir vídeo
                //Adicionar a opção de remover um vídeo do sistema.


                case 6:
                //Filtrar vídeos por categoria
                //Listar apenas os vídeos de uma categoria específica


                case 7:
                //Ordenar vídeos por data de publicação
                //Listar os vídeos em ordem cronológica


                case 8:
                //Exibir relatório de estatísticas
                //Número total de vídeos.
                //Duração total de todos os vídeos.
                //Quantidade de vídeos por categoria.

                case 9:
                //Sair
                    System.out.println("Saindo do sistema...");
                    break;


            }

        }
    }
}