package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.role.RolePermissionRequestDTO;
import fs19.java.backend.application.dto.role.RolePermissionResponseDTO;
import fs19.java.backend.application.service.RolePermissionServiceImpl;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import fs19.java.backend.presentation.shared.response.ResponseHandler;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/app/rolepermission")
@OpenAPIDefinition(info = @Info(title = "RolePermission API", version = "v1"))
public class RolePermissionController {

    @Autowired
    private RolePermissionServiceImpl rolePermissionService;

    /**
     * Assign permission to role
     * @param rolePermissionRequestDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<GlobalResponse<RolePermissionResponseDTO>> assignPermissionToRole(@RequestBody @Valid RolePermissionRequestDTO rolePermissionRequestDTO) {
        RolePermissionResponseDTO theResponse = rolePermissionService.assignPermissionToRole(rolePermissionRequestDTO);
        HttpStatus responseCode = ResponseHandler.getResponseCode(HttpStatus.CREATED, theResponse.getStatus());
        return new ResponseEntity<>(new GlobalResponse<>(responseCode.value(), theResponse, ResponseHandler.convertResponseStatusToError(theResponse.getStatus())), responseCode);
    }
}
