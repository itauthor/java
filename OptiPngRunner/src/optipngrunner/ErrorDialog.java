package optipngrunner;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ErrorDialog {

	public static void showError(String message, String messType) {

		JTextArea jta = new JTextArea(message);
		jta.setLineWrap(true);
		jta.setMargin( new Insets(10,10,10,10) ); 
		JScrollPane jsp = new JScrollPane(jta);
		jsp.setPreferredSize(new Dimension(480, 320));
		
		if (messType == "error"){
			JOptionPane.showMessageDialog(new JFrame(), jsp, "Error",
					JOptionPane.ERROR_MESSAGE);			
		} else if (messType == "info"){
			JOptionPane.showMessageDialog(new JFrame(), jsp, "Result",
					JOptionPane.INFORMATION_MESSAGE);						
		}

	}
}
