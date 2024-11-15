package fs19.java.backend.domain.abstraction;

import fs19.java.backend.domain.entity.Company;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    void save(Company company);
    Optional<Company> findById(UUID id);
    List<Company> findAll();
    void deleteById(UUID id);
    boolean existsById(UUID id);
}
