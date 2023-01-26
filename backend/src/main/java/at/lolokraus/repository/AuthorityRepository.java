package at.lolokraus.repository;

import at.lolokraus.model.Authority;
import at.lolokraus.model.UserRoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(UserRoleName name);
}
