import org.junit.jupiter.api.Test;
import ru.nsu.fit.icg.lab1.instrument.FillInstrument;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class SpanTest {

    private int imageSide = 100, squareSide = 50, squareStart = (imageSide - squareSide) / 2;

    @Test
    void squareTest() {

        BufferedImage bufferedImage = new BufferedImage(imageSide, imageSide, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
        Color black = new Color(0, 0, 0);
        g2d.setPaint(black);
        g2d.fillRect(0, 0, imageSide, imageSide);
        g2d.setPaint(new Color(255, 255, 255));
        g2d.fillRect(squareStart, squareStart, squareSide, squareSide);

        assertDoesNotThrow(() -> {
            FillInstrument fillInstrument = new FillInstrument(new ParametersParser(), null);
            fillInstrument.draw(bufferedImage, imageSide / 2, imageSide / 2);
            fillInstrument.setColor(black);
        });

        for (int x = 0; x < imageSide; ++x) {
            for (int y = 0; y < imageSide; ++y) {
                assertEquals(black.getRGB(), bufferedImage.getRGB(x, y));
            }
        }
    }
}
