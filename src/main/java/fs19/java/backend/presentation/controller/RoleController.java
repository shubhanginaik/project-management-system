package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.application.dto.role.RoleResponseDTO;
import fs19.java.backend.application.RoleServiceImpl;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/app/roles")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @PostMapping("/createRoles")
    public ResponseEntity<GlobalResponse<RoleResponseDTO>> createRoles(@RequestBody @Valid RoleRequestDTO roleRequestDTO) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), roleService.createRole(roleRequestDTO)), HttpStatus.OK);
    }

    @PutMapping("/updateRoles")
    public ResponseEntity<GlobalResponse<RoleResponseDTO>> updateRoles(@RequestBody @Valid RoleRequestDTO roleRequestDTO) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), roleService.updateRole(roleRequestDTO)), HttpStatus.OK);
    }

    @DeleteMapping("/deleteRoles")
    public ResponseEntity<GlobalResponse<RoleResponseDTO>> deleteRoles(@RequestBody @Valid RoleRequestDTO roleRequestDTO) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), roleService.deleteRole(roleRequestDTO)), HttpStatus.OK);
    }

    @GetMapping("/getRoles")
    public ResponseEntity<GlobalResponse<List<RoleResponseDTO>>> getRoles() {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), roleService.getRoles()), HttpStatus.OK);
    }

}