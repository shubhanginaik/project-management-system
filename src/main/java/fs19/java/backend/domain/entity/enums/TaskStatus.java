package fs19.java.backend.domain.entity.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {
    TODO(1),
    IN_DEVELOPMENT(2),
    COMPLETE(3),
    RELEASED(4);

    private final int taskStatus;

    TaskStatus(int id) {
        this.taskStatus = id;
    }
}
