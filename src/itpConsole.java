import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

class itpConsole extends JFrame{
private static final long serialVersionUID = 2L;

public static void main(String args[]){
	itpConsole frame = new itpConsole("Console");
	frame.setResizable(false);
    frame.setVisible(true);
    frame.setResizable(false);
	Image icon=frame.getImage("icon_Mac.png");
	frame.setIconImage(icon);

  }

private  Image getImage(String string) {
	return getToolkit().getImage(string);
}

public void log_save()
{
	File f = new File("log.txt");// WindowsÇ…ñ¢ëŒâûÇæÇ¡ÇΩÅB

	String t=area.getText();
	byte[] b = new byte[t.length()];
	b=t.getBytes();
	try 
	{	
		FileOutputStream fo= new FileOutputStream(f);
		fo.write(b);	
		fo.close();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	itp_Logger.logger.info("log save");

}

JTextArea area=null;

public	itpConsole(String title){
    setTitle(title);
    setBounds(10, 10, 650, 490);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    JPanel p = new JPanel();

    area = new JTextArea();
    //area.setEditable(false);  // ReadOnly
    JTextAreaStream stream = new JTextAreaStream(area);
    
       	// full path need.
    	File f = new File("nolog.txt");
    	try 
    	{	
    		// nolog file available
       		FileInputStream fi = new FileInputStream(f);
    		fi.close();
    	}
    	catch(Exception e)
    	{
    	    System.setOut(new PrintStream(stream, true));    // true ÇÕ AutoFlush ÇÃê›íË
    	    System.setErr(new PrintStream(stream, true));    // true ÇÕ AutoFlush ÇÃê›íË
    	}

   
    JScrollPane scrollpane = new JScrollPane(area);
    scrollpane.setPreferredSize(new Dimension(this.getWidth()-20, this.getHeight()-40));

    p.add(scrollpane);

    Container contentPane = getContentPane();
    contentPane.add(p, BorderLayout.CENTER);
    
    scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    ////
  }

}