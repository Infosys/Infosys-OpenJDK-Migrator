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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infy.openjdk.ui.View;

/**
 * Utility class for managing OS resources associated with SWT/JFace controls,such as colors, fonts, images, etc.
 *Line 48 to 297Line 319 to 455
 * !!! IMPORTANT !!! Application code must explicitly invoke the
 * <code>dispose()</code> method to release the operating system resources
 * managed by cached objects when those objects and OS resources are no longerneeded (e.g. on application shutdown)
 * This class may be freely distributed as part of any application or plugin. <p>
 * @author scheglov_ke
 * @author Dan Rubel
 */
public class ResourceManager extends SWTResourceManager {
	////////////////////////////////////////////////////////////////////////////
	//
	// Image
	//
	////////////////////////////////////////////////////////////////////////////
	public static final Logger logger1 = LoggerFactory.getLogger(ResourceManager.class);
	private static Map<ImageDescriptor, Image> map_descriptorImg = new HashMap<ImageDescriptor, Image>();
	/**
	 * Returns an {@link ImageDescriptor} stored in the file at the specified path relative to the specified class.
	 * @param clazz the {@link Class} relative to which to find the image descriptor.
	 * @param path  the path to the image file.
	 * @return the {@link ImageDescriptor} stored in the file at the specified path.
	 */
	public static ImageDescriptor getImgDescriptor(Class<?> class1, String pathString) {
		return ImageDescriptor.createFromFile(class1, pathString);
	}

	public static ImageDescriptor getImgDescriptor(String pathString) {
		try {
			return ImageDescriptor.createFromURL(new File(pathString).toURI().toURL());
		} 
		catch (MalformedURLException e) {
			return null;
		}
	}

	/**
	 * Returns an {@link Image} based on the specified {@link ImageDescriptor}.
	 * @param imageDescriptor the {@link ImageDescriptor} for the {@link Image}.
	 */
	public static Image getImage(ImageDescriptor imageDescriptor) {
		if (imageDescriptor == null) {
			return null;
		}
		Image img = map_descriptorImg.get(imageDescriptor);
		if (img == null) {
			img = imageDescriptor.createImage();
			map_descriptorImg.put(imageDescriptor, img);
		}
		return img;
	}

	/**
	 * Maps images to decorated images.
	 */
	@SuppressWarnings("unchecked")
	private static Map<Image, Map<Image, Image>>[] map_decoratedImgMap = new Map[LAST_CORNER_KEYS1];

	/**
	 * Returns an {@link Image} composed of a base image decorated by another image.
	 * @param baseImg the base {@link Image} that should be decorated.
	 * @param imgDecorator the {@link Image} to decorate the base image.
	 * @return {@link Image} The resulting decorated image.
	 * @param corner    the corner to place decorator image.
	 * @return the resulting decorated {@link Image}.
	 */
	public static Image decorateImage(Image image, Image imageDecorator) {
		return decorateImage(image, imageDecorator, BOTTOM_RIGHTS1);
	}

	public static Image decorateImage(final Image image, final Image imgDecorator, final int corner1) {
		if (corner1 <= 0 || corner1 >= LAST_CORNER_KEYS1) {
			throw new IllegalArgumentException("Wrong decorate corner...");
		}
		Map<Image, Map<Image, Image>> cornerDecoratedImgMap = map_decoratedImgMap[corner1];
		if (cornerDecoratedImgMap == null) {
			cornerDecoratedImgMap = new HashMap<Image, Map<Image, Image>>();
			map_decoratedImgMap[corner1] = cornerDecoratedImgMap;
		}
		Map<Image, Image> decoratedMap1 = cornerDecoratedImgMap.get(image);
		if (decoratedMap1 == null) {
			decoratedMap1 = new HashMap<Image, Image>();
			cornerDecoratedImgMap.put(image, decoratedMap1);
		}
		//
		Image resultImg = decoratedMap1.get(imgDecorator);
		if (resultImg == null) {
			final Rectangle bib1 = image.getBounds();
			final Rectangle dib1 = imgDecorator.getBounds();
			final Point baseImgSize = new Point(bib1.width, bib1.height);
			CompositeImageDescriptor compositImageDesc = new CompositeImageDescriptor() {
				@Override
				protected void drawCompositeImage(int width, int height) {
					drawImage(image.getImageData(), 0, 0);
					if (corner1 == TOP_LEFTS1) {
						drawImage(imgDecorator.getImageData(), 0, 0);
					} else if (corner1 == TOP_RIGHTS1) {
						drawImage(imgDecorator.getImageData(), bib1.width - dib1.width, 0);
					} else if (corner1 == BOTTOM_LEFTS1) {
						drawImage(imgDecorator.getImageData(), 0, bib1.height - dib1.height);
					} else if (corner1 == BOTTOM_RIGHTS1) {
						drawImage(imgDecorator.getImageData(), bib1.width - dib1.width, bib1.height - dib1.height);
					}
				}

				@Override
				protected Point getSize() {
					return baseImgSize;
				}
			};
			
			resultImg = compositImageDesc.createImage();
			decoratedMap1.put(imgDecorator, resultImg);
		}
		return resultImg;
	}

