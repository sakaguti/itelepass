
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
		pm=new ProgressMonitor(null, "�ݒ蒆", "�m�[�g", 0, 10);
	}
	
	public static void main(String[] args) {


//		String s = Path.getPath()+"/images/USBcam.jpg";
//		if(IsMacorWin.isMacOrWin()==false) s = Path.getPath()+"images\\USBcam.jpg";
		Progress p=new Progress();
		//pm=new ProgressMonitor(null, "�N����", "�m�[�g", 0, 50);
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
		// �I������
		if (pm.isCanceled()) {
			pm.close();
			break;
		}

		pm.setNote("���݁F" + i);

		//�����̏���
		try {
			sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pm.setProgress(i + 1); //�v���O���X�o�[�Ɍ��ݒl���Z�b�g
		
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
