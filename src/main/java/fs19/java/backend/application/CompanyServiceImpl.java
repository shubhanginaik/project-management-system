package fs19.java.backend.application;

import fs19.java.backend.application.dto.company.CompanyRequestDTO;
import fs19.java.backend.application.dto.company.CompanyResponseDTO;
import fs19.java.backend.application.dto.company.CompanyUpdateDTO;
import fs19.java.backend.application.mapper.CompanyMapper;
import fs19.java.backend.application.service.CompanyService;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.JpaRepositories.CompanyJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.presentation.shared.exception.CompanyNotFoundException;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final String COMPANY_NOT_FOUND_MESSAGE = "Company with ID %s not found";
    private static final String USER_NOT_FOUND_MESSAGE = "User not found with ID %s";

    private final CompanyJpaRepo companyRepository;
    private final UserJpaRepo userRepository;

    public CompanyServiceImpl(CompanyJpaRepo companyRepository, UserJpaRepo userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CompanyResponseDTO createCompany(CompanyRequestDTO companyDTO) {
        User createdBy = userRepository.findById(companyDTO.getCreatedBy())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, companyDTO.getCreatedBy())));

        // Use CompanyMapper to map the DTO to entity
        Company company = CompanyMapper.toEntity(companyDTO, createdBy);
        company.setCreatedDate(ZonedDateTime.now());

        // Save the company entity, UUID and createdDate will be automatically generated
        companyRepository.save(company);

        // Return the DTO with the correct ID and createdDate
        return CompanyMapper.toResponseDTO(company);
    }

    @Override
    public CompanyResponseDTO updateCompany(UUID id, CompanyUpdateDTO companyDTO) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, id)));

        User createdBy = userRepository.findById(companyDTO.getCreatedBy())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, companyDTO.getCreatedBy())));

        // Use CompanyMapper to update the existing entity
        CompanyMapper.updateEntity(existingCompany, companyDTO, createdBy);
        companyRepository.save(existingCompany);

        return CompanyMapper.toResponseDTO(existingCompany);
    }

    @Override
    public CompanyResponseDTO getCompanyById(UUID id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE, id)));

        return CompanyMapper.toResponseDTO(company);
    }

    @Override
    public List<CompanyResponseDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(CompanyMapper::toResponseDTO)
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
