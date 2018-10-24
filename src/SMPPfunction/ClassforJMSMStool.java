package SMPPfunction;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.JCheckBox;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.AlertNotification;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.DataSm;
import org.jsmpp.bean.DeliverSm;
import org.jsmpp.bean.DeliveryReceipt;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.MessageType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.OptionalParameter;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.SubmitSmResp;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.session.Session;
import org.jsmpp.util.InvalidDeliveryReceiptException;

import TextSenderPages.MTTab;


public class ClassforJMSMStool {
		
	private static SMPPSession smppSession;
	private static String DRreport;
	private static String DMmessage;
	static Logger log = Logger.getLogger(ClassforJMSMStool.class);
	String newline = "\n";

	public SMPPSession NewSession(String Username, String Password,	String iPaddress, String portnum, BindType bindtype, JTextArea DRarea) {
 	    int portnum1 = Integer.parseInt(portnum);
		    BindParameter bP = new BindParameter(bindtype,Username,Password,"SMPP",TypeOfNumber.UNKNOWN,NumberingPlanIndicator.UNKNOWN, null);
		    log.info("Binding params are done");
		   // BindParameter bP1 = new BindParameter(bindtype,Password,"",TypeOfNumber.UNKNOWN,NumberingPlanIndicator.UNKNOWN, null);
		    	    
			try {
				System.out.println("Binding credential done" + bP);
				smppSession = new SMPPSession(iPaddress, portnum1, bP);
				SMSlistner(smppSession,DRarea);
			}
			catch (IOException e1) {
				
				log.info("Cant bound to gateway system. Please try again after rechecking your tuunel and binding credentials"); 
			}

		return smppSession;

	}
	public  String sendSMPPTXT( String fromMSISDN, String toMSISDN,  String TXTmessage,String ston, String dton,String sNPI, String dNPI,
							String validity, String Schtime,JCheckBox cbx1, String dacod, SMPPSession smppSession,Logger log) throws UnsupportedEncodingException {
		//int numMes1 = Integer.parseInt(nummessages);
		String messageID = null;
		            log.info("MSG Parameters ::: " + "  OA: "+ fromMSISDN +",  DA: "+ toMSISDN +",  TEXT MSG : "+TXTmessage +", STON: "+ston+
		            		", DTON: "+dton +", SNPI: "+sNPI+ ", DNPI: "+sNPI+ ", Validity Period : "+validity+ ", Scheduled Delivery : " +Schtime+
		            		",  D-coding : " +dacod);
		            		//);
		            GeneralDataCoding dataCoding = new GeneralDataCoding();
					
					if (dacod == "Binary"){	dataCoding = new GeneralDataCoding(Alphabet.ALPHA_8_BIT);}
					if (dacod == "GSM"){dataCoding = new GeneralDataCoding(Alphabet.ALPHA_DEFAULT);}
					if (dacod == "UCS"){dataCoding = new GeneralDataCoding(Alphabet.ALPHA_UCS2);}
					//String house = "\u0628" + "\u064e" + "\u064a" +"\u0652" + "\u067a" + "\u064f";
					//byte[] data = house.getBytes("UTF-16BE"); 
					//DataCoding.newInstance(195);					
					ESMClass esmClass = new ESMClass();										
					TypeOfNumber sTon= TypeOfNumber.UNKNOWN;
					if (ston =="International") {sTon= TypeOfNumber.INTERNATIONAL;}
					if (ston =="Alphanumeric") {sTon= TypeOfNumber.ALPHANUMERIC;}
					if (ston =="Network Specific") {sTon= TypeOfNumber.NETWORK_SPECIFIC;}
					if (ston =="National") {sTon= TypeOfNumber.NATIONAL;}
					if (ston =="UNKNOWN") {sTon= TypeOfNumber.UNKNOWN;}
					log.info("ESM class set correctly");
					TypeOfNumber dTon= TypeOfNumber.UNKNOWN;
					if (dton =="International") {dTon= TypeOfNumber.INTERNATIONAL;}
					if (dton =="Alphanumeric") {dTon= TypeOfNumber.ALPHANUMERIC;}
					if (dton =="Network Specific") {dTon= TypeOfNumber.NETWORK_SPECIFIC;}
					if (dton =="National") {dTon= TypeOfNumber.NATIONAL;}
					if (dton =="UNKNOWN") {dTon= TypeOfNumber.UNKNOWN;}
					
					NumberingPlanIndicator snpi= NumberingPlanIndicator.ISDN;
					if (sNPI =="ISDN") {snpi= NumberingPlanIndicator.ISDN;}
					if (sNPI =="Data") {snpi= NumberingPlanIndicator.DATA;}
					if (sNPI =="National") {snpi= NumberingPlanIndicator.NATIONAL;}
					if (sNPI =="Land Mobile") {snpi= NumberingPlanIndicator.LAND_MOBILE;}
					if (sNPI =="Unknown") {snpi= NumberingPlanIndicator.UNKNOWN;}
					

					NumberingPlanIndicator dnpi= NumberingPlanIndicator.ISDN;
					if (dNPI =="ISDN") {dnpi= NumberingPlanIndicator.ISDN;}
					if (dNPI =="Data") {dnpi= NumberingPlanIndicator.DATA;}
					if (dNPI =="National") {dnpi= NumberingPlanIndicator.NATIONAL;}
					if (dNPI =="Land Mobile") {dnpi= NumberingPlanIndicator.LAND_MOBILE;}
					if (dNPI =="Unknown") {dnpi= NumberingPlanIndicator.UNKNOWN;}
					log.info("NPI set correctly");
					
					SMSCDeliveryReceipt DR = SMSCDeliveryReceipt.DEFAULT ;					
					if (cbx1.isSelected()){ DR = SMSCDeliveryReceipt.SUCCESS_FAILURE ;}
					log.info("D set correctly");
					String Schtime1 = null;
					String validity1 = null;
					if (Schtime != null){ Schtime1= Schtime ;}
					if (validity != null){validity1= validity;}
					log.info(fromMSISDN + toMSISDN +TXTmessage +sTon+dTon +sNPI+ sNPI+Schtime1+validity1+dacod);
					try {
							
						messageID = smppSession.submitShortMessage("cm",
								sTon,
								snpi, fromMSISDN,
								dTon,
								dnpi, toMSISDN,
								esmClass, (byte) 0, (byte) 0, Schtime1, validity1,
								new RegisteredDelivery(DR),
								(byte) 0, dataCoding, (byte) 0, TXTmessage.getBytes());

					} catch (PDUException e) { 
			            // Invalid PDU parameter 
						log.info("Invalid PDU parameter"); 
			            e.printStackTrace(); 
			        } catch (ResponseTimeoutException e) { 
			            // Response timeout 
			        	log.info("Response timeout"); 
			            e.printStackTrace(); 
			        } catch (InvalidResponseException e) { 
			            // Invalid response 
			        	log.info("Receive invalid respose"); 
			            e.printStackTrace(); 
			        } catch (NegativeResponseException e) { 
			            // Receiving negative response (non-zero command_status) 
			        	log.info("Receive negative response"); 
			            e.printStackTrace(); 
			        } catch (IOException e) { 
			        	log.info("IO error occur"); 
			            e.printStackTrace(); 
			        }
			        

					return messageID; 

							
	}

/*	public String deliverymesg(SMPPSession smppSession1, final JTextArea DRarea) {
		
		smppSession1.setMessageReceiverListener(new MessageReceiverListener() {
			
			public void onAcceptDeliverSm(DeliverSm deliverSm) throws ProcessRequestException { 
				
                if (MessageType.SMSC_DEL_RECEIPT.containedIn(deliverSm.getEsmClass())) { 
                    // delivery receipt 
                    try { 
                    	DeliveryReceipt delReceipt = deliverSm.getShortMessageAsDeliveryReceipt(); 
                    	                   
                        OptionalParameter[] delReceipt1 = deliverSm.getOptionalParameters();
                        long id = Long.parseLong(delReceipt.getId()) & 0xffffffff; 
                        int messopt = delReceipt1.length;
                        //long id1 = Long.parseLong(delReceipt.DELREC_ID) & 0xffffffff; 
                        String messageId = Long.toString(id, 16).toUpperCase(); 
                        //String messageId1 = Long.toString(id1, 16).toUpperCase();
                        System.out.println("Received MessageID : " + messageId );
                       // System.out.println("Received DR :" + delReceipt);
                                              
                        DRreport =  delReceipt.toString();
                        if (!(DRreport == null) ) {
          			 	  DRarea.append("SMS : "+DRreport+ newline);
          			 	  }
          			 	  try {
          			 	   Thread.sleep(1 * 500);
          			 	  } catch (InterruptedException e1) {
          			 	   // TODO Auto-generated catch block
          			 	   e1.printStackTrace();
          			 	  }
                        System.out.println( DRreport);
                        //System.out.println("Optional Parameter MessageID : " + messageId );
                        //OptionalParameter[] delReceipt1 = deliverSm.getOptionalParametes();
                        System.out.println("Optional Parameter MessageID : " + messopt );
                        log.info(DRreport);
                                                
                    } catch (InvalidDeliveryReceiptException e) { 
                        System.err.println("receive faild"); 
                        e.printStackTrace(); 
                    } 
                } else { 
                    // regular short message 
                   System.out.println("Receiving message : " + new String(deliverSm.getShortMessage())); 
                   System.out.println("DA : " + new String(deliverSm.getDestAddress()));
                   System.out.println("OA : " + new String(deliverSm.getSourceAddr()));
                                                      } 
            } 
 
            public void onAcceptAlertNotification(AlertNotification alertNotification) { 
                System.out.println("onAcceptAlertNotification" + alertNotification); 
            } 
 
            public DataSmResult onAcceptDataSm(DataSm dataSm, Session source) throws ProcessRequestException { 
                System.out.println("onAcceptDataSm"); 
                return null; 
            } 
        });
		return DRreport; 
	}
	
public String deliverSMS(SMPPSession smppSession1, final JTextArea DRarea) {
	
	smppSession1.setMessageReceiverListener(new MessageReceiverListener() {
		
		public void onAcceptDeliverSm(DeliverSm deliverSm) throws ProcessRequestException { 
			log.info("Receiving message");
			
			//log.info("Receiving message : " + new String(deliverSm.getShortMessage())); 
			
			DMmessage = new String(deliverSm.getShortMessage());	
			if (!(DMmessage == null) ) {
			 	  DRarea.append("SMS : "+DMmessage+ newline);
			 	  }
			 	  try {
			 	   Thread.sleep(1 * 500);
			 	  } catch (InterruptedException e1) {
			 	   // TODO Auto-generated catch block
			 	   e1.printStackTrace();
			 	  }
		} 

        public void onAcceptAlertNotification(AlertNotification alertNotification) { 
            System.out.println("onAcceptAlertNotification" + alertNotification); 
        } 

        public DataSmResult onAcceptDataSm(DataSm dataSm, Session source) throws ProcessRequestException { 
            System.out.println("onAcceptDataSm"); 
            return null; 
        } 
    });
	return DMmessage; 
	}*/
public String MessageID1(SMPPSession smppSession1, final Logger log) {
	
	SubmitSmResp submitRes = null;
	String abd = submitRes.getMessageId();
	System.out.println("it is your SMSID"+ abd); 
		
	return abd;		
}

public String SMSlistner(SMPPSession smppSession1, final JTextArea DRarea) {
	
	smppSession1.setMessageReceiverListener(new MessageReceiverListener() {
		
		public void onAcceptDeliverSm(DeliverSm deliverSm) throws ProcessRequestException { 
			
            if (MessageType.SMSC_DEL_RECEIPT.containedIn(deliverSm.getEsmClass())) { 
                // delivery receipt 
                try { 
                	DeliveryReceipt delReceipt = deliverSm.getShortMessageAsDeliveryReceipt(); 
                	DRreport =  delReceipt.toString();
                    if (!(DRreport == null) ) {
      			 	  DRarea.append("DR : "+ DRreport + newline);
      			 	// log.info(DRreport);
      			 	  }
      			 	  try {
      			 	   Thread.sleep(1 * 500);
      			 	  } catch (InterruptedException e1) {
      			 	   // TODO Auto-generated catch block
      			 	   e1.printStackTrace();
      			 	  }
    
                } catch (InvalidDeliveryReceiptException e) { 
                	log.info("SMS receive faild"); 
                    e.printStackTrace(); 
                } 
            }  else {
            DMmessage = new String(deliverSm.getShortMessage());	
			if (!(DMmessage == null) ) {
			 	  DRarea.append("SMS : "+ DMmessage + newline);
			 	  }
			 	  try {
			 	   Thread.sleep(1 * 500);
			 	  } catch (InterruptedException e1) {
			 	   // TODO Auto-generated catch block
			 	   e1.printStackTrace();
			 	  }
            }                        
        } 

        public void onAcceptAlertNotification(AlertNotification alertNotification) { 
            System.out.println("onAcceptAlertNotification" + alertNotification); 
        } 

        public DataSmResult onAcceptDataSm(DataSm dataSm, Session source) throws ProcessRequestException { 
            System.out.println("onAcceptDataSm"); 
            return null; 
        } 
    });
	return DRreport; 
}


}
