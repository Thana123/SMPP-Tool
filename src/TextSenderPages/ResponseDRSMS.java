package TextSenderPages;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ResponseDRSMS {
	
	public static void MtPage(JPanel pnl6) {
		
		JTextArea DRarea=new JTextArea(""); 
	      DRarea.setBounds(10,460,685,340);
	      JScrollPane scrollPane = new JScrollPane(DRarea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);          
        DRarea.setLineWrap(true);
	}

}
