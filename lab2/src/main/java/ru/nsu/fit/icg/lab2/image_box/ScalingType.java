package ru.nsu.fit.icg.lab2.image_box;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public enum ScalingType {
    ORIGINAL(null, "Оригинал"),
    BICUBIC(AffineTransformOp.TYPE_BICUBIC, "Бикубическая интерполяция"),
    BILINEAR(AffineTransformOp.TYPE_BILINEAR, "Билинейная интерполяция"),
    NEAREST_NEIGHBOR(AffineTransformOp.TYPE_NEAREST_NEIGHBOR,
            "Интерполяция методом ближайшего соседа");

    private final Integer interpolationType;
    private final String name;

    ScalingType(Integer interpolationType, String name) {
        this.interpolationType = interpolationType;
        this.name = name;
    }

    public WritableImage scaleImage(WritableImage image, double scale) {
        if (null != interpolationType) {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            AffineTransform affineScale = AffineTransform.getScaleInstance(scale, scale);
            AffineTransformOp transform = new AffineTransformOp(affineScale, interpolationType);
            BufferedImage scaledBufferedImage = transform.filter(bufferedImage, null);
            return SwingFXUtils.toFXImage(scaledBufferedImage, null);
        } else {
            return null;
        }
    }

    public String getName() {
        return name;
    }

}
