package at.lolokraus.service;

import at.lolokraus.model.Authority;
import at.lolokraus.model.UserRoleName;

import java.util.List;

public interface AuthorityService {
  List<Authority> findById(Long id);

  List<Authority> findByName(UserRoleName name);

}
