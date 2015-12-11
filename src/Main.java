import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public class Main {

	private static final int RED_POS = 0;
	private static final int GREEN_POS = 1;
	private static final int BLUE_POS = 2;

	private static final int RED_THRES = 200;

	private static final int HUE_POS = 0;
	private static final int SATURATION_POS = 1;

	private static final float TARGET_HUE = 0;
	private static final float TARGET_SATURATION = .8f;

	private static final float HUE_THRES = .2f;
	private static final float SATURATION_THRES = .2f;

	private static final String FILE_PATH = "/Users/loptevjr/Desktop/Desktop Junk/jellybackground.jpeg";
	private static final String SAVE_PATH = "/Users/loptevjr/Desktop/Desktop Junk/newnewjellybackground.jpeg";

	public static void main(String[] args) {
		BufferedImage loaded = loadImage(FILE_PATH);
		BufferedImage processed = processImage(loaded);
		saveImage(SAVE_PATH, processed);
	}

	public static BufferedImage processImage(BufferedImage img) {
		BufferedImage filtered = filterImage(img);
		return filtered;

	}

	private static List<Blob> floodFill(BufferedImage img) {
		boolean[][] checked = new boolean[img.getWidth()][img.getHeight()];
		List<Blob> blobList = new LinkedList<>();
		for (int x = 0; x<img.getWidth(); x++) {
			for (int y = 0; y<img.getHeight(); y++) {
				if (checked[x][y] == false) {
					if (check(x, y, img)) {

					}
				}
			}
		}
		return blobList;
	}

	private static boolean check(int x, int y, BufferedImage img) {
		return true;
	}

	private void createBlobMap(int x, int y, boolean[][] checked, BufferedImage img, List<Point> pointList) {
		if (x < img.getWidth() && y < img.getHeight() && x > 0 && y > 0) {
			if (checked[x][y] == false) {
				checked[x][y] = true;
				if (check(x, y, img)) {
					pointList.add(new Point(x, y));
					createBlobMap(x - 1, y, checked, img, pointList);
					createBlobMap(x + 1, y, checked, img, pointList);
					createBlobMap(x, y - 1, checked, img, pointList);
					createBlobMap(x, y + 1, checked, img, pointList);
				}
			}
		}
	}

	private static BufferedImage filterImage(BufferedImage img) {
		BufferedImage filtered = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		for (int x = 0; x<img.getWidth(); x++) {
			for (int y = 0; y<img.getHeight(); y++) {
				int[] rgb = getRGBFromPixel(img.getRGB(x, y));
				float[] hsl = rgbToHSL(rgb);
				if (Math.abs(hsl[HUE_POS] - TARGET_HUE) < HUE_THRES
						&& Math.abs(hsl[SATURATION_POS] - TARGET_SATURATION) < SATURATION_THRES) {
					filtered.setRGB(x, y, 0xFFFFFFFF);
				}
				else {
					filtered.setRGB(x, y, 0xFF000000);
				}
			}
		}
		return filtered;
	}

	public static float[] rgbToHSL(int[] rgb) {
		return Color.RGBtoHSB(rgb[RED_POS], rgb[GREEN_POS], rgb[BLUE_POS], null);
	}

	public static int[] getRGBFromPixel(int pixel) {
		int b = pixel & 0xFF;
		int g = (pixel>>8) & 0xFF;
		int r = (pixel>>16) & 0xFF;
		return new int[]{r, g, b};
	}

	public int[][][] generateRGBMap(BufferedImage img) {
		int[][][] map = new int[img.getWidth()][img.getHeight()][3];
		for (int x = 0; x<img.getWidth(); x++) {
			for (int y = 0; y<img.getWidth(); y++) {
				int pixel = img.getRGB(x, y);
				int b = pixel & 0xFF;
				int g = (pixel >> 8) & 0xFF;
				int r = (pixel >> 16) & 0xFF;
				map[x][y][0] = r;
				map[x][y][1] = g;
				map[x][y][2] = b;
			}
		}
		return map;
	}

	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void saveImage(String path, BufferedImage img) {
		try {
			ImageIO.write(img, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
