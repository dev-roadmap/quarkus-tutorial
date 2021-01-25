package io.vepo.tutorial.quarkus.user;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.Size;

@Transactional
@ApplicationScoped
public class Users {
    @PersistenceContext
    EntityManager em;

    public List<User> list() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        query.from(User.class);
        return em.createQuery(query).getResultList();
    }

    public User create(User user) {
        if (Objects.nonNull(user.getId())) {
            throw new IllegalStateException("Id should be null!");
        }
        em.persist(user);
        em.detach(user);
        return user;
    }

    public Optional<User> findByUsername(@Size(min = 4, max = 15) String username) {
        TypedQuery<User> query = em.createNamedQuery("User.findByUsername", User.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst();
    }

    public User get(int userId) {
        return em.find(User.class, userId);
    }
}
