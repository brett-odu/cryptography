/**
 * 
 */
package cryptography;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.ECGenParameterSpec;

/**
 * @author bwarren
 *
 */
public class Exchange {
	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 */
	public User[] createAndExchange(User[] users) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
		//create key pair
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
		
		//set EC parameters (choose which EC)
		ECGenParameterSpec ecgps = new ECGenParameterSpec("secp256r1");
		
		//initialize key pair
		kpg.initialize(ecgps);
		
		//create two users
		User user1 = new User("Alice", kpg);
		User user2 = new User("Bob", kpg);
		
		users[0] = user1;
		users[1] = user2;
		
		return users;
	}
}
