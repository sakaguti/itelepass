import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class PlantAbsPanel extends JPanel  implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new PlantAbsPanel());
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setSize(new Dimension(640,480));
		frame.setVisible(true);
		frame.setResizable(false);

	}

	/**
	 * This is the default constructor
	 */
	public PlantAbsPanel() {
		super();
		initialize();
	}

	private JButton editBtn=null;
	public static ProgramList prgList=null;
	private TripleButton tripleButton=null;
	
	public ProgramList getPrgList()
	{
		return prgList;
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// panel
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.gridy = 3;
		
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 2;
		
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		
		GridBagConstraints gridBagConstraints0 = new GridBagConstraints();
		gridBagConstraints0.gridx = 0;
		gridBagConstraints0.gridwidth = 1;
		gridBagConstraints0.gridy = 0;
		
		GridBagLayout gridbag = new GridBagLayout();
		JPanel jp = new JPanel(gridbag);
		jp.setBackground(new Color(250,251,245));
		
		// 栽培プログラムのリスト
		prgList=new ProgramList();
		gridbag.setConstraints(prgList, gridBagConstraints0);
		jp.add(prgList);
		
		PrgAbs prgAbs=new PrgAbs();
		prgAbs.setBackground(new Color(250,251,245));
		gridbag.setConstraints(prgAbs, gridBagConstraints1);
		jp.add(prgAbs);
		
		//
		editBtn=new JButton("栽培プログラムの詳細を開く");
		editBtn.setBackground(new Color(250,251,245));
		editBtn.setBorder(null);
		editBtn.setName(editBtn.getText());
		gridbag.setConstraints(editBtn, gridBagConstraints2);
		editBtn.addActionListener(this);
		jp.add(editBtn);
		
		tripleButton=new TripleButton();
		//tripleButton.addActionListener(this);
		gridbag.setConstraints(tripleButton, gridBagConstraints3);
		jp.add(tripleButton);
				
		//
		this.add(jp);
		this.setPreferredSize(new Dimension(600,480));
		this.setBackground(new Color(250,251,245));
		
		//
		// 観察される人を生成
		observableMan = new ObservableMan();

		// 観察者を追加
		//defaultO = new AutoPicture.ObserverA();
		//observableMan.addObserver((java.util.Observer)defaultO);
	}


	ActionEvent actionevent=null;  //  @jve:decl-index=0:
	
	

	void addObserver(Observer o)
	{
		if(observableMan!=null)
			observableMan.addObserver(o);
		if(tripleButton!=null)
			prgList.addObserver(o);
		// if(tripleButton!=null)
		//	tripleButton.addObserver((java.util.Observer)o);
	}
	
	//
	private ObservableMan observableMan=null;  //  @jve:decl-index=0:
	private String message="";  //  @jve:decl-index=0:
	/**
	* 観察者を観察する人A。
	*
	*/
	static class ObserverA implements Observer {
	@Override
	public void update(Observable o, Object arg) {
		String str = (String) arg;
		System.out.println("私はPlantAbsPanel classです。観察対象の通知を検知したよ。" + str);
		String[] s=str.split(" ");
		
		if(prgList!=null){
		if(s.length>1){
		// Category 自作 
		if(s[0].equals("Category")==true) prgList.setCategory(s[1]);
		// Plants チャイブ 
		if(s[0].equals("Plants")==true) prgList.setPlantName(s[1]);
		// Program chive 
		if(s[0].equals("Program")==true) prgList.setProgram(s[1]);
		
		// Save selected item information
		int ic=ITPlanterClass.getCurrentPlanterNo();
		Information pi=ITPlanterClass.getPlanterList().get(ic).getInformation();
		PlantProgram p=pi.getPlantProgram();
		p.setProgramName(prgList.getProgramName());
		p.setPlantCategory(prgList.getCategory());
		p.setPlantName(prgList.getPlantName());
		//
		
		}
		}
		
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println(e);
		actionevent=e;
		JButton btn = (JButton)e.getSource();
//			System.out.println(btn.getName()+" "+btn.getText());
			message="PlantAbsPanel class"+btn.getName()+" "+btn.getText(); // 
			observableMan.setMessage(message);
		//
		// 観察者全員に通知
		observableMan.notifyObservers();// 引数はとらない
		//
	}

	public void reList() {
		//String s=prgList.getSelectedPlants();
		prgList.reList();
//		PrgAbs.getParentClass().setInformation("default");
	}
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
