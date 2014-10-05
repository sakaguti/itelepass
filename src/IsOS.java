	import java.util.Properties;

	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.WindowConstants;
	
public class IsOS {
		public static String isOS()
		{
			// システムプロパティをすべて取得する。
					Properties properties = System.getProperties();
					String s=(String)properties.get("os.name");
			return s;
		}
		
		/**
		 * @param args
		 */
		public static void main(String[] args) {
			JFrame frame = new JFrame();
			
			JLabel gjp=new JLabel("OS is "+isOS());
			
			frame.getContentPane().add(gjp);
			
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
			
			System.out.println(IsMacorWin.isMacOrWin());

		}
		
	
}
