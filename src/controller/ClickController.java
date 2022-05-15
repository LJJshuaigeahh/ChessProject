package controller;


import model.ChessColor;
import model.ChessComponent;
import view.Chessboard;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                File click = new File("./musics/click.wav");
                playMusic(click);

                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                File click = new File("./musics/click2.wav");
                playMusic(click);

                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                File move = new File("./musics/move.wav");
                playMusic(move);

                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();

                first.setSelected(false);
                first = null;

                chessboard.getRecordChessBoard().add(chessboard.getChessStringList2());
//                回合状态显示
                JLabel jLabel = (JLabel) chessboard.getParent().getComponent(1);
                jLabel.setText("第" + (int) chessboard.getCount() + "回合  " + "It's " + chessboard.getCurrentColor().getName() + "'s turn.");
            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }

    private void playMusic(File file) {
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
