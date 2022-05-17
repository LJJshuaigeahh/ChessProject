package view;

import controller.GameController;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {

    //    public final Dimension FRAME_SIZE;  //窗体大小  ？？


    private int WIDTH;
    private int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;


    private JPanel panel0 = new JPanel();
    private JPanel panel1 = new JPanel();
//    private JButton jButton1 = new JButton();
//
//
//    public JPanel getPanel0() {
//        return panel0;
//    }
//
//    public JButton getJButton1() {
//        return jButton1;
//    }


    public ChessGameFrame(int width, int height) {

        setTitle("2022 CS102A Chess Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH); //设置窗体大小
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);  //绝对布局

//        背景音乐
        File bgm = new File("./musics/backgroundMusic.wav");
//        播放背景音乐
        playMusic(bgm);

//开始游戏界面
        panel0.setSize(WIDTH, HEIGTH);
        panel0.setLayout(null);
//游戏界面
        panel1.setSize(WIDTH, HEIGTH);
        panel1.setLayout(null);

//        把平面加到窗体上
        getContentPane().add(panel0);
        setVisible(true);

        addBeginGameButten(panel0);

//        开始界面背景图片
        ImageIcon image = new ImageIcon("./images/背景/background2.jpg");
        JLabel jLabel = new JLabel(image);
        jLabel.setSize(WIDTH, HEIGTH);
        jLabel.setLocation(0, 0);
        panel0.add(jLabel);

//        给平面加组件
        addChessboard(panel1);
        addRoundLabel(panel1);
        addStoreButton(panel1);
        addReBeginGameButten(panel1);
        addLoadButton(panel1);
        addBackButton(panel1);
        addStoreIncludingStopsButton(panel1);

//        游戏界面背景图片
        ImageIcon image1 = new ImageIcon("./images/背景/国际象棋8-1581498150.jpg");
        JLabel jLabel1 = new JLabel(image1);
        jLabel1.setSize(image1.getIconWidth(), image1.getIconHeight());
        panel1.add(jLabel1);

//        适应窗口大小变化
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
//                if (getContentPane().equals(panel0)) {
//                }
                WIDTH = getWidth();
                HEIGTH = getHeight();
                panel0.setSize(WIDTH, HEIGTH);
                panel1.setSize(WIDTH, HEIGTH);
                updateLayOutJPanel0(panel0, WIDTH, HEIGTH);
                updateLayOutJPanel1(panel1, WIDTH, HEIGTH);
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    public void updateLayOutJPanel0(JPanel jPanel, int width, int height) {
        JButton jButton = (JButton) jPanel.getComponent(0);
        JLabel jLabel = (JLabel) jPanel.getComponent(1);

        jButton.setSize(width / 5, height / 10);
        jButton.setLocation(width * 2 / 5, height * 2 / 3);

        ImageIcon image = new ImageIcon("./images/背景/background2.jpg");
        jLabel = new JLabel(image);
        jLabel.setSize(width, height);
        jLabel.setLocation(0, 0);
    }

    public void updateLayOutJPanel1(JPanel jPanel, int width, int height) {
//        addChessboard(panel1);
//        addRoundLabel(panel1);
//        addStoreButton(panel1);
//        addReBeginGameButten(panel1);
//        addLoadButton(panel1);
//        addBackButton(panel1);
//        addStoreIncludingStopsButton(panel1);
        Chessboard chessboard = (Chessboard) jPanel.getComponent(0);
        JLabel roundLabel = (JLabel) jPanel.getComponent(1);
        JButton storeButton = (JButton) jPanel.getComponent(2);
        JButton reBeginGameButton = (JButton) jPanel.getComponent(3);
        JButton loadButton = (JButton) jPanel.getComponent(4);
        JButton backButton = (JButton) jPanel.getComponent(5);
        JButton storeIncludingStopsButton = (JButton) jPanel.getComponent(6);

        chessboard.setSize(height * 4 / 5, height * 4 / 5);
        chessboard.setLocation(height / 10, height / 10);

        roundLabel.setSize(width * 3 / 10, height * 3 / 38);
        roundLabel.setLocation(0, width / 20);

        storeButton.setSize(width * 1 / 5, height * 3 / 38);
        storeButton.setLocation(width * 7 / 10, height * 10 / 19);

        reBeginGameButton.setSize(width * 1 / 5, height * 3 / 38);
        reBeginGameButton.setLocation(width * 7 / 10, height * 15 / 19);

        loadButton.setSize(width * 1 / 5, height * 3 / 38);
        loadButton.setLocation(width * 7 / 10, height * 25 / 38);

        backButton.setSize(width * 1 / 5, height * 3 / 38);
        backButton.setLocation(width * 7 / 10, height * 15 / 38);

        storeIncludingStopsButton.setSize(width * 1 / 5, height * 3 / 38);
        storeIncludingStopsButton.setLocation(width * 7 / 10, height * 5 / 19);
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard(JPanel panel) {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(20, 76);
        panel.add(chessboard);
    }

    /**
     * 在游戏面板中添加回合状态标签
     */
    private void addRoundLabel(JPanel panel) {
        JLabel statusLabel = new JLabel("第1回合  It's White's turn.");
        statusLabel.setLocation(200, 0);
        statusLabel.setSize(300, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        statusLabel.setForeground(Color.black);
        panel.add(statusLabel);
    }

    private void addBeginGameButten(JPanel panel) {
        JButton button = new JButton("开始游戏");
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        设置组件位置和大小
        button.setLocation(WIDTH * 2 / 5, HEIGTH * 2 / 3);
        button.setSize(WIDTH / 5, HEIGTH / 10);
        button.addActionListener((e) -> {
            remove(panel0);
            getContentPane().add(panel1);
            setVisible(true);
            validate();
            repaint();
        });
//        设置背景颜色
//        button.setBackground(Color.green);
//        button.setBackground(Color.cyan);
//        button.setBackground(new Color(200,255,255));
//        按钮变透明
//        button.setContentAreaFilled(false);
//        jButton1 = button;
        panel.add(button);
    }

    private void addReBeginGameButten(JPanel panel) {
        JButton button = new JButton("重新开始游戏");
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.addActionListener((e) -> {
            Object[] options = {"确定", "取消"};
            int x = JOptionPane.showOptionDialog(null, "是否重新开始游戏？", "判断",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (x == 0) {
                remove(panel1);
                panel1.removeAll();
                addChessboard(panel1);
                addRoundLabel(panel1);
                addReBeginGameButten(panel1);
                addStoreButton(panel1);
                addStoreIncludingStopsButton(panel1);
                addLoadButton(panel1);
                addBackButton(panel1);
                setContentPane(panel1);

                ImageIcon image1 = new ImageIcon("./images/背景/国际象棋8-1581498150.jpg");
                JLabel jLabel1 = new JLabel(image1);
                jLabel1.setSize(image1.getIconWidth(), image1.getIconHeight());
                panel1.add(jLabel1);
                validate();
                repaint();
            }
        });
        button.setLocation(700, 600);
        button.setSize(200, 60);
        panel.add(button);
    }

    private void addLoadButton(JPanel panel) {
        JButton button = new JButton("Load a game");
        button.setLocation(700, 500);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));

        button.addActionListener(e -> {
            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this, "Input load path here");

            // 创建一个默认的文件选取器
            JFileChooser fileChooser = new JFileChooser();

            // 设置默认显示的文件夹为当前文件夹
            fileChooser.setCurrentDirectory(new File("."));

            // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            // 设置是否允许多选
            fileChooser.setMultiSelectionEnabled(false);

            // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("txt", "txt"));
            // 设置默认使用的文件过滤器
            fileChooser.setFileFilter(new FileNameExtensionFilter("txt", "txt"));

            // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
            int result = fileChooser.showOpenDialog(panel);

            if (result == JFileChooser.APPROVE_OPTION) {
                // 如果点击了"确定", 则获取选择的文件路径
                File file = fileChooser.getSelectedFile();

                // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
                // File[] files = fileChooser.getSelectedFiles();
                final JTextArea msgTextArea = new JTextArea(10, 30);
                msgTextArea.setLineWrap(true);
                panel.add(msgTextArea);
                msgTextArea.append("打开文件: " + file.getAbsolutePath() + "\n\n");
                gameController.loadGameFromFile(file);
            }
        });
        panel.add(button);
    }

    private void addStoreButton(JPanel panel) {
        JButton button = new JButton("Store a game");
        button.setLocation(700, 400);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));

        button.addActionListener(e -> {
            System.out.println("Click store");
//            String path = JOptionPane.showInputDialog(this, "Input store path here");
            /*
             * 选择文件保存路径
             */
            // 创建一个默认的文件选取器
            JFileChooser fileChooser = new JFileChooser();

            // 设置打开文件选择框后默认输入的文件名
            fileChooser.setSelectedFile(new File("Chessboard"));

            // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
            int result = fileChooser.showSaveDialog(panel);

            if (result == JFileChooser.APPROVE_OPTION) {
                // 如果点击了"保存", 则获取选择的保存路径
                File file = fileChooser.getSelectedFile();
                final JTextArea msgTextArea = new JTextArea(10, 30);
                msgTextArea.setLineWrap(true);
                panel.add(msgTextArea);
                msgTextArea.append("保存到文件: " + file.getAbsolutePath() + "\n\n");
                String path = file.getPath();
                gameController.storeGameFromFile(path);
            }
//            gameController.storeGameFromFile(path);
        });
        panel.add(button);
    }

    private void addStoreIncludingStopsButton(JPanel panel) {
        JButton button = new JButton("Store a game including stops");
        button.setLocation(700, 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));

        button.addActionListener(e -> {
            System.out.println("Click store");
            String path = JOptionPane.showInputDialog(this, "Input store path here");
            gameController.storeGameFromFileIncludingStops(path);
        });
        panel.add(button);
    }

    private void addBackButton(JPanel panel) {
        JButton button = new JButton("Back");
        button.setLocation(700, 300);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        panel.add(button);
        button.addActionListener((e) -> {
            System.out.println("Click back");
            Object[] options = {"确定", "取消"};
            int x = JOptionPane.showOptionDialog(null, "是否悔棋？", "判断",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (x == 0) {
                File back = new File("./musics/back.wav");
                playBackChessMusic(back);
                gameController.backChess1();
            }
        });
    }

//    private void addMusicButton(JPanel panel) {
////        背景音乐
//        File bgm = new File("./musics/backgroundMusic.wav");
////        //播放背景音乐
//        playMusic(bgm);
//        JButton button = new JButton();
//        button.setLocation(700, 0);
//        button.setBackground(new Color(200, 255, 255));
//        ImageIcon image = new ImageIcon("./images/音乐开关-开.png");
//        Image image1 = image.getImage();
//        Image image2 = image1.getScaledInstance(30, 30, Image.SCALE_FAST);
//        ImageIcon image3 = new ImageIcon(image2);
//        button.setSize(image3.getIconWidth(), image3.getIconHeight());
//        button.setIcon(image3);
//        button.addActionListener((e) -> {
//            stopMusic(bgm);
//        });
//        panel.add(button);
//    }


    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Hi!!");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void playMusic(File file) {
        try {
            //创建相当于音乐播放器的对象
            Clip clip = AudioSystem.getClip();
            //将传入的文件转成可播放的文件
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
            //播放器打开这个文件
            clip.open(audioInput);
            //clip.start();//只播放一次
            //循环播放
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //死循环不让主程序结束（swing可不用）
    /*
      while(true){
      }
    */
    }

//    private void stopMusic(File file) {
//        try {
//            //创建相当于音乐播放器的对象
//            Clip clip = AudioSystem.getClip();
//            //将传入的文件转成可播放的文件
//            AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
//            //播放器打开这个文件
//            clip.open(audioInput);
//            //clip.start();//只播放一次
//            //循环播放
////            clip.loop(Clip.LOOP_CONTINUOUSLY);
//            clip.stop();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        //死循环不让主程序结束（swing可不用）
//    /*
//      while(true){
//      }
//    */
//    }

    private void playBackChessMusic(File file) {
        try {
            //创建相当于音乐播放器的对象
            Clip clip = AudioSystem.getClip();
            //将传入的文件转成可播放的文件
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
            //播放器打开这个文件
            clip.open(audioInput);
            clip.start();//只播放一次
            //循环播放
//            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //死循环不让主程序结束（swing可不用）
    /*
      while(true){
      }
    */
    }
}
