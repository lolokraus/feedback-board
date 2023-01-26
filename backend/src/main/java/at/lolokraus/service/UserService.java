package at.lolokraus.service;

import at.lolokraus.model.User;
import at.lolokraus.model.UserRequest;

import java.util.List;

public interface UserService {
  void resetCredentials();

  User findById(Long id);

  User findByUsername(String username);

  List<User> findAll();

  User save(UserRequest user);
}
