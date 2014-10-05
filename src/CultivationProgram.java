import info.bix.tokai.bixpp.binding.BIXpp;
import info.bix.tokai.bixpp.binding.DefinitionItem;
import info.bix.tokai.bixpp.binding.RecordingDefinition;
import info.bix.tokai.bixpp.io.BIXppIO;
import info.bix.tokai.bixpp.xml.ValidationException;
import info.bix.tokai.bixpp.xml.XMLException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class CultivationProgram {

	private ArrayList<cultivationComponent> component=new ArrayList<cultivationComponent>();
	private ArrayList<String> fileName=new ArrayList<String>();
	private ArrayList<ArrayList<cultivationComponent>> components=new ArrayList<ArrayList<cultivationComponent>>();
	
	private BIXpp bixpp;
	
	public class cultivationComponent{
		private String plantName="";// 植物名
		private String category=""; // カテゴリ
		private String file="";     // BIXppファイル [.xml]
		private boolean flag=false;
		
		public  cultivationComponent(String s1, String s2, String s3)
		{
			setPlantName(s1);
			setCategory(s2);
			setFile(s3);
		}

		public void setPlantName(String plantName) {
			this.plantName = plantName;
		}

		public String getPlantName() {
			return this.plantName;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getCategory() {
			return category;
		}

		public void setFile(String file) {
			this.file = file;
		}

		public String getFile() {
			return file;
		}

	}
	
	public static void main(String[] args) 
	{
		CultivationProgram cp=new CultivationProgram();
		
		System.out.println("植物名分け");

		ArrayList<cultivationComponent> c=cp.getPlantName("ステビア");
		for(int i=0;i<c.size();i++){
			System.out.println("("+i+")"+"植物名 "+c.get(i).getPlantName());
			System.out.println("("+i+")"+"カテゴリ "+c.get(i).getCategory());
			System.out.println("("+i+")"+"ファイル "+c.get(i).getFile());
			cp.components.add(c);
		}
		
		System.out.println("カテゴリ分け");
		ArrayList<cultivationComponent> c2=cp.getCategory("自作");
		for(int i=0;i<c2.size();i++){
			System.out.println("("+i+")"+"植物名 "+c2.get(i).getPlantName());
			System.out.println("("+i+")"+"カテゴリ "+c2.get(i).getCategory());
			System.out.println("("+i+")"+"ファイル "+c2.get(i).getFile());
		}

		System.out.println("ファイル名分け");
		ArrayList<cultivationComponent> c3=cp.getFile("stevia");
		for(int i=0;i<c3.size();i++){
			System.out.println("("+i+")"+"植物名 "+c3.get(i).getPlantName());
			System.out.println("("+i+")"+"カテゴリ "+c3.get(i).getCategory());
			System.out.println("("+i+")"+"ファイル "+c3.get(i).getFile());
		}

		
		ArrayList<ArrayList<cultivationComponent>> s=cp.getUniqPlantNameList();
		
		System.out.println("重複のない植物名分け");
		for(int i=0;i<s.size();i++){
			for(int j=0;j<s.get(i).size();j++){
//			for(int j=0;j<=0;j++){
			System.out.println("("+i+","+j+")"+"植物名 "+s.get(i).get(j).getPlantName());
			}
		}
/*
		System.out.println("重複のないカテゴリ分け");
		ArrayList<ArrayList<cultivationComponent>> t=cp.getUniqCategoryList();

		for(int i=0;i<t.size();i++){
		for(int j=0;j<s.get(i).size();j++){
//			for(int j=0;j<=0;j++){
			System.out.println("("+i+","+j+")"+"カテゴリ "+t.get(i).get(j).getCategory());
			}
		}
*/
	}
	
	public ArrayList<cultivationComponent> getComponent()
	{
		return component;
	}
	//
	// 重複していない植物名リストを返す
	public ArrayList<ArrayList<cultivationComponent>> getUniqPlantNameList()
	{
		for(int i=0;i<component.size();i++) component.get(i).flag=false;// reset flag
		ArrayList<ArrayList<cultivationComponent>> cs=new ArrayList<ArrayList<cultivationComponent>>();
		
		for(int i=0;i<component.size();i++){
			if(component.get(i).flag==false){
			ArrayList<cultivationComponent> c=getPlantName( component.get(i).getPlantName() );
			for(int j=0;j<c.size();j++) c.get(j).flag=true;
			cs.add(c);
			//i=0;// 最初からやりなおし
			}
		}
		return cs;
	}

	//
	// 重複していないカテゴリリストを返す
		public ArrayList<ArrayList<cultivationComponent>> getUniqCategoryList()
		{
			for(int i=0;i<component.size();i++) component.get(i).flag=false;// reset flag
			ArrayList<ArrayList<cultivationComponent>> cs=new ArrayList<ArrayList<cultivationComponent>>();
			
			for(int i=0;i<component.size();i++){
				if(component.get(i).flag==false){
				ArrayList<cultivationComponent> c=getCategory(component.get(i).getCategory());
				for(int j=0;j<c.size();j++) c.get(j).flag=true;
				cs.add(c);
				i=0;// 最初からやりなおし
				}
			}
			return cs;
		}



	public ArrayList<cultivationComponent> getCategory(String s)
	{
		ArrayList<cultivationComponent> cc=new ArrayList<cultivationComponent>();
		for(int i=0;i<component.size();i++){
			if(component.get(i).getCategory().contains(s)==true){
				//　カテゴリが一致した
				cc.add(component.get(i));
			}
		}
		return cc;
	}
	
	public ArrayList<cultivationComponent> getFile(String s)
	{
		ArrayList<cultivationComponent> cc=new ArrayList<cultivationComponent>();
		for(int i=0;i<component.size();i++){
			if(component.get(i).getFile().contains(s)==true){
				//　ファイル名が一致した
				cc.add(component.get(i));
			}
		}
		return cc;
	}
	
	public ArrayList<cultivationComponent> getPlantName(String s)
	{
		ArrayList<cultivationComponent> cc=new ArrayList<cultivationComponent>();
		for(int i=0;i<component.size();i++){
			String pn=component.get(i).getPlantName();
			if(pn.contains(s)==true && s.length()!=0){
				//　植物名が一致した
				cc.add(component.get(i));
			}
		}
		return cc;
	}
	
	public CultivationProgram()
	{
		// initialize
		makeList();
		for(int i=0;i<fileName.size();i++){
			addComponent(fileName.get(i));
		}
	}
	
	public void makeList()
	{
		//
		// DB内のファイルをリストアップする
		System.out.println(Files.getDBPath());
		File[] files=Files.ls(Files.getDBPath());
		String s="";
		for(int i=0;i<files.length;i++){
		File file=files[i];
		if(file.getName().contains(".xml")== true){
			String filename=file.getName().replace(".xml", "");
			s=s+filename+" ";
		}
		}
		String[] t=s.split(" ");
		for(int i=0;i<t.length;i++){
			fileName.add(t[i]);		
		}
	}

	public void addComponent(String f)
	{
		try {
			String name=Files.getDBPath()+f+".xml";
//			System.out.println(name);
			bixpp = BIXppIO.read(new File(name));
		} catch (ValidationException e) {
			System.out.println("error "+f);
			e.printStackTrace();
		} catch (XMLException e) {
			System.out.println("error "+f);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("error "+f);
			e.printStackTrace();
		}
		String p=bixpp.getCultivation(0).getPlant().getName();// 植物の名前
		
		String c="";
		 RecordingDefinition rd=bixpp.getRecordingDefinition();
		 for(int i=0;i<rd.getDescriptionItemCount();i++){
		 DefinitionItem di=rd.getDefinitionItemByIndex(i);
		 if(di!=null){
		 if(di.getName().contains("Category")){
			 c=di.getComment();//カテゴリの名称
			 break;
		 	}
		 }
		 }
		component.add(new cultivationComponent(p,c,f));
	}
}


