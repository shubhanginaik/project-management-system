package fs19.java.backend.application;

import fs19.java.backend.application.service.RedirectService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RedirectServiceImpl implements RedirectService {
    @Override
    public String processRedirect(String email, UUID roleId, UUID workspaceId) {
        // Perform business logic here (e.g., save to database, validate inputs, etc.)
        System.out.println("Email: " + email);
        System.out.println("Role ID: " + roleId);
        System.out.println("Workspace ID: " + workspaceId);
        // todo: handle invitation signup here
        // Return response (e.g., a confirmation message)
        return "Redirect processed for email: " + email + ", Role ID: " + roleId + ", Workspace ID: " + workspaceId;
    }
}