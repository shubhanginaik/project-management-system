package fs19.java.backend.application.mapper;

import fs19.java.backend.application.dto.CompanyDTO;
import fs19.java.backend.domain.entity.Company;

public class CompanyMapper {

    public static Company toEntity(CompanyDTO companyDTO) {
        return new Company(
                companyDTO.getId(),
                companyDTO.getName(),
                companyDTO.getCreatedDate(),
                companyDTO.getCreatedBy()
        );
    }

    public static CompanyDTO toDTO(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getName(),
                company.getCreatedDate(),
                company.getCreatedBy()
        );
    }
}