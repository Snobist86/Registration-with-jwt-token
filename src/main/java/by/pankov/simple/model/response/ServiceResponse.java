package by.pankov.simple.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceResponse {

    private String message;
    private Object content;
    private LocalDateTime localDateTime;

    public ServiceResponse(String message, Object content) {
        this(message, content, LocalDateTime.now());
    }


    public ServiceResponse(String message, Object content, LocalDateTime localDateTime) {
        super();
        this.message = message;
        this.content = content;
        this.localDateTime = localDateTime;
    }
}
