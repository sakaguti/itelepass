import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class JTextAreaStream extends OutputStream {

    private JTextArea _area;
    private ByteArrayOutputStream _buf;

    public JTextAreaStream(JTextArea area) {
        _area = area;
        _buf = new ByteArrayOutputStream();
    }
    
    @Override
    public void write(int b) throws IOException {
        _buf.write(b);
    }
    
    @Override
    public void flush() throws IOException {

        // Swing �̃C�x���g�X���b�h�ɂ̂���
        SwingUtilities.invokeLater(new Runnable() {
            @Override
			public void run() {
                _area.append(_buf.toString());
                _buf.reset();
            }
        });
    }

    public static void main(String[] args) {
        JTextArea area = new JTextArea();
        area.setEditable(false);  // ReadOnly ��
        JTextAreaStream stream = new JTextAreaStream(area);
        System.setOut(new PrintStream(stream, true));    // true �� AutoFlush �̐ݒ�

        JFrame frame = new JFrame();
        frame.getContentPane().add(area);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(50,100);
        frame.setVisible(true);

        System.out.println("������");
        System.out.println("������");
        System.out.println("������");
    }
}