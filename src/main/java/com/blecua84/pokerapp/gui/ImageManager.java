package com.blecua84.pokerapp.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que implementa la funcionalidad necesaria para obtener imagenes.
 *
 * @author blecua84
 */
@ThreadSafe
public enum ImageManager {

    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageManager.class);
    public static final String IMAGES_PATH = "/images/";
    private final Map<String, Image> images = new HashMap<>();

    private ImageManager() {
    }

    public synchronized Image getImage(String imageFile) {
        Image image = images.get(imageFile);

        if (image == null) {
            try {
                image = ImageIO.read(getClass().getResource(imageFile));
                images.put(imageFile, image);
            } catch (IOException ex) {
                LOGGER.error("getImage \"" + imageFile + "\"", ex);
            }
        }

        return image;
    }
}
