package model;

public class Piece {
    private Color color;
    private PieceType type;

    public Piece(Color color, PieceType type){
        this.color = color;
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public PieceType getType() {
        return type;
    }

    public boolean isNone() {
        return type == PieceType.NONE;
    }
}

