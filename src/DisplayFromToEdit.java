
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class DisplayFromToEdit extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		DisplayFromToEdit gjp=new DisplayFromToEdit();
		
		gjp.setEnabled(false);
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	//	frame.setResizable(false);
	}

	/**
	 * This is the default constructor
	 */
	public DisplayFromToEdit() {
		super();
		initialize();
	}
	
	private DelFourthComb gjp=null;
	
	@Override
	public void setEnabled(boolean b)
	{
		gjp.setEnabled(b);
	}
	
	public void setFromHour(int i){
		gjp.setFromHour(i);
	}
	
	public int getFromHour(){
		return gjp.getFromHour();
	}
	

	public void setFromMin(int i){
		gjp.setFromMin(i);
	}
	
	public int getFromMin(){
		return gjp.getFromMin();
	}


	public void setToHour(int i){
		gjp.setToHour(i);
	}

	public int getToHour(){
		return gjp.getToHour();
	}
	
	public void setToMin(int i){
		gjp.setToMin(i);
	}

	public int getToMin(){
		return gjp.getToMin();
	}
	
	@Override
	public String getName()
	{
		if(gjp != null)
			return gjp.getName();
		else
			return null;
	}
	
	@Override
	public void setName(String s)
	{
		if(gjp != null)
			 gjp.setName(s);
	}
	
	@Override
	public void setBackground(Color c)
	{
		if(gjp != null)
			gjp.setBackground(c);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		//
		//
		gjp=new DelFourthComb();
		gjp.setName("Component");// default name
		gjp.setBackgrounds(new Color(250,250,50));

		this.add(gjp);
		//this.setBackground(Color.white);
		//this.setSize(300, 150);
		//this.setPreferredSize(new Dimension(300,170));
		
		//
		// 追加
		// 観察される人を生成
		observableMan = new ObservableMan();

		// デフォルトの観察者を追加
		defaultO=new PlantPrgEdit.ObserverA();
		setObserver(defaultO);
	}
	
	// 必要なメッソドを追加
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
	
	// Observerを削除
	void deleteObserver(Observer o)
	{
		observableMan.deleteObserver(o);
	}
	
	// 通知
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton cb = (JButton)e.getSource();

        // Get the new item
		//  System.out.println(cb.getSelectedItem());
		message = "私はDisplayFromToEdit classです。"+cb.getName();
		//
		// Do processing using temperature value
		observableMan.setMessage(message);
		// 観察者全員に通知
		observableMan.notifyObservers();
	}


//
	private ObservableMan observableMan=null;  
	private String message="";
	private Observer defaultO=null;
	

// 各クラス固有
	
	/**
	* 観察者を観察する人A。
	*
	*/
	class ObserverA implements Observer {

	@Override
	public void update(Observable o, Object arg) {
	String str = (String) arg;
	System.out.println("私はDisplayFromToEdit classです。観察対象の通知を検知したよ。" + str);
		}
	}


}
