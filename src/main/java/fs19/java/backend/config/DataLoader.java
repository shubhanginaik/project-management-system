package fs19.java.backend.config;

import fs19.java.backend.application.InvitationServiceImpl;
import fs19.java.backend.domain.entity.*;
import fs19.java.backend.domain.entity.enums.*;
import fs19.java.backend.infrastructure.JpaRepositories.*;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    private final CompanyJpaRepo companyJpaRepo;
    private final RoleJpaRepo roleJpaRepo;
    private final PermissionJpaRepo permissionJpaRepo;
    private final UserJpaRepo userJpaRepo;
    private final ProjectJpaRepo projectJpaRepo;
    private final WorkspaceJpaRepo workspaceJpaRepo;
    private final WorkspaceUserJpaRepo workspaceUserJpaRepo;
    private final TaskJpaRepo taskJpaRepo;
    private final CommentJpaRepo commentJpaRepo;
    private final InvitationJpaRepo invitationJpaRepo;
    private final NotificationJpaRepo notificationJpaRepo;
    private final ActivityLogJpaRepo activityLogJpaRepo;
    private final RolePermissionJpaRepo rolePermissionJpaRepo;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public DataLoader(
            CompanyJpaRepo companyJpaRepo,
            RoleJpaRepo roleJpaRepo,
            PermissionJpaRepo permissionJpaRepo,
            UserJpaRepo userJpaRepo,
            ProjectJpaRepo projectJpaRepo,
            WorkspaceJpaRepo workspaceJpaRepo,
            WorkspaceUserJpaRepo workspaceUserJpaRepo, TaskJpaRepo taskJpaRepo, CommentJpaRepo commentJpaRepo, InvitationJpaRepo invitationJpaRepo, NotificationJpaRepo notificationJpaRepo, ActivityLogJpaRepo activityLogJpaRepo, RolePermissionJpaRepo rolePermissionJpaRepo) {

        this.companyJpaRepo = companyJpaRepo;
        this.roleJpaRepo = roleJpaRepo;
        this.permissionJpaRepo = permissionJpaRepo;
        this.userJpaRepo = userJpaRepo;
        this.projectJpaRepo = projectJpaRepo;
        this.workspaceJpaRepo = workspaceJpaRepo;
        this.workspaceUserJpaRepo = workspaceUserJpaRepo;
        this.taskJpaRepo = taskJpaRepo;
        this.commentJpaRepo = commentJpaRepo;
        this.invitationJpaRepo = invitationJpaRepo;
        this.notificationJpaRepo = notificationJpaRepo;
        this.activityLogJpaRepo = activityLogJpaRepo;
        this.rolePermissionJpaRepo = rolePermissionJpaRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> byEmail = userJpaRepo.findByEmail("aaaa@gmail.com");
        if (byEmail.isEmpty()) {
            User user = new User();
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setEmail("aaaa@gmail.com");
            user.setPassword(passwordEncoder.encode("123456789"));
            user.setCreatedDate(DateAndTime.getDateAndTime());
            User saveUser = userJpaRepo.save(user);

            Company company = new Company();
            company.setName("ABC");
            company.setCreatedBy(user);
            company.setCreatedDate(DateAndTime.getDateAndTime());
            companyJpaRepo.save(company);

            Permission permission = new Permission();
            permission.setName("ROLE_VIEW");
            permission.setPermissionType(PermissionType.GET);
            permission.setUrl("app/v1/roles");
            Permission savePermission1 = permissionJpaRepo.save(permission);


            Role role = new Role();
            role.setName("ADMIN");
            role.setCreatedDate(DateAndTime.getDateAndTime());
            role.setCompany(companyJpaRepo.findAll().getFirst());
            Role saveRole = roleJpaRepo.save(role);

            Role role2 = new Role();
            role2.setName("ROLE-NAME");
            role2.setCreatedDate(DateAndTime.getDateAndTime());
            role2.setCompany(companyJpaRepo.findAll().getFirst());
            roleJpaRepo.save(role2);

            Role role3 = new Role();
            role3.setName("MEMBER");
            role3.setCreatedDate(DateAndTime.getDateAndTime());
            role3.setCompany(companyJpaRepo.findAll().getFirst());
            Role saveRole3 = roleJpaRepo.save(role3);

            Workspace workspace = new Workspace();
            workspace.setName("Workspace1");
            workspace.setDescription("Workspace1");
            workspace.setType(WorkspaceType.PUBLIC);
            workspace.setCompanyId(companyJpaRepo.findAll().getFirst());
            workspace.setCreatedBy(userJpaRepo.findAll().getFirst());
            Workspace saveWorkspace = workspaceJpaRepo.save(workspace);

            Workspace workspace2 = new Workspace();
            workspace2.setName("workspace2");
            workspace2.setDescription("workspace2");
            workspace2.setType(WorkspaceType.PUBLIC);
            workspace2.setCompanyId(companyJpaRepo.findAll().getFirst());
            workspace2.setCreatedBy(user);
            Workspace saveWorkspace2 = workspaceJpaRepo.save(workspace2);

            Project project = new Project();
            project.setName("Project1");
            project.setStatus(true);
            project.setStartDate(DateAndTime.getDateAndTime());
            project.setCreatedByUser(user);
            project.setCreatedDate(DateAndTime.getDateAndTime());
            project.setWorkspace(workspaceJpaRepo.findAll().getFirst());
            Project saveProject = projectJpaRepo.save(project);

            WorkspaceUser workspaceUser = new WorkspaceUser();
            workspaceUser.setRole(saveRole);
            workspaceUser.setUser(saveUser);
            workspaceUser.setWorkspace(saveWorkspace);
            workspaceUserJpaRepo.save(workspaceUser);


            WorkspaceUser workspaceUser2 = new WorkspaceUser();
            workspaceUser2.setRole(role3);
            workspaceUser2.setUser(saveUser);
            workspaceUser2.setWorkspace(saveWorkspace2);
            workspaceUserJpaRepo.save(workspaceUser2);

            Permission permission1 = new Permission();
            permission1.setName("TASK_VIEW");
            permission1.setUrl("app/v1/tasks");
            permission1.setPermissionType(PermissionType.GET);
            Permission savePermission2 = permissionJpaRepo.save(permission1);

            Permission permission2 = new Permission();
            permission2.setName("PERMISSION_VIEW");
            permission2.setUrl("app/v1/permissions");
            permission2.setPermissionType(PermissionType.GET);
            Permission permission3 = permissionJpaRepo.save(permission2);

            Permission permission4 = new Permission();
            permission4.setName("INVITATION_VIEW");
            permission4.setUrl("app/v1/invitations");
            permission4.setPermissionType(PermissionType.GET);
            Permission permission5 = permissionJpaRepo.save(permission4);

            RolePermission rolePermission = new RolePermission();
            rolePermission.setRole(saveRole);
            rolePermission.setPermission(savePermission1);
            rolePermissionJpaRepo.save(rolePermission);

            RolePermission rolePermission1 = new RolePermission();
            rolePermission1.setRole(saveRole);
            rolePermission1.setPermission(savePermission2);
            rolePermissionJpaRepo.save(rolePermission1);

            RolePermission rolePermission3 = new RolePermission();
            rolePermission3.setRole(saveRole);
            rolePermission3.setPermission(permission3);
            rolePermissionJpaRepo.save(rolePermission3);

            RolePermission rolePermission4 = new RolePermission();
            rolePermission4.setRole(saveRole3);
            rolePermission4.setPermission(savePermission2);
            RolePermission rolePermissions4 = rolePermissionJpaRepo.save(rolePermission4);

            RolePermission rolePermission5 = new RolePermission();
            rolePermission5.setRole(saveRole);
            rolePermission5.setPermission(permission5);
            rolePermissionJpaRepo.save(rolePermission5);


            Task task = new Task();
            task.setName("Task 1");
            task.setDescription("Initial setup task");
            task.setCreatedDate(DateAndTime.getDateAndTime());
            task.setTaskStatus("TODO");
            task.setAttachments(Arrays.asList("doc1.pdf", "image1.png"));
            task.setProject(saveProject);
            task.setCreatedUser(user);
            task.setAssignedUser(user);
            task.setPriority("LOW_PRIORITY");
            taskJpaRepo.save(task);

            Comment comment = new Comment();
            comment.setTaskId(task);
            comment.setContent("This is a comment on the task.");
            comment.setCreatedDate(DateAndTime.getDateAndTime());
            comment.setCreatedBy(user);
            commentJpaRepo.save(comment);

            Invitation invitation1 = new Invitation();
            invitation1.setAccepted(false);
            invitation1.setExpiredAt(DateAndTime.getDateAndTime().plusDays(7));
            invitation1.setEmail("invitee@example.com");
            invitation1.setRole(saveRole);
            invitation1.setWorkspace(saveWorkspace);
            invitation1.setCreatedBy(saveUser);
            invitation1.setUrl(String.format(InvitationServiceImpl.urlBody, "invitee@example.com", saveRole.getId(), saveWorkspace.getId()));
            Invitation saveInvitation = invitationJpaRepo.save(invitation1);

            Notification notification = new Notification();
            notification.setContent("You have a new task assigned.");
            notification.setNotifyType(NotificationType.PROJECT_UPDATED);
            notification.setCreatedDate(DateAndTime.getDateAndTime());
            notification.setRead(false);
            notification.setProjectId(project);
            notification.setMentionedBy(user);
            notification.setMentionedTo(user);
            notificationJpaRepo.save(notification);

            ActivityLog activityLog = new ActivityLog();
            activityLog.setEntityType(EntityType.TASK);
            activityLog.setEntityId(task.getId());
            activityLog.setAction(ActionType.CREATED);
            activityLog.setCreatedDate(DateAndTime.getDateAndTime());
            activityLog.setUserId(user);

        }
    }
}