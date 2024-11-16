package fs19.java.backend.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {

  private UUID id;

  @NotNull(message = "First name is required")
  private String firstName;

  @NotNull(message = "Last name is required")
  private String lastName;

  @NotNull(message = "Email is required")
  private String email;

  @NotNull(message = "Password is required")
  @Size(min = 8, message = "Password should be at least 8 characters long")
  private String password;

  private String phone;

  private ZonedDateTime createdDate;
  private String profileImage;
}
