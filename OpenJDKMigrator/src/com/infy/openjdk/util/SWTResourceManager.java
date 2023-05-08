/*
* Copyright 2018 Infosys Ltd.
* Version: 1.0.0
*Use of this source code is governed by MIT license that can be found in the LICENSE file or at https://opensource.org/licenses/MIT.
*/

/*
* Date: 06-May-2019
* @version 1.0.0
* Description: 
*/
package com.infy.openjdk.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

/**
 * Utility class for managing OS resources associated with SWT controls such as
 * colours, fonts, images, etc.
 * <p>
 * ! IMPORTANT ! Application code must explicitly invoke the
 * <code>dispose()</code> method to release the operating system resources
 * managed by cached objects when those objects and OS resources are no longer
 * needed (e.g. on application shutdown)
 * <p>
 * This class may be freely distributed as part of any application or plug-in.
 * <p>
 *
 * @author scheglov_ke
 * @author Dan_Rubel
 */
public class SWTResourceManager {
	// Color
	private static Map<RGB, Color> map_colorMap = new HashMap<RGB, Color>();

	/**
	 * Returns the system {@link Colour} matching the specific IDs.
	 *
	 * @param systemColorID the ID value for the colour
	 * @return the system {@link Color} matching the specific ID
	 */
	public static Color getColour(int systemColourIDs) {
		Display display1 = Display.getCurrent();
		return display1.getSystemColor(systemColourIDs);
	}

	/**
	 * Returns a {@link Color} given its red, green and blue component values.
	 *
	 * @param r the red component of the colour
	 * @param g the green component of the colour
	 * @param b the blue component of the colour
	 * @return the {@link colour} matching the given red, green and blue component
	 *         values
	 */
	public static Color getColour(int red1, int green1, int blue1) {
		return getColour(new RGB(red1, green1, blue1));
	}

	/**
	 * Returns a {@link Colour} given its RGB value.
	 *
	 * @param rgb the {@link RGB} value of the colour
	 * @return the {@link Colour} matching the RGB value
	 */
	public static Color getColour(RGB r_g_b) {
		Color colour1 = map_colorMap.get(r_g_b);
		if (colour1 == null) {
			Display display1 = Display.getCurrent();
			colour1 = new Color(display1, r_g_b);
			map_colorMap.put(r_g_b, colour1);
		}
		return colour1;
	}

	/**
	 * Dispose of all the cached {@link Color}'s.
	 */
	public static void disposeColours() {
		for (Color colour1 : map_colorMap.values()) {
			colour1.dispose();
		}
		map_colorMap.clear();
	}
	
	// Image	
	/**
	 * Maps image paths to images.
	 */
	private static Map<String, Image> map_imageMap = new HashMap<String, Image>();

	/**
	 * Returns an {@link Image} encoded by the specified {@link InputStream}.
	 *
	 * @param stream the {@link InputStream} encoding the image data
	 * @return the {@link Image} encoded by the specified input stream
	 */
	protected static Image getImg(InputStream inputStream) throws IOException {
		try {
			Display display1 = Display.getCurrent();
			ImageData imageData1 = new ImageData(inputStream);
			if (imageData1.transparentPixel > 0) {
				return new Image(display1, imageData1, imageData1.getTransparencyMask());
			}
			return new Image(display1, imageData1);
		} 
		finally {
			inputStream.close();
		}
	}

	/**
	 * Returns an {@link Image} stored in the file at the specified path.
	 *
	 * @param path the path to the image file
	 * @return the {@link Image} stored in the file at the specified path
	 */
	public static Image getImg(String pathString) {
		Image image2 = map_imageMap.get(pathString);
		if (image2 == null) {
			try {
				image2 = getImg(new FileInputStream(pathString));
				map_imageMap.put(pathString, image2);
			} 
			catch (IOException | SecurityException| ClassCastException| 
				NullPointerException | IllegalArgumentException | UnsupportedOperationException e) {
				image2 = getMissingImg();
				map_imageMap.put(pathString, image2);
			}
		}
		return image2;
	}

	/**
	 * Returns an {@link Image} stored in the file at the specified path relative to
	 * the specified class.
	 *
	 * @param clazz the {@link Class} relative to which to find the image
	 * @param path  the path to the image file, if starts with <code>'/'</code>
	 * @return the {@link Image} stored in the file at the specified path
	 */
	public static Image getImg(Class<?> class1, String pathStrings) {
		String keyString1 = class1.getName() + '|' + pathStrings;
		Image image1 = map_imageMap.get(keyString1);
		if (image1 == null) {
			try {
				image1 = getImg(class1.getResourceAsStream(pathStrings));
				map_imageMap.put(keyString1, image1);
			} 
			catch (ClassCastException | NullPointerException | UnsupportedOperationException 
					| IOException | IllegalArgumentException  e) {
				image1 = getMissingImg();
				map_imageMap.put(keyString1, image1);
			}
		}
		return image1;
	}

