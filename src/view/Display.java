package view;

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
    private Board board;
    private BufferedImage pieceSpriteSheet;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Display::new);
    }

    public Display(){
        setTitle("Chess Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        try{
            pieceSpriteSheet = ImageIO.read(new File("resources/Chess_Pieces_Sprite.svg.png"));
        } catch (IOException e){
            e.printStackTrace();
        }

        board = new Board();
        createGraphicalBoard();
        addGraphicalPieces();

        add(chessBoardPanel, BorderLayout.CENTER);
        setVisible(true);
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
                chessBoardPanel.add(square);
            }
        }
    }

    void addGraphicalPieces(){
        for (int rank = 0; rank < 8; rank++){
            for (int file = 0; file < 8; file++){
                int index = rank * 8 + file;
                Piece piece = board.square[index];

                if (piece != null && !piece.isNone()){
                    JLabel pieceLabel = new JLabel();
                    pieceLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    pieceLabel.setIcon(getPieceIcon(piece));
                    JPanel square = (JPanel) chessBoardPanel.getComponent(index);
                    square.add(pieceLabel);
                }
            }
        }
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
