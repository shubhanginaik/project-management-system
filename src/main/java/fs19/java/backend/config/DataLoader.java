package fs19.java.backend.config;

import fs19.java.backend.domain.entity.*;
import fs19.java.backend.infrastructure.JpaRepositories.*;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CompanyJpaRepo companyJpaRepo;
    private final RoleJpaRepo roleJpaRepo;
    private final PermissionJpaRepo permissionJpaRepo;
    private final UserJpaRepo userJpaRepo;
    private final ProjectJpaRepo projectJpaRepo;
    private final WorkspaceJpaRepo workspaceJpaRepo;

    public DataLoader(CompanyJpaRepo companyJpaRepo, RoleJpaRepo roleJpaRepo, PermissionJpaRepo permissionJpaRepo, UserJpaRepo userJpaRepo, ProjectJpaRepo projectJpaRepo, WorkspaceJpaRepo workspaceJpaRepo) {
        this.companyJpaRepo = companyJpaRepo;
        this.roleJpaRepo = roleJpaRepo;
        this.permissionJpaRepo = permissionJpaRepo;
        this.userJpaRepo = userJpaRepo;
        this.projectJpaRepo = projectJpaRepo;
        this.workspaceJpaRepo = workspaceJpaRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setEmail("aaaa@gmail.com");
        user.setCreatedDate(DateAndTime.getDateAndTime());
        userJpaRepo.save(user);

        Company company = new Company();
        company.setName("ABC");
        company.setCreatedBy(user);
        company.setCreatedDate(DateAndTime.getDateAndTime());
        companyJpaRepo.save(company);

        Permission permission = new Permission();
        permission.setName("ACCESS");
        permissionJpaRepo.save(permission);
        Role role = new Role();
        role.setName("ROLE-NAME");
        role.setCreatedDate(DateAndTime.getDateAndTime());
        role.setCompany(companyJpaRepo.findAll().getFirst());
        roleJpaRepo.save(role);

        Role role2 = new Role();
        role2.setName("ROLE-NAME2");
        role2.setCreatedDate(DateAndTime.getDateAndTime());
        role2.setCompany(companyJpaRepo.findAll().getFirst());
        roleJpaRepo.save(role2);

        Project project = new Project();
        projectJpaRepo.save(project);
        Workspace workspace = new Workspace();
        workspace.setCompanyId(companyJpaRepo.findAll().getFirst());
        workspace.setCreatedBy(userJpaRepo.findAll().getFirst());
        workspaceJpaRepo.save(workspace);

    }
}