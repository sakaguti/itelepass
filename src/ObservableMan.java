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

	// 観察者に伝えるメッセージ
	private String str="";
	
	// 観察者に伝えるメッセージを設定する
	public void setMessage(String s){
		str=s;
	}
	
	// 直前に与えられたメッセージ
	private String previousArg = "";

	/**
	* 観察者に通知します。
	* @param オブジェクト
	*/
	@Override
	public void notifyObservers() {
		
		// 直前に与えられたメッセージと同じならそのままリターン
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
	* 観察者を観察する人A。
	*
	*/
	public class ObserverA implements Observer {
		String name="";
		public void setName(String s){
			name=s;
		}
		
		// override して変更するメソッド
		@Override
		public void update(Observable o, Object arg) {
			
			String str = (String) arg;
			System.out.println("私は"+name+"です。観察対象の通知を検知したよ。" + str);
			
			if(str.contains("適用する")){
				System.out.println("適用します");
			} else 
				if(str.contains("栽培プログラムの詳細を開く")){
					System.out.println("適用栽培プログラムの詳細を開く");
				} else 
					if(str.contains("新しく栽培プログラムを作る")){
						System.out.println("新しく栽培プログラムを作る");
					}
			
				}// update
		
			}
}
