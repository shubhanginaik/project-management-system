package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.CompanyDTO;
import fs19.java.backend.application.service.CompanyService;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "Companies", description = "Manage companies")
@RestController
@RequestMapping("/v1/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Operation(summary = "Create a company", description = "Creates a new company with the provided details.")
    @PostMapping
    public ResponseEntity<GlobalResponse<CompanyDTO>> createCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        CompanyDTO createdCompany = companyService.createCompany(companyDTO);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), createdCompany), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a company", description = "Updates the details of an existing company.")
    @PutMapping("/{companyId}")
    public ResponseEntity<GlobalResponse<CompanyDTO>> updateCompany(@PathVariable UUID companyId, @Valid @RequestBody CompanyDTO companyDTO) {
        CompanyDTO updatedCompany = companyService.updateCompany(companyId, companyDTO);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), updatedCompany), HttpStatus.OK);
    }

    @Operation(summary = "Get a company by ID", description = "Retrieves the details of a company by its ID.")
    @GetMapping("/{companyId}")
    public ResponseEntity<GlobalResponse<CompanyDTO>> getCompanyById(@PathVariable UUID companyId) {
        CompanyDTO company = companyService.getCompanyById(companyId);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), company), HttpStatus.OK);
    }

    @Operation(summary = "Get all companies", description = "Retrieves the details of all companies.")
    @GetMapping
    public ResponseEntity<GlobalResponse<List<CompanyDTO>>> getAllCompanies() {
        List<CompanyDTO> companies = companyService.getAllCompanies();
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), companies), HttpStatus.OK);
    }

    @Operation(summary = "Delete a company", description = "Deletes a company by its ID.")
    @DeleteMapping("/{companyId}")
    public ResponseEntity<GlobalResponse<Void>> deleteCompany(@PathVariable UUID companyId) {
        companyService.deleteCompany(companyId);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null), HttpStatus.NO_CONTENT);
    }

}