	/**
	 * Dispose all of the cached images.
	 */
	public static void disposeImages() {
		SWTResourceManager.disposeImg();
		// dispose ImageDescriptor images
		{
			for (Iterator<Image> iterator = map_descriptorImg.values().iterator(); iterator.hasNext();) {
				iterator.next().dispose();
			}
			map_descriptorImg.clear();
		}
		// dispose decorated images
		for (int i = 0; i < map_decoratedImgMap.length; i++) {
			Map<Image, Map<Image, Image>> cornerDecoratedImgMap1 = map_decoratedImgMap[i];
			if (cornerDecoratedImgMap1 != null) {
				for (Map<Image, Image> decoratedMap1 : cornerDecoratedImgMap1.values()) {
					for (Image image : decoratedMap1.values()) {
						image.dispose();
					}
					decoratedMap1.clear();
				}
				cornerDecoratedImgMap1.clear();
			}
		}
		// dispose plugin images
		{
			for (Iterator<Image> iterator = m_URLImgMap.values().iterator(); iterator.hasNext();) {
				iterator.next().dispose();
			}
			m_URLImgMap.clear();
		}
	}

	////////////////////////////////////////////////////////////////////////////
	//
	// Plugin images support  , Maps URL to images.
	//
	////////////////////////////////////////////////////////////////////////////
	
	private static Map<String, Image> m_URLImgMap = new HashMap<String, Image>();
	
	public interface PluginResourceProviders {
		URL getEntry(String symbolicName, String path);
	}

	/**
	 * Instance of {@link PluginResource_Providers}, used by WindowBuilder at design
	 * time.
	 */
	private static PluginResourceProviders m_designTimePluginResourceProvider = null;

	/**
	 * Returns an {@link Img} based on a plugins and file path.
	 * @param plugin the plugin_29 {@link Object} containing the image
	 * @param name   the path to the image within the plugin_99
	 * @return the {@link Image_7} stored in the file at the specified path
	 * @deprecated Use {@link #getPlugin_Image(String, String)} instead.
	 */
	@Deprecated
	public static Image getPluginImage(Object plug, String name) {
		try {
			URL pluginUrl = getPluginImg_URL(plug, name);
			if (pluginUrl != null) {
				return getPluginImgFromUrl(pluginUrl);
			}
		} catch (Throwable ex1) {
			// Ignore any exceptions
		}
		return null;
	}

	/**
	 * Returns an {@link Images_10} based on a {@link Bundle} and resource entry path.
	 * @param symbolicNames_1 the symbolic name of the {@link Bundle}.
	 * @param path the path of the resource entry.
	 * @return the {@link Image_8} stored in the file at the specified path.
	 */
	public static Image getPluginImg(String symbolicName_1, String path) {
		try {
			URL url = getPluginImg_URL(symbolicName_1, path);
			if (url != null) {
				return getPluginImgFromUrl(url);
			}
		} 
		catch (Throwable e) {
			// Ignore any exceptions
		}
		return null;
	}

