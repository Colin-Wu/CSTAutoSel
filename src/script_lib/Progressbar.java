package script_lib;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

public class Progressbar  extends JFrame{
    private static final long serialVersionUID = 1L;  
    private JProgressBar processBar;  
	public Progressbar(){  

    	setUndecorated(true);
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
          
        setBounds(100, 100, 150, 26);
          
        setLocationRelativeTo(null); 

        JPanel contentPane = new JPanel();
          
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
          
        setContentPane(contentPane);
          
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER,1,1));
        contentPane.setOpaque(false);
          
        processBar = new JProgressBar();
          
        processBar.setStringPainted(true);
          
        processBar.setBackground(Color.GREEN);  
           
        contentPane.add(processBar);
        
        setAlwaysOnTop(true);
    }  
	
    public void showProgress() {
    	setVisible(true);
    }
    public void refreshProgress(int i) {
    	processBar.setValue(i);
    }	
    public void closeProgress() {
        setVisible(false);
        dispose();
    }	
	
}
