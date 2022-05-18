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

    private int WIDTH;//窗口的高度
    private int HEIGTH;//窗口的宽度
    public final int CHESSBOARD_SIZE;//棋盘大小
    private GameController gameController;
    private Clip clip;

    private JPanel panel0 = new JPanel();//开始界面
    private JPanel panel1 = new JPanel();//游戏界面


    public ChessGameFrame(int width, int height) {

        setTitle("2022 CS102A Chess Project by 林俊杰 and 王子凡"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH); //设置窗体大小
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);  //绝对布局

//        背景音乐
        File bgm = new File("./musics/backgroundMusic3.wav");
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

//        开始游戏界面
        addBeginGameButten(panel0);
        addLoadButton(panel0);

//        开始游戏界面背景图片
        Image image = new ImageIcon("./images/背景/background2.jpg").getImage();
        JLabel jLabel = new aLabel(image);
        jLabel.setSize(WIDTH, HEIGTH);
        jLabel.setLocation(0, 0);
        panel0.add(jLabel);

//        游戏界面
        addChessboard(panel1);
        addRoundLabel(panel1);
        addBackButton(panel1);
        addStoreButton(panel1);
        addStoreIncludingStopsButton(panel1);
        addReBeginGameButten(panel1);
        addstopMusicButton(panel1);
        addBlackCapturedChessLabel(panel1);
        addWhiteCapturedChessLabel(panel1);
        addBlackCapturedChessLabel2(panel1);
        addWhiteCapturedChessLabel2(panel1);