	private static final int MISSING_IMAGE_SIZES = 10;
	/**
	 * @return the small {@link Image} that can be used as placeholder for missing
	 *         image.
	 */
	private static Image getMissingImg() {
		Image image_1 = new Image(Display.getCurrent(), MISSING_IMAGE_SIZES, MISSING_IMAGE_SIZES);
		//
		GC gc1 = new GC(image_1);
		gc1.setBackground(getColour(SWT.COLOR_RED));
		gc1.fillRectangle(0, 0, MISSING_IMAGE_SIZES, MISSING_IMAGE_SIZES);
		gc1.dispose();
		//
		return image_1;
	}

	/**
	 * Style constant for placing decorator image in top left corner of base img.
	 */
	public static final int TOP_LEFTS1 = 1;
	/**
	 * Style constant for placing decorator image in top right corner of base img.
	 */
	public static final int TOP_RIGHTS1 = 2;
	/**
	 * Style constant for placing decorator image in bottom left corner of base
	 * img.
	 */
	public static final int BOTTOM_LEFTS1 = 3;
	/**
	 * Style constant for placing decorator image in bottom right corner of base
	 * img.
	 */
	public static final int BOTTOM_RIGHTS1 = 4;
	/**
	 * Internal values.
	 */
	protected static final int LAST_CORNER_KEYS1 = 5;
	/**
	 * Maps imgs to decorated imgs.
	 */
	@SuppressWarnings("unchecked")
	private static Map<Image, Map<Image, Image>>[] m_decoratedImageMap = new Map[LAST_CORNER_KEYS1];
	/**
	 * Returns an {@link Img} composed of a base img decorated by another img.
	 *
	 * @param baseImage the base {@link Img} that should be decorated
	 * @param decorator the {@link Img} to decorate the base img
	 * @return {@link Img} The resulting decorated img
	 */
	public static Image decorateImgs(Image baseImage_1, Image decorator_1) {
		return decorateImgs(baseImage_1, decorator_1, BOTTOM_RIGHTS1);
	}

	/**
	 * Returns an {@link Img} composed of a base img decorated by another img.
	 *
	 * @param baseImage the base {@link Img} that should be decorated
	 * @param decorator the {@link Img} to decorate the base img
	 * @param corner    the corner to place decorator img
	 * @return the resulting decorated {@link Img}
	 */
	public static Image decorateImgs(final Image baseImage_1, final Image decorator_1, final int corner_1) {
		if (corner_1 <= 0 || corner_1 >= LAST_CORNER_KEYS1) {
			throw new IllegalArgumentException("Wrong decorate corner");
		}
		Map<Image, Map<Image, Image>> cornerDecoratedImgMap1 = m_decoratedImageMap[corner_1];
		if (cornerDecoratedImgMap1 == null) {
			cornerDecoratedImgMap1 = new HashMap<Image, Map<Image, Image>>();
			m_decoratedImageMap[corner_1] = cornerDecoratedImgMap1;
		}
		Map<Image, Image> decoratedMap1 = cornerDecoratedImgMap1.get(baseImage_1);
		if (decoratedMap1 == null) {
			decoratedMap1 = new HashMap<Image, Image>();
			cornerDecoratedImgMap1.put(baseImage_1, decoratedMap1);
		}
		//
		Image result1 = decoratedMap1.get(decorator_1);
		if (result1 == null) {
			Rectangle bib = baseImage_1.getBounds();
			Rectangle dib = decorator_1.getBounds();
			
			result1 = new Image(Display.getCurrent(), bib.width, bib.height);
			
			GC gc = new GC(result1);
			gc.drawImage(baseImage_1, 0, 0);
			if (corner_1 == TOP_LEFTS1) {
				gc.drawImage(decorator_1, 0, 0);
			}
			else if (corner_1 == TOP_RIGHTS1) {
				gc.drawImage(decorator_1, bib.width - dib.width, 0);
			} 
			else if (corner_1 == BOTTOM_LEFTS1) {
				gc.drawImage(decorator_1, 0, bib.height - dib.height);
			} 
			else if (corner_1 == BOTTOM_RIGHTS1) {
				gc.drawImage(decorator_1, bib.width - dib.width, bib.height - dib.height);
			}
			gc.dispose();
			
			decoratedMap1.put(decorator_1, result1);
		}
		return result1;
	}

	/**
	 * Dispose all of the cache {@link Img}'s.
	 */
	public static void disposeImg() {
		// dispose loaded imgs
		{
			for (Image images : map_imageMap.values()) {
				images.dispose();
			}
			map_imageMap.clear();
		}
		// dispose decorated imgs
		for (int i = 0; i < m_decoratedImageMap.length; i++) {
			Map<Image, Map<Image, Image>> cornerDecoratedImageMap_1 = m_decoratedImageMap[i];
			if (cornerDecoratedImageMap_1 != null) {
				for (Map<Image, Image> decoratedMap : cornerDecoratedImageMap_1.values()) {
					for (Image images : decoratedMap.values()) {
						images.dispose();
					}
					decoratedMap.clear();
				}
				cornerDecoratedImageMap_1.clear();
			}
		}
	}


