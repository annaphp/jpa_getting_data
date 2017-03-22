package repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import model.User;

@Repository
@Transactional
public class UserRepo {
	
	@PersistenceContext
	private EntityManager em;
	
	public User save(User user){
		em.persist(user);
		return user;
	}
	
	public User update(User user){
		em.merge(user);
		return user;
	}
	
	public User findById(Long id){	
		return em
				.createQuery("select distinct u from User u left join fetch u.roles where u.id = :id", User.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findOnlyUsersWithRoles (){
		Query query = em.createQuery("select distinct u from User u join fetch u.roles");
		return query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<User> findAllUsers(){
		Query query = em.createNamedQuery("findAllUsers");
	    return query.getResultList();	
	}
	

}
