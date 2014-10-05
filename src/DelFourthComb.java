
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class DelFourthComb extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		DelFourthComb gjp=new DelFourthComb();
		
		//gjp.setEnabled(false);
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public DelFourthComb() {
		super();
		initialize();
	}

	private JButton jb=null;
	private FourthComb dc=null;
	private JPanel base=null;
	
	@Override
	public void setEnabled(boolean b)
	{
		jb.setVisible(b);
		jb.setEnabled(b);
		dc.setEnabled(b);
	}
	
	void setONBackgroud(Color c)
	{
		if(dc !=null )
			dc.setONBackground(c);
	}

	void setOFFBackgroud(Color c)
	{
		if(dc !=null )
			dc.setOFFBackground(c);
	}

	void setFromHour(int i)
	{
		if(dc !=null )
			dc.setFromHour(i);
	}
	
	public int getFromHour() {	
		return dc.getFromHour();
	}
	
	void setFromMin(int i)
	{
		if(dc !=null )
			dc.setFromMin(i);
	}
	
	public int getFromMin() {	
		return dc.getFromMin();
	}
	
	void setToHour(int i)
	{
		if(dc !=null )
			dc.setToHour(i);
	}
	
	public int getToHour()
	{
		return	dc.getToHour();
	}
	
	void setToMin(int i)
	{
		if(dc !=null )
			dc.setToMin(i);
	}
	
	public int getToMin()
	{
		return dc.getToMin();
	}
	
	   // Hour, Minの背景色を変更する。
	   public void 	setBackgrounds(Color c)
	   {
		   if(dc !=null)
			   dc.setBackgrounds(c);	   
	   }
	   
	   
	   @Override
	public void 	setBackground(Color c)
	   {
		   if(base !=null)
			   base.setBackground(c);	   
	   }
	/*
	 *   X ボタンを押された時のActionを付け加えること
	 */

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		//
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		base=new JPanel( new GridBagLayout() );
		//jp.setBackground(Color.white);
		
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth= 1;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.weightx = 1.0;
		dc=new FourthComb();
		//
		dc.setObserver(new PlantPrgEdit.ObserverA());//
		
		base.add(dc, gridBagConstraints);
		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth= 1;
		gridBagConstraints.gridheight = 1;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.weightx = 1.0;
		jb=new JButton();
		//
		jb.setText("X");
		jb.setBorder(null);
		jb.setBackground(Color.white);
		jb.setSize(new Dimension(20,20));
		jb.setPreferredSize(new Dimension(20,20));
		jb.addActionListener(this);
		base.add(jb, gridBagConstraints);
		
		this.add(base);
		//this.setSize(250, 250);
		this.setLayout(new GridBagLayout());
		
		//
		//
		// 観察される人を生成
		observableMan = new ObservableMan();

		// デフォルトの観察者を追加
		defaultO=new illumPanelEdit3.ObserverA();
		setObserver(defaultO);
		
		
	}
	
	// Observerを追加する
	void addObserver(Observer o)
	{
		observableMan.addObserver(o);
	}
	
	// Observerを設定する
	// 以前に追加されたObserverは全て破棄される
	void setObserver(Observer o)
	{
		observableMan.deleteObservers();
		observableMan.addObserver(o);
	}
	
	// Observerを削除
	void deleteObserver(Observer o)
	{
		observableMan.deleteObserver(o);
	}
	
	
	private ObservableMan observableMan=null;  
	private String message="";
	private Observer defaultO=null;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		message="私はDelFourthComb classです。"+"Delete Buttonが押されました。";
		
		deleteProcessing();
		//	this.setVisible(false);
		
//			System.out.println("Delete Button");
		observableMan.setMessage(message);
		// 観察者全員に通知
		observableMan.notifyObservers();
		

	}
	
	/**
	* 観察者を観察する人A。
	*
	*/
	class ObserverA implements Observer {
	/* (非 Javadoc)
	* @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	*/
	@Override
	public void update(Observable o, Object arg) {
	String str = (String) arg;
	System.out.println("私はDelFourthComb classです。観察対象の通知を検知したよ。" + str);
		}
	}
	
	// Should be Override
	void deleteProcessing()
	{
//		System.out.println("deleteProcessing: " +this.getName());
		// finish processing
		this.setName(this.getName()+" X");// add delete mark
		
//		System.out.println("deleteProcessing: " +this.getName());
		//this.remove(base);
	}


	

}
