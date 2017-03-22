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

@ContextConfiguration(classes = Config.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepoTest {

	@Autowired
	UserRepo repo;

	@Autowired
	RoleRepo roleRepo;

	User user;
	User user2;
	Role role;

	@Before
	public void setup() {
		user = new User("helloWorld");
		user2 = new User("Zhopa");

		role = new Role("moderator");
	}

	@Test
	public void shouldSave() {
		user = repo.save(user);
		role = roleRepo.save(role);
		assertNotNull(repo.findById(user.getId()));
		assertNotNull(roleRepo.findById(role.getId()));
	}

	@Test
	public void shouldAddUser() {
		user = repo.save(user);
		user = repo.findById(user.getId());
		role = roleRepo.save(role);

		// bidirectional linking:
		role.setUser(user);
		user.getRoles().add(role);

		// saving owning side, creates new relationship.
		roleRepo.update(role);

		role = roleRepo.findById(role.getId());
		assertTrue(role.getUser().equals(user));
	}

	@Test
	public void shouldfindOnlyUsersWithRoles(){
		user2 = repo.save(user2);
		
		user = repo.save(user);
		user = repo.findById(user.getId());
		role = roleRepo.save(role);
		
		// bidirectional linking:
		role.setUser(user);
		user.getRoles().add(role);
		
		// saving owning side, creates new relationship.
		roleRepo.update(role);
		
		assertTrue(repo.findOnlyUsersWithRoles().contains(user));
		assertFalse(repo.findOnlyUsersWithRoles().contains(user2));
		
	   
	}

	@Test 
	public void shouldfindAllUsers(){
		user2 = repo.save(user2);
		
		user = repo.save(user);
		user = repo.findById(user.getId());
		role = roleRepo.save(role);
		
		// bidirectional linking:
		role.setUser(user);
		user.getRoles().add(role);
		
		// saving owning side, creates new relationship.
		roleRepo.update(role);
		assertTrue(repo.findAllUsers().contains(user));
		assertTrue(repo.findAllUsers().contains(user2));
	}

}
