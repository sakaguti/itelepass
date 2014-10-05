
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;

import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.BoxLayout;
import javax.swing.event.ListSelectionEvent;


public class JLabelList extends JPanel implements MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JLabelList jl=new JLabelList();
		frame.getContentPane().add(jl);
		
		jl.setScrollPosition(50);// 50%
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);

		String[] s=jl.getStringList();
		for(int i=0;i<s.length;i++){
			System.out.println("["+i+"]"+s[i]);
		}
	}

	/**
	 * This is the default constructor
	 */
	public JLabelList() {
		super();
		initialize();
	}
	private JLabel jlabel=null;
	private JList jlist=null;
	private DefaultListModel model=null;
	
	public void setText(String s)
	{
		jlabel.setText(s);
	}
	
	public String getText()
	{
		return jlabel.getText();
	}

	
	public void setList(String[] data)
	{
		if(model!=null){
		model.removeAllElements();
		//
		for(int i=0;i<data.length;i++){
//			System.out.println("JLabelList setList["+i+"]="+data[i]);
			model.addElement(data[i]);
			}
		}
		jlist.revalidate();
//		System.out.println("JLabelList size_final="+model.size());
	}
	
	JScrollPane scrollPane=null;
	
	public void setScrollPosition(int p)
	{
		JScrollBar vBar = scrollPane.getVerticalScrollBar();
		int vBarMin = vBar.getMinimum();
		int vBarMax = vBar.getMaximum();
		if(p >= vBarMin && p <= vBarMax){
	        vBar.setValue(p);
		}
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		JPanel base=new JPanel();
		base.setBackground(Color.white);
		base.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// label
		JPanel jp=new JPanel();
		jp.setBackground(Color.green);
		jp.setPreferredSize(new Dimension(80,30));
		jlabel=new JLabel();
	    Font f = new Font("ＭＳ 明朝", Font.BOLD, 16);
	    jlabel.setFont(f);
	    jlabel.setText("Plant");
		jlabel.setBackground(Color.green);
		jlabel.setForeground(Color.white);
		Font font = new Font("Arial", Font.BOLD, 16);
		jlabel.setFont(font);
		jlabel.setPreferredSize(new Dimension(80,20));
		jp.add(jlabel);
		this.add(jp);
//		
		String[] data = {"すべて","工場出荷値","バジル","イタリアンパセリ","チャービル","チャイブ","クレソン","A","B","C","D","E"};
		model = new DefaultListModel();
		int n=data.length;
		for(int i=0;i<n;i++){
			model.addElement(data[i]);
		}
		
		jlist=new JList(model);
//		jlist.setPreferredSize(new Dimension(120,100));
//		jlist.addListSelectionListener(this);
		jlist.addMouseListener(this);
		jlist.addKeyListener(this);

//
		scrollPane=new JScrollPane(jlist);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(120, 100));
		this.add(scrollPane);
		
//		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(80, 200);
		this.setBackground(Color.white);
		
		//
		// 観察される人を生成
		observableMan = new ObservableMan();

		// 観察者を追加
		observableMan.addObserver(new PlanterSetting.ObserverA());
		observableMan.addObserver(new PlantAbsPanel.ObserverA());
		observableMan.addObserver(new PrgAbs.ObserverA());
		observableMan.addObserver(new ProgramList.ObserverA());
		
		// Observerを設定してから選択すること。
		jlist.setSelectedIndex(0);
	}

	public void setSelectedIndex(int i)
	{
		jlist.setSelectedIndex(i);
	}
	
	public int getSelectedIndex()
	{
		return jlist.getSelectedIndex();
	}

	public String getSelectedItem()
	{
		return (String) jlist.getSelectedValue();
	}

	public JList getList()
	{
		return jlist;
	}

	public String[] getStringList()
	{
		String s="";
		String[] r=null;
		if(model!=null){
		for(int i=0;i<model.size();i++){
			s+= model.get(i)+System.getProperty("line.separator");
			}
		r=s.split(System.getProperty("line.separator"));
		}
		return r;
	}

	// 観察者を追加
	//	o=new  AutoPicture.ObserverA();
	//	jLabelList.addObserver(o);
	//
	void addObserver(Observer o)
	{
		observableMan.addObserver(o);
	}
// マウスの押したときと離した時の２度、イベントが発生する。
	public void valueChanged(ListSelectionEvent e) {	
	    Object[] arr = jlist.getSelectedValues();
	   message = jlabel.getText()+" ";
	    for(Object obj:arr){
	      message += (String)obj + " ";
	    }
//		System.out.println(message);
		observableMan.setMessage(message);
		// 観察者全員に通知
		observableMan.notifyObservers();
	}
	
	private ObservableMan observableMan=null;  //  @jve:decl-index=0:
	private String message="";  //  @jve:decl-index=0:
	
	/**
	* 観察者を観察する人A。
	*
	*/
	class ObserverA implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		String str = (String) arg;
		System.out.println("私はJLabelList classです。観察対象の通知を検知したよ。" + str);
		
	}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	    Object[] arr = jlist.getSelectedValues();
		   message = jlabel.getText()+" ";
		    for(Object obj:arr){
		      message += (String)obj + " ";
		    }
//			System.out.println(message);
			observableMan.setMessage(message);
			// 観察者全員に通知
			observableMan.notifyObservers();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

//System.out.println("Press Key ="+e.getKeyCode());
		if(e.getKeyCode()==38 || e.getKeyCode() ==40){
			// arrow key
		    Object[] arr = jlist.getSelectedValues();
			   message = jlabel.getText()+" ";
			    for(Object obj:arr){
			      message += (String)obj + " ";
			    }
//				System.out.println(message);
				observableMan.setMessage(message);
				// 観察者全員に通知
				observableMan.notifyObservers();
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void setSelectedItem(String str) {
		//
		String[] list=getStringList();
		for(int i=0;i<list.length;i++){
			if(str.equals(list[i])==true){
				//setScrollPosition(i);//
				jlist.setSelectedIndex(i);//
			}
		}
	}

}
