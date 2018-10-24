package SMPPfunction;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.jsmpp.bean.AlertNotification;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.DeliverSm;
import org.jsmpp.bean.DeliveryReceipt;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.session.GenericMessageReceiverListener;
import org.jsmpp.session.SMPPSession;

import com.jcraft.jsch.Session;



public class Actionfiles {
 SMPPSession smppSession;
 SMPPSession smppSession1;
 static Session session1 = null;
 private static DeliveryReceipt delReceipt;
 static PropertiesFile prop = new PropertiesFile();
 static Logger log = Logger.getLogger(Actionfiles.class);
 ClassforJMSMStool jSmpp = new ClassforJMSMStool();

 public void newActionclass(
  final JButton b,
  final JButton b1,
  final String key,
  final JTextField t1,
  final JPasswordField p1,
  final JTextField t2,
  final JTextField t3,
  final JTextField t4,
  final JTextField t5,
  final JTextArea t6,
  final JComboBox t7,
  final JComboBox t8,
  final JComboBox t9,
  final JComboBox t10,
  final JTextField t11,
  final JTextField t12,
  final JTextArea DRarea,
  final JLabel lt13,
  final JCheckBox cbx1,
  final JComboBox t14
 ) {

  b.addActionListener(new ActionListener() {
	  String UserName = prop.GeneralProperties("UserName");
	  String Password = prop.GeneralProperties("Password");
	  String IpAddress = prop.GeneralProperties("IpAddress");
	  String MTPort = prop.GeneralProperties("MTPort");
	  String newline = "\n";
	  
   public void actionPerformed(ActionEvent e) {

    if (key == "send") {
     if (smppSession == null) {
        lt13.setText("Not bound to GW");
        log.info("not bound to System ID: ");
        log.info("tool attempt to bind as BIND_TX && system ID is : " );
        smppSession = jSmpp.NewSession(UserName, Password, IpAddress, MTPort, BindType.BIND_TRX, DRarea);
        if (smppSession.getSessionState().isBound()) {
         log.info("tool is bound to system ID: ");
         smppSession.setEnquireLinkTimer(60000);
         log.info("EnquireLinkTimer is : 60000");
         //b.setText("Bound");
         lt13.setText("");
        }
       }
       String t7smpp = (String) t7.getSelectedItem();
       String t8smpp = (String) t8.getSelectedItem();
       String t9smpp = (String) t9.getSelectedItem();
       String t10smpp = (String) t10.getSelectedItem();
       String t14smpp = (String) t14.getSelectedItem();

       String mID1 ="";
    		   
       try {
        mID1 = jSmpp.sendSMPPTXT(t4.getText(), t5.getText(), t6.getText(), t7smpp, t8smpp, t9smpp, t10smpp, t11.getText(), t12.getText(), cbx1, t14smpp, smppSession, log);
       } catch (UnsupportedEncodingException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
       }
       int mID = hex2Decimal(mID1);
       log.info("SMS Sent to AG &&& Message ID is " + mID);
       log.info("MSG ID : " + mID + "  &&&    Content of SMS is   " + "\"" + t6.getText() + "\"");
       b.setText("SMS Sent");
       if (smppSession.getSessionState().isBound()) {
    	   log.info("what is my message ID is " + mID);
         lt13.setText("MSG-ID:" +mID);
       } else {
        lt13.setText("Not bound to GW");
       }
    }

     if (key =="DR") {
    	 
    	 if (smppSession1 == null) {
    	 	 smppSession1 = jSmpp.NewSession(UserName,Password,IpAddress, MTPort,BindType.BIND_RX, DRarea);
    	 	 log.info("tool is bound to system ID: ");
    	 	 smppSession1.setEnquireLinkTimer(60000);
    	 	 log.info("EnquireLinkTimer for BIND_RX is : 60000");
    	      }

	 if (smppSession1.getSessionState().isBound()) {
	//  String DR01 = jSmpp.deliverymesg(smppSession1,DRarea );
	 /* log.info(DR01);
	  if (!(DR01 == null) ) {
	  DRarea.append("Del Report: "+DR01+newline);
	  }
	  try {
	   Thread.sleep(1 * 500);
	  } catch (InterruptedException e1) {
	   // TODO Auto-generated catch block
	   e1.printStackTrace();
	  }*/
	  //System.out.println( "it is from appa tucker : "+ DRM);

	 }
     }
	
	if (key =="Response SMS") {
	      log.info("For DR, tool attempt to bind as BIND_RX && system ID is " );
	      if (smppSession1 == null) {
	 	 smppSession1 = jSmpp.NewSession(UserName,Password,IpAddress, MTPort,BindType.BIND_RX, DRarea);
	 	 log.info("tool is bound to system ID: ");
	 	 smppSession1.setEnquireLinkTimer(60000);
	 	 log.info("EnquireLinkTimer for BIND_RX is : 60000");
	      }

	 	 if (smppSession1.getSessionState().isBound()) {
	 	 // jSmpp.deliverSMS(smppSession1,DRarea);
	 	 // log.info(DR02);
	 	  
	 	  //System.out.println( "it is from appa tucker : "+ DRM);

 }
		 }
	 if (smppSession1 == null) {
	  log.info("For DR, tool attempt to bind as BIND_RX && system ID " );
	  smppSession1 = jSmpp.NewSession(UserName,Password,IpAddress, MTPort, BindType.BIND_RX, DRarea);
	  log.info("tool is bound to system ID: ");
	  smppSession.setEnquireLinkTimer(60000);
	  log.info("EnquireLinkTimer for BIND_RX is : 60000");

	 }
	}
	  
   
  });


 }

 public static int hex2Decimal(String s) {
  String digits = "0123456789ABCDEF";
  s = s.toUpperCase();
  int val = 0;
  for (int i = 0; i < s.length(); i++) {
   char c = s.charAt(i);
   int d = digits.indexOf(c);
   val = 16 * val + d;
  }
  return val;
 }

 public static void bulkMT() throws IOException {
  TextField jTextField1 = null;
  TextArea jTextArea1 = null;

  JFileChooser fileDlg = new JFileChooser();
  fileDlg.showOpenDialog(fileDlg);
  String filename = fileDlg.getSelectedFile().getAbsolutePath();
  jTextField1.setText(filename);

  FileInputStream fis = new FileInputStream(filename);
  byte buffer[] = new byte[fis.available()];
  fis.read(buffer);
  String message = new String(buffer);
  jTextArea1.setText(message);

 }

 public static String checkSession() throws IOException {
	 System.out.println("Check for session");
  	 
	 if ((session1 == null) || (!(session1).isConnected())) {
	  System.out.println("because the  session not created the code is about to start");	  
	  setUpTextsendertool.Lefttunneling();
	  System.out.println("Session is created now");
      return "Connected";
  } else if (session1.isConnected()){	  
		  return "Connected";
  
  }
  return null;

 }
 
 
}