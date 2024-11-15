package fs19.java.backend.infrastructure;

import fs19.java.backend.domain.abstraction.CompanyRepository;
import fs19.java.backend.domain.entity.Company;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CompanyRepoImpl implements CompanyRepository {

    private final Map<UUID, Company> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public void save(Company company) {
        inMemoryDatabase.put(company.getId(), company);
    }

    @Override
    public Optional<Company> findById(UUID id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }

    @Override
    public List<Company> findAll() {
        return new ArrayList<>(inMemoryDatabase.values());
    }

    @Override
    public void deleteById(UUID id) {
        inMemoryDatabase.remove(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return inMemoryDatabase.containsKey(id);
    }
}
