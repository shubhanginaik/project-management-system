package fs19.java.backend.presentation.shared.status;

import lombok.Getter;

/**
 * These statuses can use only internal usages, and when it passes to end user please convert it into global status
 */
@Getter
public enum ResponseStatus {

    //success status
    PENDING_PROCESS(0),
    SUCCESSFULLY_SAVED(1),
    SUCCESSFULLY_DELETED(2),
    SUCCESSFULLY_FOUND(3),
    SUCCESSFULLY_CREATED(4),
    SUCCESSFULLY_UPDATED(5),

    //Error status
    INVALID_INFORMATION(-1000),
    DELETE_REQUEST_FAILED(-1001);

    final private int status;

    ResponseStatus(int status) {
        this.status = status;
    }

}
