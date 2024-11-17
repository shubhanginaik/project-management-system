package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import fs19.java.backend.application.RolePermissionServiceImpl;
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

@RestController
@RequestMapping("v1/app/rolePermission")
@OpenAPIDefinition(info = @Info(title = "RolePermission API", version = "v1"))
public class RolePermissionController {

    @Autowired
    private RolePermissionServiceImpl rolePermissionService;

    /**
     * Assign permission to role
     *
     * @param rolePermissionRequestDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<GlobalResponse<RolePermissionResponseDTO>> assignPermissionToRole(@RequestBody @Valid RolePermissionRequestDTO rolePermissionRequestDTO) {
        RolePermissionResponseDTO theResponse = rolePermissionService.assignPermissionToRole(rolePermissionRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.CREATED, theResponse.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theResponse, ResponseHandler.convertResponseStatusToError(theResponse.getStatus())), responseCode);
    }

    /**
     * Role permission Id
     *
     * @param rolePermissionId         UUID
     * @param rolePermissionRequestDTO RolePermissionRequestDTO
     * @return
     */
    @PutMapping("/{rolePermissionId}")
    public ResponseEntity<GlobalResponse<RolePermissionResponseDTO>> updateRolePermissionId(@PathVariable UUID rolePermissionId, @RequestBody @Valid RolePermissionRequestDTO rolePermissionRequestDTO) {
        RolePermissionResponseDTO theRoleResponseDTO = rolePermissionService.updateRolePermission(rolePermissionId, rolePermissionRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theRoleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theRoleResponseDTO, ResponseHandler.convertResponseStatusToError(theRoleResponseDTO.getStatus())), responseCode);
    }

    /**
     * Delete Role-Permission By Id
     *
     * @param rolePermissionId
     * @return
     */
    @DeleteMapping("/{rolePermissionId}")
    public ResponseEntity<GlobalResponse<RolePermissionResponseDTO>> deleteRolePermissionById(@PathVariable UUID rolePermissionId) {
        RolePermissionResponseDTO roleResponseDTO = rolePermissionService.deleteRolePermission(rolePermissionId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, roleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), roleResponseDTO, ResponseHandler.convertResponseStatusToError(roleResponseDTO.getStatus())), responseCode);
    }




    /**
     * Return the role-Permission
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<GlobalResponse<List<RolePermissionResponseDTO>>> getRolePermissions() {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), rolePermissionService.getRolePermissions()), HttpStatus.OK);
    }



    /**
     * Return the role-permission according to given Id
     *
     * @return
     */
    @GetMapping("/{rolePermissionId}")
    public ResponseEntity<GlobalResponse<RolePermissionResponseDTO>> getRolePermissionById(@PathVariable UUID rolePermissionId) {
        RolePermissionResponseDTO roleResponseDTO = rolePermissionService.getRolePermissionById(rolePermissionId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, roleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), roleResponseDTO, ResponseHandler.convertResponseStatusToError(roleResponseDTO.getStatus())), responseCode);
    }


    /**
     * Return the role-permission according to given Id
     *
     * @return
     */
    @GetMapping("/{roleId}/{permissionId}")
    public ResponseEntity<GlobalResponse<RolePermissionResponseDTO>> getExistingRecord(@PathVariable UUID roleId,@PathVariable UUID permissionId) {
        RolePermissionResponseDTO roleResponseDTO = rolePermissionService.getExistingRecord(roleId,permissionId);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, roleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), roleResponseDTO, ResponseHandler.convertResponseStatusToError(roleResponseDTO.getStatus())), responseCode);
    }


    /**
     * Return the permission according to given permission-Id
     *
     * @return
     */
    @GetMapping("findAllRoles/{permissionId}")
    public ResponseEntity<GlobalResponse<List<RolePermissionResponseDTO>>> getRolePermissionByPermissionId(@PathVariable UUID permissionId) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), rolePermissionService.getRolesByPermissionByPermissionId(permissionId)), HttpStatus.OK);
    }

    /**
     * Return the roles according to given rolesId
     *
     * @return
     */
    @GetMapping("findAllPermissions/{roleId}")
    public ResponseEntity<GlobalResponse<List<RolePermissionResponseDTO>>> getRolePermissionByRoleId(@PathVariable UUID roleId) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), rolePermissionService.getPermissionByRoleId(roleId)), HttpStatus.OK);
    }



}
