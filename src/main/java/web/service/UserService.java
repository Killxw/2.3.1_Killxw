package web.service;

import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void add(User user);
    void delete(Long id);
    void change(long id, String firstName, String lastName,String email);
    List<User> listUsers();
    Optional<User> findUserById(Long id);
}
