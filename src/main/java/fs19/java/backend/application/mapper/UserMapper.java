package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.user.UserCreateDto;
import fs19.java.backend.application.dto.user.UserDTO;
import fs19.java.backend.application.dto.user.UserReadDto;
import fs19.java.backend.domain.entity.User;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {


  public static UserDTO toDTO(User user) {
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

  public static User toEntity(UserDTO dto) {
    return new User(
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

  public static User toEntity(UserCreateDto dto) {
    User user = new User();
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setEmail(dto.getEmail());
    user.setPassword(dto.getPassword());
    user.setPhone(dto.getPhone());
    user.setProfileImage(dto.getProfileImage());
    return user;
  }

  public static UserReadDto toReadDto(User user) {
    UserReadDto userReadDto = new UserReadDto();
    userReadDto.setId(user.getId());
    userReadDto.setFirstName(user.getFirstName());
    userReadDto.setLastName(user.getLastName());
    userReadDto.setEmail(user.getEmail());
    userReadDto.setPhone(user.getPhone());
    userReadDto.setCreatedDate(ZonedDateTime.now());
    userReadDto.setProfileImage(user.getProfileImage());
    return userReadDto;
  }
}
