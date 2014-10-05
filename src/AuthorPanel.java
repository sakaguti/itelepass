import info.bix.tokai.bixpp.binding.BIXpp;
import info.bix.tokai.bixpp.io.BIXppIO;
import info.bix.tokai.bixpp.xml.XMLException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AuthorPanel extends JPanel implements DocumentListener, MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		AuthorPanel gjp=new AuthorPanel("current.xml");
		
		gjp.setEditable(true);
		
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * This is the default constructor
	 */
	public AuthorPanel(String s) {
		super();
		if(ITPlanterClass.getState()==false) new ITPlanterClass();
		ITPlanterClass.getCurrentPlanterClass().getInformation().setBIXppFileName(s);// set
		filename=s;
		initialize();
	}
	
	public void setAutorInfo(String s)
	{
		ITPlanterClass.getCurrentPlanterClass().getInformation().setBIXppFileName(s);// set
		filename=ITPlanterClass.getCurrentPlanterClass().getInformation().getBIXppFileName();
		//
		String s2 = Path.getPath()+"DB/"+filename;// << 
		if(IsMacorWin.isMacOrWin()==false) s2 = Path.getPath()+"DB\\"+filename; // <<
		//
		getBIX(s2);
	}

	public void saveAutorInfo()
	{
/*
		filename=ITPlanterClass.getCurrentPlanterClass().getInformation().getBIXppFileName();
		//
		String sauthor=author.getText();
		ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram().setAuthor(sauthor);
		String sdate=date.getText();
		ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram().setDate(sdate);
		String supdate=update.getText();
		ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram().setUpdate(supdate);
		
		String s2 = Path.getPath()+"DB/"+filename;// << 
		if(IsMacorWin.isMacOrWin()==false) s2 = Path.getPath()+"DB\\"+filename; // <<
		//
		setBIX(s2);
*/
	}
	
	public String getFileName()
	{
		return filename;
	}
	// 編集不可
	public void setEditable(boolean b)
	{
		author.setEditable(b);
		date.setEditable(b);
		update.setEditable(b);
	}
	
	// 文字がグレイになる
	@Override
	public void setEnabled(boolean b)
	{
		author.setEnabled(b);
		date.setEnabled(b);
		update.setEnabled(b);
	}
	
	private JLabel authorTitle=null;
	private JLabel dateTitle=null;
	private JLabel updateTitle=null;
	private JTextField author=null;
	private JTextField date=null;
	private JTextField update=null;
	private BIXpp bixpp=null;
	private static String filename="";
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		GridBagLayout gridbag = new GridBagLayout();
		JPanel jp = new JPanel(gridbag);
		//
		authorTitle=new JLabel("製作者");
		authorTitle.setForeground(Color.green);
		author=new JTextField("sakaguti3");
		//author.setLineWrap(true);// 折り返し
		author.setName("author");
		author.setPreferredSize(new Dimension(180,20));
		author.addMouseListener(this);
		author.getDocument().addDocumentListener(this);
		author.addKeyListener(this);
		
		dateTitle=new JLabel("制作日");
		dateTitle.setForeground(Color.green);
		date=new JTextField("2012年３月31日");
		//date.setLineWrap(true);// 折り返し
		date.setName("date");
		date.setPreferredSize(new Dimension(180,20));
		date.addMouseListener(this);
		date.addKeyListener(this);
		date.getDocument().addDocumentListener(this);
		
		updateTitle=new JLabel("更新日");		
		updateTitle.setForeground(Color.green);
		update=new JTextField("2012年4月41日");
		//update.setLineWrap(true);// 折り返し
		update.setPreferredSize(new Dimension(180,20));
		update.setName("update");
		update.addMouseListener(this);
		update.addKeyListener(this);

		update.getDocument().addDocumentListener(this);
		
		//
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 0;
		jp.add(authorTitle, gridBagConstraints1);
		
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 1;
		jp.add(author, gridBagConstraints2);
		
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.gridy = 2;
		jp.add(dateTitle, gridBagConstraints3);
		
		GridBagConstraints gridBagConstraints4= new GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.gridy = 3;
		jp.add(date, gridBagConstraints4);
		
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.gridx = 0;
		gridBagConstraints5.gridy = 4;
		jp.add(updateTitle, gridBagConstraints5);
		
		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.gridx = 0;
		gridBagConstraints6.gridy = 5;
		jp.add(update, gridBagConstraints6);
		
		jp.setBackground(new Color(250,251,245));
		
		this.add(jp);

		this.setBackground(new Color(250,251,245));
		
		// BIXpp
		if(ITPlanterClass.getState()==true)
			filename=ITPlanterClass.getCurrentPlanterClass().getInformation().getBIXppFileName();

		String s2 = Path.getPath()+"DB/"+filename;// << 
		if(IsMacorWin.isMacOrWin()==false) s2 = Path.getPath()+"DB\\"+filename; // <<

		getBIX(s2);
	}
	
	public void getBIX(String s)
	{
        try {
        	//
            File file=new File(s);
            if(file.exists()==false) return;
            bixpp = BIXppIO.read(new File(s));
            //
            // Author
            ///sakaguti
            setAuthor(bixpp.getProducer().getName());
            //setAuthor(s);
            // Date 
            setDate(String.valueOf(bixpp.getStart()).replace("JST ", ""));
            // UpDate
            setUpDate(String.valueOf(bixpp.getUpdate()).replace("JST ",""));
            //         
        } catch (XMLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void setBIX(String s)
	{
System.out.println("setBIX");
        try {
            bixpp = BIXppIO.read(new File(s));
            
            // 書き換えられていたら、ファイルに保存する
            if( author.getText().equals(bixpp.getProducer())==false ){
            // Author
            if(author.getText().equals("")==false){
System.out.println("author.getText()1 "+author.getText());
            bixpp.getProducer().setName(author.getText());
System.out.println("author.getText()2 "+ bixpp.getProducer().getName());
            }
            }
            
            if( date.getText().equals(bixpp.getStart())==false ){
            // Date
            bixpp.setStart(new Date());
            }
            
            if( update.getText().equals(bixpp.getUpdate())==false ){
            // UpDate
            bixpp.setUpdate(new Date());
            }
                     
        } catch (XMLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
			BIXppIO.write(bixpp, new File(s));
System.out.println("BIXppIO.write");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void setAuthor(String s)
	{
		author.setText(s);
	}
	
	public void setDate(String s)
	{
		date.setText(s);
	}
	public void setUpDate(String s)
	{
		update.setText(s);
	}
	
	public void setAuthorTitle(String s)
	{
		authorTitle.setText(s);
	}
	
	public void setDateTitle(String s)
	{
		dateTitle.setText(s);
	}
	
	public void setUpDateTitle(String s)
	{
		updateTitle.setText(s);
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		System.out.println("changedUpdate");
		// なんだか良く分からない。Macでは検出されない。
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
//		System.out.println("insertUpdate");
		// 編集された。
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		//System.out.println("removeUpdate");

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		//System.out.println("mouseClicked");
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		//System.out.println("mouseEntered");
		// 編集しようとしている。
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// 入力内容を確定するかどうかダイアログをだす。
		/*
		System.out.println("mouseExited");
		// ここで入力を確定する。
			saveInfo();
		*/
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		//System.out.println("mousePressed");
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		//System.out.println("mouseReleased");
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.print(e.getKeyCode());
		if(e.getKeyCode()==10){
			// return then save
			// ここで入力を確定する。
			saveInfo();
		}
		
	}

	public void saveInfo()
	{
		// 編集された。
		String s = Path.getPath()+"DB/"+filename;// << 
		if(IsMacorWin.isMacOrWin()==false) s = Path.getPath()+"DB\\"+filename;// <<
		System.out.println("save BIXpp file "+s);
		setBIX(s);// BIXppを書き出す
	}
	@Override
	public void keyTyped(KeyEvent e) {

		
	}
}
