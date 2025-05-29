import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static PilhaReimpressao pilhaReimpressao = new PilhaReimpressao(5);

    public static void main(String[] args) {
        FilaImpressao fila = new FilaImpressao(10);
        Scanner scanner = new Scanner(System.in);

        try (BufferedReader br = new BufferedReader(new FileReader("Entrada.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;
                String nome = parts[0].trim();
                String usuario = parts[1].trim();
                LocalDateTime horario = Documento.parseHora(parts[2].trim());
                Documento doc = new Documento(nome, usuario, horario);
                fila.enfileirar(doc);
                System.out.println("Adicionado: " + doc);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }

        String comando;
        do {
            System.out.println("\nComando: [imprimir|relatorio|consultar <nomeArquivo>|adicionar <nomeArquivo>,<usuario>|solicitar <nomeArquivo>,<usuario>|reimprimir|consultarReimpressao <nomeArquivo>|relatorioReimpressao|sair]");
            comando = scanner.nextLine().trim();
            if (comando.isEmpty()) continue;
            String[] cmd = comando.split(" ", 2);
            switch (cmd[0]) {
                case "imprimir":
                    fila.imprimir();
                    break;
                case "consultar":
                    if (cmd.length < 2 || cmd[1].isEmpty()) {
                        System.out.println("Uso: consultar <nomeArquivo>");
                    } else {
                        fila.consultar(cmd[1].trim());
                    }
                    break;
                case "relatorio":
                    fila.relatorio();
                    break;
                case "adicionar":
                    if (cmd.length < 2 || !cmd[1].contains(",")) {
                        System.out.println("Uso: adicionar <nomeArquivo>,<usuario>");
                    } else {
                        String[] dados = cmd[1].split(",");
                        if (dados.length < 2) {
                            System.out.println("Uso: adicionar <nomeArquivo>,<usuario>");
                        } else {
                            fila.adicionar(dados[0].trim(), dados[1].trim());
                        }
                    }
                    break;
                case "solicitar":
                    if (cmd.length < 2 || !cmd[1].contains(",")) {
                        System.out.println("Uso: solicitar <nomeArquivo>,<usuario>");
                    } else {
                        String[] dados = cmd[1].split(",");
                        if (dados.length < 2) {
                            System.out.println("Uso: solicitar <nomeArquivo>,<usuario>");
                        } else {
                            try {
                                Documento doc = new Documento(dados[0].trim(), dados[1].trim(), LocalDateTime.now());
                                pilhaReimpressao.empilhar(doc);
                                System.out.println("Solicitação de reimpressão adicionada: " + doc);
                            } catch (RuntimeException e) {
                                System.out.println("Erro: " + e.getMessage());
                            }
                        }
                    }
                    break;
                case "reimprimir":
                    try {
                        Documento doc = pilhaReimpressao.desempilhar();
                        doc.registrarImpressao();
                        System.out.println("Reimpresso: " + doc.getNomeArquivo() + ", Tempo de espera = " + doc.calcularTempoEsperaSegundos() + "s");
                    } catch (RuntimeException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case "consultarReimpressao":
                    if (cmd.length < 2 || cmd[1].isEmpty()) {
                        System.out.println("Uso: consultarReimpressao <nomeArquivo>");
                    } else {
                        pilhaReimpressao.consultar(cmd[1].trim());
                    }
                    break;
                case "relatorioReimpressao":
                    pilhaReimpressao.relatorio();
                    break;
                case "sair":
                    break;
                default:
                    System.out.println("Comando inválido. Tente novamente.");
            }
        } while (!comando.equals("sair"));

        scanner.close();
    }
}