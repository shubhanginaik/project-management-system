package fs19.java.backend.application;

import fs19.java.backend.application.dto.invitation.InvitationRequestDTO;
import fs19.java.backend.application.dto.invitation.InvitationResponseDTO;
import fs19.java.backend.application.mapper.InvitationMapper;
import fs19.java.backend.application.service.InvitationService;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.Invitation;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.infrastructure.CompanyRepoImpl;
import fs19.java.backend.infrastructure.InvitationRepoImpl;
import fs19.java.backend.infrastructure.RoleRepoImpl;
import fs19.java.backend.presentation.shared.status.ResponseStatus;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepoImpl invitationRepo;

    private final RoleRepoImpl roleRepo;

    private final CompanyRepoImpl companyRepo;

    public InvitationServiceImpl(InvitationRepoImpl invitationRepo, RoleRepoImpl roleRepo, CompanyRepoImpl companyRepo) {
        this.invitationRepo = invitationRepo;
        this.roleRepo = roleRepo;
        this.companyRepo = companyRepo;
    }

    @Override
    public InvitationResponseDTO createAnInvitation(InvitationRequestDTO invitationRequestDTO) {
        Invitation myInvitation;
        if (invitationRequestDTO.getEmail().isEmpty()) {
            System.out.println("Invitation Email from DTO is null, cannot proceed with Invitation creation.");
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_EMAIL_NOT_FOUND);
        }
        if (invitationRequestDTO.getRoleId() != null) {
            Role roleById = roleRepo.getRoleById(invitationRequestDTO.getRoleId());
            if (roleById == null) {
                System.out.println("No Valid Role Result Found");
                return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ROLE_NOT_FOUND);
            } else {
                if (invitationRequestDTO.getCompanyId() != null) {
                    Optional<Company> companyResult = companyRepo.findById(invitationRequestDTO.getId());
                    if (companyResult.isPresent()) {
                        Company company = companyResult.get();
                        myInvitation = invitationRepo.createInvitation(invitationRequestDTO, roleById, company);
                        if (myInvitation == null) {
                            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVALID_INFORMATION_INVITATION_DETAILS_NOT_CREATED);
                        }
                        System.out.println("Invitation-Created successfully");
                        return InvitationMapper.toInvitationResponseDTO(myInvitation, ResponseStatus.SUCCESSFULLY_CREATED);

                    } else {
                        System.out.println("No Valid Company Result Found");
                        return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_COMPANY_NOT_FOUND);
                    }

                } else {
                    System.out.println("No Valid Company ID Found");
                    return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_COMPANY_ID_NOT_FOUND);

                }

            }

        } else {
            System.out.println("No RoleId specified");
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ROLE_ID_NOT_FOUND);
        }
    }

    @Override
    public InvitationResponseDTO updateInvitation(UUID invitationId, @Valid InvitationRequestDTO invitationRequestDTO) {
        Invitation myInvitation;
        if (invitationId == null) {
            System.out.println("Invitation Id is null, cannot proceed with Invitation update.");
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ID_NOT_FOUND);
        } else if (invitationRequestDTO.getEmail().isEmpty()) { // e
            System.out.println("Invitation Email from DTO is null, cannot proceed with Invitation update.");
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_EMAIL_NOT_FOUND);
        }
        if (invitationRequestDTO.getRoleId() != null) {
            Role roleById = roleRepo.getRoleById(invitationRequestDTO.getRoleId());
            if (roleById == null) {
                System.out.println("No Valid Role Result Found");
                return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ROLE_NOT_FOUND);
            } else {
                if (invitationRequestDTO.getCompanyId() != null) {
                    Optional<Company> companyResult = companyRepo.findById(invitationRequestDTO.getId());
                    if (companyResult.isPresent()) {
                        Company company = companyResult.get();
                        myInvitation = invitationRepo.updateInvitation(invitationId, invitationRequestDTO, roleById, company);
                        if (myInvitation == null) {
                            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVALID_INFORMATION_INVITATION_DETAILS_NOT_CREATED);
                        }
                        System.out.println("Invitation-Updated successfully");
                        return InvitationMapper.toInvitationResponseDTO(myInvitation, ResponseStatus.SUCCESSFULLY_CREATED);

                    } else {
                        System.out.println("No Valid Company Result Found");
                        return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_COMPANY_NOT_FOUND);
                    }

                } else {
                    System.out.println("No Valid Company ID Found");
                    return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_COMPANY_ID_NOT_FOUND);

                }

            }

        } else {
            System.out.println("No RoleId specified");
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ROLE_ID_NOT_FOUND);
        }
    }

    @Override
    public InvitationResponseDTO deleteInvitation(UUID invitationId) {
        Invitation myInvitation;
        if (invitationId == null) {
            System.out.println("Invitation Id is null, cannot proceed with Invitation delete.");
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ID_NOT_FOUND);
        }
        myInvitation = invitationRepo.findInvitationById(invitationId);
        if (myInvitation == null) {
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVALID_INFORMATION_INVITATION_DETAILS_NOT_DELETED);
        }
        System.out.println("Invitation-Deleted successfully");
        return InvitationMapper.toInvitationResponseDTO(invitationRepo.deleteInvitation(invitationId), ResponseStatus.SUCCESSFULLY_DELETED);
    }

    @Override
    public List<InvitationResponseDTO> getInvitations() {
        return InvitationMapper.toInvitationResponseDTOs(this.invitationRepo.getInvitations(), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    @Override
    public InvitationResponseDTO getInvitationById(UUID invitationId) {
        Invitation myInvitation;
        if (invitationId == null) {
            System.out.println("Invitation Id is null, cannot proceed with Invitation search.");
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ID_NOT_FOUND);
        }
        myInvitation = invitationRepo.findInvitationById(invitationId);
        if (myInvitation == null) {
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVALID_INFORMATION_INVITATION_DETAILS_NOT_FOUND);
        }
        System.out.println("Invitation-Found successfully");
        return InvitationMapper.toInvitationResponseDTO(myInvitation, ResponseStatus.SUCCESSFULLY_FOUND);
    }
}
