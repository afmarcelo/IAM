package fr.mfa.iam.services.dao;

import java.io.IOException;
import java.util.List;

import fr.mfa.aim.datamodel.Identity;
import fr.mfa.aim.datamodel.User;
import fr.mfa.iam.exceptions.CustomException;


/**
 * TODO document
 * @author tbrou
 *
 */
public interface IdentityDAO {

	public List<Identity> search(Identity criteria);
	
	public void create(Identity identity);
	
	public void update(Identity identity) throws CustomException;
	
	public void delete(Identity identity) throws CustomException;
	
	public List<Identity> readAll() throws Exception ;
	
	public User searchUser(User citeria);
	
}