//        游戏界面背景图片
        Image image1 = new ImageIcon("./images/背景/国际象棋8-1581498150.jpg").getImage();
        JLabel jLabel1 = new aLabel(image1);
        jLabel1.setSize(WIDTH, HEIGTH);
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
        JButton beginGameButten = (JButton) jPanel.getComponent(0);
        JButton loadGameButton = (JButton) jPanel.getComponent(1);
        JLabel backgroundImage = (aLabel) jPanel.getComponent(2);

        beginGameButten.setBounds(width / 4, height * 2 / 3, width / 6, height / 12);
        loadGameButton.setBounds(width * 7 / 12, height * 2 / 3, width / 6, height / 12);
        backgroundImage.setBounds(0, 0, width, height);
    }

    public void updateLayOutJPanel1(JPanel jPanel, int width, int height) {

        Chessboard chessboard = (Chessboard) jPanel.getComponent(0);
        JLabel roundLabel = (JLabel) jPanel.getComponent(1);
        JButton backButton = (JButton) jPanel.getComponent(2);
        JButton storeButton = (JButton) jPanel.getComponent(3);
        JButton storeIncludingStopsButton = (JButton) jPanel.getComponent(4);
        JButton reBeginGameButton = (JButton) jPanel.getComponent(5);
        JButton stopMusicButton = (JButton) jPanel.getComponent(6);
        JLabel blackCapturedChessLabel = (JLabel) jPanel.getComponent(7);
        JLabel whiteCapturedChessLabel = (JLabel) jPanel.getComponent(8);
        JLabel blackCapturedChessLabel2 = (JLabel) jPanel.getComponent(9);
        JLabel whiteCapturedChessLabel2 = (JLabel) jPanel.getComponent(10);
        JLabel backgroundImage = (aLabel) jPanel.getComponent(11);

        chessboard.setSize(height * 4 / 5, height * 4 / 5);
        chessboard.setLocation(width / 30, height / 8);
        chessboard.resetChessboardSize(height / 10);

        roundLabel.setSize(width * 3 / 10, height * 2 / 19);
        roundLabel.setLocation(width / 10, 0);

        backButton.setSize(width / 5, height * 3 / 38);
        backButton.setLocation(width * 7 / 10, height * 15 / 38);

        storeButton.setSize(width / 5, height * 3 / 38);
        storeButton.setLocation(width * 7 / 10, height * 10 / 19);

        storeIncludingStopsButton.setSize(width / 5, height * 3 / 38);
        storeIncludingStopsButton.setLocation(width * 7 / 10, height * 25 / 38);

        reBeginGameButton.setSize(width / 5, height * 3 / 38);
        reBeginGameButton.setLocation(width * 7 / 10, height * 15 / 19);

        stopMusicButton.setSize(width / 18, height / 15);
        stopMusicButton.setLocation(width * 17 / 18, 0);

        blackCapturedChessLabel.setBounds(width * 7 / 10, height * 2 / 19, width / 5, height * 1 / 19);

        whiteCapturedChessLabel.setBounds(width * 7 / 10, height * 9 / 38, width / 5, height * 1 / 19);

        blackCapturedChessLabel2.setBounds(WIDTH * 55 / 80, HEIGTH * 2 / 38, WIDTH / 4, HEIGTH * 1 / 19);

        whiteCapturedChessLabel2.setBounds(WIDTH * 55 / 80, HEIGTH * 7 / 38, WIDTH / 4, HEIGTH * 1 / 19);

        backgroundImage.setBounds(0, 0, width, height);
    }

    private void addBeginGameButten(JPanel panel) {
        JButton button = new JButton("Start a game");
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBounds(WIDTH / 4, HEIGTH * 2 / 3, WIDTH / 6, HEIGTH / 12);
        button.addActionListener((e) -> {
            remove(panel0);
            getContentPane().add(panel1);
            setVisible(true);
            validate();
            repaint();
        });
//        设置组件位置和大小
//        button.setLocation(WIDTH / 6, HEIGTH * 2 / 3);
//        button.setSize(WIDTH / 6, HEIGTH / 12);
//        设置背景颜色
//        button.setBackground(Color.green);
//        button.setBackground(Color.cyan);
//        button.setBackground(new Color(200,255,255));
//        按钮变透明
//        button.setContentAreaFilled(false);
//        jButton1 = button;
        panel.add(button);
    }

    private void addLoadButton(JPanel panel) {
        JButton button = new JButton("Load a game");
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBounds(WIDTH * 7 / 12, HEIGTH * 2 / 3, WIDTH / 6, HEIGTH / 12);

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
                remove(panel0);
                getContentPane().add(panel1);
                gameController.loadGameFromFile(file);
                setVisible(true);
                validate();
                repaint();
            }
        });
        panel.add(button);
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard(JPanel panel) {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        chessboard.setLocation(WIDTH / 30, HEIGTH / 8);
        gameController = new GameController(chessboard);
        panel.add(chessboard);
    }

    /**
     * 在游戏面板中添加回合状态标签
     */
    private void addRoundLabel(JPanel panel) {
        JLabel roundLabel = new JLabel("第1回合  It's White's turn.");
        roundLabel.setFont(new Font("Rockwell", Font.BOLD, 25));
        roundLabel.setSize(WIDTH * 3 / 10, HEIGTH * 2 / 19);
        roundLabel.setLocation(WIDTH / 10, 0);
        roundLabel.setForeground(Color.black);
        panel.add(roundLabel);
    }

    private void addBackButton(JPanel panel) {
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        backButton.setSize(WIDTH * 1 / 5, HEIGTH * 3 / 38);
        backButton.setLocation(WIDTH * 7 / 10, HEIGTH * 15 / 38);
        backButton.addActionListener((e) -> {
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
        panel.add(backButton);
    }

    private void addStoreButton(JPanel panel) {
        JButton storeButton = new JButton("Store the game");
        storeButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        storeButton.setSize(WIDTH * 1 / 5, HEIGTH * 3 / 38);
        storeButton.setLocation(WIDTH * 7 / 10, HEIGTH * 10 / 19);

        storeButton.addActionListener(e -> {
            System.out.println("Click store");
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
        });
        panel.add(storeButton);
    }

    private void addStoreIncludingStopsButton(JPanel panel) {
        JButton storeIncludingStopsButton = new JButton("Store a game including stops");
        storeIncludingStopsButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        storeIncludingStopsButton.setSize(WIDTH * 1 / 5, HEIGTH * 3 / 38);
        storeIncludingStopsButton.setLocation(WIDTH * 7 / 10, HEIGTH * 25 / 38);

        storeIncludingStopsButton.addActionListener(e -> {
            System.out.println("Click store");
            String path = JOptionPane.showInputDialog(this, "Input store path here");
            gameController.storeGameFromFileIncludingStops(path);
        });
        panel.add(storeIncludingStopsButton);
    }

    private void addReBeginGameButten(JPanel panel) {
        JButton reBeginGameButton = new JButton("重新开始游戏");
        reBeginGameButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        reBeginGameButton.setSize(WIDTH / 5, HEIGTH * 3 / 38);
        reBeginGameButton.setLocation(WIDTH * 7 / 10, HEIGTH * 15 / 19);
        reBeginGameButton.addActionListener((e) -> {
            Object[] options = {"确定", "取消"};
            int x = JOptionPane.showOptionDialog(null, "是否重新开始游戏？", "判断",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (x == 0) {
                remove(panel1);
                panel1.removeAll();
                addChessboard(panel1);
                addRoundLabel(panel1);
                addBackButton(panel1);
                addStoreButton(panel1);
                addStoreIncludingStopsButton(panel1);
                addReBeginGameButten(panel1);
                addstopMusicButton(panel1);
                addBlackCapturedChessLabel(panel1);
                addWhiteCapturedChessLabel(panel1);
                addBlackCapturedChessLabel2(panel1);
                addWhiteCapturedChessLabel2(panel1);

                Image image1 = new ImageIcon("./images/背景/国际象棋8-1581498150.jpg").getImage();
                JLabel jLabel1 = new aLabel(image1);
                jLabel1.setSize(WIDTH, HEIGTH);
                panel1.add(jLabel1);

                setContentPane(panel1);
                validate();
                repaint();
            }
        });
        panel.add(reBeginGameButton);
    }

    private void addstopMusicButton(JPanel panel) {
        JButton stopMusicButton = new JButton("♬");
        stopMusicButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        stopMusicButton.setSize(WIDTH / 18, HEIGTH / 15);
        stopMusicButton.setLocation(WIDTH * 17 / 18, 0);
        stopMusicButton.addActionListener((e) -> {
            if (clip.isRunning()) {
                clip.stop();
            } else {
                clip.start();
            }
        });
        panel.add(stopMusicButton);
    }

    private void addBlackCapturedChessLabel(JPanel panel) {
        JLabel blackCapturedChessLabel = new JLabel();
        blackCapturedChessLabel.setBackground(new Color(211, 211, 211));
        blackCapturedChessLabel.setForeground(Color.black);
        blackCapturedChessLabel.setOpaque(true);
        blackCapturedChessLabel.setBounds(WIDTH * 7 / 10, HEIGTH * 2 / 19, WIDTH / 5, HEIGTH * 1 / 19);
        blackCapturedChessLabel.setFont(new Font("Rockwell", Font.BOLD, 15));
        blackCapturedChessLabel.setText("");
        panel.add(blackCapturedChessLabel);
    }

    private void addBlackCapturedChessLabel2(JPanel panel) {
        JLabel blackCapturedChessLabel = new JLabel();
        blackCapturedChessLabel.setForeground(Color.black);
        blackCapturedChessLabel.setBounds(WIDTH * 55 / 80, HEIGTH * 2 / 38, WIDTH / 4, HEIGTH * 1 / 19);
        blackCapturedChessLabel.setFont(new Font("Rockwell", Font.BOLD, 15));
        blackCapturedChessLabel.setText("Player Black's captured chess:");
        panel.add(blackCapturedChessLabel);
    }

    private void addWhiteCapturedChessLabel(JPanel panel) {
        JLabel whiteCapturedChessLabel = new JLabel();
        whiteCapturedChessLabel.setBackground(new Color(105, 105, 105));
        whiteCapturedChessLabel.setForeground(Color.white);
        whiteCapturedChessLabel.setOpaque(true);
        whiteCapturedChessLabel.setBounds(WIDTH * 7 / 10, HEIGTH * 9 / 38, WIDTH / 5, HEIGTH * 1/ 19);
        whiteCapturedChessLabel.setFont(new Font("Rockwell", Font.BOLD, 15));
        whiteCapturedChessLabel.setText("");
        panel.add(whiteCapturedChessLabel);
    }

    private void addWhiteCapturedChessLabel2(JPanel panel) {
        JLabel whiteCapturedChessLabel = new JLabel();
        whiteCapturedChessLabel.setForeground(Color.white);
        whiteCapturedChessLabel.setBounds(WIDTH * 55 / 80, HEIGTH * 7 / 38, WIDTH / 4, HEIGTH * 1 / 19);
        whiteCapturedChessLabel.setFont(new Font("Rockwell", Font.BOLD, 15));
        whiteCapturedChessLabel.setText("Player White's captured chess:");
        panel.add(whiteCapturedChessLabel);
    }

    private void playMusic(File file) {
        try {
            //创建相当于音乐播放器的对象
            Clip clip = AudioSystem.getClip();
            this.clip = clip;
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
