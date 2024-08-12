package model;

import java.util.HashMap;
import java.util.Map;

public class Board {
    public static Piece[] square;
    private static final String startFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    private static final Map<Character, PieceType> pieceTypeFromSymbol = new HashMap<Character, PieceType>() {{
        put('p', PieceType.PAWN);
        put('n', PieceType.KNIGHT);
        put('b', PieceType.BISHOP);
        put('r', PieceType.ROOK);
        put('q', PieceType.QUEEN);
        put('k', PieceType.KING);
    }};

    public Board(){
        square = new Piece[64];
        loadPositionFromFEN(startFEN);
    }

    public static void loadPositionFromFEN(String fen) {
        String fenBoard = fen.split(" ")[0];
        int file = 0, rank = 0;

        for (char symbol : fenBoard.toCharArray()) {
            if (symbol == '/') {
                file = 0;
                rank++;
            } else {
                if (Character.isDigit(symbol)) {
                    file += Character.getNumericValue(symbol);
                } else {
                    Color pieceColor = Character.isUpperCase(symbol) ? Color.WHITE : Color.BLACK;
                    PieceType pieceType = pieceTypeFromSymbol.get(Character.toLowerCase(symbol));
                    square[rank * 8 + file] = new Piece(pieceColor, pieceType);
                    file++;
                }
            }

        }
    }
}
