package controller;


import model.*;
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

                chessComponent.aiCanMoveTo(chessboard.getChessComponents());
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                File click = new File("./musics/click2.wav");
                playMusic(click);

                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();

                chessComponent.removeAiCanMoveTo(chessboard.getChessComponents());
            } else if (handleSecond(chessComponent)) {

                ChessComponent first_ = first;
                ChessComponent chessComponent_ = chessComponent;
                ChessColor color1 = first.getChessColor();
                ChessColor color2;
                if (color1 == ChessColor.BLACK) {
                    color2 = ChessColor.WHITE;
                } else {
                    color2 = ChessColor.BLACK;
                }

                first.removeAiCanMoveTo(chessboard.getChessComponents());

//                兵底线升变
                if (first instanceof PawnChessComponent && (chessComponent.getChessboardPoint().getX() == 7 || chessComponent.getChessboardPoint().getX() == 0)) {
                    Object[] options = {"后", "车", "马", "象"};
                    int x = JOptionPane.showOptionDialog(null, "Please choose a kind of chessComponent",
                            "兵底线升变",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    switch (x) {
                        case -1:
                            return;
                        case 0:
                            chessboard.remove(first);
                            chessboard.add(first = new QueenChessComponent(first.getChessboardPoint(), first.getLocation(), first.getChessColor(), first.getClickController(), chessboard.getCHESS_SIZE()));
                            break;
                        case 1:
                            chessboard.remove(first);
                            chessboard.add(first = new RookChessComponent(first.getChessboardPoint(), first.getLocation(), first.getChessColor(), first.getClickController(), chessboard.getCHESS_SIZE()));
                            break;
                        case 2:
                            chessboard.remove(first);
                            chessboard.add(first = new KnightChessComponent(first.getChessboardPoint(), first.getLocation(), first.getChessColor(), first.getClickController(), chessboard.getCHESS_SIZE()));
                            break;
                        case 3:
                            chessboard.remove(first);
                            chessboard.add(first = new BishopChessComponent(first.getChessboardPoint(), first.getLocation(), first.getChessColor(), first.getClickController(), chessboard.getCHESS_SIZE()));
                            break;
                    }
                }

//                吃过路兵
                if (chessComponent instanceof EmptySlotComponent) {
                    if (first instanceof PawnChessComponent && first.getChessColor() == ChessColor.BLACK && first.getChessboardPoint().getX() == 4) {
                        if (first.getChessboardPoint().getY() != 0 && first.getChessboardPoint().getY() != 7) {
                            if (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 1).get(first.getChessboardPoint().getX()).charAt(first.getChessboardPoint().getY() + 1) == 'p' && (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 2)).get(first.getChessboardPoint().getX() + 2).charAt(first.getChessboardPoint().getY() + 1) == 'p'
                            ) {
                                ChessComponent second = chessboard.getChessComponents()[first.getChessboardPoint().getX()][first.getChessboardPoint().getY() + 1];
                                ChessComponent emptySlot = new EmptySlotComponent(second.getChessboardPoint(), second.getLocation(), second.getClickController(), chessboard.getCHESS_SIZE());
                                chessboard.putChessOnBoard(emptySlot);
                                emptySlot.repaint();
                            }
                            if (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 1).get(first.getChessboardPoint().getX()).charAt(first.getChessboardPoint().getY() - 1) == 'p' && (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 2)).get(first.getChessboardPoint().getX() + 2).charAt(first.getChessboardPoint().getY() - 1) == 'p'
                            ) {
                                ChessComponent second = chessboard.getChessComponents()[first.getChessboardPoint().getX()][first.getChessboardPoint().getY() - 1];
                                ChessComponent empytSlot = new EmptySlotComponent(second.getChessboardPoint(), second.getLocation(), second.getClickController(), chessboard.getCHESS_SIZE());
                                chessboard.putChessOnBoard(empytSlot);
                                empytSlot.repaint();
                            }
                        } else if (first.getChessboardPoint().getY() == 0) {
                            if (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 1).get(first.getChessboardPoint().getX()).charAt(first.getChessboardPoint().getY() + 1) == 'p' && (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 2)).get(first.getChessboardPoint().getX() + 2).charAt(first.getChessboardPoint().getY() + 1) == 'p'
                            ) {
                                ChessComponent second = chessboard.getChessComponents()[first.getChessboardPoint().getX()][first.getChessboardPoint().getY() + 1];
                                ChessComponent empytSlot = new EmptySlotComponent(second.getChessboardPoint(), second.getLocation(), second.getClickController(), chessboard.getCHESS_SIZE());
                                chessboard.putChessOnBoard(empytSlot);
                                empytSlot.repaint();
                            }
                        } else if (first.getChessboardPoint().getY() == 7) {
                            if (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 1).get(first.getChessboardPoint().getX()).charAt(first.getChessboardPoint().getY() - 1) == 'p' && (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 2)).get(first.getChessboardPoint().getX() + 2).charAt(first.getChessboardPoint().getY() - 1) == 'p'
                            ) {
                                ChessComponent second = chessboard.getChessComponents()[first.getChessboardPoint().getX()][first.getChessboardPoint().getY() - 1];
                                ChessComponent empytSlot = new EmptySlotComponent(second.getChessboardPoint(), second.getLocation(), second.getClickController(), chessboard.getCHESS_SIZE());
                                chessboard.putChessOnBoard(empytSlot);
                                empytSlot.repaint();
                            }
                        }
                    } else if (first instanceof PawnChessComponent && first.getChessColor() == ChessColor.WHITE && first.getChessboardPoint().getX() == 3) {
                        if (first.getChessboardPoint().getY() != 0 && first.getChessboardPoint().getY() != 7) {
                            if (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 1).get(first.getChessboardPoint().getX()).charAt(first.getChessboardPoint().getY() + 1) == 'P' && (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 2)).get(first.getChessboardPoint().getX() - 2).charAt(first.getChessboardPoint().getY() + 1) == 'P'
                            ) {
                                ChessComponent second = chessboard.getChessComponents()[first.getChessboardPoint().getX()][first.getChessboardPoint().getY() + 1];
                                ChessComponent emptySlot = new EmptySlotComponent(second.getChessboardPoint(), second.getLocation(), second.getClickController(), chessboard.getCHESS_SIZE());
                                chessboard.putChessOnBoard(emptySlot);
                                emptySlot.repaint();
                            }
                            if (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 1).get(first.getChessboardPoint().getX()).charAt(first.getChessboardPoint().getY() - 1) == 'P' && (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 2)).get(first.getChessboardPoint().getX() - 2).charAt(first.getChessboardPoint().getY() - 1) == 'P'
                            ) {
                                ChessComponent second = chessboard.getChessComponents()[first.getChessboardPoint().getX()][first.getChessboardPoint().getY() - 1];
                                ChessComponent emptySlot = new EmptySlotComponent(second.getChessboardPoint(), second.getLocation(), second.getClickController(), chessboard.getCHESS_SIZE());
                                chessboard.putChessOnBoard(emptySlot);
                                emptySlot.repaint();
                            }
                        } else if (first.getChessboardPoint().getY() == 0) {
                            if (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 1).get(first.getChessboardPoint().getX()).charAt(first.getChessboardPoint().getY() + 1) == 'P' && (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 2)).get(first.getChessboardPoint().getX() - 2).charAt(first.getChessboardPoint().getY() + 1) == 'P'
                            ) {
                                ChessComponent second = chessboard.getChessComponents()[first.getChessboardPoint().getX()][first.getChessboardPoint().getY() + 1];
                                ChessComponent emptySlot = new EmptySlotComponent(second.getChessboardPoint(), second.getLocation(), second.getClickController(), chessboard.getCHESS_SIZE());
                                chessboard.putChessOnBoard(emptySlot);
                                emptySlot.repaint();
                            }
                        } else if (first.getChessboardPoint().getY() == 7) {
                            if (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 1).get(first.getChessboardPoint().getX()).charAt(first.getChessboardPoint().getY() - 1) == 'P' && (chessboard.getRecordChessBoard().get(chessboard.getRecordChessBoard().size() - 2)).get(first.getChessboardPoint().getX() - 2).charAt(first.getChessboardPoint().getY() - 1) == 'P'
                            ) {
                                ChessComponent second = chessboard.getChessComponents()[first.getChessboardPoint().getX()][first.getChessboardPoint().getY() - 1];
                                ChessComponent emptySlot = new EmptySlotComponent(second.getChessboardPoint(), second.getLocation(), second.getClickController(), chessboard.getCHESS_SIZE());
                                chessboard.putChessOnBoard(emptySlot);
                                emptySlot.repaint();
                            }
                        }
                    }
                }

                File move = new File("./musics/move.wav");
                playMusic(move);

                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();

