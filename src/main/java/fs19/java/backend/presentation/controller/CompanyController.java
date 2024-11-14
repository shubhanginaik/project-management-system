package fs19.java.backend.presentation.controller;

import fs19.java.backend.application.dto.CompanyDTO;
import fs19.java.backend.application.service.CompanyService;
import fs19.java.backend.presentation.shared.response.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<CompanyDTO>> createCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        CompanyDTO createdCompany = companyService.createCompany(companyDTO);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), createdCompany), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse<CompanyDTO>> updateCompany(@PathVariable UUID id, @Valid @RequestBody CompanyDTO companyDTO) {
        CompanyDTO updatedCompany = companyService.updateCompany(id, companyDTO);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), updatedCompany), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<CompanyDTO>> getCompanyById(@PathVariable UUID id) {
        CompanyDTO company = companyService.getCompanyById(id);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), company), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<CompanyDTO>>> getAllCompanies() {
        List<CompanyDTO> companies = companyService.getAllCompanies();
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), companies), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse<Void>> deleteCompany(@PathVariable UUID id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null), HttpStatus.NO_CONTENT);
    }

}