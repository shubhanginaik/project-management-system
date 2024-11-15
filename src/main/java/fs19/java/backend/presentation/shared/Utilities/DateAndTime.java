package fs19.java.backend.presentation.shared.Utilities;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Main class for defined system date and time
 */
public class DateAndTime {

    public static ZonedDateTime getDateAndTime(){
        Instant instant = Instant.now();
        return instant.atZone(ZoneId.systemDefault());
    }
}
