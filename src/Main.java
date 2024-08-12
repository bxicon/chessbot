import controller.ChessController;
import view.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        BufferedImage pieceSpriteSheet = null;
        try {
            pieceSpriteSheet = ImageIO.read(new File("resources/Chess_Pieces_Sprite.svg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Display display = new Display(pieceSpriteSheet);
        ChessController controller = new ChessController(display);
    }
}
