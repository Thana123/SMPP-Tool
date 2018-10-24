package SMPPfunction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.jsmpp.session.SMPPServerSession;

public class MOActionfiles {
	static SMPPServerSession serverSession;
	 public void newActionclass(
				final JButton b,
				final JButton b3,
				final JButton b4,
				final String key,
				final JTextField t4,
				final JTextField t5,
				final JTextArea t6,
				final JTextArea DRarea,
				final Logger log

		){

			b.addActionListener(new ActionListener(){  
				
				public void actionPerformed(ActionEvent e){ 
					 System.out.println("Start server button is notrecognised");
					if (key == "Start Server") {
						System.out.println("Start server button recognised");
						serverSession = MoServer.startserver01(); 
						log.info("MO Server is not up: " );
					}
					
					if (key == "Send SMS") {
						
			    	if (!(serverSession == null)) {
			    		String sourceNum = t4.getText();
				    	String destinationNum = t5.getText();
				    	String textSMS = t6.getText();
				    	MoServer.sendMOSMS(serverSession);
			    		
			    	}
					}
				}
			});

			
		}

}
