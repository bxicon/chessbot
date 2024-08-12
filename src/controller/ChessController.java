package controller;

import model.Board;
import model.Piece;
import view.Display;

public class ChessController {
    private Board board;
    private Display display;
    private int selectedFile = -1;
    private int selectedRank = -1;
    private int prevSelectedFile = -1;
    private int prevSelectedRank = -1;
    private int prevDestFile = -1;
    private int prevDestRank = -1;

    public ChessController(Display display) {
        this.display = display;
        this.board = new Board();
        display.setController(this);
        display.updateBoard(board);
    }

    public void handleSquareClick(int file, int rank) {
        if (selectedFile == -1 && selectedRank == -1) {
            if (board.square[rank * 8 + file] != null){
                selectedFile = file;
                selectedRank = rank;
                display.highlightSquare(file, rank);
            }
        }
        else {
            movePiece(selectedFile, selectedRank, file, rank);

            prevSelectedFile = selectedFile;
            prevSelectedRank = selectedRank;
            prevDestFile = file;
            prevDestRank = rank;

            selectedFile = -1;
            selectedRank = -1;
        }
    }

    private void movePiece(int fromFile, int fromRank, int toFile, int toRank){
        Piece piece = board.square[fromRank * 8 + fromFile];

        if (piece != null && !piece.isNone()) {
            board.square[toRank * 8 + toFile] = piece;
            board.square[fromRank * 8 + fromFile] = null;

            display.updateBoard(board);
            display.highlightSquare(toFile, toRank);
        }
    }

    public void removePreviousHighlights() {
        if (prevSelectedFile != -1 && prevSelectedRank != -1) {
            display.removeHighlight(prevSelectedFile, prevSelectedRank);
            display.removeHighlight(prevDestFile, prevDestRank);
            prevSelectedFile = -1;
            prevSelectedRank = -1;
            prevDestFile = -1;
            prevDestRank = -1;
        }
    }

    public boolean isValidSelect(int file, int rank){
        Piece piece = board.square[rank * 8 + file];

        return piece != null && !piece.isNone();
    }
}
