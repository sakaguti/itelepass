public class Version {
	private static  int mejar=2;
	private static  int miner=1;

	private static  int update=140821;// version 
	
	static public void setMejar(int mejar) {
		Version.mejar = mejar;
	}

	static public int getMejar() {
		return mejar;
	}
	static public void setMiner(int miner) {
		Version.miner = miner;
	}
	static public int getMiner() {
		return miner;
	}
	static public int getUpdate() {
		return update;
	}
	static public String getRevision(){
//		return "Pro";
		return "Standard";
//		return "Education";
	}
	
	static public String getFreeOrPaid(){
// CloudGarden.jar‚ğ¶¬‚·‚éê‡‚É‚ÍAPaid‚ğ‘I‘ğ‚·‚é‚±‚ÆB
		return "Paid";
//		return "Free";
	}
	
	static public String getFolderName(){
		if(getFreeOrPaid().equals("Free"))	return "Free"; else return "";
	}
}
