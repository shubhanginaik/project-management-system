package fs19.java.backend.application.service;

import fs19.java.backend.application.dto.CompanyDTO;
import fs19.java.backend.application.mapper.CompanyMapper;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.abstraction.CompanyRepository;
import fs19.java.backend.presentation.shared.exception.CompanyNotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final String COMPANY_NOT_FOUND_MESSAGE = "Company with ID %s not found";
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        Company company = CompanyMapper.toEntity(companyDTO);
        company.setId(UUID.randomUUID());
        company.setCreatedDate(ZonedDateTime.now());
        companyRepository.save(company);
        return CompanyMapper.toDTO(company);
    }

    @Override
    public CompanyDTO updateCompany(UUID id, CompanyDTO companyDTO) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, id)));
        existingCompany.setName(companyDTO.getName());
        companyRepository.save(existingCompany);
        return CompanyMapper.toDTO(existingCompany);
    }

    @Override
    public CompanyDTO getCompanyById(UUID id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, id)));
        return CompanyMapper.toDTO(company);
    }

    @Override
    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(CompanyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCompany(UUID id) {
        if (!companyRepository.existsById(id)) {
            throw new CompanyNotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, id));
        }
        companyRepository.deleteById(id);
    }
}
