import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class itp_Logger {
	   /**
	    * ���O�ݒ�v���p�e�B�t�@�C���̃t�@�C�����e
	    */
	    protected static final String LOGGING_PROPERTIES_DATA
	        = "handlers=java.util.logging.ConsoleHandler\n"
	        + ".level=INFO\n"
	        + "java.util.logging.ConsoleHandler.level=INFO\n"
	        + "java.util.logging.ConsoleHandler.formatter"
	        + "=java.util.logging.SimpleFormatter";
	    
	    // Constructor
		public  itp_Logger()
		{
			itp_LoggerSetup();
		}
		
	    public static void itp_LoggerSetup()
	    {
	    	String fileName = "itp_Logger.properties";
	    	InputStream in = null;
			try {
				in = new FileInputStream(fileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	    	try {
				LogManager.getLogManager().readConfiguration(in);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	    }
	    /**
	    * static initializer �ɂ�郍�O�ݒ�̏�����
	    */
	    static {
	        final Logger logger = Logger.getLogger("SampleLogging");
	        InputStream inStream = null;
	        try {
	            inStream = new ByteArrayInputStream(
	                LOGGING_PROPERTIES_DATA.getBytes("UTF-8"));
	            try {
	                LogManager.getLogManager().readConfiguration(inStream);
	                logger.config(
	                    "���O�ݒ�: LogManager��ݒ肵�܂����B");
	            } catch (IOException e) {
	                logger.warning("���O�ݒ�: LogManager�ݒ�̍ۂ�"
	                    + "��O���������܂����B:" + e.toString());
	            }
	        } catch (UnsupportedEncodingException e) {
	            logger.severe("���O�ݒ�: UTF-8�G���R�[�f�B���O��"
	                + "�T�|�[�g����Ă��܂���B:" + e.toString());
	        } finally {
	            try {
	                if (inStream != null) inStream.close(); 
	            } catch (IOException e) {
	                logger.warning(
	                    "���O�ݒ�: ���O�ݒ�v���p�e�B�t�@�C���̃X�g"
	                    + "���[���N���[�Y���ɗ�O���������܂����B:"
	                    + e.toString());
	            }
	        }
	    }
	    
        final static Logger logger = Logger.getLogger("itp_Logger");
	    
	    public static void main(final String[] args) {
	        itp_LoggerSetup();
	        logger.finest("finest");
	        logger.finer("finer");
	        logger.fine("fine");
	        logger.config("config");
	        logger.info("info");
	        logger.warning("warning");
	        logger.severe("severe");
	        
	        logger.info("test");
	    }

}
