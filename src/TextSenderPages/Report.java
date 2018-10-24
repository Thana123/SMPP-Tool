package TextSenderPages;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.apache.log4j.Logger;
import org.jsmpp.session.SMPPSession;

import com.jcraft.jsch.Channel;
import com.teamdev.jxbrowser.Browser;
import com.teamdev.jxbrowser.BrowserFactory;
import com.teamdev.jxbrowser.BrowserType;

import SMPPfunction.Actionfiles;
import SMPPfunction.ClassforJMSMStool;
import SMPPfunction.PropertiesFile;
import SMPPfunction.setUpTextsendertool;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

public class Report {
	static ClassforJMSMStool jSmpp = new ClassforJMSMStool();
	static PropertiesFile prop = new PropertiesFile();
	static SMPPSession smppSession;
	static Logger log = Logger.getLogger(LoginTab.class);
	static Actionfiles act = new Actionfiles();
	public static void loginpage(JPanel pnl5, Channel channel) throws IOException {
		String ShellScript = prop.GeneralProperties("ShellScript");
    	JTable tblTaskList = new JTable();
    	JButton breport = new JButton("Generate Report");
    	breport.setBounds(10,50,120,25);
    	JScrollPane scrollPane = new JScrollPane(tblTaskList);
    	pnl5.add(tblTaskList);pnl5.add(scrollPane);pnl5.add(breport);
    	
    	
    	   Vector<Object> tableheader = new Vector<Object>();
    	    String ColumnNames[] = { "Date and Time", "Destination Number","Originated number", "Text Message"};
    	    DefaultTableModel dm = new DefaultTableModel(ColumnNames, 0);
    	    tblTaskList=new JTable(dm);
    	    tblTaskList.setModel(dm);
    	    pnl5.setLayout(new BorderLayout());
    	    pnl5.add(tblTaskList, BorderLayout.CENTER);
    	    pnl5.add(tblTaskList.getTableHeader(), BorderLayout.NORTH);
    	    
    	    log.info(" About to run shell script");
    	    //String scriptOutputis = setUpTextsendertool.runCommand(channel,ShellScript,dm ); 
    	    
    	    
    	    breport.addActionListener(new ActionListener(){  
				
	    		public void actionPerformed(ActionEvent e){  
	    				try {
							setUpTextsendertool.runCommand(channel,ShellScript,dm );
							log.info(" About to run shell script");
							String scriptOutputis = setUpTextsendertool.runCommand(channel,ShellScript,dm );
							log.info(" Hi this is Linx shell script output:    " +scriptOutputis);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    		}
	      });
	      
	   	      
	}
}