package fs19.java.backend.config;

import fs19.java.backend.application.InvitationServiceImpl;
import fs19.java.backend.domain.entity.*;
import fs19.java.backend.domain.entity.enums.*;
import fs19.java.backend.infrastructure.JpaRepositories.*;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Component
public class DataLoader implements CommandLineRunner {

    public static String ADMIN_USER_NAME = "ADMIN";
    public static String MEMBER_USER_NAME = "MEMBER";
    public static String ADMIN_USER_FIRST_NAME = "John";

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
        Optional<User> byEmail = userJpaRepo.findByEmail("admin@gmail.com");
        if (byEmail.isEmpty()) {
            // Step 1: Create a default admin user
            User user = new User();
            user.setFirstName(ADMIN_USER_FIRST_NAME);
            user.setLastName("Doe");
            user.setEmail("admin@gmail.com");
            user.setPhone("1234567890");
            user.setPassword(passwordEncoder.encode("123456789"));
            user.setCreatedDate(DateAndTime.getDateAndTime());
            User saveUser = userJpaRepo.save(user);

            User user2 = new User();
            user2.setFirstName("Test");
            user2.setLastName("Test");
            user2.setEmail("test@gmail.com");
            user2.setPhone("1234567890");
            user2.setPassword(passwordEncoder.encode("123456789"));
            user2.setCreatedDate(DateAndTime.getDateAndTime());
            User saveUser2 = userJpaRepo.save(user2);

            //new user with member_role
            Optional<User> byMemberEmail = userJpaRepo.findByEmail("shubhangi@gmail.com");
            User saveUser3 = null;
            if (byMemberEmail.isEmpty()) {
                User user3 = new User();
                user3.setFirstName("Default");
                user3.setLastName("name");
                user3.setEmail("shubhangi@gmail.com");
                user3.setPhone("1234567890");
                user3.setPassword(passwordEncoder.encode("123456789"));
                user3.setCreatedDate(DateAndTime.getDateAndTime());
                saveUser3 = userJpaRepo.save(user3);
            }


            // Step 2: Create a default company
            Company company = new Company();
            company.setName("Company-A");
            company.setCreatedBy(user);
            company.setCreatedDate(DateAndTime.getDateAndTime());
            Company saveCompany = companyJpaRepo.save(company);

            // Step 3: Create roles (Admin, Member, etc.)
            Role adminRole = new Role();
            adminRole.setName(ADMIN_USER_NAME);
            adminRole.setCreatedDate(DateAndTime.getDateAndTime());
            adminRole.setCompany(saveCompany);
            Role saveAdminRole = roleJpaRepo.save(adminRole);

            Role memberRole = new Role();
            memberRole.setName(MEMBER_USER_NAME);
            memberRole.setCreatedDate(DateAndTime.getDateAndTime());
            memberRole.setCompany(saveCompany);
            Role saveMemberRole = roleJpaRepo.save(memberRole);

            // Step 4: Create workspaces
            Workspace workspace = new Workspace();
            workspace.setName("Workspace1");
            workspace.setDescription("Workspace1");
            workspace.setType(WorkspaceType.PUBLIC);
            workspace.setCompanyId(saveCompany);
            workspace.setCreatedDate(DateAndTime.getDateAndTime());
            workspace.setCreatedBy(user);
            Workspace saveWorkspace = workspaceJpaRepo.save(workspace);


            Workspace workspace2 = new Workspace();
            workspace2.setName("Workspace2");
            workspace2.setDescription("Workspace2");
            workspace2.setType(WorkspaceType.PUBLIC);
            workspace2.setCompanyId(company);
            workspace2.setCreatedDate(DateAndTime.getDateAndTime());
            workspace2.setCreatedBy(user);
            Workspace saveWorkspace2 = workspaceJpaRepo.save(workspace2);


            // Step 5: Assign users to workspaces
            WorkspaceUser adminWorkspaceUser = new WorkspaceUser();
            adminWorkspaceUser.setRole(saveAdminRole);
            adminWorkspaceUser.setUser(saveUser);
            adminWorkspaceUser.setWorkspace(saveWorkspace);
            workspaceUserJpaRepo.save(adminWorkspaceUser);

            WorkspaceUser memberWorkspaceUser = new WorkspaceUser();
            memberWorkspaceUser.setRole(saveMemberRole);
            memberWorkspaceUser.setUser(saveUser);
            memberWorkspaceUser.setWorkspace(saveWorkspace2);
            workspaceUserJpaRepo.save(memberWorkspaceUser);

            // Step 6: Create CRUD permissions for all entities
            String[] entities = {
                    "activity-log", "comments", "companies", "invitations",
                    "notifications", "permissions", "projects", "roles",
                    "rolePermissions", "tasks", "users", "workspaces", "workspace-users"
            };

            for (String entity : entities) {
                String baseUrl = "/api/v1/" + entity + "/**"; // E.g., "/api/v1/users"
                assignCrudPermissionsForEntity(entity, baseUrl, saveAdminRole);
            }

//            //assigning member role permissions
//            //check if user is present in user table
//            if(saveUser3 != null){
//                Workspace newWorkspace = new Workspace();
//                newWorkspace.setName("Default_Workspace");
//                newWorkspace.setDescription("Default workspace for new user");
//                newWorkspace.setType(WorkspaceType.PUBLIC);
//                newWorkspace.setCompanyId(company);
//                newWorkspace.setCreatedDate(DateAndTime.getDateAndTime());
//                newWorkspace.setCreatedBy(saveUser3);
//                Workspace saveNewWorkspace = workspaceJpaRepo.save(newWorkspace);
//
//                WorkspaceUser newMemberWorkspaceUser = new WorkspaceUser();
//                newMemberWorkspaceUser.setRole(saveMemberRole);
//                newMemberWorkspaceUser.setUser(saveUser2);
//                newMemberWorkspaceUser.setWorkspace(saveNewWorkspace);
//                workspaceUserJpaRepo.save(newMemberWorkspaceUser);
//            }

            // Step 7: Additional setup for Projects, Tasks, etc.
            Project project = new Project();
            project.setName("Project1");
            project.setStatus(true);
            project.setStartDate(DateAndTime.getDateAndTime());
            project.setCreatedByUser(user);
            project.setCreatedDate(DateAndTime.getDateAndTime());
            project.setWorkspace(saveWorkspace);
            Project saveProject = projectJpaRepo.save(project);

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
            Task saveTask = taskJpaRepo.save(task);


            Comment comment = new Comment();
            comment.setTaskId(task);
            comment.setContent("This is a comment on the task.");
            comment.setCreatedDate(DateAndTime.getDateAndTime());
            comment.setCreatedBy(user);
            commentJpaRepo.save(comment);

            // Notifications, Invitations, etc.
            Notification notification = new Notification();
            notification.setContent("You have a new task assigned.");
            notification.setNotifyType(NotificationType.PROJECT_UPDATED);
            notification.setCreatedDate(DateAndTime.getDateAndTime());
            notification.setRead(false);
            notification.setProjectId(saveProject);
            notification.setMentionedBy(user);
            notification.setMentionedTo(user);
            notificationJpaRepo.save(notification);

            Invitation invitation = new Invitation();
            invitation.setAccepted(false);
            invitation.setExpiredAt(DateAndTime.getExpiredDateAndTime());
            invitation.setEmail("invitee@example.com");
            invitation.setRole(saveAdminRole);
            invitation.setWorkspace(saveWorkspace);
            invitation.setCreatedBy(saveUser);
            invitation.setUrl(String.format(InvitationServiceImpl.urlBody, "invitee@example.com", saveAdminRole.getId(), saveWorkspace.getId()));
            invitationJpaRepo.save(invitation);
        }

//        // Member role permissions
//            // Step 1: Create a new member user
//            Optional<User> byMemberEmail = userJpaRepo.findByEmail("member@gmail.com");
//            User saveMemberUser = null;
//        if (byMemberEmail.isEmpty()) {
//            User memberUser = new User();
//            memberUser.setFirstName("Member");
//            memberUser.setLastName("User");
//            memberUser.setEmail("member@gmail.com");
//            memberUser.setPhone("0987654321");
//            memberUser.setPassword(passwordEncoder.encode("password123"));
//            memberUser.setCreatedDate(DateAndTime.getDateAndTime());
//            saveMemberUser = userJpaRepo.save(memberUser);
//
//            // Step 2: Create a company for the member user
//            Company memberCompany = new Company();
//            memberCompany.setName("MemberCompany");
//            memberCompany.setCreatedBy(saveMemberUser);
//            memberCompany.setCreatedDate(DateAndTime.getDateAndTime());
//            Company saveMemberCompany = companyJpaRepo.save(memberCompany);
//
////            // Step 3: Create a role for the member user
////            Role memberRole = new Role();
////            memberRole.setName("MEMBER");
////            memberRole.setCreatedDate(DateAndTime.getDateAndTime());
////            memberRole.setCompany(saveMemberCompany);
////            Role saveMemberRole = roleJpaRepo.save(memberRole);
//            //get member role
//            Role saveMemberRole = roleJpaRepo.findByName("MEMBER");
//
//            // Step 4: Create a workspace for the member user
//            Workspace memberWorkspace = new Workspace();
//            memberWorkspace.setName("MemberWorkspace");
//            memberWorkspace.setDescription("Workspace for member user");
//            memberWorkspace.setType(WorkspaceType.PUBLIC);
//            memberWorkspace.setCompanyId(saveMemberCompany);
//            memberWorkspace.setCreatedDate(DateAndTime.getDateAndTime());
//            memberWorkspace.setCreatedBy(saveMemberUser);
//            Workspace saveMemberWorkspace = workspaceJpaRepo.save(memberWorkspace);
//
//            // Step 5: Assign the member user to the workspace
//            WorkspaceUser memberWorkspaceUser = new WorkspaceUser();
//            memberWorkspaceUser.setRole(saveMemberRole);
//            memberWorkspaceUser.setUser(saveMemberUser);
//            memberWorkspaceUser.setWorkspace(saveMemberWorkspace);
//            workspaceUserJpaRepo.save(memberWorkspaceUser);
//
//            // Step 6: Create a project for the member user
//            Project memberProject = new Project();
//            memberProject.setName("MemberProject");
//            memberProject.setStatus(true);
//            memberProject.setStartDate(DateAndTime.getDateAndTime());
//            memberProject.setCreatedByUser(saveMemberUser);
//            memberProject.setCreatedDate(DateAndTime.getDateAndTime());
//            memberProject.setWorkspace(saveMemberWorkspace);
//            Project saveMemberProject = projectJpaRepo.save(memberProject);
//
//            // Step 7: Create an activity log for the member user
//            createActivityLog(EntityType.PROJECT, saveMemberProject.getId(), ActionType.CREATED, "Created a new project", saveMemberUser);
//
//            // Step 8: Create a comment for the member user's project
//            Task memberTask = new Task();
//            memberTask.setName("MemberTask");
//            memberTask.setDescription("Task for member project");
//            memberTask.setCreatedDate(DateAndTime.getDateAndTime());
//            memberTask.setTaskStatus("TODO");
//            memberTask.setProject(saveMemberProject);
//            memberTask.setCreatedUser(saveMemberUser);
//            memberTask.setAssignedUser(saveMemberUser);
//            memberTask.setPriority("LOW_PRIORITY");
//            Task saveMemberTask = taskJpaRepo.save(memberTask);
//
//            Comment memberComment = new Comment();
//            memberComment.setTaskId(saveMemberTask);
//            memberComment.setContent("This is a comment on the member's task.");
//            memberComment.setCreatedDate(DateAndTime.getDateAndTime());
//            memberComment.setCreatedBy(saveMemberUser);
//            commentJpaRepo.save(memberComment);
//
//            // Step 9: Assign permissions to the member role
//            String[] entitiesForMemberUser = {
//                "activity-log", "comments", "companies", "invitations",
//                "notifications", "permissions", "projects", "roles",
//                "rolePermissions", "tasks", "users", "workspaces", "workspace-users"
//            };
//
//            for (String entity : entitiesForMemberUser) {
//                String baseUrl = "/api/v1/" + entity + "/**"; // E.g., "/api/v1/users"
//                assignMemberPermissionsForEntity(entity, baseUrl, saveMemberRole);
//            }
//        }
    }

    // Utility method to create CRUD permissions for an entity
    private void assignCrudPermissionsForEntity(String entity, String baseUrl, Role role) {
        for (PermissionType type : PermissionType.values()) {
            String action = type.name(); // GET, POST, DELETE, PUT
            Permission permission = new Permission();
            permission.setName(entity + "_" + action);
            permission.setUrl(baseUrl);
            permission.setPermissionType(type);
            Permission savedPermission = permissionJpaRepo.save(permission);

            RolePermission rolePermission = new RolePermission();
            rolePermission.setRole(role);
            rolePermission.setPermission(savedPermission);
            rolePermissionJpaRepo.save(rolePermission);
        }
    }

    private void createActivityLog(EntityType entityType, UUID entityId, ActionType action, String description, User user) {
        ActivityLog activityLog = ActivityLog.builder()
                .entityType(entityType)
                .entityId(entityId)
                .action(action)
                .createdDate(DateAndTime.getDateAndTime())
                .userId(user)
                .build();

        activityLogJpaRepo.save(activityLog);
    }

//    private void assignMemberPermissionsForEntity(String entity, String baseUrl, Role role) {
//        for (PermissionType type : PermissionType.values()) {
//            if (type == PermissionType.GET || type == PermissionType.PUT) { // Allow only read and update permissions
//                String action = type.name(); // GET, PUT
//                String permissionName = entity + "_" + action;
//
//                // Check if the permission already exists
//                Optional<Permission> existingPermission = Optional.ofNullable(permissionJpaRepo.findByName(permissionName));
//                Permission permission;
//                if (existingPermission.isPresent()) {
//                    permission = existingPermission.get();
//                } else {
//                    permission = new Permission();
//                    permission.setName(permissionName);
//                    permission.setUrl(baseUrl);
//                    permission.setPermissionType(type);
//                    permission = permissionJpaRepo.save(permission);
//                }
//
//                // Check if the role already has this permission
//                if (!rolePermissionJpaRepo.existsById(role.getId())) {
//                    RolePermission rolePermission = new RolePermission();
//                    rolePermission.setRole(role);
//                    rolePermission.setPermission(permission);
//                    rolePermissionJpaRepo.save(rolePermission);
//                }
//            }
//
//            }
//    }
}
