

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jsmpp.session.SMPPSession;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;

import SMPPfunction.linuxOutPut;
import SMPPfunction.setUpTextsendertool;
import TextSenderPages.BulkMT;
import TextSenderPages.LoginTab;
import TextSenderPages.MOTab;
import TextSenderPages.MTTab;
import TextSenderPages.Report;

public class Homepage {  
	static Logger log = Logger.getLogger(Homepage.class);
	static SMPPSession smppSession;
			
    public static void main(String[] args) throws IOException {  
    	PropertyConfigurator.configure("Resources/log4j.properties");
    	log.debug("Logger started ");
    
// ########### Frame and pannel ###########	
      JFrame f=new JFrame("Text Sender 2"); 
      //f.setFont("Arial", Font.BOLD, (width + height/25) );
      f.setFont(new Font("Serif", Font.PLAIN, 24));
      f.setBackground(Color.LIGHT_GRAY);
      f.setSize(720, 875);
      JTabbedPane tp=new JTabbedPane();
      JPanel pnl1 = new JPanel ();
      JPanel pnl2 = new JPanel ();
      JPanel pnl3 = new JPanel ();
      JPanel pnl4 = new JPanel ();
      JPanel pnl5 = new JPanel ();
      JPanel pnl6 = new JPanel ();
      
    //  tp.add("           Login       ",pnl1);  
      tp.add("              MT             ",pnl2);  
      tp.add("              MO             ",pnl3); 
      tp.add("           Bulk_MT           ",pnl4);
      tp.add("            Report           ",pnl5);
      tp.add("            DR and SMS           ",pnl6);
      tp.setBounds(450,450,400,400);          
     // JLabel nf =new JLabel(new ImageIcon(ImageIO.read(new File("images\\1kMonkeys.jpg"))));
     // p.add(nf);
      JTextArea DRarea=new JTextArea(""); 
      DRarea.setBounds(10,460,685,340);
      JScrollPane scrollPane = new JScrollPane(DRarea);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);          
      DRarea.setLineWrap(true);
      pnl6.add(DRarea);      
      f.add(tp);
      f.setVisible(true);
      
      Channel channel=setUpTextsendertool.Lefttunneling();    
      MTTab.MtPage(pnl2, DRarea);
      f.setResizable(true);
      Report.loginpage(pnl5, channel);
      f.setResizable(true);  
      BulkMT.bulkMT(pnl4,DRarea );
      f.setResizable(true);
      setUpTextsendertool.Righttunnel();      
      MOTab.MOPage(pnl3);
      f.setResizable(true);   
      
      //Report.loginpage(pnl5);
      
    }
   
} 