package fs19.java.backend.application.dto.project;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectUpdateDTO {

  private String description;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
  private Boolean status;
}
