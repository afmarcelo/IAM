package fr.mfa.iam.services.dao;
import java.util.List;
import fr.mfa.aim.datamodel.Identity;
import fr.mfa.aim.datamodel.User;
import fr.mfa.iam.exceptions.CustomException;
/**
 * Identity DAO provides the behavior of the DAO strategy for the project.
 * @author marcelo
 */
public interface IdentityDAO {
	/**
	 * List of the identities found according to the search criteria.
	 * @param criteria
	 * @return
	 */
	public List<Identity> search(Identity criteria);
	/**
	 * Create a new identity.
	 * @param identity
	 */
	public void create(Identity identity);
	/**
	 * Update identity information.
	 * @param identity
	 * @throws CustomException
	 */
	public void update(Identity identity) throws CustomException;
	/**
	 * Delete the informed identity.
	 * @param identity
	 * @throws CustomException
	 */
	public void delete(Identity identity) throws CustomException;
	/**
	 * Retrieve all the identities.
	 * @return
	 * @throws Exception
	 */
	public List<Identity> readAll() throws Exception ;
	/**
	 * Search for user according to the search criteria.
	 * @param citeria
	 * @return
	 */
	public User searchUser(User citeria);
}
