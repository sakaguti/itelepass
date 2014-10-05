import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class Selecters extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		Selecters gjp=new Selecters();
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	private Object selectButton;
	// Observerを追加する
	void addObserver(Observer o)
	{
		//observableMan.deleteObserver(defaultO);// 前に設定されたObserverを削除する。
		//observableMan.deleteObservers();
		observableMan.addObserver(o);
	}
	
	// Observerを設定する
	// 以前に追加されたObserverは全て破棄される
	void setObserver(Observer o)
	{
		observableMan.deleteObservers();
		observableMan.addObserver(o);
	}
	
	public void reListPlanter()
	{
		ps.reListPlanter();
	}
	
	// Observerを削除
	void deleteObserver(Observer o)
	{
		observableMan.deleteObserver(o);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.equals(selectButton)==false) return;
	      JButton btn = (JButton)arg0.getSource();
	      
	      if(btn.equals(selectButton)){
	    //	  System.out.println("addTimeButton");  	  
	      }       
	      message ="私はSelectors classです。"+btn.getText()+"が押されました。!!!!!!"+"btn.getName():"+btn.getName()+" +btn.getText():"+btn.getText();
	      
	      if(btn.getText().contains("このプランターを選択する")==true){
			 int pno=Integer.parseInt(btn.getText().replaceAll("[^0-9]",""));//
	    	 //int pno=Integer.valueOf(btn.getText());
	    	 ITPlanterClass.setCurrentPlanterNo(pno);// 現在のプランターの番号
	    	 ITPlanterClass.setCurrentPlanterClass(ITPlanterClass.getPlanterList().get(pno));// 現在のプランターのクラス
	    	 
	    	 // save cam no
	    	 int camno=cs.getCamNo();
	    	 Files.setCamNo(camno);
	      }
	      
	      //
	      	observableMan.setMessage(message);
			// 観察者全員に通知
			observableMan.notifyObservers();
			
	}
	
	private ObservableMan observableMan=null;  
	private String message="";
	//private Observer defaultO=null;
	
	/**
	* 観察者を観察する人A。
	*
	*/
	static class ObserverA implements Observer {
	/* (非 Javadoc)
	* @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	*/
	@Override
	public void update(Observable o, Object arg) {
	String str = (String) arg;

	System.out.println("私はSelectors classです。観察対象の通知を検知したよ。" + str);

		}
	}

	CameraSelecter cs=null;
	/**
	 * This is the default constructor
	 */
	public Selecters() {
		super();
		initialize();
	}

	private PlanterSelecter ps=null;
	private JPanel base=null;
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagLayout gridbag = new GridBagLayout();
		base=new JPanel(gridbag);
		//base.setPreferredSize(new Dimension(640,400));
		base.setBackground(new Color(250,251,245));

		// カメラ選択
		cs=new CameraSelecter();
		cs.setPreferredSize(new Dimension(640,120));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;	
		constraints.gridy = 0;	
		constraints.gridwidth= 1;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(cs, constraints);
		base.add(cs);
		
		// separator
		JSeparator vspr=new JSeparator(SwingConstants.HORIZONTAL);
		vspr.setPreferredSize(new Dimension(640,10));
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridx = 0;	
		constraints2.gridy = 1;	
		constraints2.gridwidth= 1;
		constraints2.gridheight = 1;
		constraints2.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(vspr, constraints2);
		base.add(vspr);
		
		// プランター選択
		ps=new PlanterSelecter();
		ps.setPreferredSize(new Dimension(640,180));
		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3.gridx = 0;	
		constraints3.gridy = 2;	
		constraints3.gridwidth= 1;
		constraints3.gridheight = 1;
		constraints3.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(ps, constraints3);
		base.add(ps);
		
		this.add(base);
		this.setBackground(new Color(250,251,245));
		
		// 観察される人を生成
		observableMan = new ObservableMan();

		// デフォルトの観察者を追加
		Observer defaultO=new PlanterSetting.ObserverA();
		observableMan.addObserver(defaultO);
	}

}
