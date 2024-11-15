package fs19.java.backend.domain.entity;

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String phone;
  private ZonedDateTime createdDate;
  private String profileImage;
}
