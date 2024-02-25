package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaolmp implements UserDao {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserDaolmp(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public void add(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            entityManager.persist(user);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = entityManager.find(User.class, id);

        if (user != null) {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();
                entityManager.remove(user);
                entityTransaction.commit();
            } catch (Exception e) {
                if (entityTransaction.isActive()) {
                    entityTransaction.rollback();
                }
                e.printStackTrace();
            } finally {
                entityManager.close();
            }
        }
    }

    @Override
    public void change(long id, String firstName, String lastName, String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = entityManager.find(User.class, id);

        if (user != null) {
            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                entityManager.merge(user);
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                e.printStackTrace();
            } finally {
                entityManager.close();
            }
        }
    }


    @Override
    public List<User> listUsers() {
        String jpql = "SELECT u FROM User u";
        TypedQuery<User> query = entityManagerFactory.createEntityManager().createQuery(jpql, User.class);
        return query.getResultList();
    }
    @Override
    public Optional<User> findUserById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.id = :id";
            TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
            query.setParameter("id", id);

            User result = query.getSingleResult();
            return Optional.ofNullable(result);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }
}
