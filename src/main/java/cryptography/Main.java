package cryptography;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Color;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Main extends JFrame {
	//create a simple array of two users
	User[] users = {new User("Alice"), new User("Bob")};

	private JPanel contentPane;
	
	//variables
	private JTextArea textAreaAPub = new JTextArea();
	private JTextArea textAreaAPriv = new JTextArea();
	private JTextArea textAreaBPub = new JTextArea();
	private JTextArea textAreaBPriv = new JTextArea();
	private JTextArea textAreaASecVal = new JTextArea();
	private JTextArea textAreaBSecVal = new JTextArea();
	private JTextArea textAreaAliceSend = new JTextArea();
	private JTextArea textAreaBobSend = new JTextArea();
	private JTextArea textAreaAliceRec = new JTextArea();
	private JTextArea textAreaBobRec = new JTextArea();
	private JLabel lblAtoBVerify = new JLabel("Please Verify Signature");
	private JLabel lblBtoAVerify = new JLabel("Please Verify Signature");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1241, 699);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(588, 0, 111, 25);
		contentPane.add(lblName);
		
		JLabel lblPublicKey = new JLabel("Public Key");
		lblPublicKey.setBounds(568, 37, 111, 25);
		contentPane.add(lblPublicKey);
		
		JLabel lblPrivateKey = new JLabel("Private Key");
		lblPrivateKey.setBounds(568, 74, 111, 25);
		contentPane.add(lblPrivateKey);
		
		JLabel lblAlice = new JLabel("Alice");
		lblAlice.setBounds(135, 17, 111, 20);
		contentPane.add(lblAlice);
		
		JLabel lblBob = new JLabel("Bob");
		lblBob.setBounds(1017, 20, 70, 15);
		contentPane.add(lblBob);
		
		JLabel lblSecretValue = new JLabel("Secret Value");
		lblSecretValue.setBounds(554, 108, 114, 15);
		contentPane.add(lblSecretValue);
		
		textAreaAliceSend.setWrapStyleWord(true);
		textAreaAliceSend.setTabSize(4);
		textAreaAliceSend.setRows(7);
		textAreaAliceSend.setLineWrap(true);
		textAreaAliceSend.setColumns(40);
		textAreaAliceSend.setBounds(43, 328, 361, 76);
		contentPane.add(textAreaAliceSend);

		textAreaBobSend.setColumns(40);
		textAreaBobSend.setLineWrap(true);
		textAreaBobSend.setTabSize(4);
		textAreaBobSend.setRows(7);
		textAreaBobSend.setWrapStyleWord(true);
		textAreaBobSend.setText("");
		textAreaBobSend.setBounds(754, 311, 401, 76);
		contentPane.add(textAreaBobSend);
		
		textAreaAPub.setWrapStyleWord(true);
		textAreaAPub.setTabSize(4);
		textAreaAPub.setRows(7);
		textAreaAPub.setLineWrap(true);
		textAreaAPub.setEditable(false);
		textAreaAPub.setColumns(40);
		textAreaAPub.setBounds(31, 42, 387, 142);
		contentPane.add(textAreaAPub);
		
		textAreaAPriv.setWrapStyleWord(true);
		textAreaAPriv.setTabSize(4);
		textAreaAPriv.setRows(5);
		textAreaAPriv.setLineWrap(true);
		textAreaAPriv.setEditable(false);
		textAreaAPriv.setColumns(40);
		textAreaAPriv.setBounds(217, 196, 119, 25);
		contentPane.add(textAreaAPriv);
		
		textAreaBPub.setWrapStyleWord(true);
		textAreaBPub.setTabSize(4);
		textAreaBPub.setRows(5);
		textAreaBPub.setLineWrap(true);
		textAreaBPub.setEditable(false);
		textAreaBPub.setColumns(40);
		textAreaBPub.setBounds(754, 42, 387, 142);
		contentPane.add(textAreaBPub);
		
		textAreaBPriv.setWrapStyleWord(true);
		textAreaBPriv.setTabSize(4);
		textAreaBPriv.setRows(5);
		textAreaBPriv.setLineWrap(true);
		textAreaBPriv.setEditable(false);
		textAreaBPriv.setColumns(40);
		textAreaBPriv.setBounds(764, 196, 131, 20);
		contentPane.add(textAreaBPriv);
		
		textAreaASecVal.setWrapStyleWord(true);
		textAreaASecVal.setTabSize(4);
		textAreaASecVal.setRows(5);
		textAreaASecVal.setLineWrap(true);
		textAreaASecVal.setEditable(false);
		textAreaASecVal.setColumns(40);
		textAreaASecVal.setBounds(42, 233, 361, 47);
		contentPane.add(textAreaASecVal);
		
		textAreaBSecVal.setWrapStyleWord(true);
		textAreaBSecVal.setTabSize(4);
		textAreaBSecVal.setRows(5);
		textAreaBSecVal.setLineWrap(true);
		textAreaBSecVal.setEditable(false);
		textAreaBSecVal.setColumns(40);
		textAreaBSecVal.setBounds(754, 227, 361, 40);
		contentPane.add(textAreaBSecVal);
		
		textAreaAliceRec.setWrapStyleWord(true);
		textAreaAliceRec.setTabSize(4);
		textAreaAliceRec.setRows(7);
		textAreaAliceRec.setLineWrap(true);
		textAreaAliceRec.setEditable(false);
		textAreaAliceRec.setColumns(40);
		textAreaAliceRec.setBounds(43, 416, 361, 76);
		contentPane.add(textAreaAliceRec);
		
		textAreaBobRec.setWrapStyleWord(true);
		textAreaBobRec.setText("");
		textAreaBobRec.setTabSize(4);
		textAreaBobRec.setRows(7);
		textAreaBobRec.setLineWrap(true);
		textAreaBobRec.setEditable(false);
		textAreaBobRec.setColumns(40);
		textAreaBobRec.setBounds(764, 399, 401, 66);
		contentPane.add(textAreaBobRec);
		
		lblAtoBVerify.setBounds(1001, 518, 207, 25);
		contentPane.add(lblAtoBVerify);
		
		lblBtoAVerify.setBounds(347, 503, 167, 20);
		contentPane.add(lblBtoAVerify);

		JButton btnGenerateKeyPair = new JButton("Generate Key Pair");
		btnGenerateKeyPair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Exchange exchange = new Exchange();
				try {
					//call createAndExchange to create users
					users = exchange.createAndExchange(users);
					//show public and private keys of both users
					updateKeys();
				} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnGenerateKeyPair.setBounds(512, 135, 167, 25);
		contentPane.add(btnGenerateKeyPair);
		
		JButton btnComputeSecret = new JButton("Compute Secret Value");
		btnComputeSecret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//call computeValue to generate shared secret value
					computeValue();				
				} catch (InvalidKeyException | NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnComputeSecret.setBounds(484, 219, 222, 30);
		contentPane.add(btnComputeSecret);
		
		JButton btnAliceToBob = new JButton("Send Alice's Message to Bob");
		btnAliceToBob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//create signature from message
					Signature ecdsa = Signature.getInstance("SHA256withECDSA");
					ecdsa.initSign(users[0].getPrivKey());
					users[0].setMsg(textAreaAliceSend.getText().getBytes());
					ecdsa.update(users[0].getMsg());
					users[0].setSig(ecdsa.sign());
					
					//encrypt message
					SecretKey secretKey = new SecretKeySpec(users[0].getSharedSecret(), 0, users[0].getSharedSecret().length, "AES");
					users[0].setSecretKey(secretKey);
					Cipher cipher = Cipher.getInstance("AES");
					cipher.init(Cipher.ENCRYPT_MODE, secretKey);
					byte[] msgInBytes = textAreaAliceSend.getText().getBytes("UTF-8");
					byte[] cipherText = cipher.doFinal(msgInBytes);
					String cipherString = Base64.getEncoder().encodeToString(cipherText);
					
					//send encrypted message
					textAreaBobRec.setText(cipherString);
				} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | NoSuchPaddingException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAliceToBob.setBounds(433, 328, 273, 40);
		contentPane.add(btnAliceToBob);

		JButton btnBobToAlice = new JButton("Send Bob's Message to Alice");
		btnBobToAlice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//create signature from message
					Signature ecdsa = Signature.getInstance("SHA256withECDSA");
					ecdsa.initSign(users[1].getPrivKey());
					users[1].setMsg(textAreaBobSend.getText().getBytes());
					ecdsa.update(users[1].getMsg());
					users[1].setSig(ecdsa.sign());
					
					//encrypt message
					SecretKey secretKey = new SecretKeySpec(users[1].getSharedSecret(), 0, users[1].getSharedSecret().length, "AES");
					users[1].setSecretKey(secretKey);
					Cipher cipher = Cipher.getInstance("AES");
					cipher.init(Cipher.ENCRYPT_MODE, secretKey);
					byte[] msgInBytes = textAreaBobSend.getText().getBytes("UTF-8");
					byte[] cipherText = cipher.doFinal(msgInBytes);
					String cipherString = Base64.getEncoder().encodeToString(cipherText);
					
					//send encrypted message
					textAreaAliceRec.setText(cipherString);
				} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | NoSuchPaddingException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnBobToAlice.setBounds(433, 403, 273, 40);
		contentPane.add(btnBobToAlice);
		
		JButton btnAliceDecryptBob = new JButton("Decrypt Bob's Message");
		btnAliceDecryptBob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//decrypt message
					Cipher cipher = Cipher.getInstance("AES");
					cipher.init(Cipher.DECRYPT_MODE, users[1].getSecretKey());
					byte[] msgInBytes = Base64.getDecoder().decode(textAreaAliceRec.getText());
					byte[] decipherBytes = cipher.doFinal(msgInBytes);
					String decipheredText = new String(decipherBytes);
					
					//show decrypted message
					textAreaAliceRec.setText(decipheredText);
				} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAliceDecryptBob.setBounds(60, 585, 252, 20);
		contentPane.add(btnAliceDecryptBob);
		
		JButton btnBobVerifyAlice = new JButton("Verify Alice's Signature");
		btnBobVerifyAlice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Signature ecdsa = Signature.getInstance("SHA256withECDSA");
					ecdsa.initVerify(users[0].getPubKey());
					ecdsa.update(users[0].getMsg());
					boolean validSig = ecdsa.verify(users[0].getSig());
					if (validSig == true) {
						lblAtoBVerify.setText("Valid");
						lblAtoBVerify.setForeground(Color.GREEN);
					} else {
						lblAtoBVerify.setText("Invalid");
						lblAtoBVerify.setForeground(Color.RED);
					}
				} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnBobVerifyAlice.setBounds(703, 510, 273, 40);
		contentPane.add(btnBobVerifyAlice);

		JButton btnBobDecrypt = new JButton("Decrypt Alice's Message");
		btnBobDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Cipher cipher = Cipher.getInstance("AES");
					cipher.init(Cipher.DECRYPT_MODE, users[0].getSecretKey());
					byte[] msgInBytes = Base64.getDecoder().decode(textAreaBobRec.getText());
					byte[] decipherBytes = cipher.doFinal(msgInBytes);
					String decipheredText = new String(decipherBytes);
					textAreaBobRec.setText(decipheredText);
				} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnBobDecrypt.setBounds(821, 575, 266, 30);
		contentPane.add(btnBobDecrypt);
		
		JButton btnAliceVerifyBob = new JButton("Verify Bob's Signature");
		btnAliceVerifyBob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Signature ecdsa = Signature.getInstance("SHA256withECDSA");
					ecdsa.initVerify(users[1].getPubKey());
					ecdsa.update(users[1].getMsg());
					boolean validSig = ecdsa.verify(users[1].getSig());
					if (validSig == true) {
						lblBtoAVerify.setText("Valid");
						lblBtoAVerify.setForeground(Color.GREEN);
					} else {
						lblBtoAVerify.setText("Invalid");
						lblBtoAVerify.setForeground(Color.RED);
					}
				} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAliceVerifyBob.setBounds(53, 500, 259, 50);
		contentPane.add(btnAliceVerifyBob);
	}
	
	public void updateKeys() {
		textAreaAPub.setText(users[0].getPubKey().toString());
		textAreaAPriv.setText(users[0].getPrivKey().toString().substring(33));
		textAreaBPub.setText(users[1].getPubKey().toString());
		textAreaBPriv.setText(users[1].getPrivKey().toString().substring(33));
	}
	
	public void computeValue() throws InvalidKeyException, NoSuchAlgorithmException {
		String aValue = users[0].computeValue(users[1]);
		textAreaASecVal.setText(aValue);
		String bValue = users[1].computeValue(users[0]);
		textAreaBSecVal.setText(bValue);
	}
}