//                无效行棋提醒
                if (chessboard.tellIfKingIsAttacked2(chessboard.getChessComponents(), color2)) {
                    JOptionPane.showMessageDialog(null, "行棋后己方被将军", "无效行棋提示", 0);
                    chessboard.swapChessComponents(first_, chessComponent_);
                    if (chessboard.getCurrentColor() == ChessColor.BLACK) {
                        chessboard.setCurrentColor(ChessColor.WHITE);
                    } else {
                        chessboard.setCurrentColor(ChessColor.BLACK);
                    }
                    chessboard.setCount((float) (chessboard.getCount() - 1.0));
                }

//                提醒被将军
                if (chessboard.tellIfKingIsAttacked(color1)){

                }

//                王被将死
                if (chessboard.tellIfKingIsAttacked2(chessboard.getChessComponents(), color1)) {
                    if (chessboard.tellWinOrDefeat(color1)) {
                        JOptionPane.showMessageDialog(null, "Player " + color1.getName() + " wins!!", "游戏结束", -1);
                    }
                }

                first.setSelected(false);
                first = null;

                chessboard.getRecordChessBoard().add(chessboard.getChessStringList());
//                回合状态显示
                JLabel jLabel = (JLabel) chessboard.getParent().getComponent(1);
                jLabel.setText("第" + (int) chessboard.getCount() + "回合  " + "It's " + chessboard.getCurrentColor().getName() + "'s turn.");

                JLabel blackCapturedChessLabel = (JLabel) chessboard.getParent().getComponent(7);
                JLabel whiteCapturedChessLabel = (JLabel) chessboard.getParent().getComponent(8);
                if (color1 == ChessColor.BLACK) {
                    blackCapturedChessLabel.setText(chessboard.getCapturedChess(color1));
                    whiteCapturedChessLabel.setText(chessboard.getCapturedChess(color2));
                } else {
                    blackCapturedChessLabel.setText(chessboard.getCapturedChess(color2));
                    whiteCapturedChessLabel.setText(chessboard.getCapturedChess(color1));
                }

//                无子可动
                if (chessboard.tellIfHasNotChessToMove(color2)){
                    JOptionPane.showMessageDialog(null, "Player " + color1.getName() + " wins!!", "游戏结束", -1);
                }

//                三次重复
                if (chessboard.tellIfMoreThanThreeTimes()){
                    JOptionPane.showMessageDialog(null, "Draw!!", "游戏结束", -1);
                }
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
