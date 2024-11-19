package fs19.java.backend.domain.abstraction;

import fs19.java.backend.application.dto.invitation.InvitationRequestDTO;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.Invitation;
import fs19.java.backend.domain.entity.Role;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface InvitationRepository {
    Invitation createInvitation(InvitationRequestDTO invitationRequestDTO, Role roleById, Company company);

    Invitation updateInvitation(UUID invitationId, @Valid InvitationRequestDTO invitationRequestDTO, Role role, Company company);

    Invitation findInvitationById(UUID invitationId);

    List<Invitation> getInvitations();

    Invitation deleteInvitation(UUID invitationId);
}
