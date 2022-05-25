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
        addBeginGameButton(panel0);
        addBeginAIGameButton(panel0);
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
        addReBeginGameButton(panel1);
        addStopMusicButton(panel1);
        addBlackCapturedChessLabel(panel1);
        addWhiteCapturedChessLabel(panel1);
        addBlackCapturedChessLabel2(panel1);
        addWhiteCapturedChessLabel2(panel1);
        addBackPanel0Button(panel1);

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
        JButton beginGameButton = (JButton) jPanel.getComponent(0);
        JButton beginAIGameButton = (JButton) jPanel.getComponent(1);
        JButton loadGameButton = (JButton) jPanel.getComponent(2);
        JLabel backgroundImage = (aLabel) jPanel.getComponent(3);

        beginGameButton.setBounds(width * 3 / 8, height / 2, width / 4, height / 10);
        beginAIGameButton.setBounds(width * 3 / 8, height * 5 / 8, width / 4, height / 10);
        loadGameButton.setBounds(width * 3 / 8, height * 3 / 4, width / 4, height / 10);
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
        JButton backPanel0Button = (JButton) jPanel.getComponent(11);
        JLabel backgroundImage = (aLabel) jPanel.getComponent(12);

        chessboard.setSize(height * 4 / 5, height * 4 / 5);
        chessboard.setLocation(width / 30, height / 8);
        chessboard.resetChessboardSize(height / 10);

        roundLabel.setSize(width / 2, height * 3 / 38);
        roundLabel.setLocation(width * 3 / 40, height / 38);

        backButton.setSize(width / 5, height * 3 / 38);
        backButton.setLocation(width * 7 / 10, height * 10 / 19);

        storeButton.setSize(width / 5, height * 3 / 38);
        storeButton.setLocation(width * 7 / 10, height * 12 / 19);

        storeIncludingStopsButton.setSize(width / 5, height * 3 / 38);
        storeIncludingStopsButton.setLocation(width * 7 / 10, height * 14 / 19);

        reBeginGameButton.setSize(width / 5, height * 3 / 38);
        reBeginGameButton.setLocation(width * 7 / 10, height * 16 / 19);

        stopMusicButton.setSize(width / 18, height / 15);
        stopMusicButton.setLocation(width * 17 / 18, 0);

        blackCapturedChessLabel.setBounds(width * 55 / 80, height * 4 / 19, width / 4, height * 3 / 38);

        whiteCapturedChessLabel.setBounds(width * 55 / 80, height * 13 / 38, width / 4, height * 3 / 38);

        blackCapturedChessLabel2.setBounds(width * 52 / 80, height * 3 / 19, width / 3, height / 19);

        whiteCapturedChessLabel2.setBounds(width * 52 / 80, height * 11 / 38, width / 3, height / 19);

        backPanel0Button.setBounds(width * 17 / 18, height / 15, width / 18, height / 15);

        backgroundImage.setBounds(0, 0, width, height);
    }

    private void addBeginGameButton(JPanel panel) {
        JButton button = new JButton("Human vs Human");
        button.setFont(new Font("Rockwell", Font.BOLD, 25));
        button.setBounds(WIDTH * 3 / 8, HEIGTH / 2, WIDTH / 4, HEIGTH / 10);
        button.addActionListener((e) -> {
            remove(panel0);
            getContentPane().add(panel1);
            Chessboard chessboard = (Chessboard) panel1.getComponent(0);
            chessboard.setGameMode(0);
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

    private void addBeginAIGameButton(JPanel panel) {
        JButton button = new JButton("Human vs AI");
        button.setFont(new Font("Rockwell", Font.BOLD, 25));
        button.setBounds(WIDTH * 3 / 8, HEIGTH * 5 / 8, WIDTH / 4, HEIGTH / 10);
        button.addActionListener((e) -> {
//            JOptionPane.showMessageDialog(null, "人机模式待开发", "提示", JOptionPane.PLAIN_MESSAGE);
            remove(panel0);
            getContentPane().add(panel1);
            Chessboard chessboard = (Chessboard) panel1.getComponent(0);
            chessboard.setGameMode(2);
            setVisible(true);
            validate();
            repaint();
        });
        panel.add(button);
    }

    private void addLoadButton(JPanel panel) {
        JButton button = new JButton("Load a game");
        button.setFont(new Font("Rockwell", Font.BOLD, 25));
        button.setBounds(WIDTH * 3 / 8, HEIGTH * 3 / 4, WIDTH / 4, HEIGTH / 10);

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
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("json", "json"));
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
                if (gameController.loadGameFromFile(file)) {
                    remove(panel0);
                    getContentPane().add(panel1);
                    setVisible(true);
                    validate();
                    repaint();
                }
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
        JLabel roundLabel = new JLabel();
        roundLabel.setBackground(new Color(211, 211, 211));
        roundLabel.setForeground(Color.black);
        roundLabel.setOpaque(true);
        roundLabel.setSize(WIDTH / 2, HEIGTH * 3 / 38);
        roundLabel.setLocation(WIDTH * 3 / 40, HEIGTH / 38);
        roundLabel.setFont(new Font("Rockwell", Font.BOLD, 25));
        roundLabel.setText("Round  1————It's White's turn.");
        roundLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(roundLabel);
    }

    private void addBackButton(JPanel panel) {
        JButton backButton = new JButton("Regret");
        backButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        backButton.setSize(WIDTH / 5, HEIGTH * 3 / 38);
        backButton.setLocation(WIDTH * 7 / 10, HEIGTH * 10 / 19);
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
        JButton storeButton = new JButton("Store");
        storeButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        storeButton.setSize(WIDTH / 5, HEIGTH * 3 / 38);
        storeButton.setLocation(WIDTH * 7 / 10, HEIGTH * 12 / 19);

        storeButton.addActionListener(e -> {
            System.out.println("Click store");
            /*
             * 选择文件保存路径
             */
            // 创建一个默认的文件选取器
            JFileChooser fileChooser = new JFileChooser();

            // 设置打开文件选择框后默认输入的文件名
            fileChooser.setSelectedFile(new File("D:\\ChessDemo\\ChessDemo\\resource\\Chessboard"));

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
        JButton storeIncludingStopsButton = new JButton("Store the game");
        storeIncludingStopsButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        storeIncludingStopsButton.setSize(WIDTH / 5, HEIGTH * 3 / 38);
        storeIncludingStopsButton.setLocation(WIDTH * 7 / 10, HEIGTH * 14 / 19);

        storeIncludingStopsButton.addActionListener(e -> {
            System.out.println("Click store");
            /*
             * 选择文件保存路径
             */
            // 创建一个默认的文件选取器
            JFileChooser fileChooser = new JFileChooser();

            // 设置打开文件选择框后默认输入的文件名
            fileChooser.setSelectedFile(new File("D:\\ChessDemo\\ChessDemo\\resource\\Chessboard"));

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
                gameController.storeGameFromFileIncludingStops(path);
            }
        });
        panel.add(storeIncludingStopsButton);
    }

    private void addReBeginGameButton(JPanel panel) {
        JButton reBeginGameButton = new JButton("Restart");
        reBeginGameButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        reBeginGameButton.setSize(WIDTH / 5, HEIGTH * 3 / 38);
        reBeginGameButton.setLocation(WIDTH * 7 / 10, HEIGTH * 16 / 19);
        reBeginGameButton.addActionListener((e) -> {
            Object[] options = {"确定", "取消"};
            int x = JOptionPane.showOptionDialog(null, "是否重新开始游戏？", "判断",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (x == 0) {
                Chessboard chessboard1 = (Chessboard) panel1.getComponent(0);

                remove(panel1);
                panel1.removeAll();
                addChessboard(panel1);
                addRoundLabel(panel1);
                addBackButton(panel1);
                addStoreButton(panel1);
                addStoreIncludingStopsButton(panel1);
                addReBeginGameButton(panel1);
                addStopMusicButton(panel1);
                addBlackCapturedChessLabel(panel1);
                addWhiteCapturedChessLabel(panel1);
                addBlackCapturedChessLabel2(panel1);
                addWhiteCapturedChessLabel2(panel1);
                addBackPanel0Button(panel1);

                Image image1 = new ImageIcon("./images/背景/国际象棋8-1581498150.jpg").getImage();
                JLabel jLabel1 = new aLabel(image1);
                jLabel1.setSize(WIDTH, HEIGTH);
                panel1.add(jLabel1);

                Chessboard chessboard2 = (Chessboard) panel1.getComponent(0);
                if (chessboard1.getGameMode() == 2) {
                    chessboard2.setGameMode(2);
                }

                updateLayOutJPanel1(panel1, WIDTH, HEIGTH);
                getContentPane().add(panel1);
                validate();
                repaint();
            }
        });
        panel.add(reBeginGameButton);
    }

    private void addStopMusicButton(JPanel panel) {
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
        blackCapturedChessLabel.setBounds(WIDTH * 55 / 80, HEIGTH * 4 / 19, WIDTH / 4, HEIGTH * 3 / 38);
        blackCapturedChessLabel.setFont(new Font("Rockwell", Font.BOLD, 22));
        blackCapturedChessLabel.setText("");
        panel.add(blackCapturedChessLabel);
    }

    private void addBlackCapturedChessLabel2(JPanel panel) {
        JLabel blackCapturedChessLabel = new JLabel();
        blackCapturedChessLabel.setForeground(Color.black);
        blackCapturedChessLabel.setBounds(WIDTH * 52 / 80, HEIGTH * 3 / 19, WIDTH / 3, HEIGTH / 19);
        blackCapturedChessLabel.setFont(new Font("Rockwell", Font.BOLD, 22));
        blackCapturedChessLabel.setText("Player Black's captured chess:");
        panel.add(blackCapturedChessLabel);
    }

    private void addWhiteCapturedChessLabel(JPanel panel) {
        JLabel whiteCapturedChessLabel = new JLabel();
        whiteCapturedChessLabel.setBackground(new Color(105, 105, 105));
        whiteCapturedChessLabel.setForeground(Color.white);
        whiteCapturedChessLabel.setOpaque(true);
        whiteCapturedChessLabel.setBounds(WIDTH * 55 / 80, HEIGTH * 13 / 38, WIDTH / 4, HEIGTH * 3 / 38);
        whiteCapturedChessLabel.setFont(new Font("Rockwell", Font.BOLD, 22));
        whiteCapturedChessLabel.setText("");
        panel.add(whiteCapturedChessLabel);
    }

    private void addWhiteCapturedChessLabel2(JPanel panel) {
        JLabel whiteCapturedChessLabel = new JLabel();
        whiteCapturedChessLabel.setForeground(Color.white);
        whiteCapturedChessLabel.setBounds(WIDTH * 52 / 80, HEIGTH * 11 / 38, WIDTH / 3, HEIGTH / 19);
        whiteCapturedChessLabel.setFont(new Font("Rockwell", Font.BOLD, 22));
        whiteCapturedChessLabel.setText("Player White's captured chess:");
        panel.add(whiteCapturedChessLabel);
    }

    private void addBackPanel0Button(JPanel panel) {
        JButton stopMusicButton = new JButton("↩");
        stopMusicButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        stopMusicButton.setSize(WIDTH / 18, HEIGTH / 15);
        stopMusicButton.setLocation(WIDTH * 17 / 18, HEIGTH / 15);
        stopMusicButton.addActionListener((e) -> {
            Object[] options = {"确定", "取消"};
            int x = JOptionPane.showOptionDialog(null, "是否返回主界面？", "判断",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (x == 0) {
                remove(panel1);
                getContentPane().add(panel0);
                setVisible(true);
                validate();
                repaint();
            }
        });
        panel.add(stopMusicButton);
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