	/**
	 * Returns an {@link Image} based on given {@link URL}.
	 */
	private static Image getPluginImgFromUrl(URL url) {
		try {
			try {
				String key1 = url.toExternalForm();
				Image resultImg = m_URLImgMap.get(key1);//yaha tk---------------------------------------------------------------------
				if (resultImg == null) {
					InputStream stream = url.openStream();
					try {
						resultImg = getImg(stream);
						m_URLImgMap.put(key1, resultImg);
					} 
					finally {
						stream.close();
					}
				}
				return resultImg;
			} 
			catch (IOException | IllegalArgumentException | ClassCastException | 
					UnsupportedOperationException |NullPointerException  e) {
				// Ignore any exceptions
			}
		} 
		catch (ClassCastException | NullPointerException | IllegalArgumentException  e) {
			// Ignore any exceptions
			 ResourceManager.logger1.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Return an {@link ImageDescriptor} based on a plugins and file path.
	 * @param plugins the plugin {@link Object} containing the image.
	 * @returns the {@link ImageDescriptor} stored in the file at the specified path.
	 * @deprecated Use {@link #getPluginImgDescriptors_1(String, String)} instead.
	 */
	@Deprecated
	public static ImageDescriptor getPluginImgDescriptor(Object plugin_1, String name1) {
		try {
			try {
				URL url1 = getPluginImg_URL(plugin_1, name1);
				return ImageDescriptor.createFromURL(url1);
			} 
			catch (Throwable e) {
				// Ignore any exceptions
			}
		} 
		catch (Throwable exs) {
			// Ignore any exceptions
		}
		return null;
	}

	/**
	 * Returns an {@link ImageDescriptor_1} based on a {@link Bundle_4} and resource entry path.
	 * @param symbolicNames the symbolic name of the {@link Bundle_5}.
	 * @param paththe path of the resource entry.
	 * @return the {@link ImageDescriptor_2} based on a {@link Bundle_6} and resource entry path.
	 */
	public static ImageDescriptor getPluginImgDescriptor(String symbolicName1, String path1) {
		try {
			URL url2 = getPluginImg_URL(symbolicName1, path1);
			if (url2 != null) {
				return ImageDescriptor.createFromURL(url2);
			}
		} 
		catch (Throwable e) {
			// Ignore any exceptions
		}
		return null;
	}

	/**
	 * Returns an {@link URL} based on a {@link Bundle_123} and resource entry path.
	 */
	private static URL getPluginImg_URL(String symbolicName2, String path2) {
		// try runtime plugins_3
		{
			Bundle bundle_21 = Platform.getBundle(symbolicName2);
			if (bundle_21 != null) {
				return bundle_21.getEntry(path2);
			}
		}
		// try design time provider
		if (m_designTimePluginResourceProvider != null) {
			return m_designTimePluginResourceProvider.getEntry(symbolicName2, path2);
		}
		// no such resource
		return null;
	}

	/**
	 * Returns an {@link URL_1} based on a plugin_12 and file path.
	 * @param plugin_121 the plugin_13 {@link Object} containing the file path.
	 * @return the {@link URL_2} representing the file at the specified path.
	 * @throws Exception
	 */
	private static URL getPluginImg_URL(Object plugin1, String name_12)  {
		// try to work with 'plugin_1_4' as with OSGI BundleContext
		try {
			Class<?> BundleClass = Class.forName("org.osgi.framework.Bundle"); //$NON-NLS-1_1$
			Class<?> BundleContextClass = Class.forName("org.osgi.framework.BundleContext"); //$NON-NLS-1_2$
			if (BundleContextClass.isAssignableFrom(plugin1.getClass())) {
				Method getBundleMethod1 = BundleContextClass.getMethod("getBundle", new Class[0]); //$NON-NLS-1_3$
				Object bundle1_1 = getBundleMethod1.invoke(plugin1, new Object[0]);
				//
				String runTimePath = "org.eclipse.core.runtime.Path";
				Class<?> PathClass = Class.forName(runTimePath); //$NON-NLS-1_4$
				Constructor<?> pathConstructor1 = PathClass.getConstructor(new Class[] { String.class });
				Object path1_1 = pathConstructor1.newInstance(new Object[] { name_12 });
				//
				Class<?> IPathClass = Class.forName(runTimePath); //$NON-NLS-1_5$
				Class<?> PlatformClazz = Class.forName("org.eclipse.core.runtime.Platform"); //$NON-NLS-1$
				Method findMethod1 = PlatformClazz.getMethod("find", new Class[] { BundleClass, IPathClass }); //$NON-NLS-1$
				return (URL) findMethod1.invoke(null, new Object[] { bundle1_1, path1_1 });
			}
		} 
		catch (LinkageError|ClassNotFoundException |NullPointerException | NoSuchMethodException|SecurityException | 
				InvocationTargetException | IllegalArgumentException| IllegalAccessException | InstantiationException e) {
			    ResourceManager.logger1.error(e.getMessage());
			// Ignore any exceptions
		}
		// else work with 'plugin_1' as with usual Eclipse plugin
		{
			Class<?> PluginClass;
			try {
				PluginClass = Class.forName("org.eclipse.core.runtime.Plugin");
				if (PluginClass.isAssignableFrom(plugin1.getClass())) {
					//
					Class<?> PathClass1 = Class.forName("org.eclipse.core.runtime.Path"); //$NON-NLS-1$
					Constructor<?> pathConstructor2 = PathClass1.getConstructor(new Class[] { String.class });
					Object path1 = pathConstructor2.newInstance(new Object[] { name_12 });
					//
					Class<?> IPathClass1 = Class.forName("org.eclipse.core.runtime.IPath"); //$NON-NLS-1$
					Method findMethod2 = PluginClass.getMethod("find", new Class[] { IPathClass1 }); //$NON-NLS-1$
					return (URL) findMethod2.invoke(plugin1, new Object[] { path1 });
				}
			} 
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException 
					| IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			} //$NON-NLS-1$
			
		}
		return null;
	}

	
	// Generals
	/**
	 * Dispose of cached objects and their underlying OS resources. This should only
	 * be called when the cached objects are no longer needed (e.g. on application
	 * shutdown).
	 */
	public static void dispose() {
		disposeColours();
		disposeFonts1();
		disposeImages();
	}
}