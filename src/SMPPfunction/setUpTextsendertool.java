package SMPPfunction;


import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

import TextSenderPages.MTTab;

public class setUpTextsendertool {
 static PropertiesFile prop = new PropertiesFile();
 static Logger log = Logger.getLogger(setUpTextsendertool.class);
 public static Channel Lefttunneling() {
  JSch jsch = new JSch();
  Session session1 = null;
  Channel channel = null;
  String host = prop.GeneralProperties("host");
  String user = prop.GeneralProperties("user");
  String password = prop.GeneralProperties("password");
  String MTPort = prop.GeneralProperties("MTPort");
  int mtport = Integer.parseInt(MTPort);
  String Leftunnel = prop.GeneralProperties("Lefttunnel");
  String AGpassword = prop.GeneralProperties("AGpassword");
  int port = 22;
  try {
	log.info("session initiated");
   session1 = jsch.getSession(user, host, port); //default port is 22   
   UserInfo ui = new MyUserInfo();
   session1.setUserInfo(ui);
   session1.setPassword(password);
   session1.setConfig("StrictHostKeyChecking", "no");
   session1.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
   session1.setPortForwardingL(mtport, "localhost", 5000);
   session1.connect();
   log.info("session created");
   List < String > commands = new ArrayList < String > ();
   commands.add(Leftunnel);
   commands.add(AGpassword);
   commands.add("mr");
   channel = session1.openChannel("shell");
   channel.connect();
   channel.setXForwarding(true);
   channel.setInputStream(System.in);
   channel.setOutputStream(System.out);
   OutputStream os = channel.getOutputStream();
   PrintWriter shell = new PrintWriter(os, true);

   for (String cmd: commands) {
        try {
     Thread.sleep(2000);
    } catch (Exception ee) {}
    shell.println(cmd);

   }
   Thread.sleep(2000);
   System.out.println("Created tunnel");

  } catch (JSchException e) {

   e.printStackTrace(System.out);
  } catch (Exception e) {
   e.printStackTrace(System.out);
  } finally {

  }
  return channel;
 }
 
 public static String runCommand(Channel channel, String command, DefaultTableModel dm) throws IOException {
	 String line = "";
	 List < String > commands = new ArrayList < String > ();
	   commands.add(command);
	   OutputStream os = channel.getOutputStream();
	   PrintWriter shell = new PrintWriter(os, true);
	   
	   for (String cmd: commands) {
	        try {	        	
	        	Thread.sleep(2000);
	    } catch (Exception ee) {}
	    shell.println(cmd);
	    line = readChannelOutput(channel, dm);
	   }
	return line;
	 
 }
 
 public static Session Righttunnel() throws UnknownHostException {
  JSch jsch = new JSch();
  Session session2 = null;
  String host = prop.GeneralProperties("host");
  String user = prop.GeneralProperties("user");
  String password = prop.GeneralProperties("password");
  String MOPort = prop.GeneralProperties("MOPort");
  int moport = Integer.parseInt(MOPort);
  String RightTunnel = prop.GeneralProperties("RightTunnel");
  String AGpassword = prop.GeneralProperties("AGpassword");
  int port = 22;
  String lacalIP = getIPaddress();
  String righttunnel = RightTunnel + ":" + lacalIP + ":80 airgate@172.24.193.33";

  try {
   session2 = jsch.getSession(user, host, port); //default port is 22

   UserInfo ui = new MyUserInfo();
   session2.setUserInfo(ui);
   session2.setPassword(password);
   session2.setConfig("StrictHostKeyChecking", "no");
   session2.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
   session2.setPortForwardingL(moport, "localhost", 5000);
   session2.connect();
   List < String > commands = new ArrayList < String > ();
   commands.add(righttunnel);
   commands.add(AGpassword);
   commands.add("mr");
   Channel channel = session2.openChannel("shell");
   channel.connect();
   channel.setXForwarding(true);
   channel.setInputStream(System.in);
   channel.setOutputStream(System.out);
   OutputStream os = channel.getOutputStream();
   PrintWriter shell = new PrintWriter(os, true);

   for (String cmd: commands) {
    shell.println(cmd);

    try {
     Thread.sleep(3000);
    } catch (Exception ee) {}

   }

   //channel.disconnect();
   System.out.println("Created tunnel");

  } catch (JSchException e) {

   e.printStackTrace(System.out);
  } catch (Exception e) {
   e.printStackTrace(System.out);
  } finally {

  }
  return session2;
 }

 public static class MyUserInfo implements UserInfo, UIKeyboardInteractive {

  public String getPassphrase() {
   return null;
  }
  public String getPassword() {
   return null;
  }
  public boolean promptPassphrase(String arg0) {
   return false;
  }
  public boolean promptPassword(String arg0) {
   return false;
  }
  public boolean promptYesNo(String arg0) {
   return false;
  }
  public void showMessage(String message) {
   System.out.println("SUserInfo.showMessage()");
  }
  public String[] promptKeyboardInteractive(String arg0, String arg1,
   String arg2, String[] arg3, boolean[] arg4) {
   return null;
  }
 }

 public static String readChannelOutput(Channel channel, DefaultTableModel dm) {

  byte[] buffer = new byte[150* 1024*1024];
  String line = "";
  try {
   InputStream in = channel.getInputStream();
   
   while (true) {
    while ( in .available() > 0) {
	     int i = in .read(buffer, 0, (150 * 1024 * 1024));
	     line = new String(buffer, 0, i);
	     //System.out.println("Thana here is the output" + line);
	     
	     Scanner scanner = new Scanner(line);
	     while (scanner.hasNextLine()) {
	       String line0 = scanner.nextLine();	       
	       String[] abc  =line0.split(" ");	       	       
	       String Time = abc[0] + abc[1];
	       String DA = abc[2];
	       String OA = abc[3];
	       String[] textSMS = Arrays.copyOfRange(abc,4, abc.length);	
	        Vector<Object> data = new Vector<Object>();
	        data.add(Time);
	        data.add(DA);
	        data.add(OA);
	        data.add(Arrays.toString(textSMS));	
	        dm.addRow(data);
	       
	       	       // process the line
	     }
	     scanner.close();
	     
	     log.info(line);
	     
     if (i < 0) {
      break;
     }
    }
    if (line.contains("End-Of-File")) {
     break;
    }
    if (channel.isClosed()) {
     break;
    }
    try {
     Thread.sleep(1000);
    } catch (Exception ee) {
     break;
    }

   }
  } catch (Exception e) {
   System.out.println("Error while reading channel output: " + e);

  }
  
return line;
 }

 public static String getIPaddress() throws UnknownHostException {
  InetAddress i = null;
  i = InetAddress.getLocalHost();
  return i.getHostAddress();
 }

}