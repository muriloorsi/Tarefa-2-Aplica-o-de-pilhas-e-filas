import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {

        ReprintStack pilha = new ReprintStack(5);

        String inputFile = "Entrada.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                String op = parts[0].toUpperCase();

                switch (op) {
                    case "ADD":
                        if (parts.length >= 3) {
                            pilha.requestReprint(parts[1], parts[2]);
                        } else {
                            System.err.println("[ERRO] Sintaxe inválida para ADD.");
                        }
                        break;
                    case "REPRINT":
                        pilha.executeReprint();
                        break;
                    case "QUERY":
                        if (parts.length >= 2) {
                            pilha.queryDocument(parts[1]);
                        } else {
                            System.err.println("[ERRO] Sintaxe inválida para QUERY.");
                        }
                        break;
                    case "STATUS":
                        pilha.reportStatus();
                        break;
                    default:
                        System.err.println("[ERRO] Operação desconhecida: " + op);
                }
            }
        } catch (Exception e) {
            System.err.println("[FALHA] Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
