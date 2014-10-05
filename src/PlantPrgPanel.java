
import info.bix.tokai.bixpp.binding.BIXpp;
import info.bix.tokai.bixpp.binding.Cultivation;
import info.bix.tokai.bixpp.binding.Plant;

import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class PlantPrgPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new PlantPrgPanel());
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * This is the default constructor
	 */
	public PlantPrgPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public  PlantPrgEdit plantPrgEdit=null;
	private void initialize() {

		
		plantPrgEdit=new PlantPrgEdit();
		plantPrgEdit.setEnabled(true);
		this.add(plantPrgEdit);
		//
		//
		// �ώ@�����l�𐶐�
		observableMan = new ObservableMan();

		// �ώ@�҂�ǉ�
//		observableMan.addObserver((java.util.Observer)new AutoPicture.ObserverA());
	}

	void addObserver(Observer o)
	{
		if(observableMan==null) return;
			observableMan.addObserver(o);
	}
	
	private ObservableMan observableMan=null;  //  @jve:decl-index=0:

	public  void setInformation(String s) {
		plantPrgEdit.setInformation(s);
	}

	public  void setProgramTitle()
	{
		PlantProgram plantProgram=ITPlanterClass.getCurrentPlanterClass().getInformation().getPlantProgram();
		BIXpp bixpp=plantProgram.getBIXpp();
		
		// TODO ��������
		//if(plantPrgEdit.getStatus()==true)
			plantPrgEdit.setPlantProgramDataToPanel(plantPrgEdit);// �p�l���̐ݒ�l��ǂݍ���

		
		if(bixpp!=null){
		if(bixpp.getCultivationCount()>0){
		Cultivation c=bixpp.getCultivation(0);
		if(c!=null){
		Plant p=c.getPlant();
		String t=p.getName();//
		plantProgram.getBIXppFileName();

		plantPrgEdit.setProgramTitles(t);
		// TODO �Ȃ񂩕ρH�@�@static�̕��Q
		plantPrgEdit.prgInfoEdit.setText(c.getComment());
		
		}
		}
		}
	}

}
