package fs19.java.backend.application;

import fs19.java.backend.application.dto.user.UserCreateDTO;
import fs19.java.backend.application.dto.user.UserReadDTO;
import fs19.java.backend.application.mapper.UserMapper;
import fs19.java.backend.application.mapper.WorkspaceUserMapper;
import fs19.java.backend.application.service.UserService;
import fs19.java.backend.config.URLParameterExtractor;
import fs19.java.backend.domain.entity.*;
import fs19.java.backend.domain.entity.enums.ActionType;
import fs19.java.backend.domain.entity.enums.EntityType;
import fs19.java.backend.infrastructure.JpaRepositories.*;
import fs19.java.backend.presentation.shared.exception.InvalidInvitationFoundException;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import fs19.java.backend.presentation.shared.exception.UserValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static final String ERROR_MESSAGE = "User not found with ID ";

    private final UserJpaRepo userRepository;
    private final InvitationJpaRepo invitationJpaRepo;
    private final WorkspaceJpaRepo workspaceJpaRepo;
    private final RoleJpaRepo roleJpaRepo;
    private final WorkspaceUserJpaRepo workspaceUserJpaRepo;

    private final ActivityLoggerService activityLoggerService;

    public UserServiceImpl(UserJpaRepo userRepository, InvitationJpaRepo invitationJpaRepo, WorkspaceJpaRepo workspaceJpaRepo, RoleJpaRepo roleJpaRepo, WorkspaceUserJpaRepo workspaceUserJpaRepo, ActivityLoggerService activityLoggerService) {
        this.userRepository = userRepository;
        this.invitationJpaRepo = invitationJpaRepo;
        this.workspaceJpaRepo = workspaceJpaRepo;
        this.roleJpaRepo = roleJpaRepo;
        this.workspaceUserJpaRepo = workspaceUserJpaRepo;
        this.activityLoggerService = activityLoggerService;
    }

    @Override
    public UserReadDTO createUser(UserCreateDTO createUserDTO) {
        logger.info("Creating user with DTO: {}", createUserDTO);

        validateUserCreateDTO(createUserDTO);
        User user = UserMapper.toEntity(createUserDTO);
        user.setCreatedDate(ZonedDateTime.now());
        user = userRepository.save(user);
        updateWorkSpaceUserInfo(createUserDTO, user);
        logger.info("User created and saved: {}", user);

        logger.info("EntityType: {}", EntityType.USER);
        logger.info("Entity ID: {}", user.getId());
        logger.info("Action: {}", ActionType.CREATED);
        logger.info("User ID: {}", user.getId());
        activityLoggerService.logActivity(EntityType.USER, user.getId(), ActionType.CREATED,
                user.getId());
        logger.info("Activity logged for user creation: {}", user.getId());

        return UserMapper.toReadDTO(user);
    }

    private void updateWorkSpaceUserInfo(UserCreateDTO createUserDTO, User user) {
        if (createUserDTO.getInvitation_url() != null && !createUserDTO.getInvitation_url().isEmpty()) {
            Map<String, String> stringStringMap = URLParameterExtractor.extractParameters(createUserDTO.getInvitation_url());
            if (stringStringMap.isEmpty()) {
                throw new InvalidInvitationFoundException("Invalid Invitation Information");
            }
            createWorkSpaceUserInfo(user, stringStringMap);

        }
    }
    private void createWorkSpaceUserInfo(User user, Map<String, String> stringStringMap) {
        UUID roleId = UUID.fromString(stringStringMap.get("roleId"));
        UUID workspaceId = UUID.fromString(stringStringMap.get("workspaceId"));
        Invitation invitation = invitationJpaRepo.finByEmailRoleIdAndWorkspaceId(stringStringMap.get("email"), roleId, workspaceId);
        if (!invitation.isAccepted()) {
            invitation.setAccepted(true);
            invitationJpaRepo.save(invitation);
            Optional<Workspace> workspaceOptional = workspaceJpaRepo.findById(workspaceId);
            if (workspaceOptional.isPresent()) {
                Optional<Role> roleOptional = roleJpaRepo.findById(roleId);
                if (roleOptional.isPresent()) {
                    WorkspaceUser workspaceUser = WorkspaceUserMapper.toEntity(user, roleOptional.get(), workspaceOptional.get());
                    workspaceUserJpaRepo.save(workspaceUser);
                }
                else {
                    throw new InvalidInvitationFoundException("Invalid Invitation-Role Information");
                }
            }
            else {
                throw new InvalidInvitationFoundException("Invalid Invitation-workspace Information");
            }
        }
        else {
            throw new InvalidInvitationFoundException("Already accepted invitation Found");
        }
    }

    @Override
    public UserReadDTO updateUser(UUID id, UserReadDTO userDTO) {
        logger.info("Updating user with ID: {} and DTO: {}", id, userDTO);

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            logger.info("User found for update: {}", user.get());

            User updatedUser = user.get();
            updatedUser.setFirstName(userDTO.getFirstName());
            updatedUser.setLastName(userDTO.getLastName());
            updatedUser.setEmail(userDTO.getEmail());
            updatedUser.setPhone(userDTO.getPhone());
            updatedUser.setProfileImage(userDTO.getProfileImage());
            updatedUser = userRepository.save(updatedUser);
            logger.info("User updated and saved: {}", updatedUser);
            activityLoggerService.logActivity(EntityType.USER, updatedUser.getId(), ActionType.UPDATED,
                    updatedUser.getId());

            return UserMapper.toReadDTO(updatedUser);
        } else {
            throw new UserNotFoundException(ERROR_MESSAGE + id);
        }
    }

    @Override
    public UserReadDTO findUserById(UUID id) {
        logger.info("Finding user by ID: {}", id);

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(ERROR_MESSAGE + id);
        }
        logger.info("User found: {}", userOptional.get());
        return UserMapper.toReadDTO(userOptional.get());
    }

    @Override
    public List<UserReadDTO> findAllUsers() {
        logger.info("Finding all users");

        List<User> users = userRepository.findAll();
        List<UserReadDTO> userReadDTOs = users.stream()
                .map(UserMapper::toReadDTO)
                .toList();
        logger.info("Users found: {}", userReadDTOs);
        return userReadDTOs;

    }

    @Override
    public boolean deleteUser(UUID id) {
        logger.info("Deleting user with ID: {}", id);

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            logger.info("User found for deletion: {}", user.get());

            userRepository.delete(user.get());
            logger.info("User deleted successfully");

            return true;
        } else {
            throw new UserNotFoundException(ERROR_MESSAGE + id);
        }
    }

    private void validateUserCreateDTO(UserCreateDTO createUserDTO) {
        if (createUserDTO.getFirstName() == null || createUserDTO.getFirstName().isEmpty()) {
            throw new UserValidationException("First name is required");
        }
        if (createUserDTO.getLastName() == null || createUserDTO.getLastName().isEmpty()) {
            throw new UserValidationException("Last name is required");
        }
        if (createUserDTO.getEmail() == null || createUserDTO.getEmail().isEmpty()) {
            throw new UserValidationException("Email is required");
        }
        if (createUserDTO.getPassword() != null && createUserDTO.getPassword().length() < 8) {
            throw new UserValidationException("Password must be at least 8 characters long");
        }
        if (createUserDTO.getPhone().length() > 15 || createUserDTO.getPhone().length() < 10) {
            throw new UserValidationException("Phone number must be between 10 and 15 characters long");
        }
    }
}


