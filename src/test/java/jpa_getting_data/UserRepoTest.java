package jpa_getting_data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.Config;
import model.Role;
import model.User;
import repo.RoleRepo;
import repo.UserRepo;



@ContextConfiguration(classes=Config.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepoTest {

	@Autowired
	UserRepo repo;
	
	@Autowired
	RoleRepo roleRepo;
	
	User user;
	Role role;
	
	@Before
	public void setup(){
	  user = new User("myLogin");	
	  role = new Role("administrator");
	}
	
	@Test
	public void shouldSave(){
		user = repo.save(user);
		role = roleRepo.save(role);
		assertNotNull(repo.findById(user.getId()));
		assertNotNull(roleRepo.findById(role.getId()));
	}
	
	@Test
	public void shouldAddRole(){
		user = repo.save(user);
		role = roleRepo.save(role);
		user.getRoles().add(role);
		repo.update(user);
		user = repo.findById(user.getId());	
		assertTrue(user.getRoles().contains(role));
	}
	
	@Test
	public void shouldRemoveRole(){
		user = repo.save(user);
		role = roleRepo.save(role);
		user.getRoles().add(role);
		repo.update(user);
		user = repo.findById(user.getId());	
		user.getRoles().remove(role);
		repo.update(user);
		assertFalse(user.getRoles().contains(role));
	}
}
