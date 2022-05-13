package controller;

import com.sun.jdi.connect.Connector;
import model.*;
import view.Chessboard;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            chessboard.loadGame(chessData);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void storeGameFromFile(String path) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (String line : getChessString()
            ) {
                writer.write(line);
            }
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getChessString() {
        List<String> chessString = new ArrayList<>();
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
            chessString.add(sb.toString());
            chessString.add("\n");
        }
        if (chessboard.getCurrentColor() == ChessColor.BLACK) {
            chessString.add("b");
        } else {
            chessString.add("w");
        }
        return chessString;
    }
}
