package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.application.dto.permission.PermissionResponseDTO;
import fs19.java.backend.application.PermissionServiceImpl;
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
 * This class will work as the main permission entity controller and each method will work as endpoints
 */

@RestController
@RequestMapping("app/v1/permissions")
@OpenAPIDefinition(info = @Info(title = "Permission API", version = "v1"))
public class PermissionController {

    @Autowired
    private PermissionServiceImpl permissionService;

    /**
     * Create a permission object using the permission-name
     *
     * @param permissionRequestDTO PermissionRequestDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<GlobalResponse<PermissionResponseDTO>> createPermission(@RequestBody @Valid PermissionRequestDTO permissionRequestDTO) {
        PermissionResponseDTO thePermissionDTO = permissionService.createPermission(permissionRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.CREATED, thePermissionDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), thePermissionDTO, ResponseHandler.convertResponseStatusToError(thePermissionDTO.getStatus())), responseCode);
    }

    /**
     * Update a Permission by id
     *
     * @param permissionRequestDTO PermissionRequestDTO
     * @return
     */
    @PutMapping("/{permissionId}")
    public ResponseEntity<GlobalResponse<PermissionResponseDTO>> updatePermission(@PathVariable UUID permissionId, @RequestBody @Valid PermissionRequestDTO permissionRequestDTO) {
        PermissionResponseDTO thePermissionDTo = permissionService.updatePermission(permissionId, permissionRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, thePermissionDTo.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), thePermissionDTo, ResponseHandler.convertResponseStatusToError(thePermissionDTo.getStatus())), responseCode);
    }

    /**
     * Delete Permission By Id
     *
     * @param permissionId
     * @return
     */
    @DeleteMapping("/{permissionId}")
    public ResponseEntity<GlobalResponse<PermissionResponseDTO>> deletePermissionById(@PathVariable UUID permissionId) {
        PermissionResponseDTO permissionResponseDTO = permissionService.deletePermission(permissionId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, permissionResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), permissionResponseDTO, ResponseHandler.convertResponseStatusToError(permissionResponseDTO.getStatus())), responseCode);
    }

    /**
     * Return the Permission
     * @return
     */
    @GetMapping
    public ResponseEntity<GlobalResponse<List<PermissionResponseDTO>>> getPermissions() {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), permissionService.getPermissions()), HttpStatus.OK);
    }

    /**
     * Return the permission according to given Id
     * @return
     */
    @GetMapping("/{permissionId}")
    public ResponseEntity<GlobalResponse<PermissionResponseDTO>> getPermissionById(@PathVariable UUID permissionId) {
        PermissionResponseDTO permissionResponseDTO = permissionService.getPermissionById(permissionId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, permissionResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), permissionResponseDTO, ResponseHandler.convertResponseStatusToError(permissionResponseDTO.getStatus())), responseCode);
    }

    /**
     * Return the permission according to given name
     * @return
     */
    @GetMapping("search/{permissionName}")
    public ResponseEntity<GlobalResponse<PermissionResponseDTO>> getPermissionByName(@PathVariable String permissionName) {
        PermissionResponseDTO permissionResponseDTO = permissionService.getPermissionByName(permissionName);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, permissionResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), permissionResponseDTO, ResponseHandler.convertResponseStatusToError(permissionResponseDTO.getStatus())), responseCode);
    }


}
