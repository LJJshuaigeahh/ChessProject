package view;

import controller.GameController;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {

    //    public final Dimension FRAME_SIZE;  //窗体大小  ？？


    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;


    private JPanel panel0 = new JPanel();
    private JPanel panel1 = new JPanel();
    private JButton jButton1 = new JButton();


    public JPanel getPanel0() {
        return panel0;
    }

    public JButton getJButton1() {
        return jButton1;
    }


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
        ImageIcon image = new ImageIcon("./images/背景/2962d5f01c94fefbda7d5e282bca0df9b2ada0e7(1).jpg");
        JLabel jLabel = new JLabel(image);
        jLabel.setSize(image.getIconWidth(), image.getIconHeight());
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
        button.addActionListener((e) -> {
            remove(panel0);
            getContentPane().add(panel1);
            setVisible(true);
            validate();
            repaint();
        });
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        设置组件位置和大小
//        button.setLocation(1000, 300);
//        button.setSize(WIDTH / 5, HEIGTH / 10);
//        设置背景颜色
//        button.setBackground(Color.green);
//        button.setBackground(Color.cyan);
//        button.setBackground(new Color(200,255,255));
//        按钮变透明
//        button.setContentAreaFilled(false);
        jButton1 = button;
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
            String path = JOptionPane.showInputDialog(this, "Input load path here");
            gameController.loadGameFromFile(path);
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
            String path = JOptionPane.showInputDialog(this, "Input store path here");
            gameController.storeGameFromFile(path);
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
