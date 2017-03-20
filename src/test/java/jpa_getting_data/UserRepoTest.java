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
import repo.UserRepo;



@ContextConfiguration(classes=Config.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepoTest {

	@Autowired
	UserRepo repo;
	
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
		assertNotNull(repo.findById(user.getId()));
	}
	
}