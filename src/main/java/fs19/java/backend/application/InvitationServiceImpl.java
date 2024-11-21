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
    public InvitationResponseDTO save(InvitationRequestDTO invitationRequestDTO) {
        Invitation myInvitation;
        if (invitationRequestDTO.getEmail().isEmpty()) {
            System.out.println("Invitation Email from DTO is null, cannot proceed with Invitation creation.");
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_EMAIL_NOT_FOUND);
        }
        if (invitationRequestDTO.getRoleId() != null) {
            Role roleById = roleRepo.findById(invitationRequestDTO.getRoleId());
            if (roleById == null) {
                System.out.println("No Valid Role Result Found");
                return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ROLE_NOT_FOUND);
            } else {
                if (invitationRequestDTO.getCompanyId() != null) {
                    Optional<Company> companyResult = companyRepo.findById(invitationRequestDTO.getCompanyId());
                    if (companyResult.isPresent()) {
                        Company company = companyResult.get();
                        myInvitation = invitationRepo.save(InvitationMapper.toInvitation(invitationRequestDTO, roleById, company));
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
    public InvitationResponseDTO update(UUID invitationId, @Valid InvitationRequestDTO invitationRequestDTO) {
        Invitation myInvitation;
        if (invitationId == null) {
            System.out.println("Invitation Id is null, cannot proceed with Invitation update.");
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ID_NOT_FOUND);
        } else if (invitationRequestDTO.getEmail().isEmpty()) { // e
            System.out.println("Invitation Email from DTO is null, cannot proceed with Invitation update.");
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_EMAIL_NOT_FOUND);
        }
        if (invitationRequestDTO.getRoleId() != null) {
            Role roleById = roleRepo.findById(invitationRequestDTO.getRoleId());
            if (roleById == null) {
                System.out.println("No Valid Role Result Found");
                return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ROLE_NOT_FOUND);
            } else {
                if (invitationRequestDTO.getCompanyId() != null) {
                    Optional<Company> companyResult = companyRepo.findById(invitationRequestDTO.getCompanyId());
                    if (companyResult.isPresent()) {
                        Company company = companyResult.get();
                        myInvitation = invitationRepo.update(invitationId, invitationRequestDTO, roleById, company);
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
    public InvitationResponseDTO delete(UUID invitationId) {
        Invitation myInvitation;
        if (invitationId == null) {
            System.out.println("Invitation Id is null, cannot proceed with Invitation delete.");
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ID_NOT_FOUND);
        }
        myInvitation = invitationRepo.findById(invitationId);
        if (myInvitation == null) {
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVALID_INFORMATION_INVITATION_DETAILS_NOT_DELETED);
        }
        System.out.println("Invitation-Deleted successfully");
        return InvitationMapper.toInvitationResponseDTO(invitationRepo.delete(invitationId), ResponseStatus.SUCCESSFULLY_DELETED);
    }

    @Override
    public List<InvitationResponseDTO> findAll() {
        return InvitationMapper.toInvitationResponseDTOs(this.invitationRepo.findAll(), ResponseStatus.SUCCESSFULLY_FOUND);
    }

    @Override
    public InvitationResponseDTO findById(UUID invitationId) {
        Invitation myInvitation;
        if (invitationId == null) {
            System.out.println("Invitation Id is null, cannot proceed with Invitation search.");
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVITATION_ID_NOT_FOUND);
        }
        myInvitation = invitationRepo.findById(invitationId);
        if (myInvitation == null) {
            return InvitationMapper.toInvitationResponseDTO(new Invitation(), ResponseStatus.INVALID_INFORMATION_INVITATION_DETAILS_NOT_FOUND);
        }
        System.out.println("Invitation-Found successfully");
        return InvitationMapper.toInvitationResponseDTO(myInvitation, ResponseStatus.SUCCESSFULLY_FOUND);
    }
}
