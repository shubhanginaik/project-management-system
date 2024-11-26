package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.RedirectServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Redirect", description = "Handle Invitation Requests")
@RequestMapping("/api/v1/accept-invitation")
@RestController
public class RedirectController {

    private final RedirectServiceImpl redirectService;

    public RedirectController(RedirectServiceImpl redirectService) {
        this.redirectService = redirectService;
    }

    @Operation(summary = "Handle invitations", description = "Handle invitations via coming through the third party APIs")
    @GetMapping("/redirect")
    public String handleInvitations(
            @RequestParam String email,
            @RequestParam UUID roleId,
            @RequestParam UUID workspaceId) {
        return redirectService.processRedirect(email, roleId, workspaceId);
    }
}
