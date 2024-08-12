package view;

import controller.ChessController;
import model.Board;
import model.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Display extends JFrame {
    private JPanel chessBoardPanel;
    private ChessController controller;
    private JPanel[][] squares = new JPanel[8][8];
    private BufferedImage pieceSpriteSheet;

    public Display(BufferedImage pieceSpriteSheet){
        this.pieceSpriteSheet = pieceSpriteSheet;
        setTitle("Chess Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        createGraphicalBoard();
        setVisible(true);
    }

    public void setController(ChessController controller){
        this.controller = controller;
    }

    void createGraphicalBoard(){
        chessBoardPanel = new JPanel(new GridLayout(8, 8));

        for (int rank = 0; rank < 8; rank++){
            for (int file = 0; file < 8; file++){
                JPanel square = new JPanel();
                boolean isLightSquare = (file + rank) % 2 == 0;
                if (isLightSquare) {
                    square.setBackground(Color.decode("#eeeed2"));
                }
                else {
                    square.setBackground(Color.decode("#769656"));
                }

                squares[rank][file] = square;

                int currentRank = rank;
                int currentFile = file;
                square.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mousePressed(java.awt.event.MouseEvent e) {
                        if (controller != null) {
                            if (controller.isValidSelect(currentFile, currentRank))
                                controller.removePreviousHighlights();
                            controller.handleSquareClick(currentFile, currentRank);
                        }
                    }
                });

                chessBoardPanel.add(square);
            }
        }
        add(chessBoardPanel, BorderLayout.CENTER);
    }

    public void highlightSquare(int file, int rank) {
        boolean isLightSquare = (file + rank) % 2 == 0;
        if (isLightSquare){
            squares[rank][file].setBackground(Color.decode("#f5f682"));
        }
        else {
            squares[rank][file].setBackground(Color.decode("#b9ca43"));
        }
    }

    public void removeHighlight(int file, int rank) {
        boolean isLightSquare = (file + rank) % 2 == 0;
        if (isLightSquare) {
            squares[rank][file].setBackground(Color.decode("#eeeed2"));
        }
        else {
            squares[rank][file].setBackground(Color.decode("#769656"));
        }
    }

    public void updateBoard(Board board) {
        chessBoardPanel.removeAll();

        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                JPanel square = squares[rank][file];

                Piece piece = board.square[rank * 8 + file];

                square.removeAll();

                if (piece != null && !piece.isNone()){
                    JLabel pieceLabel = new JLabel();
                    pieceLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    pieceLabel.setIcon(getPieceIcon(piece));
                    square.add(pieceLabel);
                }

                chessBoardPanel.add(square);
            }
        }

        chessBoardPanel.revalidate();
        chessBoardPanel.repaint();
    }

    private  ImageIcon getPieceIcon(Piece piece){
        int col = 0, row = 0;

        switch (piece.getType()){
            case KING: col = 0; break;
            case QUEEN: col = 1; break;
            case BISHOP: col = 2; break;
            case KNIGHT: col = 3; break;
            case ROOK: col = 4; break;
            case PAWN: col = 5; break;
            default: break;
        }

        switch (piece.getColor()){
            case WHITE: row = 0; break;
            case BLACK: row = 1; break;
            default: break;
        }

        BufferedImage pieceImage = pieceSpriteSheet.getSubimage(
                col * 133, row * 133, 133, 133
        );

        Image scaledImage = pieceImage.getScaledInstance(
                133 / 2,
                133 / 2,
                Image.SCALE_SMOOTH
        );

        return new ImageIcon(scaledImage);
    }
}
