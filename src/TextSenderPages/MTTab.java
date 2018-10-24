package TextSenderPages;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.jsmpp.bean.BindType;
import org.jsmpp.session.SMPPSession;

import SMPPfunction.Actionfiles;
import SMPPfunction.ClassforJMSMStool;

public class MTTab {
	static ClassforJMSMStool jSmpp = new ClassforJMSMStool();
	static Logger log = Logger.getLogger(MTTab.class);
	static Actionfiles act = new Actionfiles();
	public static void MtPage(JPanel pnl2,JTextArea DRarea) {
		
    	JTextField t1,t2,t3,t4,t5,t11,t12;
		JComboBox t7,t8,t9,t10,t14;
    	JLabel lt1,lt2,lp1,lt3,lt4,lt5,lt6,lt7,lt8,lt9,lt10,lt11,lt12,lt13,lt14,cbx1l;
    	JButton b = null,b3,b4,b5 = null; 
        JCheckBox cbx1 = null;
		
		  // ########### Text Area ########### 
	      JTextArea t6=new JTextArea("Enter your text SMS here");      
	      //JTextArea DRarea=new JTextArea("");	      
	      //JScrollPane scroll = new JScrollPane (DRarea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
/*	      DRarea.setBounds(10,460,685,340);
	      JScrollPane scrollPane = new JScrollPane(DRarea);
          scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
          scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);          
          DRarea.setLineWrap(true);*/
          //DRarea.setWrapStyleWord(true);
          //scrollPane.setBounds(20, 30, 100, 40);
       
	      
	      lt4=new JLabel("Source Add:");lt4.setBounds(10,50,120,25);
	      t4=new JTextField("901234");t4.setBounds(100,50,120,25);
	      
	      lt5=new JLabel("Dest Add:");lt5.setBounds(310,50,120,25);
	      t5=new JTextField("64210130309");t5.setBounds(400,50,120,25);
	      
	      lt6=new JLabel("Enter your Message to send: "); lt6.setBounds(10,300,715,50);
	      t6.setLineWrap(true);t6.setBounds(10,340,535,340);
	     
	      // ########### TCombo box ###########  
	         
	      lt7=new JLabel("Source TON:");lt7.setBounds(10,100, 120,25);
	      String ton[]={"International","National","Network Specific","Alphanumeric","Unknown"};
	      t7= new JComboBox(ton); t7.setBounds(100,100, 120,25); 
	      lt8=new JLabel("Dest TON:");lt8.setBounds(310,100, 120,25);
	      t8=new JComboBox(ton);t8.setBounds(400,100, 120,25);
	      
	      String npi[]={"ISDN","Data","National","Land Mobile","Unknown"};
	      
	      lt9=new JLabel("Source NPI:");lt9.setBounds(10,150, 120,25);
	      t9=new JComboBox(npi);t9.setBounds(100,150, 120,25);
	      
	      lt10=new JLabel("Dest NPI:");lt10.setBounds(310,150, 120,25);
	      t10=new JComboBox(npi); t10.setBounds(400,150, 120,25);
	      
	      lt11=new JLabel("Validity Priod:"); lt11.setBounds(10,200, 120,25);
	      t11=new JTextField("");t11.setBounds(100,200, 120,25);
	      lt12=new JLabel("Delivery Time:");lt12.setBounds(310,200, 120,25);
	      t12=new JTextField("");      t12.setBounds(400,200, 120,25);
	      
	      
	      
	      String dcode[]={"GSM","Binary","UCS"};
	      lt14=new JLabel("Datacode:");lt14.setBounds(10,250, 120,25);
	      t14=new JComboBox(dcode); t14.setBounds(100,250, 120,25);
	      
	      cbx1l=new JLabel("Delivery Report?");cbx1l.setBounds(310,250, 120,30);
	      cbx1= new JCheckBox();cbx1.setBounds(400,250, 120,30);
	      b3 =new JButton("Send SMS"); b3.setBounds(550,350,120,25);
	      b4 =new JButton("Delivery Report");b4.setBounds(10,428,160,25); 
	      b5 =new JButton("Reponse SMS");b5.setBounds(250,428,160,25);
	      lt13=new JLabel("");lt13.setBounds(550,380,120,25);
	      
	      pnl2.add(lt4);pnl2.add(lt5);pnl2.add(lt6);pnl2.add(lt7);pnl2.add(lt8);
	      pnl2.add(lt9);pnl2.add(lt10);pnl2.add(lt11);pnl2.add(lt12);pnl2.add(lt13);pnl2.add(lt14);pnl2.add(cbx1l);pnl2.add(cbx1);
	      pnl2.add(t4); pnl2.add(t5);pnl2.add(t6); pnl2.add(t7); pnl2.add(t8);pnl2.add(t9);
	      pnl2.add(t10);pnl2.add(t11);pnl2.add(t12);pnl2.add(t14);pnl2.add(b3);pnl2.add(b4);pnl2.add(b5);
	      //pnl2.add(DRarea); 
	      
	      //pnl2.setLayout(null); 
	      pnl2.setLayout(new BorderLayout());
	      pnl2.setVisible(true);
	      
	     // b3 =new JButton("Send SMS"); b3.setBounds(550,555,120,25); 
	      act.newActionclass(b3, b,"send",null,null,null,null,t4,t5,t6,t7,t8,t9,t10,t11,t12,DRarea,lt13,cbx1,t14);
	      //act.newActionclass(b4, b,"DR",null,null,null,null,t4,t5,t6,t7,t8,t9,t10,t11,t12,DRarea,lt13,cbx1,t14);
	      //act.newActionclass(b5, b,"Response SMS",null,null,null,null,t4,t5,t6,t7,t8,t9,t10,t11,t12,DRarea,lt13,cbx1,t14);
	      //act.newActionclass(b4,b,"DeliverReport",t1,p1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,DRarea,lt13,cbx1,t14,log,b4);

	      
/*	      b3.addActionListener(new ActionListener(){  
				
	    		public void actionPerformed(ActionEvent e){  
	    		    	
    		    	if (smppSession == null) {
    		    		lt13.setText("Not bound to GW");
    		    		log.info("not bound to System ID: " );
    		    	}
			    	String t7smpp = (String) t7.getSelectedItem();
			    	String t8smpp = (String) t8.getSelectedItem();
			    	String t9smpp = (String) t9.getSelectedItem();
			    	String t10smpp = (String) t10.getSelectedItem();
			    	String t14smpp = (String) t14.getSelectedItem();
			    	
			    	  
			    	try {
						String MSGID = jSmpp.sendSMPPTXT(t4.getText(), t5.getText(), t6.getText(),t7smpp,t8smpp,t9smpp,t10smpp, t11.getText(), t12.getText(),cbx1,t14smpp, smppSession,log);
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    	int mID =hex2Decimal(MSGID);
			    	log.info("SMS Sent to AG &&& Message ID is " + mID );
			    	log.info("MSG ID : "+mID +"  &&&    Content of SMS is   " +"\"" + t6.getText()+"\"" );
	    	  
	    	   // System.out.println("Read Combo Box value : " + t8.getSelectedItem());
	    	  	    //b.setText("SMS Sent");
			    	String MSGID1 = jSmpp.MessageID1(smppSession, log);
	    	 if (smppSession.getSessionState().isBound()){
	    		  lt13.setText("MSG-ID:" ); 
	    		  System.out.println("Hey Iam new SID  "+MSGID1);
	    	  } else {
	    		  lt13.setText("Not bound to GW"); 
		    	  }	    	  	
		      
		}
  });*/
	      

	}
	public static int hex2Decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }
}
