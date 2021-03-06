package br.com.diegomunhoz.core;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class ImageManager {

    static private ImageManager instance;
    private HashMap<String, BufferedImage> images;

    private ImageManager() {
        images = new HashMap<String, BufferedImage>();
    }

    static public ImageManager getInstance() {
        if (instance == null) {
            instance = new ImageManager();
        }
        return instance;
    }

    public BufferedImage loadImage(String fileName) throws IOException {
        URL url = getClass().getResource("/" + fileName);
        if (url == null) {
            throw new RuntimeException("A imagem /" + fileName
                    + " não foi encontrada.");
        } else {
            String path = url.getPath();
            if (images.containsKey(path)) {
                return images.get(path);
            } else {
                BufferedImage img = ImageIO.read(url);
                images.put(path, img);
                return img;
            }
        }
    }

    public BufferedImage loadImage(String fileName, int x, int y, int w, int h)
            throws IOException {
        BufferedImage sheet = loadImage(fileName);
        BufferedImage img = sheet.getSubimage(x, y, w, h);
        return img;
    }

    public SpriteAnimation loadSpriteAnimation(String fileName, int qtdFrames) throws
            IOException {
        BufferedImage sheet = loadImage(fileName);
        if (sheet.getWidth() % qtdFrames != 0) {
            throw new RuntimeException("A imagem /" + fileName
                    + " não possui " + qtdFrames + " sprites de mesmo tamanho.");
        } else {
            SpriteAnimation anim = new SpriteAnimation();
            int w = sheet.getWidth() / qtdFrames;
            int h = sheet.getHeight();
            for (int i = 0; i < qtdFrames; i++) {
                anim.addImage(sheet.getSubimage(i * w, 0, w, h));
            }
            return anim;
        }
    }
}