package TextSenderPages;

import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.jsmpp.bean.BindType;
import org.jsmpp.session.SMPPSession;

import SMPPfunction.ClassforJMSMStool;
import SMPPfunction.PropertiesFile;
import excel.ReadExcel;

public class BulkMT {
	static Logger log = Logger.getLogger(MTTab.class);
	static SMPPSession smppSession;
	static ClassforJMSMStool jSmpp = new ClassforJMSMStool();
	static PropertiesFile prop = new PropertiesFile();
	public static void bulkMT ( JPanel pnl4,JTextArea DRarea) throws IOException {
		JButton	choosefile =new JButton("Choose file");choosefile.setBounds(10,50,160,25); 
		pnl4.add(choosefile);
		JLabel comment=new JLabel("Please select your file: only .xlsx is supported");comment.setBounds(190,50,560,25);
		pnl4.add(comment);
		JButton	sendbulk =new JButton("Send Bulk SMS");sendbulk.setBounds(10,100,160,25);
		pnl4.add(sendbulk);	

		
		choosefile.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent ae) {
					JFileChooser fileDlg = new JFileChooser();
				    fileDlg.showOpenDialog(fileDlg);
				    String filename = fileDlg.getSelectedFile().getAbsolutePath();
				    log.info(filename);		
				    comment.setText("File  \""+filename+"\"  is selected");
			   }
			});
		
	pnl4.setLayout(null);  
    pnl4.setVisible(true);
    
	}
	public static void SendbulkMTSMS ( String filePath, JTextArea DRarea) throws IOException {
		String UserName = prop.GeneralProperties("UserName");
		  String Password = prop.GeneralProperties("Password");
		  String IpAddress = prop.GeneralProperties("IpAddress");
		  String MTPort = prop.GeneralProperties("MTPort");
			if (smppSession == null) {
		        log.info("not bound to System ID: ");
		        log.info("tool attempt to bind as BIND_TX && system ID is : " );
		        smppSession = jSmpp.NewSession(UserName, Password, IpAddress, MTPort, BindType.BIND_TRX, DRarea);
		        if (smppSession.getSessionState().isBound()) {
		         log.info("tool is bound to system ID: ");
		         smppSession.setEnquireLinkTimer(60000);
		         log.info("EnquireLinkTimer is : 60000");
		         //b.setText("Bound");
		        }
		       }
		  
		ReadExcel rdxl= new ReadExcel();		
		Sheet datatypeSheet =rdxl.OpenExcelSheet(filePath,0);
		 log.info("Data Sheet num is " +datatypeSheet);
		 int lastrownum = datatypeSheet.getLastRowNum();
		 log.info("The Last row num is " +lastrownum);
		 System.out.println("The Last row num is " +lastrownum);
		 
		 for ( int i = 1; i <= lastrownum; i++) {
			 try {
			String SourceAddress = ReadExcel.readCelValue(0, i, datatypeSheet);
			String DestinationAddress = ReadExcel.readCelValue(1, i, datatypeSheet);
			String TextMessage = ReadExcel.readCelValue(2, i, datatypeSheet);

			if (SourceAddress!= null){
				log.info("Started Sending SMS Row details are " + SourceAddress +"  &  "+ DestinationAddress +"   &   " +TextMessage);
				String mID1 ="";
				try {
			        mID1 = jSmpp.sendSMPPTXT(SourceAddress, DestinationAddress, TextMessage, "1", "3", "1", "1", null,null, null,"GSM", smppSession, log);
			       } catch (UnsupportedEncodingException e1) {
			        // TODO Auto-generated catch block
			        e1.printStackTrace();
			       }
				
				} 

			 } catch (NullPointerException e) {
				 
			 }

			}
		
	}
}
