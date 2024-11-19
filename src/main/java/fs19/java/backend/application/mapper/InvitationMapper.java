package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.invitation.InvitationResponseDTO;
import fs19.java.backend.domain.entity.Invitation;
import fs19.java.backend.presentation.shared.status.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

public class InvitationMapper {

    /**
     * Convert object to response
     * @param invitation
     * @param responseStatus
     * @return
     */
    public static InvitationResponseDTO toInvitationResponseDTO(Invitation invitation, ResponseStatus responseStatus) {
        return new InvitationResponseDTO(invitation.getId(), invitation.isAccepted(), invitation.getExpiredAt(), invitation.getEmail(), invitation.getRole() == null ? null : invitation.getRole().getId(), invitation.getCompany() == null ? null : invitation.getCompany().getId(), responseStatus);
    }

    /**
     * Convert list object to response list
     * @param invitations
     * @param status
     * @return
     */
    public static List<InvitationResponseDTO> toInvitationResponseDTOs(List<Invitation> invitations, ResponseStatus status) {
        List<InvitationResponseDTO> responseDTOS = new ArrayList<>();
        invitations.forEach(role -> {
            responseDTOS.add(toInvitationResponseDTO(role, status));
        });
        return responseDTOS;
    }
}
