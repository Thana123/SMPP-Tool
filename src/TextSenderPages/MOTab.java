package TextSenderPages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.jsmpp.bean.BindType;
import org.jsmpp.session.SMPPServerSession;
import org.jsmpp.session.SMPPSession;

import SMPPfunction.Actionfiles;
import SMPPfunction.ClassforJMSMStool;
import SMPPfunction.MOActionfiles;
import SMPPfunction.MoServer;

public class MOTab {
	static SMPPServerSession serverSession;
	static Logger log = Logger.getLogger(MTTab.class);
	static MOActionfiles act1 = new MOActionfiles();
	
public static void MOPage(JPanel pnl3) {
		
    	JTextField t4,t5;
		JLabel lt4,lt5,lt6;
    	JButton b = null,b3,b4; 
        
		
		  // ########### Text Area ########### 
	      JTextArea t6=new JTextArea("");      
      
	      lt4=new JLabel("Source Add:");lt4.setBounds(10,50, 120,25);
	      t4=new JTextField("");t4.setBounds(100,50, 120,25);
	      
	      lt5=new JLabel("Dest Add:");lt5.setBounds(310,50, 120,25);
	      t5=new JTextField("");t5.setBounds(400,50, 120,25);
	      
	      lt6=new JLabel("Text Message: "); lt6.setBounds(10,100,130,50);
	      t6.setLineWrap(true);t6.setBounds(10,140,505,580);
	      
		  b3 =new JButton("Send SMS"); b3.setBounds(550,350,120,25);
		 // b4 =new JButton("Start Server"); b4.setBounds(550,300,120,25);
	      // ########### TCombo box ###########  
	    	      
	      pnl3.add(lt4);pnl3.add(lt5);pnl3.add(lt6);
	      pnl3.add(t4); pnl3.add(t5);pnl3.add(t6);	pnl3.add(b3);      
	      pnl3.setLayout(null);  
	      pnl3.setVisible(true);
	         serverSession = MoServer.startserver01(); 	      
			log.info("MO Server is not up: " );
	      
	      b3.addActionListener(new ActionListener(){  
				
				public void actionPerformed(ActionEvent e){ 
					//MoServer.sendMOSMS(serverSession);
					//System.out.println("Start server button is notrecognised");
						
			    	if ((serverSession.getSessionState()).isBound()) {
			    		log.info("The messsage ID is " + serverSession.getSessionState() );
			    		String sourceNum = t4.getText();
				    	String destinationNum = t5.getText();
				    	String textSMS = t6.getText();
				    	MoServer.sendMO(serverSession,sourceNum,destinationNum,textSMS);
			    		
			    	} else {
			    		log.info("MO server is not up");
			    					    		
			    	}
					
				}
			});
	            
	}


}
