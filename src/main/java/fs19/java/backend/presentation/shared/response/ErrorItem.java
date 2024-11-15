package fs19.java.backend.presentation.shared.response;


import lombok.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorItem {

    private final String message;
    private String errorCode;

    public ErrorItem(String message) {
        this.message = message;
    }
}
