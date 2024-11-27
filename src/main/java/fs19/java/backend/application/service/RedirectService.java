package fs19.java.backend.application.service;

import java.util.UUID;

public interface RedirectService {

    String processRedirect(String email, UUID roleId, UUID workspaceId);
}
