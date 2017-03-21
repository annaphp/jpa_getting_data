package repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import model.Role;


@Repository
@Transactional
public class RoleRepo {

	@PersistenceContext
	private EntityManager em;
	
	public Role save(Role role){
		em.persist(role);
		return role;
	}
	
	public Role update(Role role){
		em.merge(role);
		return role;
	}
	
	public Role findById(Long id){
		return em.find(Role.class, id);
	}
}
