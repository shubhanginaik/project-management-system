package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.company.CompanyDTO;
import java.util.List;
import java.util.UUID;

public interface CompanyService {
    CompanyDTO createCompany(CompanyDTO companyDTO);
    CompanyDTO updateCompany(UUID id, CompanyDTO companyDTO);
    CompanyDTO getCompanyById(UUID id);
    List<CompanyDTO> getAllCompanies();
    void deleteCompany(UUID id);
}
