import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JLabel;



public class DoubleComb extends JPanel  implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JComboBox hourCombBox = null;
	private JComboBox minCombBox = null;
	private JLabel doubleColoum = null;
    private String[] hourdata = null;
    private String[] mindata = null;
    @Override
	public void setEnabled(boolean b)
    {
 //   	hourCombBox.setVisible(b);
 //   	minCombBox.setVisible(b);
    	base.removeAll();
    	if(b==false){
    	//
    	JLabel jh=new JLabel("      "+String.valueOf(hourCombBox.getSelectedIndex())+"      ");
    	JLabel jm=new JLabel(String.valueOf("      "+minCombBox.getSelectedIndex())+"      ");
		base.add(jh, gridBagConstraints1);
    	doubleColoum.setText(":");
		base.add(doubleColoum, gridBagConstraints2);	
		base.add(jm, gridBagConstraints3);
    	} else {
    	//
    	
    	base.add(hourComb, gridBagConstraints1);
    	doubleColoum.setText(":");
		base.add(doubleColoum, gridBagConstraints2);	
		base.add(minComb, gridBagConstraints3);
    	}
    }
   /**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
    public void setHour(int i)
    {
    	if(i>=0 && i<=23)
    		hourCombBox.setSelectedIndex(i);
    }
    
    public void setMin(int i)
    {
    	if(i>=0 && i<=59)
    		minCombBox.setSelectedIndex(i);
    }

    public int getHour()
    {
    		return hourCombBox.getSelectedIndex();
    }
    
    public int getMin()
    {
    	return minCombBox.getSelectedIndex();
    }
    
    @Override
	public void setName(String s)
    {
    	hourCombBox.setName(s+" Hour");
    	minCombBox.setName(s+" Min");
    }
    
    private JComboBox getHourComboBox() {
		if (hourCombBox == null) {
			// 24 Hours
			hourdata=new String[24];
			for(int i=0;i<24;i++){
				hourdata[i]= String.valueOf(i);
			}
			hourCombBox = new JComboBox(hourdata);
		}
		return hourCombBox;
	}
	
	private JComboBox getMinComboBox() {
		if (minCombBox == null) {
			// 60 Minutes
			mindata=new String[60];
			for(int i=0;i<60;i++){
				mindata[i]= String.valueOf(i);//
			}
			minCombBox = new JComboBox(mindata);
		}
		return minCombBox;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Ž©“®ŽB‰e@F@ˆê’èŽžŠÔ‚²‚Æ
		JFrame frame = new JFrame();
		DoubleComb gjp=new DoubleComb();
		
		gjp.setEnabled(true);
		gjp.setHour(11);
		gjp.setMin(22);
		//gjp.setEnabled(false);
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * This is the default constructor
	 */
	public DoubleComb() {
		super();
		initialize();
	}

	private JPanel base=null;
	private JComboBox hourComb=null;
	private JComboBox minComb=null;
	
	private GridBagConstraints gridBagConstraints1 =null;
	private GridBagConstraints gridBagConstraints2 =null;
	private GridBagConstraints gridBagConstraints3 =null;
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		//
		// Base
		GridBagLayout layout = new GridBagLayout();
		base=new JPanel(layout);
		base.setBackground(new Color(250,251,245));
		
		// Hour
		gridBagConstraints1 = new GridBagConstraints();
//		gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 0;
//		gridBagConstraints1.weightx = 1.0;
		hourComb=getHourComboBox();
		hourComb.addActionListener(this);
		hourComb.setName("HourComb");
		base.add(hourComb, gridBagConstraints1);
		
		// :
		gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.gridy = 0;
		doubleColoum = new JLabel();
		doubleColoum.setText(":");
		base.add(doubleColoum, gridBagConstraints2);	
		
		// Min
		gridBagConstraints3 = new GridBagConstraints();
//		gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints3.gridx = 2;
		gridBagConstraints3.gridy = 0;
//		gridBagConstraints3.weightx = 1.0;
		minComb=getMinComboBox();
		minComb.addActionListener(this);
		minComb.setName("minComb");
		base.add(minComb, gridBagConstraints3);
			
		//
		this.add(base);
		this.setBackground(new Color(216,241,254));
//		this.setSize(300, 100);
		
		//
		//
		// ŠÏŽ@‚³‚ê‚él‚ð¶¬
		observableMan = new ObservableMan();

		// ŠÏŽ@ŽÒ‚ð’Ç‰Á
		setObserver(new PlantPrgEdit.ObserverA());
		setObserver(new ReportPanel.ObserverA());
	}
	
	// Observer‚ð’Ç‰Á‚·‚é
	void addObserver(Observer o)
	{
		//observableMan.deleteObserver(defaultO);// ‘O‚ÉÝ’è‚³‚ê‚½Observer‚ðíœ‚·‚éB
		//observableMan.deleteObservers();
		observableMan.addObserver(o);
	}
	
	// Observer‚ðÝ’è‚·‚é
	// ˆÈ‘O‚É’Ç‰Á‚³‚ê‚½Observer‚Í‘S‚Ä”jŠü‚³‚ê‚é
	void setObserver(Observer o)
	{
		observableMan.deleteObservers();
		observableMan.addObserver(o);
	}
	
	// Observer‚ðíœ
	void deleteObserver(Observer o)
	{
		observableMan.deleteObserver(o);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox)e.getSource();

        // Get the new item
		//  System.out.println(cb.getSelectedItem());
		message = "Ž„‚ÍDoubleComb class‚Å‚·B"+cb.getName()+" "+cb.getSelectedItem();
		//
		// Do processing using temperature value
		observableMan.setMessage(message);
		// ŠÏŽ@ŽÒ‘Sˆõ‚É’Ê’m
		observableMan.notifyObservers();
	}
	
	private ObservableMan observableMan=null;  //  @jve:decl-index=0:
	private String message="";  //  @jve:decl-index=0:
	
	
} 
