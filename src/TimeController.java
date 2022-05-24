import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeController extends JFrame implements ActionListener {
    private static final long serialVersionUID = 4603262282860990473L;
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 100;
    private static final int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    private Container container;
    private JButton btn;
    private JTextField jtfTime;
    private Timer tmr;

    public TimeController() {

        initComponents();

        Timer tmr = new Timer(1000, this);
        this.tmr = tmr;

        setVisible(true);

    }

    private void initComponents() {
        this.setTitle("SY秒表");
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation((width - DEFAULT_WIDTH) / 2, (height - DEFAULT_HEIGHT) / 2);

        jtfTime = new JTextField("10");

        btn = new JButton("开始倒计时");

        container = getContentPane();

        JPanel panel = new JPanel();

        panel.add(btn);

        panel.add(jtfTime);
        this.add(panel);

        btn.addActionListener(this);

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btn) {

            jtfTime.setText("10");

            tmr.start();

        } else {
            int t;

            t = Integer.parseInt(jtfTime.getText());

            t--;

            jtfTime.setText("" + t);
            if (t <= 0) {

                tmr.stop();

            }

        }

    }

    public static void main(String[] args) {

        TimeController timeController = new TimeController();

    }

}