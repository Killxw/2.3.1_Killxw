package web.dao;

import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void add(User user);
    void delete(Long id);
    void change(User user);
    List<User> listUsers();
    Optional<User> findUserById(Long id);
}
