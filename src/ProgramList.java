
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;



public class ProgramList extends JPanel implements ActionListener  {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		ProgramList pl=new ProgramList();
		frame.getContentPane().add(pl);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);

	}


	/**
	 * This is the default constructor
	 */
	public ProgramList() {
		super();
		initialize();
	}

	private JButton newPrgButton=null;
	private static JLabelList categolyList=null;
	private static JLabelList plantList=null;
	private static JLabelList programList=null;
	private JLabel programTitle=null;
	private static ProgramList parentClass=null;
	public void setParentClass(ProgramList p)
	{
		parentClass=p;
	}
	public static ProgramList getParentClass()
	{
		return parentClass;
	}
	
	// 苗
	public Component[] getPlantComponents()
	{
		int n=plantList.getComponentCount();
		Component[] c=new Component[n];
		for(int i=0;i<n;i++){
			c[i]=plantList.getComponent(i);
		}
		return c;
	}
	
	// 栽培プログラム
	public Component[] getProgramComponents()
	{
		int n=programList.getComponentCount();
		Component[] c=new Component[n];
		for(int i=0;i<n;i++){
			c[i]=programList.getComponent(i);
		}
		return c;
	}
	
	// カテゴリ
	public Component[] getCategolyComponents()
	{
		int n=categolyList.getComponentCount();
		Component[] c=new Component[n];
		for(int i=0;i<n;i++){
			c[i]=categolyList.getComponent(i);
		}
		return c;
	}
	
	public void addPlantComponent(Component s)
	{
		plantList.add(s);
	}
	
	public void addProgramComponent(Component s)
	{
		programList.add(s);
	}
	
	public void addCategolyComponent(Component s)
	{
		categolyList.add(s);
	}
	
	public void setPlantListTitle(String s)
	{
		plantList.setText(s);
	}
	
	public void setCategolyListTitle(String s)
	{
		categolyList.setText(s);
	}
	
	public void settProgramListTitle(String s)
	{
		programList.setText(s);
	}
	
	public void setCategolyList(String[] s)
	{
		categolyList.setList(s);
	}
	
	public void setPlantList(String[] s)
	{
		plantList.setList(s);
	}
	
	public void setProgramList(String[] s)
	{
		programList.setList(s);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		//
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints1 = new GridBagConstraints();
		
		JPanel jp=new JPanel(gridbag);
		
		jp.setBackground(new Color(250,251,245));
//		jp.setPreferredSize(new Dimension(600,200));
		
		// label
		programTitle=new JLabel("栽培プログラム");
		programTitle.setForeground(Color.green);
		constraints1.anchor=GridBagConstraints.NORTH;
		constraints1.gridx = 0;
		constraints1.gridy = 0;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(programTitle, constraints1);
		if(Version.getRevision().equals("Education")!=true)
			jp.add(programTitle);
		//
		newPrgButton=new JButton("新しく栽培プログラムを作る");
		newPrgButton.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		newPrgButton.setToolTipText("Save Data and Dispose.");
		
		newPrgButton.setName(newPrgButton.getText());
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.anchor=GridBagConstraints.NORTH;
		constraints2.gridx = 4;
		constraints2.gridy = 0;	
		constraints2.gridwidth= 1;
		constraints2.gridheight = 1;
		constraints2.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(newPrgButton, constraints2);
		newPrgButton.setPreferredSize(new Dimension(180,20));
		newPrgButton.addActionListener(this);
		jp.add(newPrgButton);
		

		//	苗
		plantList=new JLabelList();
		plantList.setText("Plants");
		String[] s1={"すべて","公式","自作","投稿"};
		plantList.setList(s1);
		
		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3.anchor=GridBagConstraints.NORTH;
		constraints3.gridx = 0;
		constraints3.gridy = 1;	
		constraints3.gridwidth= 1;
		constraints3.gridheight = 1;
		constraints3.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(plantList, constraints3);
		plantList.setPreferredSize(new Dimension(180,100));
		if(Version.getRevision().equals("Education")!=true)
			jp.add(plantList);
		
		//
		// separator
		JSeparator vspr=new JSeparator(JSeparator.VERTICAL);
		vspr.setPreferredSize(new Dimension(10,100));
		GridBagConstraints constraints4 = new GridBagConstraints();
		constraints4.gridx = 1;
		constraints4.gridy = 1;	
		constraints4.gridwidth= 1;
		constraints4.gridheight = 1;
		constraints4.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(vspr, constraints4);
		if(Version.getRevision().equals("Education")!=true)
			jp.add(vspr);
		//
		// カテゴリ
		categolyList=new JLabelList();
		categolyList.setText("Category");
		String[] s2={"すべて","公式","自作","投稿"};
		categolyList.setList(s2);
		
		constraints1.anchor=GridBagConstraints.NORTH;
		GridBagConstraints constraints5 = new GridBagConstraints();
		constraints5.gridx = 2;
		constraints5.gridy = 1;	
		constraints5.gridwidth= 1;
		constraints5.gridheight = 1;
		constraints5.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(categolyList, constraints5);
		categolyList.setPreferredSize(new Dimension(180,100));
		if(Version.getRevision().equals("Education")!=true)
			jp.add(categolyList);
		//
		// separator
		JSeparator vspr2=new JSeparator(JSeparator.VERTICAL);
		vspr2.setPreferredSize(new Dimension(10,100));
		GridBagConstraints constraints6 = new GridBagConstraints();
		constraints6.gridx = 3;
		constraints6.gridy = 1;	
		constraints6.gridwidth= 1;
		constraints6.gridheight = 1;
		constraints6.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(vspr2, constraints6);
		if(Version.getRevision().equals("Education")!=true)
			jp.add(vspr2);
		
		
		//
		// プログラム
		programList=new JLabelList();
		programList.setText("Program");
		String[] s3={"工場出荷値","ステビア"};
		programList.setList(s3);

		
		constraints1.anchor=GridBagConstraints.NORTH;
		GridBagConstraints constraints7 = new GridBagConstraints();
		constraints7.gridx = 4;
		constraints7.gridy = 1;	
		constraints7.gridwidth= 1;
		constraints7.gridheight = 1;
		constraints7.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(programList, constraints7);
		if(Version.getRevision().equals("Education")!=true)
			programList.setPreferredSize(new Dimension(180,100));
		else
			programList.setPreferredSize(new Dimension(500,100));
		jp.add(programList);
		
		setParentClass(this);
		
		this.add(jp);
		this.setBackground(new Color(250,251,245));

		//　ファイル分け
		CultivationProgram cp=new CultivationProgram();
		component=cp.getComponent();
		String sb="";
		for(int i=0;i<component.size();i++) sb=sb+component.get(i).getFile()+" ";
		String[] tb=sb.split(" ");
		setProgramList(tb);
		
		// 植物名分け
		plantNameArray=cp.getUniqPlantNameList();
		String vb="";
		
		ArrayList<ArrayList<CultivationProgram.cultivationComponent>> s=cp.getUniqPlantNameList();
		plantNameArray=s;
System.out.println("重複のない植物名分け");
		for(int i=0;i<s.size();i++){
			int j=0;
		//	for(int j=0;j<s.get(i).size();j++){
			if(s.get(i)!=null){
			if(s.get(i).size() > 0 ){
//System.out.println("("+i+","+j+")"+"植物名 "+s.get(i).get(j).getPlantName());
			vb=vb+plantNameArray.get(i).get(j).getPlantName()+" ";
			}
			}
		//	}
		}
//System.out.println("PrgList vb1="+vb);
		//
		
		String[] ub=vb.split(" ");
//		setPlantList(ub);
		plantList.setList(ub);
		plantList.setSelectedIndex(0);
		// カテゴリ分け
		categoryArray=cp.getUniqCategoryList();
		
		
		//
		// default.xmlを探して、読み込んでおくこと。
		
		programList.setSelectedIndex(0);
		
		String[] st=programList.getStringList();
		for(int i=0;i<st.length;i++){
			System.out.println("["+i+"]"+st[i]);
			if(st[i].contains("default")==true){
				programList.setSelectedIndex(i);
				programList.setScrollPosition(programList.getFont().getSize()*i);
			}
		}

		categolyList.setSelectedIndex(0);
		String[] sc=categolyList.getStringList();
		for(int i=0;i<sc.length;i++){
			System.out.println("["+i+"]"+sc[i]);
			if(sc[i].contains("公式")==true){
				categolyList.setSelectedIndex(i);
				categolyList.setScrollPosition(categolyList.getFont().getSize()*i);
			}
		}
		
		plantList.setSelectedIndex(0);
		String[] sp=plantList.getStringList();
		for(int i=0;i<sp.length;i++){
			System.out.println("["+i+"]"+sp[i]);
			if(sp[i].contains("Default")==true){
				plantList.setSelectedIndex(i);
				plantList.setScrollPosition(plantList.getFont().getSize()*i);
			}
		}
		//
		
		//
		// 観察される人を生成
		observableMan = new ObservableMan();

		// 観察者を追加
		observableMan.addObserver((java.util.Observer)new PlanterSetting.ObserverA());
		//observableMan.addObserver((java.util.Observer)new ProgramList.ObserverA());
		//observableMan.addObserver((java.util.Observer)new PrgAbs.ObserverA());
		
		
		
		// 観察者全員に通知
		//Dimension d=programList.getList().getSize();
		if(ITPlanterClass.getState()==false) new ITPlanterClass();

		// 通知
		String str ="Selected default";//+programList.getSelectedItem();
		observableMan.notifyObservers(str);
		
		 
		str ="Program　default";//+plantList.getSelectedItem();
		setItemToList(str);
		observableMan.notifyObservers(str);
		//

		str ="Category　すべて";//+plantList.getSelectedItem();
		setItemToList(str);
		observableMan.notifyObservers(str);
		
		str ="Plants　Default";//+plantList.getSelectedItem();
		setItemToList(str);
		
		//System.out.println("SET "+str);
		//
		observableMan.notifyObservers(str);
		
		
	}
	
	private void setItemToList(String str)
	{
	System.out.println("setItemToList str="+str);
	
	String[] t=str.split(" ");
	if(t.length<2) return;
	
	
	if(t[0].contains("苗")==true || t[0].contains("Plants")==true){
		String u="";
		for(int i=0;i<getParentClass().component.size();i++){
			if(getParentClass().component.get(i).getPlantName().equals(t[1])) u=u+getParentClass().component.get(i).getFile()+" ";
		}
		String[] s3=u.split(" ");
		programList.setList(s3);
		programList.setSelectedIndex(0);
	}

	if(t[0].contains("プログラム")==true || t[0].contains("Program")==true){
		String u="";
		for(int i=0;i<getParentClass().component.size();i++){
			if(getParentClass().component.get(i).getFile().equals(t[1])) u=u+getParentClass().component.get(i).getPlantName()+" ";
		}
		String[] s3=u.split(" ");
		plantList.setList(s3);
		plantList.setSelectedIndex(0);
		//programList.setSelectedIndex(0);
		//categolyList.setSelectedIndex(0);
		//plantList.setSelectedIndex(0);
	}

	if(t[0].contains("カテゴリ")==true||t[0].contains("Category")==true){
		if(t[1].equals("すべて")){
			//　ファイル分け
			String sb="";
			for(int i=0;i<getParentClass().component.size();i++) sb=sb+getParentClass().component.get(i).getFile()+" ";
			String[] tb=sb.split(" ");
			programList.setList(tb);
			
			// 植物名分け;
			String vb="";
			for(int i=0;i<getParentClass().plantNameArray.size();i++){
				if(getParentClass().plantNameArray.get(i).size()!=0)
					vb=vb+getParentClass().plantNameArray.get(i).get(0).getPlantName()+" ";
			}
			System.out.println("vb4="+vb);
			
			String[] ub=vb.split(" ");
			plantList.setList(ub);
//			programList.setSelectedIndex(0);
			} 
		}
	}
	
	 ArrayList<CultivationProgram.cultivationComponent>component=null;
	 ArrayList<ArrayList<CultivationProgram.cultivationComponent>> plantNameArray=null;
	 ArrayList<ArrayList<CultivationProgram.cultivationComponent>> categoryArray=null;
	
	void addObserver(Observer o)
	{
		observableMan.addObserver((java.util.Observer)o);
	}
	
	ObservableMan getObserver()
	{
		return observableMan;
	}
	
	ActionEvent actionevent=null;  //  @jve:decl-index=0:
	
	@Override
	public void actionPerformed(ActionEvent e) {
		actionevent=e;
		JButton btn = (JButton)e.getSource();
		// 観察者全員に通知
		observableMan.notifyObservers();
		if(btn.equals(newPrgButton)){
			System.out.println("新しく栽培プログラムを作る");
			// Do proessing
		}
		
	}

	public static ObservableMan observableMan=null;  //  @jve:decl-index=0:
	/**
	* 観察される人。
	* 
	*/
	class ObservableMan extends Observable {

	// 直前に与えられた引数
	private String previousArg = "";

	/**
	* 観察者に通知します。
	* @param オブジェクト
	*/
	public void notifyObservers() {
	JButton btn = (JButton)actionevent.getSource();
	String str= btn.getName();

	// 直前に与えられた引数と同じならそのままリターン
	if (str==previousArg) {
	//return;
	}

	previousArg = str;

	setChanged();

	// 通知
	super.notifyObservers(str);

	clearChanged();

	}
	
	/**
	* 観察者に通知します。
	* @param オブジェクト
	*/
	public void notifyObservers(String str) {

	// 直前に与えられた引数と同じならそのままリターン
	if (str==previousArg) {
	//return;
	}

	previousArg = str;

	setChanged();

	// 通知
	super.notifyObservers(str);

	clearChanged();

	}
	}
	
	/**
	* 観察者を観察する人A。
	*
	*/
	static class ObserverA implements Observer {
	/* (非 Javadoc)
	* @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	*/
	public void update(Observable o, Object arg) {
	String str = (String) arg;
	System.out.println("私はProgramList classです。観察対象の通知を検知したよ。" + str);
	
	//
	if(getParentClass()==null) return;
	if(getParentClass().component==null) return;
	
	//str="";

	String[] t=str.split(" ");
	if(t.length<2) return;
	if(t[0].contains("苗")==true || t[0].contains("Plants")==true){
		String u="";
		for(int i=0;i<getParentClass().component.size();i++){
			if(getParentClass().component.get(i).getPlantName().equals(t[1])) u=u+getParentClass().component.get(i).getFile()+" ";
		}
		String[] s3=u.split(" ");
		programList.setList(s3);
		programList.setSelectedIndex(0);
	}

	if(t[0].contains("プログラム")==true || t[0].contains("Program")==true){
		String u="";
		for(int i=0;i<getParentClass().component.size();i++){
			if(getParentClass().component.get(i).getFile().equals(t[1])) u=u+getParentClass().component.get(i).getPlantName()+" ";
		}
		String[] s3=u.split(" ");
		plantList.setList(s3);
		plantList.setSelectedIndex(0);
		//programList.setSelectedIndex(0);
		//categolyList.setSelectedIndex(0);
		//plantList.setSelectedIndex(0);
	}

	if(t[0].contains("カテゴリ")==true||t[0].contains("Category")==true){
		if(t[1].equals("すべて")){
			//　ファイル分け
			String sb="";
			for(int i=0;i<getParentClass().component.size();i++) sb=sb+getParentClass().component.get(i).getFile()+" ";
			String[] tb=sb.split(" ");
			programList.setList(tb);
			
			// 植物名分け;
			String vb="";
			for(int i=0;i<getParentClass().plantNameArray.size();i++){
				if(getParentClass().plantNameArray.get(i).size()!=0)
					vb=vb+getParentClass().plantNameArray.get(i).get(0).getPlantName()+" ";
			}
			System.out.println("vb3="+vb);
			
			String[] ub=vb.split(" ");
			plantList.setList(ub);
//			programList.setSelectedIndex(0);
		} 
		// 公式
		
		// 自作
		
		// 投稿

		
	}
	
	// 観察者全員に通知
	//Dimension d=programList.getList().getSize();
	JList jlist=programList.getList();
	int i=jlist.getComponentCount();
	//d.height;
	if(i==1 && programList.getSelectedItem()!=null){
	// 通知
	str ="Selected "+programList.getSelectedItem();
	//System.out.println("SET "+str);
	observableMan.notifyObservers(str);
	}
	

	//
		}
	}

	public void reList() {
		//　ファイル分け
		CultivationProgram cp=new CultivationProgram();
		component=cp.getComponent();
		String sb="";
		for(int i=0;i<component.size();i++) sb=sb+component.get(i).getFile()+" ";
		String[] tb=sb.split(" ");
		setProgramList(tb);
		
		// 植物名分け
		plantNameArray=cp.getUniqPlantNameList();
		String vb="";
		
		ArrayList<ArrayList<CultivationProgram.cultivationComponent>> s=cp.getUniqPlantNameList();
		plantNameArray=s;
		
		System.out.println("重複のない植物名分け");
		
		for(int i=0;i<s.size();i++){
			int j=0;
		//	for(int j=0;j<s.get(i).size();j++){
			if(s.get(i).size()!=0){
			if(s.get(i).get(j)!=null){
			System.out.println("("+i+","+j+")"+"植物名 "+s.get(i).get(j).getPlantName());
			vb=vb+plantNameArray.get(i).get(j).getPlantName()+" ";
			}
			}
		//	}
		}
		//System.out.println("vb2="+vb);
		
		String[] ub=vb.split(" ");
		setPlantList(ub);
		
		// カテゴリ分け
		categoryArray=cp.getUniqCategoryList();
		revalidiate();
		
		String str = programList.getSelectedItem();
		if(str==null) str="default";
		System.out.println("11111"+str);
		
		
		String[] st=programList.getStringList();
		for(int i=0;i<st.length;i++){
			System.out.println("["+i+"]"+st[i]);
			if(st[i].equals(str)==true){
				programList.setSelectedIndex(i);
				programList.setScrollPosition(programList.getFont().getSize()*i);
			}
		}		
		
//categolyList.setSelectedIndex(0);
		str = categolyList.getSelectedItem();
		if(str==null) str="すべて";
		System.out.println("222222"+str);

		
		String[] sc=categolyList.getStringList();
		for(int i=0;i<sc.length;i++){
			System.out.println("["+i+"]"+sc[i]);
			if(sc[i].equals(str)==true){
				categolyList.setSelectedIndex(i);
				categolyList.setScrollPosition(categolyList.getFont().getSize()*i);
				System.out.println("222333"+sc[i]);
			}
		}
		
//		plantList.setSelectedIndex(0);
		
		str = plantList.getSelectedItem();
		if(str==null) str="Default";
		System.out.println("333333"+str);

		
		String[] sp=plantList.getStringList();
		for(int i=0;i<sp.length;i++){
			System.out.println("["+i+"]"+sp[i]);
			if(sp[i].equals(str)==true){
				plantList.setSelectedIndex(i);
				plantList.setScrollPosition(plantList.getFont().getSize()*i);
				System.out.println("333444"+sp[i]);
			}
		}
		
		
	}


	private void revalidiate() {
		// TODO Auto-generated method stub
		
	}


	public String getSelectedPlants() {
		return plantList.getSelectedItem();
	}

	public void setCategory(String str) {
		categolyList.setSelectedItem(str);
	}

	public String getCategory() {
		return categolyList.getSelectedItem();
	}

	public void setProgram(String str) {
		programList.setSelectedItem(str);
	}
	
	public String getProgramName() {
		return programList.getSelectedItem();
	}

	public void setPlantName(String str) {
		plantList.setSelectedItem(str);
	}

	public String getPlantName() {
		return plantList.getSelectedItem();
	}

}
