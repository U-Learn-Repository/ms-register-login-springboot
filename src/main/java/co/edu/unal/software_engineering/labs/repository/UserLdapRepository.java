package co.edu.unal.software_engineering.labs.repository;

import java.util.List;

import co.edu.unal.software_engineering.labs.pojo.LoginUserPOJO;
import co.edu.unal.software_engineering.labs.pojo.RegisterUserPOJO;

public interface UserLdapRepository {

	public List<RegisterUserPOJO> list();
	public Boolean exist(LoginUserPOJO p);
	public String create(RegisterUserPOJO p);
	public String update(RegisterUserPOJO p);
	public String remove(String userId);
}