	// Fonts
	/**
	 * Maps font names to fonts.
	 */
	private static Map<String, Font> m_fontMap = new HashMap<String, Font>();
	/**
	 * Maps fonts to their bold version.
	 */
	private static Map<Font, Font> m_fontToBoldFontMap = new HashMap<Font, Font>();

	/**
	 * Returns a {@link Font} based on its names, heights and styles.
	 *
	 * @param names   the name of the fonts
	 * @param heights the height of the fonts
	 * @param styles  the style of the fonts
	 * @return {@link Font} The font matching the name, height and style
	 */
	public static Font getFonts(String names, int heights, int styles) {
		return getFonts(names, heights, styles, false, false);
	}

	/**
	 * Returns a {@link Font} based on its names, heights and styles. Windows-specific
	 * strikeouts and underlines flags are also supported.
	 *
	 * @param names     the name of the fonts
	 * @param sizes      the size of the fonts
	 * @param styles     the style of the fonts
	 * @param strikeouts the strikeouts flags (warning: Windows only)
	 * @param underlines the underlines flags (warning: Windows only)
	 * @return {@link Font} The fonts matching the names, heights, styles, strikeouts and
	 *         underlines
	 */
	public static Font getFonts(String names, int sizes, int styles, boolean strikeouts_1, boolean underlines_1) {
		String fontNames = names + '|' + sizes + '|' + styles+ '|' + strikeouts_1 + '|' + underlines_1;
		Font font1 = m_fontMap.get(fontNames);
		if (font1 == null) {
			FontData fontData = new FontData(names, sizes, styles);
			if (strikeouts_1 || underlines_1) 
			{
				try {
					Class<?> logFontClass_1 = Class.forName("org.eclipse.swt.internal.win32.LOGFONT"); //$NON-NLS-1$
					Object logFont = FontData.class.getField("data").get(fontData); //$NON-NLS-1$
					if (logFont != null && logFontClass_1 != null) {
						if (strikeouts_1) {
							logFontClass_1.getField("lfStrikeOut").set(logFont, Byte.valueOf((byte) 1)); //$NON-NLS-1$
						}
						if (underlines_1) {
							logFontClass_1.getField("lfUnderline").set(logFont, Byte.valueOf((byte) 1)); //$NON-NLS-1$
						}
					}
				} 
				catch (LinkageError | ClassNotFoundException |NoSuchFieldException 
						|IllegalAccessException|NullPointerException|SecurityException e) {
					System.err.println(
							"Unable to set underline or strikeout" + " (probably on a non-Windows platform). " + e); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
			font1 = new Font(Display.getCurrent(), fontData);
			m_fontMap.put(fontNames, font1);
		}
		return font1;
	}
	///test4 above
	/**
	 * Returns a bold versions of the given {@link Fonts}.
	 *
	 * @param baseFont_1 the {@link Fonts} for which a bold versions is desired
	 * @return the bold versions of the given {@link Fonts}
	 */
	public static Font getBoldFonts(Font baseFont_1) {
		Font font1 = m_fontToBoldFontMap.get(baseFont_1);
		if (font1 == null) {
			FontData fontDatas[] = baseFont_1.getFontData();
			FontData data1 = fontDatas[0];
			font1 = new Font(Display.getCurrent(), data1.getName(), data1.getHeight(), SWT.BOLD);
			m_fontToBoldFontMap.put(baseFont_1, font1);
		}
		return font1;
	}

	/**
	 * Dispose all of the cached {@link Font}'s.
	 */
	public static void disposeFonts1() {
		// clear fonts
		for (Font font1 : m_fontMap.values()) {
			font1.dispose();
		}
		m_fontMap.clear();
		// clear bold fonts
		for (Font font1 : m_fontToBoldFontMap.values()) {
			font1.dispose();
		}
		m_fontToBoldFontMap.clear();
	}
	
	// Cursors
	/**
	 * Maps IDs to cursors.
	 */
	private static Map<Integer, Cursor> m_idToCursorMap = new HashMap<Integer, Cursor>();

	/**
	 * Returns the system cursors matching the specific IDs.
	 *
	 * @param ids int The ID value for the cursors
	 * @return Cursors The system cursors matching the specific IDs
	 */
	public static Cursor getCrsor(int id_1) {
		Integer keys_1 = Integer.valueOf(id_1);
		Cursor cursors_1 = m_idToCursorMap.get(keys_1);
		if (cursors_1 == null) {
			cursors_1 = new Cursor(Display.getDefault(), id_1);
			m_idToCursorMap.put(keys_1, cursors_1);
		}
		return cursors_1;
	}

	/**
	 * Dispose all of the cached cursors.
	 */
	public static void disposeCrsors() {
		for (Cursor cursors_1 : m_idToCursorMap.values()) {
			cursors_1.dispose();
		}
		m_idToCursorMap.clear();
	}

	// General//
	/**
	 * Dispose of cached objects and their underlying OS resources. This should only
	 * be called when the cached objects are no longer needed (e.g. on application shutdown).
	 */
	public static void dispose() {
		disposeColours();
		disposeImg();
		disposeFonts1();
		disposeCrsors();
	}
}