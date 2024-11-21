package fs19.java.backend.infrastructure;

import fs19.java.backend.application.dto.invitation.InvitationRequestDTO;
import fs19.java.backend.domain.abstraction.InvitationRepository;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.Invitation;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.infrastructure.JpaRepositories.InvitationJpaRepo;
import fs19.java.backend.presentation.shared.exception.InvitationLevelException;
import fs19.java.backend.presentation.shared.exception.PermissionLevelException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InvitationRepoImpl implements InvitationRepository {
    private final InvitationJpaRepo invitationJpaRepo;

    public InvitationRepoImpl(InvitationJpaRepo invitationJpaRepo) {
        this.invitationJpaRepo = invitationJpaRepo;
    }

    @Override
    public Invitation save(Invitation invitation) {
        try {
            return invitationJpaRepo.save(invitation);
        } catch (Exception e) {
            throw new PermissionLevelException(e.getLocalizedMessage() + " : " + InvitationLevelException.INVITATION_CREATE);
        }
    }

    @Override
    public Invitation update(UUID invitationId, InvitationRequestDTO invitationRequestDTO, Role role, Company company) {

        Invitation myInvitation = findById(invitationId);
        if (myInvitation != null) {
            myInvitation.setAccepted(invitationRequestDTO.isAccepted());
            myInvitation.setEmail(invitationRequestDTO.getEmail());
            myInvitation.setRole(role);
            myInvitation.setCompany(company);
            myInvitation.setExpiredAt(invitationRequestDTO.getExpiredAt());
            return invitationJpaRepo.save(myInvitation);
        } else {
            throw new PermissionLevelException(" DB is empty: " + InvitationLevelException.INVITATION_UPDATE);
        }
    }

    @Override
    public Invitation findById(UUID invitationId) {
        Optional<Invitation> byId = invitationJpaRepo.findById(invitationId);
        return byId.orElse(null);
    }

    @Override
    public Invitation delete(UUID invitationId) {
        Invitation invitation = findById(invitationId);
        if (invitation != null) {
            invitationJpaRepo.delete(invitation);
            return invitation;
        } else {
            throw new PermissionLevelException(" DB is empty : " + InvitationLevelException.INVITATION_DELETE);
        }
    }

    @Override
    public List<Invitation> findAll() {
        return invitationJpaRepo.findAll();
    }
}
