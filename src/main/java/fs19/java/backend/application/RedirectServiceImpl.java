package fs19.java.backend.application;

import fs19.java.backend.application.dto.invitation.InvitationAcceptDTO;
import fs19.java.backend.application.mapper.InvitationMapper;
import fs19.java.backend.application.service.RedirectService;
import fs19.java.backend.domain.entity.Invitation;
import fs19.java.backend.infrastructure.JpaRepositories.InvitationJpaRepo;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RedirectServiceImpl implements RedirectService {
    private final InvitationJpaRepo invitationJpaRepo;

    public RedirectServiceImpl(InvitationJpaRepo invitationJpaRepo) {
        this.invitationJpaRepo = invitationJpaRepo;
    }

    @Override
    public InvitationAcceptDTO processRedirect(String email, UUID roleId, UUID workspaceId) {
        System.out.println("Email: " + email);
        System.out.println("Role ID: " + roleId);
        System.out.println("Workspace ID: " + workspaceId);
        Invitation invitation = invitationJpaRepo.finByEmailRoleIdAndWorkspaceId(email, roleId, workspaceId);
        if (invitation != null) {
            boolean accepted = invitation.isAccepted();
            if (accepted) {
                return InvitationMapper.toInvitationAcceptDTO(invitation, ResponseStatus.INVITATION_ID_NOT_FOUND);
            } else {
                return InvitationMapper.toInvitationAcceptDTO(invitation, ResponseStatus.SUCCESSFULLY_FOUND);

            }
        }
        return InvitationMapper.toInvitationAcceptDTO(new Invitation(), ResponseStatus.INVITATION_ID_NOT_FOUND);
    }
}