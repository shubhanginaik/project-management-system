package fs19.java.backend.infrastructure;

import fs19.java.backend.application.dto.invitation.InvitationRequestDTO;
import fs19.java.backend.domain.abstraction.InvitationRepository;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.Invitation;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.infrastructure.tempMemory.RoleInMemoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class InvitationRepoImpl implements InvitationRepository {

    @Autowired
    private RoleInMemoryDB tempRoleDB;


    @Override
    public Invitation createInvitation(InvitationRequestDTO invitationRequestDTO, Role roleById, Company company) {
        return tempRoleDB.createInvitation(invitationRequestDTO, roleById, company);
    }

    @Override
    public Invitation updateInvitation(UUID invitationId, InvitationRequestDTO invitationRequestDTO, Role role, Company company) {
        return tempRoleDB.updateInvitation(invitationId, invitationRequestDTO, role, company);
    }

    @Override
    public Invitation findInvitationById(UUID invitationId) {
        return tempRoleDB.findInvitationById(invitationId);
    }

    @Override
    public Invitation deleteInvitation(UUID invitationId) {
        return tempRoleDB.deleteInvitation(invitationId);
    }

    @Override
    public List<Invitation> getInvitations() {
        return tempRoleDB.findAllInvitations();
    }
}
