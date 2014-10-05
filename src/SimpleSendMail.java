import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * シンプルなメール送信サンプル。
 */
public class SimpleSendMail implements Runnable {
    // 日本語メールの場合には ISO-2022-JPがオススメ。
    // UTF-8だと受信時に文字化けしてしまうメーラが世の中には依然として存在しています。
    private static final String ENCODE = "ISO-2022-JP";
	private static String toString="sakaguti3@me.com";		// sakaguti@me.com
	private static String fromString="sakaguti3@gmail.com";	// sakaguti@itplants.com
	private static String serverString="smtp.gmail.com"; //	smtp.me.com
	private static String passString="|rvl|xnl6";
	private static String titleString="アイティプランターレポート";	// アイティプランターレポート
	private static String messageString="水が不足してきました。給水してください。";
	
    public SimpleSendMail(String to, String from,
			String server, String pass, String title,
			String msg) {
	    	to1 = to;
			from1 = from;
			server1 = server;
			pass1=pass;
			title1 = title;
			msg1 = msg;
			run();
	}

	public static void main(final String[] args) {
        System.out.println("メール送信開始"+Settings.Fukugouka(passString));

        SimpleSendMail ssm= new SimpleSendMail(toString,fromString,serverString,Settings.Fukugouka(passString),titleString,messageString);
        //ssm.setting();
        //ssm.process();
        //ssm.start();

        System.out.println("メール送信終了");
    }

    public void process() {

        final Properties props = new Properties();

        // 基本情報。ここでは gmailへの接続例を示します。
        //props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.host", server1);
        
//        props.setProperty("mail.smtp.host", "smtp.mail.me.com");
        // SSL用にポート番号を変更。
        props.setProperty("mail.smtp.port", "465");
//        props.setProperty("mail.smtp.port", "587");

        // タイムアウト設定
        props.setProperty("mail.smtp.connectiontimeout", "10000");
        props.setProperty("mail.smtp.timeout", "10000");

        // 認証
        props.setProperty("mail.smtp.auth", "true");

        // SSL関連設定
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        final Session session = Session.getInstance(props, new Authenticator() {
            @Override
			protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication(from1, pass1);
//                return new PasswordAuthentication("sakaguti3@me.com", "Yosiyuki3");
            }
        });

        // デバッグを行います。標準出力にトレースが出ます。
        session.setDebug(true);

        // メッセージ内容の設定。
        final MimeMessage message = new MimeMessage(session);
        
        try {
            final Address addrFrom = new InternetAddress(
                    from1, from1, ENCODE);
            message.setFrom(addrFrom);

            final Address addrTo = new InternetAddress(to1,
            		to1, ENCODE);
            message.addRecipient(Message.RecipientType.TO, addrTo);

            // メールのSubject
            message.setSubject(title1, ENCODE);

            // メール本文。setTextを用いると 自動的に[text/plain]となる。
            message.setText(msg1, ENCODE);

            // 仮対策: 開始
            // setTextを呼び出した後に、ヘッダーを 7bitへと上書きします。
            // これは、一部のケータイメールが quoted-printable を処理できないことへの対策となります。
            message.setHeader("Content-Transfer-Encoding", "7bit");
            // 仮対策: 終了

            // その他の付加情報。
            message.addHeader("X-Mailer", "blancoMail 0.1");
            message.setSentDate(new Date());
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        System.out.println("message ="+message.getMessageNumber());
        if(message==null) return;
        if(message.getMessageNumber()==0) return;
        
        // メール送信。
        try {
            Transport.send(message);
        } catch (AuthenticationFailedException e) {
            // 認証失敗は ここに入ります。
            System.out.println("指定のユーザ名・パスワードでの認証に失敗しました。"
                + e.toString());
        } catch (MessagingException e) {
            // smtpサーバへの接続失敗は ここに入ります。
            System.out.println("指定のsmtpサーバへの接続に失敗しました。" + e.toString());
            e.printStackTrace();
        }
    }

	public String to1 = "";
	public String from1 = "";
	public String server1 = "";
	public String title1 = "";
	public String msg1 = "";
	public String pass1 = "";
	
	public void setting(String to, String from, String server, String pass,
			String title, String msg) {
		to1 = to;
		from1 = from;
		server1 = server;
		pass1=pass;
		title1 = title;
		msg1 = msg;
		
	}

	@Override
	public void run() {
//System.out.println("run");
		process();
	}
}