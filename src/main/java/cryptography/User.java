/**
 * 
 */
package cryptography;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;

/**
 * @author bwarren
 *
 */
public class User {
	private String name;
	private PublicKey pubKey;
	private PrivateKey privKey;
	private byte[] sig;
	private byte[] msg;
	private byte[] sharedSecret;
	private SecretKey secretKey;
	
	public User(String name) {
		this.name = name;
	}
	
	public User(String name, KeyPairGenerator kpg) {
		this.name = name;
		KeyPair userKP = kpg.genKeyPair();
		this.pubKey = userKP.getPublic();
		this.privKey = userKP.getPrivate();
	}
	
	public String computeValue(User recipient) throws NoSuchAlgorithmException, InvalidKeyException {
		//get key agreement controller
		KeyAgreement keyAg	= KeyAgreement.getInstance("ECDH");
		
		//initialize with the user's private key
		keyAg.init(this.privKey);
		
		//compute the shared value
		keyAg.doPhase(recipient.getPubKey(), true);
		
		setSharedSecret(keyAg.generateSecret());
		
		//computeValue(recipient, keyAg);
		String secret = (new BigInteger(1, keyAg.generateSecret()).toString(16)).toUpperCase();
		
		return secret;
	}

	public PublicKey getPubKey() {
		return pubKey;
	}
	
	public PrivateKey getPrivKey() {
		return privKey;
	}
	
	public String getName() {
		return name;
	}

	public byte[] getSig() {
		return sig;
	}

	public void setSig(byte[] sig) {
		this.sig = sig;
	}

	public byte[] getMsg() {
		return msg;
	}

	public void setMsg(byte[] msg) {
		this.msg = msg;
	}

	public byte[] getSharedSecret() {
		return sharedSecret;
	}

	public void setSharedSecret(byte[] sharedSecret) {
		this.sharedSecret = sharedSecret;
	}

	public SecretKey getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(SecretKey secretKey) {
		this.secretKey = secretKey;
	}
}
