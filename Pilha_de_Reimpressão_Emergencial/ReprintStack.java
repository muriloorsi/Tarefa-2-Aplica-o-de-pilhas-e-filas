import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Stack;

public class ReprintStack {
    private Stack<Document> stack;
    private int capacity;

    public ReprintStack(int capacity) {
        this.capacity = capacity;
        this.stack = new Stack<>();
    }

    public boolean requestReprint(String fileName, String userName) {
        if (stack.size() >= capacity) {
            System.err.println("[ERRO] Capacidade máxima da pilha atingida. Solicitação negada para: " + fileName);
            return false;
        }
        Document doc = new Document(fileName, userName, LocalDateTime.now());
        stack.push(doc);
        System.out.println("[INFO] Documento adicionado à pilha: " + fileName + " (Usuário: " + userName + ")");
        return true;
    }

    public void executeReprint() {
        if (stack.isEmpty()) {
            System.err.println("[ERRO] Nenhum documento para reimprimir.");
            return;
        }
        Document doc = stack.pop();
        LocalDateTime reprintTime = LocalDateTime.now();
        Duration elapsed = Duration.between(doc.getRequestTime(), reprintTime);
        System.out.println("\n[REIMPRESSÃO] Documento: " + doc.getFileName());
        System.out.println("Usuário: " + doc.getUserName());
        System.out.println("Tempo desde solicitação: " + elapsed.toSeconds() + " segundos.\n");
    }

    public void queryDocument(String fileName) {
        int posFromTop = -1;
        for (int i = stack.size() - 1; i >= 0; i--) {
            if (stack.get(i).getFileName().equals(fileName)) {
                posFromTop = stack.size() - i;
                Document doc = stack.get(i);
                System.out.println("\n[CONSULTA] Documento encontrado: " + fileName);
                System.out.println("Posição a partir do topo: " + posFromTop);
                System.out.println("Usuário: " + doc.getUserName());
                System.out.println("Horário da solicitação: " + doc.getRequestTime().withNano(0) + "\n");
                return;
            }
        }
        System.out.println("[CONSULTA] Documento '" + fileName + "' não está na pilha.\n");
    }

    public void reportStatus() {
        System.out.println("\n--- ESTADO ATUAL DA PILHA (do topo à base) ---");
        if (stack.isEmpty()) {
            System.out.println("[INFO] A pilha está vazia.\n");
            return;
        }
        int pos = 1;
        for (int i = stack.size() - 1; i >= 0; i--) {
            Document doc = stack.get(i);
            System.out.println(pos++ + ": " + doc.getFileName() + " (Usuário: " + doc.getUserName() + ") - Solicitado em " + doc.getRequestTime().withNano(0));
        }
        System.out.println();
    }
}