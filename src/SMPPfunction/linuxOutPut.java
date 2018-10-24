package SMPPfunction;

import com.jcraft.jsch.*;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class linuxOutPut {
 static PropertiesFile prop = new PropertiesFile();

 public static void linconn() throws JSchException, UnknownHostException {

  JSch jsch = new JSch();
  Session session = null;
  String host = prop.GeneralProperties("host");
  String user = prop.GeneralProperties("user");
  String password = prop.GeneralProperties("password");
  String MOPort = prop.GeneralProperties("MOPort");
  int moport = Integer.parseInt(MOPort);
  String lacalIP = getIPaddress();
  String RightTunnel = prop.GeneralProperties("RightTunnel");
  String righttunnel = RightTunnel + ":" + lacalIP + ":80 airgate@172.24.193.33";
  String AGpassword = prop.GeneralProperties("AGpassword");
  String ShellScript = prop.GeneralProperties("ShellScript");
  int port = 22;

  try {
   session = jsch.getSession(user, host, port); //default port is 22

   UserInfo ui = new MyUserInfo();
   session.setUserInfo(ui);
   session.setPassword(password);
   session.setConfig("StrictHostKeyChecking", "no");
   session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
   session.setPortForwardingL(moport, "localhost", 5000);
   session.connect();

   List < String > commands = new ArrayList < String > ();
   commands.add(righttunnel);
   commands.add(AGpassword);
   commands.add("mr");
   Channel channel = session.openChannel("shell");
   channel.connect();
   channel.setXForwarding(true);
   channel.setInputStream(System.in);
   channel.setOutputStream(System.out);
   OutputStream os = channel.getOutputStream();
   PrintWriter shell = new PrintWriter(os, true);

   for (String cmd: commands) {
    shell.println(cmd);

    try {
     Thread.sleep(2000);
    } catch (Exception ee) {}

   }
   shell.println(ShellScript);
   readChannelOutput(channel);
   System.out.println("Thana here is the output");
   channel.disconnect();
   session.disconnect();

  } catch (JSchException e) {

   e.printStackTrace(System.out);
  } catch (Exception e) {
   e.printStackTrace(System.out);
  } finally {

  }

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

 public static void readChannelOutput(Channel channel) {

  byte[] buffer = new byte[1024];

  try {
   InputStream in = channel.getInputStream();
   String line = "";
   while (true) {
    while ( in .available() > 0) {
     int i = in .read(buffer, 0, 1024);
     if (i < 0) {
      break;
     }
     line = new String(buffer, 0, i);

     System.out.println("Thana here is the output" + line);

     try (Writer writer = new BufferedWriter(new OutputStreamWriter(
      new FileOutputStream("AigateLog.txt"), "utf-8"))) {
      writer.append(line);
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
    } catch (Exception ee) {}

   }
  } catch (Exception e) {
   System.out.println("Error while reading channel output: " + e);
  }


 }
 public static String getIPaddress() throws UnknownHostException {
  InetAddress i = null;
  i = InetAddress.getLocalHost();
  return i.getHostAddress();
 }



}