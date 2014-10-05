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
	    * ログ設定プロパティファイルのファイル内容
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
	    * static initializer によるログ設定の初期化
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
	                    "ログ設定: LogManagerを設定しました。");
	            } catch (IOException e) {
	                logger.warning("ログ設定: LogManager設定の際に"
	                    + "例外が発生しました。:" + e.toString());
	            }
	        } catch (UnsupportedEncodingException e) {
	            logger.severe("ログ設定: UTF-8エンコーディングが"
	                + "サポートされていません。:" + e.toString());
	        } finally {
	            try {
	                if (inStream != null) inStream.close(); 
	            } catch (IOException e) {
	                logger.warning(
	                    "ログ設定: ログ設定プロパティファイルのスト"
	                    + "リームクローズ時に例外が発生しました。:"
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
