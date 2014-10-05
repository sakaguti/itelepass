
import java.io.File;

import javax.swing.*;
public class FileChooser {


	
		public static void main(String[] args) {
			
			//FileChooser 
			new FileChooser(new JFrame());
//			System.out.println(fc.getSelectedFile());

		}
	 
		public FileChooser(JFrame f)
		{
			super();
			initialize(f);
		}
		
		private JFileChooser file =null;
		
		public File getSelectedFile()
		{
			return file.getSelectedFile();
		}

		/*
		JFarme frame=new JFarme();
		file = new JFileChooser(Files.getDBPath());
		file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			file.showOpenDialog(file);		
			if (file.showOpenDialog(frame) == JFileChooser.CANCEL_OPTION){
				return;
		}
		*/
		
		private void initialize(JFrame frame)
		{
			file = new JFileChooser(Files.getDBPath());
			file.addChoosableFileFilter(new XMLFilter());
			
			int selected = file.showSaveDialog(file);		
			if (selected == JFileChooser.CANCEL_OPTION){
				return;
			}
//			System.out.println(file.getSelectedFile());
			Icon icon = new ImageIcon(file.getSelectedFile().getPath());
			frame.getContentPane().add(new JScrollPane(new JLabel(icon)));
		}
		
}
