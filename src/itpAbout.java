import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

class itpAbout extends JFrame{
private static final long serialVersionUID = 2L;

private static String msg="";

private static String iconName;
public static void main(String args[]){
	itpAbout frame = new itpAbout("About");
	frame.setResizable(false);
    frame.setVisible(true);
    frame.setResizable(false);

}


	public itpAbout(String string) {
		msg=string;
	    setTitle(msg);
	    setBounds(10, 10, 300, 400);
	    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	    JPanel p = new JPanel();

		// 表示する画像ファイル名を引数にしてインスタンスを生成する
		iconName = Path.getPath()+"images/splash.png";
		if(IsMacorWin.isMacOrWin()==false) iconName = Path.getPath()+"images\\splash.png";

		GPanel gjp=null;
		if(Version.getFreeOrPaid().contains("Free")==false){
		gjp=new GPanel(iconName,"Version "+String.valueOf(Version.getMejar())+"."+String.valueOf(Version.getMiner())+" build"+String.valueOf(Version.getUpdate()));
		} else {
		gjp=new GPanel(iconName,"Version "+String.valueOf(Version.getMejar())+"."+String.valueOf(Version.getMiner())+" build"+String.valueOf(Version.getUpdate())+" "+Version.getFreeOrPaid());	
		}
		this.add(gjp);
	}



}
