package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaolmp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public void change(long id, String firstName, String lastName, String email) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            entityManager.merge(user);
        }
    }

    @Override
    public List<User> listUsers() {
        String jpql = "SELECT u FROM User u";
        return entityManager.createQuery(jpql, User.class).getResultList();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        String jpql = "SELECT u FROM User u WHERE u.id = :id";
        User user = entityManager.createQuery(jpql, User.class)
                .setParameter("id", id)
                .getSingleResult();
        return Optional.ofNullable(user);
    }
}
