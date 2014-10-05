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
 * �V���v���ȃ��[�����M�T���v���B
 */
public class SimpleSendMail implements Runnable {
    // ���{�ꃁ�[���̏ꍇ�ɂ� ISO-2022-JP���I�X�X���B
    // UTF-8���Ǝ�M���ɕ����������Ă��܂����[�������̒��ɂ͈ˑR�Ƃ��đ��݂��Ă��܂��B
    private static final String ENCODE = "ISO-2022-JP";
	private static String toString="sakaguti3@me.com";		// sakaguti@me.com
	private static String fromString="sakaguti3@gmail.com";	// sakaguti@itplants.com
	private static String serverString="smtp.gmail.com"; //	smtp.me.com
	private static String passString="|rvl|xnl6";
	private static String titleString="�A�C�e�B�v�����^�[���|�[�g";	// �A�C�e�B�v�����^�[���|�[�g
	private static String messageString="�����s�����Ă��܂����B�������Ă��������B";
	
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
        System.out.println("���[�����M�J�n"+Settings.Fukugouka(passString));

        SimpleSendMail ssm= new SimpleSendMail(toString,fromString,serverString,Settings.Fukugouka(passString),titleString,messageString);
        //ssm.setting();
        //ssm.process();
        //ssm.start();

        System.out.println("���[�����M�I��");
    }

    public void process() {

        final Properties props = new Properties();

        // ��{���B�����ł� gmail�ւ̐ڑ���������܂��B
        //props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.host", server1);
        
//        props.setProperty("mail.smtp.host", "smtp.mail.me.com");
        // SSL�p�Ƀ|�[�g�ԍ���ύX�B
        props.setProperty("mail.smtp.port", "465");
//        props.setProperty("mail.smtp.port", "587");

        // �^�C���A�E�g�ݒ�
        props.setProperty("mail.smtp.connectiontimeout", "10000");
        props.setProperty("mail.smtp.timeout", "10000");

        // �F��
        props.setProperty("mail.smtp.auth", "true");

        // SSL�֘A�ݒ�
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

        // �f�o�b�O���s���܂��B�W���o�͂Ƀg���[�X���o�܂��B
        session.setDebug(true);

        // ���b�Z�[�W���e�̐ݒ�B
        final MimeMessage message = new MimeMessage(session);
        
        try {
            final Address addrFrom = new InternetAddress(
                    from1, from1, ENCODE);
            message.setFrom(addrFrom);

            final Address addrTo = new InternetAddress(to1,
            		to1, ENCODE);
            message.addRecipient(Message.RecipientType.TO, addrTo);

            // ���[����Subject
            message.setSubject(title1, ENCODE);

            // ���[���{���BsetText��p����� �����I��[text/plain]�ƂȂ�B
            message.setText(msg1, ENCODE);

            // ���΍�: �J�n
            // setText���Ăяo������ɁA�w�b�_�[�� 7bit�ւƏ㏑�����܂��B
            // ����́A�ꕔ�̃P�[�^�C���[���� quoted-printable �������ł��Ȃ����Ƃւ̑΍�ƂȂ�܂��B
            message.setHeader("Content-Transfer-Encoding", "7bit");
            // ���΍�: �I��

            // ���̑��̕t�����B
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
        
        // ���[�����M�B
        try {
            Transport.send(message);
        } catch (AuthenticationFailedException e) {
            // �F�؎��s�� �����ɓ���܂��B
            System.out.println("�w��̃��[�U���E�p�X���[�h�ł̔F�؂Ɏ��s���܂����B"
                + e.toString());
        } catch (MessagingException e) {
            // smtp�T�[�o�ւ̐ڑ����s�� �����ɓ���܂��B
            System.out.println("�w���smtp�T�[�o�ւ̐ڑ��Ɏ��s���܂����B" + e.toString());
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