import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class FilaImpressao {
    private Documento[] dados;
    private int primeiro, ultimo, ocupacao;
    private final int capacidadeMax;
    private final List<String> extensoesValidas = Arrays.asList(".pdf", ".docx", ".jpg");

    public FilaImpressao(int capacidade) {
        this.capacidadeMax = capacidade;
        this.dados = new Documento[capacidade];
        this.primeiro = this.ultimo = this.ocupacao = 0;
    }

    public FilaImpressao() {
        this(10);
    }

    private int proxima(int pos) {
        return (pos + 1) % dados.length;
    }

    public boolean estaVazia() {
        return ocupacao == 0;
    }

    public boolean estaCheia() {
        return ocupacao == capacidadeMax;
    }

    public void enfileirar(Documento doc) {
        if (estaCheia()) {
            throw new RuntimeException("Não é permitido mais de 10 documentos na fila");
        }
        dados[ultimo] = doc;
        ultimo = proxima(ultimo);
        ocupacao++;
    }

    public void imprimir() {
        if (estaVazia()) {
            System.out.println("Não a mais documentos para imprimir");
            return;
        }
        Documento doc = dados[primeiro];
        dados[primeiro] = null;
        primeiro = proxima(primeiro);
        ocupacao--;
        doc.registrarImpressao();
        System.out.println("Impresso: " + doc.getNomeArquivo() + ", Tempo de espera = " + doc.calcularTempoEsperaSegundos() + "s");
    }

    public void consultar(String nomeArquivo) {
        for (int i = 0, idx = primeiro; i < ocupacao; i++, idx = proxima(idx)) {
            if (dados[idx].getNomeArquivo().equals(nomeArquivo)) {
                System.out.println(nomeArquivo + " está na posição " + (i + 1));
                return;
            }
        }
        System.out.println(nomeArquivo + " este documento não está na fila");
    }

    public void relatorio() {
        System.out.println("--- Fila de Impressão (ocupação: " + ocupacao + "/" + capacidadeMax + ") ---");
        if (estaVazia()) {
            System.out.println("Não a mais documentos para imprimir");
        } else {
            for (int i = 0, idx = primeiro; i < ocupacao; i++, idx = proxima(idx)) {
                System.out.println((i+1) + ". " + dados[idx]);
            }
        }
    }

    public void adicionar(String nomeArquivo, String nomeUsuario) {
        if (estaCheia()) {
            System.out.println("Erro de pedido: fila cheia");
            return;
        }

        boolean valido = extensoesValidas.stream().anyMatch(nomeArquivo::endsWith);
        if (!valido) {
            System.out.println("Erro: tipo de arquivo inválido. Permitidos: " + extensoesValidas);
            return;
        }

        Documento novo = new Documento(nomeArquivo, nomeUsuario, LocalDateTime.now());
        enfileirar(novo);
        System.out.println("Documento adicionado: " + novo);
    }
}