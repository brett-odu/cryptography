package cryptography;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
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
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

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
	private Border blackline = BorderFactory.createLineBorder(Color.black);
	
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
		setTitle("Elliptic Curve Cryptography");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAlice = new JLabel("Alice");
		lblAlice.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlice.setFont(new Font("Serif", Font.BOLD, 30));
		lblAlice.setBounds(200, 10, 100, 40);
		contentPane.add(lblAlice);
		
		JLabel lblBob = new JLabel("Bob");
		lblBob.setHorizontalAlignment(SwingConstants.CENTER);
		lblBob.setFont(new Font("Serif", Font.BOLD, 30));
		lblBob.setBounds(900, 10, 100, 40);
		contentPane.add(lblBob);
		textAreaAliceSend.setBackground(SystemColor.controlHighlight);
		
		textAreaAliceSend.setWrapStyleWord(true);
		textAreaAliceSend.setTabSize(4);
		textAreaAliceSend.setRows(7);
		textAreaAliceSend.setLineWrap(true);
		textAreaAliceSend.setColumns(40);
		textAreaAliceSend.setBounds(50, 330, 400, 80);
		contentPane.add(textAreaAliceSend);
		TitledBorder aSendBord = BorderFactory.createTitledBorder(blackline, "Alice's Message To Send");
		aSendBord.setTitleJustification(TitledBorder.CENTER);
		textAreaAliceSend.setBorder(aSendBord);
		textAreaBobSend.setBackground(SystemColor.controlHighlight);

		textAreaBobSend.setColumns(40);
		textAreaBobSend.setLineWrap(true);
		textAreaBobSend.setTabSize(4);
		textAreaBobSend.setRows(7);
		textAreaBobSend.setWrapStyleWord(true);
		textAreaBobSend.setText("");
		textAreaBobSend.setBounds(750, 330, 400, 80);
		contentPane.add(textAreaBobSend);
		TitledBorder bSendBord = BorderFactory.createTitledBorder(blackline, "Bob's Message To Send");
		bSendBord.setTitleJustification(TitledBorder.CENTER);
		textAreaBobSend.setBorder(bSendBord);
		
		
		textAreaAPub.setBackground(SystemColor.controlHighlight);
		textAreaAPub.setFont(new Font("Dialog", Font.PLAIN, 12));
		textAreaAPub.setWrapStyleWord(true);
		textAreaAPub.setTabSize(4);
		textAreaAPub.setRows(7);
		textAreaAPub.setLineWrap(true);
		textAreaAPub.setEditable(false);
		textAreaAPub.setColumns(40);
		textAreaAPub.setBounds(50, 60, 400, 155);
		contentPane.add(textAreaAPub);
		TitledBorder aPubBord = BorderFactory.createTitledBorder(blackline, "Alice's Public Key");
		aPubBord.setTitleJustification(TitledBorder.CENTER);
		textAreaAPub.setBorder(aPubBord);
		textAreaAPriv.setBackground(SystemColor.controlHighlight);
		
		textAreaAPriv.setWrapStyleWord(true);
		textAreaAPriv.setTabSize(4);
		textAreaAPriv.setRows(5);
		textAreaAPriv.setLineWrap(true);
		textAreaAPriv.setEditable(false);
		textAreaAPriv.setColumns(40);
		textAreaAPriv.setBounds(50, 225, 400, 35);
		contentPane.add(textAreaAPriv);
		TitledBorder aPrivBord = BorderFactory.createTitledBorder(blackline, "Alice's Private Key");
		aPrivBord.setTitleJustification(TitledBorder.CENTER);
		textAreaAPriv.setBorder(aPrivBord);
		textAreaBPub.setBackground(SystemColor.controlHighlight);
		
		textAreaBPub.setWrapStyleWord(true);
		textAreaBPub.setTabSize(4);
		textAreaBPub.setRows(5);
		textAreaBPub.setLineWrap(true);
		textAreaBPub.setEditable(false);
		textAreaBPub.setColumns(40);
		textAreaBPub.setBounds(750, 60, 400, 155);
		contentPane.add(textAreaBPub);
		TitledBorder bPubBord = BorderFactory.createTitledBorder(blackline, "Bob's Public Key");
		bPubBord.setTitleJustification(TitledBorder.CENTER);
		textAreaBPub.setBorder(bPubBord);
		textAreaBPriv.setBackground(SystemColor.controlHighlight);
		
		textAreaBPriv.setWrapStyleWord(true);
		textAreaBPriv.setTabSize(4);
		textAreaBPriv.setRows(5);
		textAreaBPriv.setLineWrap(true);
		textAreaBPriv.setEditable(false);
		textAreaBPriv.setColumns(40);
		textAreaBPriv.setBounds(750, 225, 400, 35);
		contentPane.add(textAreaBPriv);
		TitledBorder bPrivBord = BorderFactory.createTitledBorder(blackline, "Bob's Private Key");
		bPrivBord.setTitleJustification(TitledBorder.CENTER);
		textAreaBPriv.setBorder(bPrivBord);
		textAreaASecVal.setBackground(SystemColor.controlHighlight);
		
		textAreaASecVal.setWrapStyleWord(true);
		textAreaASecVal.setTabSize(4);
		textAreaASecVal.setRows(5);
		textAreaASecVal.setLineWrap(true);
		textAreaASecVal.setEditable(false);
		textAreaASecVal.setColumns(40);
		textAreaASecVal.setBounds(50, 270, 400, 50);
		contentPane.add(textAreaASecVal);
		TitledBorder aValBord = BorderFactory.createTitledBorder(blackline, "Alice's Computed Secret Value");
		aValBord.setTitleJustification(TitledBorder.CENTER);
		textAreaASecVal.setBorder(aValBord);
		textAreaBSecVal.setBackground(SystemColor.controlHighlight);
		
		textAreaBSecVal.setWrapStyleWord(true);
		textAreaBSecVal.setTabSize(4);
		textAreaBSecVal.setRows(5);
		textAreaBSecVal.setLineWrap(true);
		textAreaBSecVal.setEditable(false);
		textAreaBSecVal.setColumns(40);
		textAreaBSecVal.setBounds(750, 270, 400, 50);
		contentPane.add(textAreaBSecVal);
		TitledBorder bValBord = BorderFactory.createTitledBorder(blackline, "Bob's Computed Secret Value");
		bValBord.setTitleJustification(TitledBorder.CENTER);
		textAreaBSecVal.setBorder(bValBord);
		textAreaAliceRec.setBackground(SystemColor.controlHighlight);
		
		textAreaAliceRec.setWrapStyleWord(true);
		textAreaAliceRec.setTabSize(4);
		textAreaAliceRec.setRows(7);
		textAreaAliceRec.setLineWrap(true);
		textAreaAliceRec.setEditable(false);
		textAreaAliceRec.setColumns(40);
		textAreaAliceRec.setBounds(50, 450, 400, 80);
		contentPane.add(textAreaAliceRec);
		TitledBorder aRecBord = BorderFactory.createTitledBorder(blackline, "Message From Bob");
		aRecBord.setTitleJustification(TitledBorder.CENTER);
		textAreaAliceRec.setBorder(aRecBord);
		textAreaBobRec.setBackground(SystemColor.controlHighlight);
		
		textAreaBobRec.setWrapStyleWord(true);
		textAreaBobRec.setText("");
		textAreaBobRec.setTabSize(4);
		textAreaBobRec.setRows(7);
		textAreaBobRec.setLineWrap(true);
		textAreaBobRec.setEditable(false);
		textAreaBobRec.setColumns(40);
		textAreaBobRec.setBounds(750, 450, 400, 80);
		contentPane.add(textAreaBobRec);
		TitledBorder bRecBord = BorderFactory.createTitledBorder(blackline, "Message From Alice");
		bRecBord.setTitleJustification(TitledBorder.CENTER);
		textAreaBobRec.setBorder(bRecBord);
		
		lblAtoBVerify.setHorizontalAlignment(SwingConstants.CENTER);
		lblAtoBVerify.setBounds(850, 540, 200, 20);
		contentPane.add(lblAtoBVerify);
		
		lblBtoAVerify.setHorizontalAlignment(SwingConstants.CENTER);
		lblBtoAVerify.setBounds(150, 540, 200, 20);
		contentPane.add(lblBtoAVerify);

		JButton btnGenerateKeyPairs = new JButton("Generate Key Pairs");
		btnGenerateKeyPairs.addActionListener(new ActionListener() {
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

		btnGenerateKeyPairs.setBounds(500, 60, 200, 30);
		contentPane.add(btnGenerateKeyPairs);
		
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
		btnComputeSecret.setBounds(500, 230, 200, 30);
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
					lblAtoBVerify.setText("Please Verify Signature");
					lblAtoBVerify.setForeground(Color.BLACK);
				} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | NoSuchPaddingException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAliceToBob.setBounds(130, 420, 240, 20);
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
					lblBtoAVerify.setText("Please Verify Signature");
					lblBtoAVerify.setForeground(Color.BLACK);
				} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | NoSuchPaddingException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnBobToAlice.setBounds(830, 420, 240, 20);
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
		btnAliceDecryptBob.setBounds(140, 600, 220, 20);
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
						lblAtoBVerify.setForeground((new Color(46, 139, 87)));
					} else {
						lblAtoBVerify.setText("Invalid");
						lblAtoBVerify.setForeground((new Color(220, 20, 60)));
					}
				} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnBobVerifyAlice.setBounds(850, 570, 200, 20);
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
		btnBobDecrypt.setBounds(840, 600, 220, 20);
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
						lblBtoAVerify.setForeground((new Color(46, 139, 87)));
					} else {
						lblBtoAVerify.setText("Invalid");
						lblBtoAVerify.setForeground((new Color(220, 20, 60)));
					}
				} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAliceVerifyBob.setBounds(150, 570, 200, 20);
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
