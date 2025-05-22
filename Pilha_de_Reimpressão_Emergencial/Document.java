import java.time.LocalDateTime;

public class Document {
    private String fileName;
    private String userName;
    private LocalDateTime requestTime;

    public Document(String fileName, String userName, LocalDateTime requestTime) {
        this.fileName = fileName;
        this.userName = userName;
        this.requestTime = requestTime;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }
}
