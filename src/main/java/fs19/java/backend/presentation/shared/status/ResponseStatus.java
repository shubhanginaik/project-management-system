package fs19.java.backend.presentation.shared.status;

import lombok.Getter;

@Getter
public enum ResponseStatus {
    // Success status
    PENDING_PROCESS(0),
    SUCCESSFULLY_SAVED(1),
    SUCCESSFULLY_DELETED(2),
    SUCCESSFULLY_FOUND(3),
    SUCCESSFULLY_CREATED(4),
    SUCCESSFULLY_UPDATED(5),

    // Error status
    INVALID_INFORMATION(-1000),
    DELETE_REQUEST_FAILED(-1001);

    private final int status;

    ResponseStatus(int status) {
        this.status = status;
    }
}