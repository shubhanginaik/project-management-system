package fs19.java.backend.application.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserCreateDto {

  @Schema(type = "string", format = "string", description = "First name of the user")
  @NotNull(message = "First name is required")
  String firstName;

  @Schema(type = "string", format = "string", description = "Last name of the user")
  @NotNull(message = "Last name is required")
  String lastName;

  @Schema(type = "string", format = "string", description = "Email of the user")
  @NotNull(message = "Email is required")
  String email;

  @Schema(type = "string", format = "string", description = "Password of the user")
  @NotNull(message = "Password is required")
  @Size(min = 8, message = "Password should be at least 8 characters long")
  String password;

  String phone;
  String profileImage;
}
