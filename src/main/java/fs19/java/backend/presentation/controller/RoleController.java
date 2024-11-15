package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.application.dto.role.RoleResponseDTO;
import fs19.java.backend.application.service.RoleServiceImpl;
import fs19.java.backend.presentation.shared.response.ResponseHandler;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
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
 * This class will work as the main role entity controller and each method will work as endpoints
 */
@RestController
@RequestMapping("v1/app/roles")
@OpenAPIDefinition(info = @Info(title = "Role API", version = "v1"))
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    /**
     * Create a role object using the name
     *
     * @param roleRequestDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<GlobalResponse<RoleResponseDTO>> createRole(@RequestBody @Valid RoleRequestDTO roleRequestDTO) {
        RoleResponseDTO theRoleResponse = roleService.createRole(roleRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.CREATED, theRoleResponse.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theRoleResponse, ResponseHandler.convertResponseStatusToError(theRoleResponse.getStatus())), responseCode);
    }

    /**
     * Update a role by id
     *
     * @param roleUpdateDTO
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse<RoleResponseDTO>> updateRole(@PathVariable UUID id, @RequestBody @Valid RoleRequestDTO roleUpdateDTO) {
        RoleResponseDTO theRoleResponseDTO = roleService.updateRole(id, roleUpdateDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, theRoleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theRoleResponseDTO, ResponseHandler.convertResponseStatusToError(theRoleResponseDTO.getStatus())), responseCode);
    }

    /**
     * Delete Role By Id
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse<RoleResponseDTO>> deleteRoleById(@PathVariable UUID id) {
        RoleResponseDTO roleResponseDTO = roleService.deleteRole(id);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, roleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), roleResponseDTO, ResponseHandler.convertResponseStatusToError(roleResponseDTO.getStatus())), responseCode);
    }

    /**
     * Return the role
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<GlobalResponse<List<RoleResponseDTO>>> getRoles() {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), roleService.getRoles()), HttpStatus.OK);
    }

    /**
     * Return the role according to given Id
     *
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<RoleResponseDTO>> getRoleById(@PathVariable UUID id) {
        RoleResponseDTO roleResponseDTO = roleService.getRoleById(id);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, roleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), roleResponseDTO, ResponseHandler.convertResponseStatusToError(roleResponseDTO.getStatus())), responseCode);
    }

    /**
     * Return the role according to given name
     *
     * @return
     */
    @GetMapping("search/{name}")
    public ResponseEntity<GlobalResponse<RoleResponseDTO>> getRoleByName(@PathVariable String name) {
        RoleResponseDTO roleResponseDTO = roleService.getRoleByName(name);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.OK, roleResponseDTO.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), roleResponseDTO, ResponseHandler.convertResponseStatusToError(roleResponseDTO.getStatus())), responseCode);
    }


}