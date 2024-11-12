package fs19.java.backend.presentation.global;

import lombok.*;

@Getter
@Setter
public class ErrorItem {

    private final String message;
    private String errorCode;

    public ErrorItem(String message) {
        this.message = message;
    }
}
