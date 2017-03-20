package repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
		System.out.println("*****" + user.getId());
		return user;
	}
	
	public User update(User user){
		em.merge(user);
		return user;
	}
	
	public User findById(Long id){
		return em.find(User.class, id);
	}
	

}
