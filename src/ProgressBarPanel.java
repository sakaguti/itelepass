

import javax.swing.JPanel;
import javax.swing.ProgressMonitor;

public class ProgressBarPanel extends Thread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ProgressBarPanel pbp=new ProgressBarPanel();
		pbp.start();

	}

	/**
	 * This is the default constructor
	 */
	public  static ProgressMonitor monitor=null; 
	private static JPanel jp=new JPanel();
	public ProgressBarPanel() {

		monitor = new ProgressMonitor(getJp(), "お待ちください", "アイティプランターを初期化中", 0, 100);
		getJp().setVisible(true);
	}


	   @Override
	public void run() {
		   
		   int max=100;
		   int min=0;
		   monitor.setMillisToPopup(0);
		   monitor.setMillisToDecideToPopup(10);
		   monitor.setMaximum(max);
		   monitor.setMinimum(min);
		for(int i=max;i>=min;i--){
			monitor.setProgress(i);
			monitor.setNote("初期化中 "+i);
			//
			//TimerSetRoutine
			//
			try{
				Thread.sleep(100); //3000ミリ秒Sleepする
				}catch(InterruptedException e){}
		}
		monitor.close();
	}


	public JPanel getJp() {
		return jp;
	}


	public static void setJp(JPanel jp) {
		ProgressBarPanel.jp = jp;
	}

}
