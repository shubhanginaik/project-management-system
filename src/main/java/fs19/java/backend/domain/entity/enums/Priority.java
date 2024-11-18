package fs19.java.backend.domain.entity.enums;

import java.awt.Color;
import lombok.Getter;

@Getter
public enum Priority {
    LOW_PRIORITY(1, new Color(10, 20, 30)),
    MEDIUM_PRIORITY(2, new Color(100, 20, 30)),
    HIGH_PRIORITY(3, new Color(200, 150, 30));

    private final int id;
    private final Color color;

    Priority(int id, Color color) {
        this.id = id;
        this.color = color;
    }
}