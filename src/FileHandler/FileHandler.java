package FileHandler;

import model.Video;
import service.VideoService;
import strategy.SearchStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FileHandler {

    public void adicionarVideo (Scanner scanner, VideoService videoService) {
        String titulo = "";
        String descricao = "";
        int duracao = 0;
        String categoria = "";
        String dataStr;
        int escolhaCategoria;
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
                    continue;

                }

                break;
            } catch (Exception e) {
                System.out.println(" --- Digite apenas números! --- ");
                System.out.println(" ");
                scanner.nextLine();

            }

        } while (true);

        scanner.nextLine();

        do {
            try {
                // Consumir a quebra de linha
                System.out.println("Selecione a categoria do vídeo: ");
                System.out.println("1 - Filme");
                System.out.println("2 - Série");
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

            try {
                System.out.print("Digite a data de publicação (dd/MM/yyyy): ");
                dataStr = scanner.nextLine();
                System.out.println(" ");

                if (dataStr.isBlank() ) {
                    System.out.println(" --- Por favor, escreva a data da publicação! --- ");
                    System.out.println(" ");
                    continue;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date dataPublicacao = sdf.parse(dataStr);
                Video video = new Video(titulo, descricao, duracao, categoria, dataPublicacao);
                videoService.addVideo(video);
                System.out.println("Vídeo adicionado com sucesso!");

                break;

            } catch (Exception e) {
                System.out.println(" -- Data com formato inválido! Tente novamente. -- ");
                System.out.println(" ");
                continue;
            }


        } while (true);
    }

    public void editarVideo(Scanner scanner, SearchStrategy searchStrategy, VideoService videoService) {


        String tituloEdicao;

        System.out.print("Digite o título para busca: ");
        tituloEdicao = scanner.nextLine();
        System.out.println(" ");


        List<Video> resultadosEdicao = searchStrategy.search(videoService.listVideos(), tituloEdicao);
        if (resultadosEdicao.isEmpty()) {
            System.out.println("Nenhum vídeo encontrado com o título especificado.");



        } else if (!resultadosEdicao.isEmpty()) {
            Video videoParaEditar = resultadosEdicao.get(0);
            Video videoAnteriorParaDeletar = resultadosEdicao.get(0);

            System.out.println("Vídeo encontrado:");
            System.out.println(videoParaEditar);
            System.out.println("------------------------------");


            // Editar campos

            String resposta;

            do {
                try {
                    System.out.print("Deseja alterar o título? (S/N): ");
                    resposta = scanner.nextLine().toUpperCase();

                    if (!resposta.equals("N") && !resposta.equals("S")) {
                        System.out.println(" -- Por favor, digite apenas 'S' para Sim ou 'N' para Não");
                        System.out.println(" ");
                        continue;
                    }

                    break;
                } catch (Exception e) {
                    System.out.println(" -- Por favor, tente novamente! --");
                    System.out.println(" ");
                }

            } while (true);


            if (resposta.equalsIgnoreCase("S")) {
                do {
                    try {
                        System.out.print("Novo título: ");
                        String novoTitulo = scanner.nextLine();

                        if (novoTitulo.isBlank()) {
                            System.out.println(" --- Por favor, escreva o título do vídeo! --- ");
                            System.out.println(" ");
                            continue;
                        }


                        videoParaEditar = new Video(novoTitulo, videoParaEditar.getDescricao(), videoParaEditar.getDuracao(), videoParaEditar.getCategoria(), videoParaEditar.getDataPublicacao());
                        break;

                    } catch (Exception e) {
                        System.out.println(" -- Título está em branco! Digite um título. -- ");
                        System.out.println(" ");
                        continue;
                    }
                } while (true);

            }


            do {
                try {
                    System.out.print("Deseja alterar a descrição? (S/N): ");
                    resposta = scanner.nextLine().toUpperCase();

                    if (!resposta.equals("N") && !resposta.equals("S")) {
                        System.out.println(" -- Por favor, digite apenas 'S' para Sim ou 'N' para Não");
                        System.out.println(" ");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println(" -- Por favor, tente novamente! -- ");
                }
            } while (true);

            if (resposta.equalsIgnoreCase("S")) {
                do {
                    try {
                        System.out.print("Nova descrição: ");
                        String novaDescricao = scanner.nextLine();

                        if (novaDescricao.isBlank()) {
                            System.out.println(" --- Por favor, escreva uma descrição para o vídeo! --- ");
                            System.out.println(" ");
                            continue;
                        }

                        videoParaEditar = new Video(videoParaEditar.getTitulo(), novaDescricao, videoParaEditar.getDuracao(), videoParaEditar.getCategoria(), videoParaEditar.getDataPublicacao());
                        break;
                    } catch (Exception e) {
                        System.out.println(" -- Por favor, tente novamente! --");
                        System.out.println(" ");
                        continue;
                    }

                } while (true);

            }

            do {
                try {
                    System.out.print("Deseja alterar a duração? (S/N): ");
                    resposta = scanner.nextLine().toUpperCase();

                    if (!resposta.equals("S") && !resposta.equals("N")) {
                        System.out.println(" --- Por favor, digite apenas 'S' para Sim ou 'N' para Não! --- ");
                        System.out.println(" ");
                        continue;
                    }
                    break;

                } catch (Exception e) {
                    System.out.println(" -- Ocorreu um erro! Tente novamente! -- ");
                }
            } while (true);


            if (resposta.equalsIgnoreCase("S")) {

                do {
                    try {

                        System.out.print("Nova duração (em minutos): ");
                        int novaDuracao = scanner.nextInt();

                        if (novaDuracao <= 0) {
                            System.out.println(" --- Por favor, digite um valor acima de 0! --- ");
                            System.out.println(" ");
                            continue; // Volta ao início do loop para pedir novamente
                        }

                        scanner.nextLine(); // Consumir quebra de linha
                        videoParaEditar = new Video(videoParaEditar.getTitulo(), videoParaEditar.getDescricao(), novaDuracao, videoParaEditar.getCategoria(), videoParaEditar.getDataPublicacao());
                        break;

                    } catch (Exception e) {
                        System.out.println(" -- Por favor, digite apenas números! -- ");
                    }
                } while (true);
            }

            do {
                try {
                    System.out.print("Deseja alterar a categoria? (S/N): ");
                    resposta = scanner.nextLine().toUpperCase();

                    if (!resposta.equals("S") && !resposta.equals("N")) {
                        System.out.println(" --- Por favor, digite apenas 'S' para Sim ou 'N' para Não! --- ");
                        System.out.println(" ");
                        continue;
                    }
                    break;

                } catch (Exception e) {
                    System.out.println(" -- Ocorreu um erro, tente novamente! -- ");
                }
            } while (true);


            if (resposta.equalsIgnoreCase("S")) {

                int novaCategoriaEscolha;
                do {
                    try {
                        System.out.println("Selecione a nova categoria:");
                        System.out.println("1 - Filme");
                        System.out.println("2 - Série");
                        System.out.println("3 - Documentário");
                        System.out.println("4 - Curta-metragem");
                        System.out.println("5 - Desenho");
                        System.out.println("6 - Anime");

                        novaCategoriaEscolha = scanner.nextInt();

                        if (novaCategoriaEscolha > 6 || novaCategoriaEscolha < 1) {
                            System.out.println(" -- Por favor, apenas números de 1 a 6! -- ");
                            continue;
                        }


                        break;
                    } catch (Exception e) {
                        System.out.println(" -- Por favor, digite apenas números! -- ");
                        System.out.println(" ");
                        scanner.nextLine();
                    }

                } while (true);


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


            do {
                try {
                    System.out.print("Deseja alterar a data de publicação? (S/N): ");
                    resposta = scanner.nextLine().toUpperCase();

                    if (!resposta.equals("N") && !resposta.equals("S")) {
                        System.out.println(" ");
                        System.out.println(" -- Por favor, digite apenas 'S' para Sim ou 'N' para Não");
                        System.out.println(" ");
                        continue;
                    }
                    break;


                } catch (Exception e) {
                    System.out.println(" -- Houve um erro! Tente novamente! -- ");
                    System.out.println(" ");

                }
            }while (true);


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


    }

    public void excluirVideo (Scanner scanner, SearchStrategy searchStrategy, VideoService videoService, String buscaVideoExclusao) {
        String escolha = "";
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
}
