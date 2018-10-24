package TextSenderPages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.jsmpp.bean.BindType;
import org.jsmpp.session.SMPPSession;

import SMPPfunction.Actionfiles;
import SMPPfunction.ClassforJMSMStool;

public class LoginTab {
	static ClassforJMSMStool jSmpp = new ClassforJMSMStool();
	static SMPPSession smppSession;
	static Logger log = Logger.getLogger(LoginTab.class);
	static Actionfiles act = new Actionfiles();
	public static void loginpage(JPanel pnl1) {
		 
	
		JButton b = null,b1,b2; 
    	JTextField t1,t2,t3;
    	JLabel lt1,lt2,lp1,lt3;
    	
		JPasswordField p1;
	      lp1=new JLabel("Password:");lp1.setBounds(310,180, 120,25); 
	      p1 = new JPasswordField("p@ssw0rd");p1.setBounds(400,180, 120,25);
	      pnl1.add(p1);
	      
	// ########### Text Field ###########
	      lt1=new JLabel("System ID:"); lt1.setBounds(10,180, 120,25);
	      t1=new JTextField("VFtest01");t1.setBounds(100,180, 120,25);
	      lt2=new JLabel("Host:");lt2.setBounds(10,220, 120,25); 
	      t2=new JTextField("localhost"); t2.setBounds(100,220, 120,25);	      
	      lt3=new JLabel("Port:");lt3.setBounds(310,220, 120,25);
	      t3=new JTextField("40001");t3.setBounds(400,220, 120,25);
	      b1 =new JButton("Bind");b1.setBounds(550,178, 120,25);
	      b2 =new JButton("Unbind");b2.setBounds(550,218, 120,25);	      
	      pnl1.add(lp1);pnl1.add(p1);pnl1.add(lt1);pnl1.add(t1);pnl1.add(lt2);pnl1.add(t2);pnl1.add(lt3);pnl1.add(t3);
	      pnl1.add(b1);pnl1.add(b2);  
	      pnl1.setLayout(null);  
	      pnl1.setVisible(true);
	     // act.newActionclass(b1,b,"bind",t1,p1,t2,t3,null,null,null,null,null,null,null,null,null,null,null,null,null,log,null);
	      
/*	      b1.addActionListener(new ActionListener(){  
				
	    		public void actionPerformed(ActionEvent e){  
	    				System.out.print("tool attempt to bind as BIND_TX && system ID is : " + t1.getText()+p1.getText() + t2.getText()+ t3.getText());
	    				smppSession = jSmpp.NewSession(t1.getText(), p1.getText(), t2.getText(), t3.getText(), BindType.BIND_TRX, log);
	    				if (smppSession.getSessionState().isBound()){
	    					log.info("tool is bound to system ID: " + t1.getText());
	    					smppSession.setEnquireLinkTimer(60000);	
	    					log.info("EnquireLinkTimer is : 60000");
	    					
	    		    	  	b1.setText("Bound"); 
	    		    	 	}
	    		}
	      });*/
	      
	    //  return smppSession;
	      
	}
	
}
