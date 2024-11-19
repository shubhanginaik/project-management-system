package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.invitation.InvitationRequestDTO;
import fs19.java.backend.application.dto.invitation.InvitationResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface InvitationService {
    InvitationResponseDTO createAnInvitation(@Valid InvitationRequestDTO invitationRequestDTO);

    InvitationResponseDTO updateInvitation(UUID invitationId, @Valid InvitationRequestDTO invitationRequestDTO);

    InvitationResponseDTO deleteInvitation(UUID invitationId);

    List<InvitationResponseDTO> getInvitations();


    InvitationResponseDTO getInvitationById(UUID invitationId);
}
