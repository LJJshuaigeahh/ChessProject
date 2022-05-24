package controller;

import model.*;
import view.Chessboard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public boolean loadGameFromFile(File file) {
        String path = file.getPath();
        boolean test = true;
        try {
            if (!path.endsWith(".txt")) {
                JOptionPane.showMessageDialog(null, "File format error!!", "错误编码： 104", JOptionPane.ERROR_MESSAGE);
                test = false;
                return false;
            }
//            List<String> chessData = Files.readAllLines(Path.of(path));
            List<String> chessData = Files.readAllLines(Path.of(file.getPath()));
            if (!chessData.get(chessData.size() - 1).equals("w") && !chessData.get(chessData.size() - 1).equals("b")) {
                JOptionPane.showMessageDialog(null, "Missing next player!!", "错误编码： 103", JOptionPane.ERROR_MESSAGE);
                test = false;
                return false;
            }
            if (chessData.size() - 1 != 8) {
                JOptionPane.showMessageDialog(null, "Wrong chessboard size!!", "错误编码： 101", JOptionPane.ERROR_MESSAGE);
                test = false;
                return false;
            }
            for (int i = 0; i < 8; i++) {
                if (chessData.get(i).length() != 8) {
                    JOptionPane.showMessageDialog(null, "Wrong chessboard size!!", "错误编码： 101", JOptionPane.ERROR_MESSAGE);
                    test = false;
                    return false;
                }
            }
            List<Character> aList = Arrays.asList('R', 'N', 'B', 'Q', 'K', 'P', 'r', 'n', 'b', 'q', 'k', 'p', '_');
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (!aList.contains(chessData.get(i).charAt(j))) {
                        JOptionPane.showMessageDialog(null, "Wrong chess type!!", "错误编码： 102", JOptionPane.ERROR_MESSAGE);
                        test = false;
                        return false;
                    }
                }
            }
            if (test){
                chessboard.loadGame(chessData);
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void storeGameFromFile(String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (String line : getChessStringList()
            ) {
                writer.write(line);
            }
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeGameFromFileIncludingStops(String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (int i = 0; i < chessboard.getRecordChessBoard().size(); i++) {
                for (String line : chessboard.getRecordChessBoard().get(i)
                ) {
                    writer.write(line);
                }
            }
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backChess1() {
        chessboard.backChess2();
    }

    public List<String> getChessStringList() {
        List<String> chessStringList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                if (chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.BLACK) {
                    if (chessboard.getChessComponents()[i][j] instanceof RookChessComponent) {
                        sb.append('R');
                    } else if (chessboard.getChessComponents()[i][j] instanceof KnightChessComponent) {
                        sb.append('N');
                    } else if (chessboard.getChessComponents()[i][j] instanceof BishopChessComponent) {
                        sb.append('B');
                    } else if (chessboard.getChessComponents()[i][j] instanceof QueenChessComponent) {
                        sb.append('Q');
                    } else if (chessboard.getChessComponents()[i][j] instanceof KingChessComponent) {
                        sb.append('K');
                    } else if (chessboard.getChessComponents()[i][j] instanceof PawnChessComponent) {
                        sb.append('P');
                    }
                } else if (chessboard.getChessComponents()[i][j].getChessColor() == ChessColor.WHITE) {
                    if (chessboard.getChessComponents()[i][j] instanceof RookChessComponent) {
                        sb.append('r');
                    } else if (chessboard.getChessComponents()[i][j] instanceof KnightChessComponent) {
                        sb.append('n');
                    } else if (chessboard.getChessComponents()[i][j] instanceof BishopChessComponent) {
                        sb.append('b');
                    } else if (chessboard.getChessComponents()[i][j] instanceof QueenChessComponent) {
                        sb.append('q');
                    } else if (chessboard.getChessComponents()[i][j] instanceof KingChessComponent) {
                        sb.append('k');
                    } else if (chessboard.getChessComponents()[i][j] instanceof PawnChessComponent) {
                        sb.append('p');
                    }
                } else {
                    sb.append('_');
                }
            }
            chessStringList.add(sb + "\n");
        }
        if (chessboard.getCurrentColor() == ChessColor.BLACK) {
            chessStringList.add("b\n");
        } else {
            chessStringList.add("w\n");
        }
        return chessStringList;
    }
}
