import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class Documento {
    private String nomeArquivo;
    private String nomeUsuario;
    private LocalDateTime horaSolicitacao;
    private LocalDateTime horaImpressao;

    public Documento(String nomeArquivo, String nomeUsuario, LocalDateTime horaSolicitacao) {
        this.nomeArquivo = nomeArquivo;
        this.nomeUsuario = nomeUsuario;
        this.horaSolicitacao = horaSolicitacao;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public LocalDateTime getHoraSolicitacao() {
        return horaSolicitacao;
    }

    public void registrarImpressao() {
        this.horaImpressao = LocalDateTime.now();
    }

    public long calcularTempoEsperaSegundos() {
        if (horaImpressao == null) {
            throw new IllegalStateException("Documento ainda não foi impresso");
        }
        Duration duracao = Duration.between(horaSolicitacao, horaImpressao);
        return duracao.getSeconds();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s (Usuário: %s, Solicitado em: %s)",
                nomeArquivo, nomeUsuario, horaSolicitacao.format(formatter));
    }

    public static LocalDateTime parseHora(String hora) {
        return LocalDateTime.parse(hora, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}