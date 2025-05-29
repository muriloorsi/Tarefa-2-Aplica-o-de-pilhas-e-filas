public class PilhaReimpressao {
    private Documento[] documentos;
    private int topo;
    private final int capacidadeMax;

    public PilhaReimpressao(int capacidade) {
        this.capacidadeMax = capacidade;
        this.documentos = new Documento[capacidade];
        this.topo = -1;
    }

    public boolean estaVazia() {
        return topo == -1;
    }

    public boolean estaCheia() {
        return topo == capacidadeMax - 1;
    }

    public void empilhar(Documento doc) {
        if (estaCheia()) {
            throw new RuntimeException("Não é permitido mais de " + capacidadeMax + " documentos na pilha de reimpressão");
        }
        documentos[++topo] = doc;
    }

    public Documento desempilhar() {
        if (estaVazia()) {
            throw new RuntimeException("Pilha de reimpressão vazia");
        }
        return documentos[topo--];
    }

    public void consultar(String nomeArquivo) {
        for (int i = topo; i >= 0; i--) {
            if (documentos[i].getNomeArquivo().equals(nomeArquivo)) {
                System.out.println(nomeArquivo + " está na posição " + (topo - i + 1) + " (a partir do topo)");
                return;
            }
        }
        System.out.println(nomeArquivo + " não está na pilha de reimpressão");
    }

    public void relatorio() {
        System.out.println("--- Pilha de Reimpressão (ocupação: " + (topo + 1) + "/" + capacidadeMax + ") ---");
        if (estaVazia()) {
            System.out.println("Pilha vazia");
        } else {
            for (int i = topo; i >= 0; i--) {
                System.out.println((topo - i + 1) + ". " + documentos[i]);
            }
        }
    }
}