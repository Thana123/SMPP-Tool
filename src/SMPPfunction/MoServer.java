package SMPPfunction;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import javax.swing.JTextArea;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.PDUStringException;
import org.jsmpp.SMPPConstant;
import org.jsmpp.bean.AlertNotification;
import org.jsmpp.bean.DataSm;
import org.jsmpp.bean.DeliverSm;
import org.jsmpp.bean.DeliveryReceipt;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SubmitSm;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindRequest;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.SMPPServerSession;
import org.jsmpp.session.SMPPServerSessionListener;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.session.ServerMessageReceiverListener;
import org.jsmpp.session.Session;
import org.jsmpp.util.InvalidDeliveryReceiptException;
import org.jsmpp.util.MessageId;

public class MoServer   { 
 
    private final static ExecutorService execService = Executors.newFixedThreadPool(5); 
    static SMPPServerSession serverSession;
    private static String DRreport;
	private static String DMmessage;
	static Logger log = Logger.getLogger(ClassforJMSMStool.class);
	String newline = "\n";
	
    private static class WaitBindTask implements Runnable { 
        private final SMPPServerSession serverSession; 
         
        public WaitBindTask(SMPPServerSession serverSession) { 
            this.serverSession = serverSession; 
        } 
 
        public void run() { 
            try { 
                BindRequest bindRequest = serverSession.waitForBind(1000); 
                System.out.println("Accepting bind for session {}, interface version {}" + serverSession.getSessionId()); 
                
                   /*if (request.getSystemId().equals("test") &&  
                        request.getPassword().equals("test")) {*/
                
                try { 
                   bindRequest.accept("sys"); 
                } catch (PDUStringException e) { 
                	System.out.println("Invalid system id" + e); 
                    bindRequest.reject(SMPPConstant.STAT_ESME_RSYSERR); 
                } 
             
            } catch (IllegalStateException e) { 
            	System.out.println("System error" + e); 
            } catch (TimeoutException e) { 
            	System.out.println("Wait for bind has reach timeout" + e); 
            } catch (IOException e) { 
            	System.out.println("Failed accepting bind request for session {}" + serverSession.getSessionId()); 
            } 
        } 
    } 
     public static void sendMO(SMPPServerSession serverSession, String fromNo, String toNum, String smstext) {
    	 
    	 try {
            	Thread.sleep(500);
				System.out.println("about to send deliver message" );
				ESMClass esmClass = new ESMClass();
				GeneralDataCoding dataCoding = new GeneralDataCoding();
				try {
					serverSession.deliverShortMessage( 
					        "CMD",  
					        TypeOfNumber.INTERNATIONAL,
							NumberingPlanIndicator.ISDN, fromNo,
							TypeOfNumber.INTERNATIONAL,
							NumberingPlanIndicator.ISDN, toNum,
							esmClass,  
					        (byte)0, (byte)0,  
					        new RegisteredDelivery(0),  
					        dataCoding, smstext.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("message sent " );
			} catch (PDUException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ResponseTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NegativeResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}           
    	 
     }
    public static SMPPServerSession startserver01() {
   	 
    	try { 
            SMPPServerSessionListener sessionListener = new SMPPServerSessionListener(80);             
            System.out.println("Listening on port {}" + 80);
        	serverSession = sessionListener.accept(); 
            System.out.println("Accepting connection for session {}" + serverSession.getSessionId()); 
            execService.execute(new WaitBindTask(serverSession)); 

        } catch (IOException e) { 
        	System.out.println("IO error occured" + e); 
        } 
    	
        return serverSession;
   	 
    }
	public static void sendMOSMS(SMPPServerSession serverSession2) {
		// TODO Auto-generated method stub
		
	}
	public String onAcceptSubmitSm(SubmitSm submitSm, 
            SMPPServerSession source) throws ProcessRequestException, IOException { 
			new String(submitSm.getShortMessage());
			byte[] MoTextSMS = submitSm.getShortMessage();
        return new String(MoTextSMS, "UTF-8"); 
    } 

} 