import info.bix.tokai.bixpp.binding.BIXpp;
import info.bix.tokai.bixpp.io.BIXppIO;
import info.bix.tokai.bixpp.xml.XMLException;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class PlantTitle extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		PlantTitle gjp=new PlantTitle();
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public PlantTitle() {
		super();
		initialize();
	}

	private JLabel jlabel=null;
	private JTextArea jt=null;
	private String newline=System.getProperty("line.separator");  //  @jve:decl-index=0:
	private BIXpp bixpp=null;
	
	// "spinach-j.xml"
	public void getBIX(String s)
	{
        try {
//            System.out.println(s);
            if(IsMacorWin.isMacOrWin()==true)
            	s=s.replace("//", "/");
            else
            	s=s.replace("\\\\", "\\");
            File file=new File(s);
            if(file.exists()==false) return;
            
            bixpp = BIXppIO.read(file);
            String f[]=s.split("/");
            String fileName=f[f.length -1].replace(".xml","");
            
            // Title
            setTitle(fileName);	//bixpp.getCultivation(0).getPlant().getName());// 植物の名前
       
            // Comment
            // 栽培データの最初のコメントを表示する。
            setText(String.valueOf(bixpp.getCultivation(0).getPlant().getName()+newline+newline+bixpp.getCultivation(0).getComment()));
                   
        } catch (XMLException e) {
            //e.printStackTrace();
        } catch (FileNotFoundException e) {
        	System.out.println("BIXpp file "+s+" not found.");
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }
	}
	
	public void setTitle(String s)
	{
		jlabel.setText(s);
	}
	
	public void setText(String s)
	{
		jt.setText(s);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints0 = new GridBagConstraints();
//		gridBagConstraints0.fill = GridBagConstraints.BOTH;
		gridBagConstraints0.gridx = 0;
		gridBagConstraints0.gridy = 1;
		gridBagConstraints0.weightx = 1.0;
		gridBagConstraints0.weighty = 4.0;
		gridBagConstraints0.gridx = 0;
		
		//
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		JPanel jp = new JPanel(gridbag);
		jp.setBackground(new Color(250,251,245));
//		jp.setPreferredSize(new Dimension(400, 400));
		jp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		//constraints.weightx = 100.0;
		constraints.insets = new Insets(0, 0, 0, 0);
		
		JPanel jpt=new JPanel();
		jpt.setBackground(new Color(250,251,245));
		jlabel=new JLabel("工場出荷値");
		jpt.add(jlabel);
		jpt.setBackground(new Color(250,251,245));
		gridbag.setConstraints(jpt, constraints);
	//	jpt.setPreferredSize(new Dimension(200, 30));
		if(Version.getRevision().contains("Education")==false)// add 20130628
		jp.add(jpt);
		

		// separator
		JSeparator vspr=new JSeparator(SwingConstants.HORIZONTAL);
		vspr.setPreferredSize(new Dimension(300,10));
		//
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(vspr, gridBagConstraints);
		jp.add(vspr);
		

		// Title & Informations
		//
		JPanel jpTitle=new JPanel();
		jpTitle.setBackground(new Color(250,251,245));
		jt=new JTextArea("これが正しい設定です。");
		jt.setLineWrap(true);
		JScrollPane scrollpane = new JScrollPane(jt,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 2;
		gridBagConstraints1.weightx = 2.0;
		gridBagConstraints1.gridheight = 8;
		gridBagConstraints1.weighty = 0.0;
		
		jp.add(jpTitle, gridBagConstraints);
		jp.add(scrollpane, gridBagConstraints1);
		scrollpane.setPreferredSize(new Dimension(100,80));
		this.add(jp);
	//	this.setPreferredSize(new Dimension(200,200));
		this.setBackground(new Color(250,251,245));
		
		
		
		// "spinach-j.xml"
		String s = Path.getPath()+"/DB/basil.xml";
		if(IsMacorWin.isMacOrWin()==false) s = Path.getPath()+"DB\\basil.xml";
		getBIX(s);
		
	}
	
	public void setBIX(String t)
	{
		String s = Path.getPath()+"/DB/"+t;
		if(IsMacorWin.isMacOrWin()==false) s = Path.getPath()+"DB\\"+t;
		getBIX(s);
	}

}
