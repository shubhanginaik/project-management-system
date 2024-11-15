package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.UserDTO;
import fs19.java.backend.domain.entity.Users;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {


  public static UserDTO toDTO(Users user) {
    return new UserDTO(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail(),
        user.getPassword(),
        user.getPhone(),
        user.getCreatedDate(),
        user.getProfileImage()
    );
  }

  public static Users toEntity(UserDTO dto) {
    return new Users(
        dto.getId(),
        dto.getFirstName(),
        dto.getLastName(),
        dto.getEmail(),
        dto.getPassword(),
        dto.getPhone(),
        dto.getCreatedDate(),
        dto.getProfileImage()
    );
  }
}
