import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class CardDisplay extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		CardDisplay gjp=new CardDisplay();
		frame.getContentPane().add(gjp);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);

	}

	/**
	 * This is the default constructor
	 */
	public CardDisplay() {
		super();
		initialize();
	}

	private JLabel jl1=null;
	private JPanel jp=null;
	private 		int n=3;
	
	public void  addCard(int hour, int min)
	{
		DisplayComb jp2=null;
		JPanel jpPad=null;
		jpPad=new JPanel();

		jpPad.setBackground(new Color(200,251,245));
		//
		jpPad.setPreferredSize(new Dimension(160,44));
		jp2=new DisplayComb();
		jp2.setPreferredSize(new Dimension(140,30));
		jp2.setBackground(new Color(100,251,245));
		// 表示時刻を設定する
		jp2.setHour(hour);
		jp2.setMin(min);

	    //JPanelの配置
//		jpPad.setLayout(new BorderLayout());
//		jpPad.add(jp2,BorderLayout.CENTER);
		jpPad.add(jp2);
		//
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 1+n;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);

		gridbag.setConstraints(jpPad, constraints1);
		jp.add(jpPad);
		n++;
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		Container c = this;

		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);
		jp=new JPanel();
		jp.setBackground(new Color(250,251,245));
		// panel
		JPanel jp1=new JPanel();
		jp1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		jp1.setLayout(gridLayout);
//		jp1.setPreferredSize(new Dimension(gJPanel.getWidth(),gJPanel.getHeight()));
		jp1.setPreferredSize(new Dimension(160,50));
		jp1.setBackground(Color.gray);
		
		jl1=new JLabel("ポンプ動作時刻");
		jl1.setHorizontalTextPosition(SwingConstants.CENTER);
		jl1.setVerticalTextPosition(SwingConstants.CENTER);
		jl1.setHorizontalAlignment(SwingConstants.CENTER);
		jl1.setForeground(null);
		jp1.add(jl1, null);
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.gridx = 0;
		constraints1.gridy = 0;	
		constraints1.gridwidth= 1;
		constraints1.gridheight = 1;
		constraints1.insets = new Insets(0, 0, 0, 0);
		gridbag.setConstraints(jp1, constraints1);
		jp.add(jp1);
		
		//
		// ここは修正が必要
		//
		int n=3;
		for(int i=0;i<n;i++){
			// 時刻を表示したカードを追加する
			addCard(i+10,i+12);
		}

		//
		this.add(jp);
		this.setSize(200, 200);
		this.setPreferredSize(new Dimension(200,200));
		this.setLayout(new BorderLayout());
		c.add( jp, BorderLayout.CENTER );
	}

}
