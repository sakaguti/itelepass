
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;


public class InfoDispEdit extends JPanel  implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		InfoDispEdit gjp=new InfoDispEdit();
		
		//gjp.setEnabled(true);
		gjp.setUnit("度");
		gjp.setEnabled(true);
		
		gjp.setValue("40");
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public InfoDispEdit() {
		super();
		initialize();
	}

	private JLabel jl1=null;
	private JLabel jl3=null;

	public GJPanel gJPanel=null;
	private JComboBox paraCombBox = null;
	private String unit="";
	
	// 表示ラベルの文字
	public void setText(String s)
	{
		jl1.setText(s);
	}
	
	// ℃　とかの単位
	public void setUnit(String s)
	{
		unit=s;
		if(jl3 !=null) jl3.setText(s);
	}
	
	public String getUnit()
	{
		return unit;
	}
	//
	public void setSelectedIndex(int i)
	{
		paraCombBox.setSelectedIndex(i);
	}
	
	public Double getValue()
	{
		Double d=1.0;
		if(paraCombBox!=null){
		if(paraCombBox.getSelectedItem()!=null){
			d=Double.valueOf((String)paraCombBox.getSelectedItem());
		}
		}
			return d;
	}


	
    // 温度　0℃～40℃
    public JComboBox getComboBox() {
		if (paraCombBox == null) {
			// 24 Hours
			//tempdata=new String[42];
			
			paraCombBox = new JComboBox();
			//tempCombBox.removeAllItems();

			for(int i=0;i<40;i++){
				//tempdata[i]= String.valueOf(i);
				paraCombBox.addItem(String.valueOf(i));
			}

			paraCombBox.setSelectedIndex(22);
		}
		return paraCombBox;
	}
    
    // set selection Items
    public void setSelectionItems(String[] t)
    {
//    	if(paraCombBox==null) paraCombBox = new JComboBox<String>();
    	if(paraCombBox.getItemCount() > 0){
    		paraCombBox.removeAllItems();
    	}
    	for(int i=0;i<t.length;i++){
    		paraCombBox.addItem(t[i]);
    	}
    }
    
    // setEnabled(true)		:	JComboBoxが編集可能
    // setEnabled(false)	:	JComboBoxが編集不可能   
    JLabel jlabel=null;
    @Override
	public void setEnabled(boolean b)
    {
		paraCombBox.setEnabled(b);
		
		if(b==false){
		jp2.removeAll();
		jlabel=new JLabel((String)paraCombBox.getSelectedItem());
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridx = 0;	
		constraints2.gridy = 0;	
		constraints2.gridwidth= 1;
		constraints2.gridheight = 1;
		constraints2.insets = new Insets(0, 0, 0, 0);
		gridbag2.setConstraints(jlabel, constraints2);
		jp2.add(jlabel);
		// Unit [%]
		//
		jl3=new JLabel(getUnit());
		jl3.setBackground(new Color(250,251,245));
		jl3.setHorizontalAlignment(SwingConstants.RIGHT);
		jl3.setHorizontalTextPosition(SwingConstants.RIGHT);
		jl3.setPreferredSize(new Dimension(40,20));
		
		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3.gridx = 1;	
		constraints3.gridy = 0;	
		constraints3.gridwidth= 1;
		constraints3.gridheight = 1;
		constraints3.insets = new Insets(0, 0, 0, 0);
		gridbag2.setConstraints(jl3, constraints3);
		jp2.add(jl3);
		} else {
			jp2.removeAll();
			paraCombBox=getComboBox();
			GridBagConstraints constraints2 = new GridBagConstraints();
			constraints2.gridx = 0;	
			constraints2.gridy = 0;	
			constraints2.gridwidth= 1;
			constraints2.gridheight = 1;
			constraints2.insets = new Insets(0, 0, 0, 0);
			gridbag2.setConstraints(paraCombBox, constraints2);
			jp2.add(paraCombBox);
			
			// Unit [%]
			//
			jl3=new JLabel(getUnit());
			jl3.setBackground(new Color(250,251,245));
			jl3.setHorizontalAlignment(SwingConstants.RIGHT);
			jl3.setHorizontalTextPosition(SwingConstants.RIGHT);
			jl3.setPreferredSize(new Dimension(40,20));
			
			GridBagConstraints constraints3 = new GridBagConstraints();
			constraints3.gridx = 1;	
			constraints3.gridy = 0;	
			constraints3.gridwidth= 1;
			constraints3.gridheight = 1;
			constraints3.insets = new Insets(0, 0, 0, 0);
			gridbag2.setConstraints(jl3, constraints3);
			jp2.add(jl3);
			revalidate();
		}
    }
    
    private GridBagLayout gridbag = null;
    private GridBagLayout gridbag2 = null;
    private JPanel jp2=null;
    
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);
		gridbag = new GridBagLayout();
		
		JPanel base=new JPanel(gridbag);
		base.setBackground(new Color(250,251,245));
		
		// panel
		JPanel jp1=new JPanel();
		jp1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		jp1.setLayout(gridLayout);

		jp1.setPreferredSize(new Dimension(160,20));
		jp1.setBackground(new Color(150,150,150));
		
		jl1=new JLabel("警告温度");
		jl1.setHorizontalTextPosition(SwingConstants.CENTER);
		jl1.setVerticalTextPosition(SwingConstants.CENTER);
		jl1.setHorizontalAlignment(SwingConstants.CENTER);
		jl1.setForeground(null);
		jp1.add(jl1, null);
		

		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 0;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(jp1, constraints1);
		base.add(jp1);
		
		gridbag2 = new GridBagLayout();
		jp2=new JPanel(gridbag2);
		jp2.setBackground(new Color(250,251,245));

		//
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridx = 0;	
		constraints2.gridy = 0;	
		constraints2.gridwidth= 1;
		constraints2.gridheight = 1;
		constraints2.insets = new Insets(0, 0, 0, 0);
		gridbag2.setConstraints(getComboBox(), constraints2);
		paraCombBox.addActionListener(this);
		jp2.add(getComboBox());
		//
		//this.setEnabled(true);
		
		// Unit [%]
		//
		setUnit("℃");
		jl3=new JLabel(getUnit());
		jl3.setBackground(new Color(250,251,245));
		jl3.setHorizontalAlignment(SwingConstants.RIGHT);
		jl3.setHorizontalTextPosition(SwingConstants.RIGHT);
		jl3.setPreferredSize(new Dimension(40,20));
		
		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3.gridx = 1;	
		constraints3.gridy = 0;	
		constraints3.gridwidth= 1;
		constraints3.gridheight = 1;
		constraints3.insets = new Insets(0, 0, 0, 0);
		gridbag2.setConstraints(jl3, constraints3);
		jp2.add(jl3);
		
		//
		GridBagConstraints constraints4 = new GridBagConstraints();
		constraints4.gridx = 0;	
		constraints4.gridy = 1;	
		constraints4.gridwidth= 1;
		constraints4.gridheight = 1;
		constraints4.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(jp2, constraints4);
		base.add(jp2);
		//
		this.add(base);
		this.setBackground(new Color(250,251,245));
		//this.setSize(200, 100);
		//this.setPreferredSize(new Dimension(200,100));
		//this.setLayout(new BorderLayout());
		//this.add( jp, BorderLayout.CENTER );
		//
		//
		// 観察される人を生成
		observableMan = new ObservableMan();

		// 観察者を追加
		observableMan.addObserver(new PlantPrgEdit.ObserverA());
	}

    private ObservableMan observableMan=null;  
    private String message="";
    
	@Override
	public void actionPerformed(ActionEvent e) {

	    //System.out.println("tempCombBox");

		JComboBox cb = (JComboBox)e.getSource();
		if(cb==null)  return;
		if(jl1==null) return;
		//
		if(cb.getSelectedItem()==null) return;
		// 照度が０になってしまうバグがある。
		if(jl1.getText().equals("警告照度")&&cb.getSelectedItem().equals("0")) return;//<<<<< 　どこで０になっているか不明。仕方がないので、警告照度 0の場合飛ばす。
		//
		System.out.println("jl1.getText()="+jl1.getText());
		System.out.println("cb.getSelectedItem()="+cb.getSelectedItem());
        // Get the new item
		//System.out.println(cb.getSelectedItem());
		
		message="私はInfoDispEdit classです。"+jl1.getText()+" "+String.valueOf(cb.getSelectedItem())+" "+jl3.getText();
		if(message.contains("null")) return;
		observableMan.setMessage(message);
		//
		// Do processing using temperature value
		// 観察者全員に通知
		observableMan.notifyObservers();
		if(ITPlanterClass.getState()==false) new ITPlanterClass();
		
		if(cb.getSelectedItem() != null){
			String txt=jl1.getText();
		if(txt.contains("警告")&&txt.contains("温度")) {
			Information info=ITPlanterClass.getCurrentPlanterClass().getInformation();
			if(info!=null){
			PlantProgram p=info.getPlantProgram();
			if(p==null){
				p=new PlantProgram("current.xml");
				ITPlanterClass.getPlanterList().get(ITPlanterClass.currentPlanterNo).getInformation().setPlantProgam(p);
			}
			String si=(String)cb.getSelectedItem();
			p.setTempWarn(Double.valueOf(si));
			}
		}
		if(txt.contains("警告")&&txt.contains("水位")) {
			Information info=ITPlanterClass.getCurrentPlanterClass().getInformation();
			if(info!=null){
			PlantProgram p=info.getPlantProgram();
			String si=(String)cb.getSelectedItem();
			p.setWaterWarn(Double.valueOf(si));
			}
		}
		if(txt.contains("ポンプ動作時間")) {
			Information info=ITPlanterClass.getCurrentPlanterClass().getInformation();
			if(info!=null){
			PlantProgram p=info.getPlantProgram();
			String si=(String)cb.getSelectedItem();
			p.setPumpWrokTime(Double.valueOf(si));
			}
		}
		if(txt.contains("警告")&&txt.contains("照度")) {
			Information info=ITPlanterClass.getCurrentPlanterClass().getInformation();
			if(info!=null){
			String si=(String)cb.getSelectedItem();
			PlantProgram p=info.getPlantProgram();
			p.setIllumWarn(Double.valueOf(si));
			}
		}
		}
		
	}
	//
	void addObser(Observer o)
	{
		observableMan.addObserver(o);
	}

	public void setValue(String s) {
		if(paraCombBox==null) return;
			int pn=paraCombBox.getItemCount();
			for(int i=0;i<pn;i++){
				if(paraCombBox.getItemAt(i)!=null){
				boolean itm=paraCombBox.getItemAt(i).equals(s);
				if(itm==true){
					paraCombBox.setSelectedIndex(i); //
					break; //
					}
				}
			}
	}

}