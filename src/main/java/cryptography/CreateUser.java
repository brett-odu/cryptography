package cryptography;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class CreateUser extends JFrame implements ActionListener {
	// create a frame
	JFrame f = new JFrame("Create User");
	private JTextField nameField;
	private JPasswordField passwordField;
	
	public CreateUser() {

        f.setSize(400, 400);

        // create a button
        JButton btnCert = new JButton("Request Certificate");
        btnCert.setBounds(180, 294, 64, 25);
  
        // add action listener
        btnCert.addActionListener(this);
  
        // create a panel
        JPanel p1 = new JPanel();
        p1.setLayout(null);
  
        p1.add(btnCert);
        f.getContentPane().add(p1);
        
        JLabel lblInstructions = new JLabel("Please enter your name and....");
        lblInstructions.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstructions.setBounds(12, 12, 319, 147);
        p1.add(lblInstructions);
        
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(12, 188, 128, 25);
        p1.add(lblName);
        
        JLabel lblPassword = new JLabel("password:");
        lblPassword.setBounds(12, 241, 70, 15);
        p1.add(lblPassword);
        
        nameField = new JTextField();
        nameField.setBounds(144, 191, 114, 19);
        p1.add(nameField);
        nameField.setColumns(10);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(144, 239, 114, 19);
        p1.add(passwordField);
        
        f.show();
	}
	
	 // if the button is pressed
    public void actionPerformed(ActionEvent e)
    {
    	f.hide();
    }
}
