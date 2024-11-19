package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.InvitationServiceImpl;
import fs19.java.backend.application.dto.invitation.InvitationRequestDTO;
import fs19.java.backend.application.dto.invitation.InvitationResponseDTO;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import fs19.java.backend.presentation.shared.response.ResponseHandler;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * This class will work as the main invitation entity controller and each method will work as endpoints
 */

@RestController
@RequestMapping("app/v1/invitations")
@OpenAPIDefinition(info = @Info(title = "Invitation API", version = "v1"))
public class InvitationController {

    @Autowired
    private InvitationServiceImpl invitationService;


    /**
     * Create an invitation object using the define information
     *
     * @param invitationRequestDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<GlobalResponse<InvitationResponseDTO>> createInvitation(@RequestBody @Valid InvitationRequestDTO invitationRequestDTO) {
        InvitationResponseDTO theInvitationResponseDTO = invitationService.createAnInvitation(invitationRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.CREATED, theInvitationResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theInvitationResponseDTO, ResponseHandler.convertResponseStatusToError(theInvitationResponseDTO.getStatus())), responseCode);
    }

    /**
     * Update a Invitation by id
     *
     * @param invitationRequestDTO
     * @return
     */
    @PutMapping("/{invitationId}")
    public ResponseEntity<GlobalResponse<InvitationResponseDTO>> updateRole(@PathVariable UUID invitationId, @RequestBody InvitationRequestDTO invitationRequestDTO) {
        InvitationResponseDTO theInvitationResponseDTO = invitationService.updateInvitation(invitationId, invitationRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theInvitationResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theInvitationResponseDTO, ResponseHandler.convertResponseStatusToError(theInvitationResponseDTO.getStatus())), responseCode);
    }


    /**
     * Delete Invitation By invitation-Id
     *
     * @param invitationId
     * @return
     */
    @DeleteMapping("/{invitationId}")
    public ResponseEntity<GlobalResponse<InvitationResponseDTO>> deleteInvitationById(@PathVariable UUID invitationId) {
        InvitationResponseDTO theInvitationResponseDTO = invitationService.deleteInvitation(invitationId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theInvitationResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theInvitationResponseDTO, ResponseHandler.convertResponseStatusToError(theInvitationResponseDTO.getStatus())), responseCode);
    }

    /**
     * Return the invitation list
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<GlobalResponse<List<InvitationResponseDTO>>> getInvitations() {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), invitationService.getInvitations()), HttpStatus.OK);
    }

    /**
     * Return the role according to given Id
     *
     * @return
     */
    @GetMapping("/{invitationId}")
    public ResponseEntity<GlobalResponse<InvitationResponseDTO>> getInvitationById(@PathVariable UUID invitationId) {
        InvitationResponseDTO theInvitationResponseDTO = invitationService.getInvitationById(invitationId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theInvitationResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theInvitationResponseDTO, ResponseHandler.convertResponseStatusToError(theInvitationResponseDTO.getStatus())), responseCode);
    }

}
