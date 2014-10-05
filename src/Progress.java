
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


import javax.imageio.ImageIO;

import javax.swing.ProgressMonitor;

public class Progress extends Thread {

	/**
	 * @param args
	 */
	private static ProgressMonitor pm = null;
	
	public Progress()
	{
		pm=new ProgressMonitor(null, "設定中", "ノート", 0, 10);
	}
	
	public static void main(String[] args) {


//		String s = Path.getPath()+"/images/USBcam.jpg";
//		if(IsMacorWin.isMacOrWin()==false) s = Path.getPath()+"images\\USBcam.jpg";
		Progress p=new Progress();
		//pm=new ProgressMonitor(null, "起動中", "ノート", 0, 50);
		//Thread t=new Thread(p);
		p.start();
		
	}
	
	void progress_run()
	{
	int min = 0;
	int max = 10;
	pm.setMillisToDecideToPopup(0);
	pm.setMillisToPopup(0);
	for (int i = min; i < max; i++) {
		// 終了判定
		if (pm.isCanceled()) {
			pm.close();
			break;
		}

		pm.setNote("現在：" + i);

		//何かの処理
		try {
			sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pm.setProgress(i + 1); //プログレスバーに現在値をセット
		
		}
	}
	
	public static Image loadImage(String fileName) {
		InputStream is = null;
		try {
			is = new FileInputStream(fileName);
			BufferedImage img = ImageIO.read(is);
			return img;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null) try { is.close(); } catch (IOException e) {}
		}
	}

	@Override
	public void run() {
		progress_run();
	}
}
