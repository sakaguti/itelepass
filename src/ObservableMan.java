import java.util.Observable;
import java.util.Observer;

public class ObservableMan extends Observable {
	private ObservableMan observableMan=null;

	public ObservableMan getObservableMan() {
		return observableMan;
	}

	public void setObservableMan(ObservableMan observableMan) {
		this.observableMan = observableMan;
	}

	// �ώ@�҂ɓ`���郁�b�Z�[�W
	private String str="";
	
	// �ώ@�҂ɓ`���郁�b�Z�[�W��ݒ肷��
	public void setMessage(String s){
		str=s;
	}
	
	// ���O�ɗ^����ꂽ���b�Z�[�W
	private String previousArg = "";

	/**
	* �ώ@�҂ɒʒm���܂��B
	* @param �I�u�W�F�N�g
	*/
	@Override
	public void notifyObservers() {
		
		// ���O�ɗ^����ꂽ���b�Z�[�W�Ɠ����Ȃ炻�̂܂܃��^�[��
		if (str==previousArg) {
		//return;
		}
		
		previousArg = str;
		
		setChanged();
		
		// �ʒm
		super.notifyObservers(str);
		
		clearChanged();
	
	}

	/**
	* �ώ@�҂��ώ@����lA�B
	*
	*/
	public class ObserverA implements Observer {
		String name="";
		public void setName(String s){
			name=s;
		}
		
		// override ���ĕύX���郁�\�b�h
		@Override
		public void update(Observable o, Object arg) {
			
			String str = (String) arg;
			System.out.println("����"+name+"�ł��B�ώ@�Ώۂ̒ʒm�����m������B" + str);
			
			if(str.contains("�K�p����")){
				System.out.println("�K�p���܂�");
			} else 
				if(str.contains("�͔|�v���O�����̏ڍׂ��J��")){
					System.out.println("�K�p�͔|�v���O�����̏ڍׂ��J��");
				} else 
					if(str.contains("�V�����͔|�v���O���������")){
						System.out.println("�V�����͔|�v���O���������");
					}
			
				}// update
		
			}
}
