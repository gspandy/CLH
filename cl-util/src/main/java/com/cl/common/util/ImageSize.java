//package com.cl.common.util;
//
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//import org.apache.log4j.Logger;
//
//import javax.imageio.ImageIO;
//import javax.imageio.ImageReader;
//import javax.imageio.stream.ImageInputStream;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Iterator;
//
//public class ImageSize {
//	private static Logger logger = Logger.getLogger(ImageSize.class);
//	
//	// private String originalPath;
//	// private String thumbnailPath;
//	// private int width;
//	// private int height;
//	// private char mode;
//	
//	private String sPicWidth = "80";
//	private String sPicHeight = "80";
//	
//	
//	public static void drawImage(String originalPath, String thumbnailPath, int width, int height,
//                                 char mode) throws Exception {
//		File f = new File(originalPath);
//		writeAsnImage(thumbnailPath, f, width, height, (float) 0.7);
//		/*
//		Image originalImage = ImageIO.read(f);
//		int towidth = width;
//		int toheight = height;
//		int ow = originalImage.getWidth(null);
//		int oh = originalImage.getHeight(null);
//		switch(mode)
//		{
//			case '#':// 指定高宽缩放（可能变形）
//				break;
//			case 'W':// 指定宽，高按比例
//				toheight = oh * width / ow;
//				break;
//			case 'H':// 指定高，宽按比例
//				towidth = ow * height / oh;
//				break;
//			case '*':// 按比例，排除超高或超宽的情况
//				towidth = ow * height / oh;
//				if (towidth > width)
//				{
//					towidth = width;
//					toheight = oh * width / ow;
//				}
//				break;
//		}
//		BufferedImage image = new BufferedImage(width, height,
//				BufferedImage.TYPE_INT_RGB);
//		Graphics g = image.getGraphics();
//		int x = (width - towidth) / 2;
//		int y = (height - toheight) / 2;
//		g.fillRect(0, 0, width, height);
//		g.drawImage(originalImage, x, y, towidth, toheight, null);
//		ImageIO.write(image, "jpg", new File(thumbnailPath));
//		*/
//	}
//	
//	/**
//	 * 上传图片
//	 * @param originalPic
//	 * @return
//	 * @throws Exception
//	 */
//	public static String drawOriginalPic(File originalPic, String serverPath) throws Exception {
//		if (getFormatName(originalPic) == null) {
//			return drawDefaultImage()[0];
//		}
//		String picName = System.currentTimeMillis() + ".jpg";
////		String virtualPath = (String) PropertyFactory.defaultPropertyMap.get("asn.product.picPath");
//		String virtualPath = "/pic/product";
//		String realPath = serverPath + File.separator + virtualPath + "/" + picName;
//		
//		FileOutputStream fos = null;
//		FileInputStream fis = null;
//		try {
//			fos = new FileOutputStream(realPath);
//			fis = new FileInputStream(originalPic);
//			byte[] buffer = new byte[10 * 1024];
//			int len = 0;
//			while ((len = fis.read(buffer)) > 0) {
//				fos.write(buffer, 0, len);
//			}
//		} catch (Exception e) {
//			logger.error(e);
//		} finally {
//			try {
//				if (fis != null)
//					fis.close();
//			} catch (Exception e) {
//				logger.error(e);
//			}
//			try {
//				if (fos != null)
//					fos.close();
//			} catch (Exception e) {
//				logger.error(e);
//			}
//		}
//		
//		return virtualPath + "/" + picName;
//	}
//	
//	/**
//	 * 生成小图
//	 * @param originalPic
//	 * @return
//	 * @throws Exception
//	 */
//	public static String drawSmallImage(File originalPic, String serverPath) throws Exception {
//		String picName = System.currentTimeMillis() + ".jpg";
//		String tempdir = System.getProperty("java.io.tmpdir");
//		if (!(tempdir.endsWith("/") || tempdir.endsWith("\\"))) {
//			tempdir = tempdir + System.getProperty("file.separator");
//		}
//		tempdir = tempdir + picName;
//		
//		FileOutputStream fos = null;
//		FileInputStream fis = null;
//		try {
//			fos = new FileOutputStream(tempdir);
//			fis = new FileInputStream(originalPic);
//			byte[] buffer = new byte[10 * 1024];
//			int len = 0;
//			while ((len = fis.read(buffer)) > 0) {
//				fos.write(buffer, 0, len);
//			}
//		} catch (Exception e) {
//			logger.error(e);
//		} finally {
//			try {
//				if (fis != null)
//					fis.close();
//			} catch (Exception e) {
//				logger.error(e);
//			}
//			try {
//				if (fos != null)
//					fos.close();
//			} catch (Exception e) {
//				logger.error(e);
//			}
//		}
//		//String sPicWidth = PropertyFactory.getDefaultProperty().getProperty("asn.product.smallPicWidth");
//		
////		String sPicWidth = (String) PropertyFactory.defaultPropertyMap.get("asn.product.smallPicWidth");
//		String sPicWidth = "80";
//		//String sPicHeight = PropertyFactory.getDefaultProperty().getProperty("asn.product.smallPicHeight");
//		
////		String sPicHeight = (String) PropertyFactory.defaultPropertyMap	.get("asn.product.smallPicHeight");
//		String sPicHeight = "80";
//		int swidth = Integer.valueOf(sPicWidth);
//		int sheight = Integer.valueOf(sPicHeight);
//		//String virtualPath = PropertyFactory.getDefaultProperty().getProperty("asn.product.picPath");
//		
////		String virtualPath = (String) PropertyFactory.defaultPropertyMap.get("asn.product.picPath");
//		String virtualPath = "/pic/product";
//		
//		String realPath = serverPath + File.separator + virtualPath + "/" + picName;
//		
//		ImageSize.drawImage(tempdir, realPath, swidth, sheight, '*');
//		
//		return virtualPath + "/" + picName;
//	}
//	
//	/**
//	 * 生成大图
//	 * @param originalPic
//	 * @return
//	 * @throws Exception
//	 */
//	public static String drawBigImage(File originalPic, String serverPath) throws Exception {
//		String picName = System.currentTimeMillis() + ".jpg";
//		String tempdir = System.getProperty("java.io.tmpdir");
//		if (!(tempdir.endsWith("/") || tempdir.endsWith("\\"))) {
//			tempdir = tempdir + System.getProperty("file.separator");
//		}
//		tempdir = tempdir + picName;
//		
//		//TODO
//		FileOutputStream fos = null;
//		FileInputStream fis = null;
//		try {
//			fos = new FileOutputStream(tempdir);
//			fis = new FileInputStream(originalPic);
//			byte[] buffer = new byte[10 * 1024];
//			int len = 0;
//			while ((len = fis.read(buffer)) > 0) {
//				fos.write(buffer, 0, len);
//			}
//		} catch (Exception e) {
//			logger.error(e);
//		} finally {
//			try {
//				if (fis != null)
//					fis.close();
//			} catch (Exception e) {
//				logger.error(e);
//			}
//			try {
//				if (fos != null)
//					fos.close();
//			} catch (Exception e) {
//				logger.error(e);
//			}
//		}
//		
//		//String bPicWidth = PropertyFactory.getDefaultProperty().getProperty("asn.product.bigPicWidth");
///*
//		String bPicWidth = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.bigPicWidth");
//*/
//		//String bPicHeight = PropertyFactory.getDefaultProperty().getProperty("asn.product.bigPicHeight");
///*
//		String bPicHeight = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.bigPicHeight");
//*/
//		int bwidth = Integer.valueOf(1024);
//		int bheight = Integer.valueOf(1024);
//		//String virtualPath = PropertyFactory.getDefaultProperty().getProperty("asn.product.picPath");
////		String virtualPath = (String) PropertyFactory.defaultPropertyMap.get("asn.product.picPath");
//		String realPath = serverPath + File.separator + ("/pic/product") + "/" + picName;
//		
//		ImageSize.drawImage(tempdir, realPath, bwidth, bheight, '*');
//		
//		return "/pic/product" + "/" + picName;
//	}
//	
//	/**
//	 * 生成jpg图片
//	 * @param originalPic
//	 * @return
//	 * @throws Exception
//	 */
//	public static String[] drawImage(File originalPic, String serverPath) throws Exception {
//		
//		if (getFormatName(originalPic) == null) {
//			return drawDefaultImage();
//		}
//		
//		String[] pathArray = UploadFileUtils.getFilePaths(AppConstantsUtil.getYrdUploadFolder(),
//			AppConstantsUtil.getImageServerUrl(), "uploadfile", "productpic",
//			originalPic.getName(), "small_");
//		String picName = pathArray[0];
//		
//		//String bPicWidth = PropertyFactory.getDefaultProperty().getProperty("asn.product.bigPicWidth");
//		String bPicWidth = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.bigPicWidth");
//		//String bPicHeight = PropertyFactory.getDefaultProperty().getProperty("asn.product.bigPicHeight");
//		String bPicHeight = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.bigPicHeight");
//		int bwidth = Integer.valueOf(bPicWidth);
//		int bheight = Integer.valueOf(bPicHeight);
//		
//		//String sPicWidth = PropertyFactory.getDefaultProperty().getProperty("asn.product.smallPicWidth");
//		String sPicWidth = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.smallPicWidth");
//		//String sPicHeight = PropertyFactory.getDefaultProperty().getProperty("asn.product.smallPicHeight");
//		String sPicHeight = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.smallPicHeight");
//		int swidth = NumberUtil.parseInt(sPicWidth);
//		int sheight = NumberUtil.parseInt(sPicHeight);
//		
//		try {
//			//生成大图
//			//ImageSize.drawImage(tempdir, brealPath, bwidth, bheight, '*');
//			//生成小图
//			ImageSize.drawImage(originalPic.getAbsolutePath(), picName, swidth, sheight, '*');
//		} catch (Exception e) {
//			logger.error(e);
//		}
//		
//		String[] strings = new String[2];
//		strings[0] = pathArray[1];
//		return strings;
//	}
//	
//	/**
//	 * 生成400X400jpg图片
//	 * @param originalPic
//	 * @return
//	 * @throws Exception
//	 */
//	public static String[] drawMiddleImage(File originalPic, String serverPath) throws Exception {
//		
//		if (getFormatName(originalPic) == null) {
//			return drawDefaultImage();
//		}
//
//		String[] pathArray = UploadFileUtils.getFilePaths(AppConstantsUtil.getYrdUploadFolder(),
//			AppConstantsUtil.getImageServerUrl(), "uploadfile", "productpic",
//			originalPic.getName(), "middle_");
//		String picName = pathArray[0];
//		
//		//String bPicWidth = PropertyFactory.getDefaultProperty().getProperty("asn.product.bigPicWidth");
//		String bPicWidth = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.bigPicWidth");
//		//String bPicHeight = PropertyFactory.getDefaultProperty().getProperty("asn.product.bigPicHeight");
//		String bPicHeight = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.bigPicHeight");
//		int bwidth = Integer.valueOf(bPicWidth);
//		int bheight = Integer.valueOf(bPicHeight);
//		
//		//String sPicWidth = PropertyFactory.getDefaultProperty().getProperty("asn.product.smallPicWidth");
//		String sPicWidth = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.middlePicWidth");
//		//String sPicHeight = PropertyFactory.getDefaultProperty().getProperty("asn.product.smallPicHeight");
//		String sPicHeight = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.middlePicHeight");
//		int swidth = NumberUtil.parseInt(sPicWidth);
//		int sheight = NumberUtil.parseInt(sPicHeight);
//		
//		try {
//			//生成大图
//			//ImageSize.drawImage(tempdir, brealPath, bwidth, bheight, '*');
//			//生成小图
//			ImageSize.drawImage(originalPic.getAbsolutePath(), picName, swidth, sheight, '*');
//		} catch (Exception e) {
//			logger.error(e);
//		}
//		
//		String[] strings = new String[2];
//		strings[0] = pathArray[1];
//		return strings;
//	}
//	
//	/**
//	 * ??
//	 * @return
//	 * @throws Exception
//	 */
//	public static String[] drawDefaultImage() throws Exception {
//		//String sPicPath = PropertyFactory.getDefaultProperty().getProperty("asn.product.spicDefaultPath");
//		String sPicPath = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.spicDefaultPath");
//		//String bPicPath = PropertyFactory.getDefaultProperty().getProperty("asn.product.bpicDefaultPath");
//		String bPicPath = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.bpicDefaultPath");
//		String[] strings = new String[2];
//		strings[0] = bPicPath;
//		strings[1] = sPicPath;
//		
//		return strings;
//	}
//	
//	/**
//	 * 上传图片
//	 * @param picfile
//	 * @param picName
//	 * @throws Exception
//	 */
//	public static String[] uploadImage(File picfile, String picName, String serverPath)
//																						throws Exception {
//		//String tempdir = PropertyFactory.getDefaultProperty().getProperty("asn.member.picPath");
//		String tempdir = (String) PropertyFactory.defaultPropertyMap.get("asn.member.picPath");
//		String realpath = serverPath + File.separator + (tempdir);
//		realpath = realpath + "/" + picName;
//		
//		//TODO
//		FileOutputStream fos = null;
//		FileInputStream fis = null;
//		try {
//			fos = new FileOutputStream(realpath);
//			fis = new FileInputStream(picfile);
//			byte[] buffer = new byte[1024 * 1024];
//			int len = 0;
//			while ((len = fis.read(buffer)) > 0) {
//				fos.write(buffer, 0, len);
//			}
//		} catch (Exception e) {
//			logger.error(e);
//		} finally {
//			try {
//				if (fis != null)
//					fis.close();
//			} catch (Exception e) {
//				logger.error(e);
//			}
//			try {
//				if (fos != null)
//					fos.close();
//			} catch (Exception e) {
//				logger.error(e);
//			}
//		}
//		
//		//String bPicWidth = PropertyFactory.getDefaultProperty().getProperty("asn.product.bigPicWidth");
//		String bPicWidth = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.bigPicWidth");
//		//String bPicHeight = PropertyFactory.getDefaultProperty().getProperty("asn.product.bigPicHeight");
//		String bPicHeight = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.bigPicHeight");
//		int bwidth = Integer.valueOf(bPicWidth);
//		int bheight = Integer.valueOf(bPicHeight);
//		
//		//String sPicWidth = PropertyFactory.getDefaultProperty().getProperty("asn.product.smallPicWidth");
//		String sPicWidth = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.smallPicWidth");
//		//String sPicHeight = PropertyFactory.getDefaultProperty().getProperty("asn.product.smallPicHeight");
//		String sPicHeight = (String) PropertyFactory.defaultPropertyMap
//			.get("asn.product.smallPicHeight");
//		int swidth = Integer.valueOf(sPicWidth);
//		int sheight = Integer.valueOf(sPicHeight);
//		String brealPath = serverPath + File.separator + (tempdir) + "/big_" + picName;
//		String srealPath = serverPath + File.separator + (tempdir) + "/sml_" + picName;
//		try {
//			//生成大图
//			ImageSize.drawImage(realpath, brealPath, bwidth, bheight, '*');
//			//生成小图
//			ImageSize.drawImage(realpath, srealPath, swidth, sheight, '*');
//		} catch (Exception e) {
//			logger.error(e);
//		}
//		String path[] = { "/pic/member/" + picName, "/pic/member/sml_" + picName };
//		return path;
//	}
//	
//	// copy的 判断图片类型
//	public static String getFormatInFile(File f) {
//		return getFormatName(f);
//	}
//	
//	// Returns the format name of the image in the object 'o'.
//	// Returns null if the format is not known.
//	private static String getFormatName(Object o) {
//		ImageInputStream iis = null;
//		try {
//			// Create an image input stream on the image
//			iis = ImageIO.createImageInputStream(o);
//			
//			// Find all image readers that recognize the image format
//			Iterator iter = ImageIO.getImageReaders(iis);
//			if (!iter.hasNext()) {
//				// No readers found
//				return null;
//			}
//			
//			// Use the first reader
//			ImageReader reader = (ImageReader) iter.next();
//			
//			// Close stream liujunwei fixed
//			//iis.close();
//			
//			// Return the format name
//			return reader.getFormatName();
//		} catch (IOException e) {
//			logger.error(e);
//		} finally {
//			try {
//				if (iis != null)
//					iis.close();
//			} catch (Exception e) {
//				logger.error(e);
//			}
//		}
//		
//		// The image could not be read
//		return null;
//	}
//	
//	// recruitment
//	public static String drawOriginalPic(File originalPic, String path, String serverPath)
//																							throws Exception {
//		if (getFormatName(originalPic) == null) {
//			return drawDefaultImage()[0];
//		}
//		String picName = System.currentTimeMillis() + ".jpg";
//		//String virtualPath = PropertyFactory.getDefaultProperty().getProperty(path);
//		String virtualPath = (String) PropertyFactory.defaultPropertyMap.get(path);
//		String realPath = serverPath + File.separator + (virtualPath) + "/" + picName;
//		
//		FileOutputStream fos = null;
//		FileInputStream fis = null;
//		try {
//			fos = new FileOutputStream(realPath);
//			fis = new FileInputStream(originalPic);
//			byte[] buffer = new byte[10 * 1024];
//			int len = 0;
//			while ((len = fis.read(buffer)) > 0) {
//				fos.write(buffer, 0, len);
//			}
//		} catch (Exception e) {
//			logger.error(e);
//		} finally {
//			try {
//				if (fis != null)
//					fis.close();
//			} catch (Exception e) {
//				logger.error(e);
//			}
//			try {
//				if (fos != null)
//					fos.close();
//			} catch (Exception e) {
//				logger.error(e);
//			}
//		}
//		
//		return virtualPath + "/" + picName;
//	}
//	
//	/**
//	 * @param destObj 目标文件
//	 * @param srcFile 原始文件
//	 * @param w 目标宽
//	 * @param h 目标高
//	 * @param per 百分比
//	 */
//	private static void writeAsnImage(String destObj, File srcFile, int w, int h, float per) {
//		Image src;
//		FileOutputStream newimage = null;
//		try {
//			src = javax.imageio.ImageIO.read(srcFile); // 构造Image对象
//			
//			int old_w = src.getWidth(null); // 得到源图宽
//			int old_h = src.getHeight(null);
//			int new_w = 0;
//			int new_h = 0; // 得到源图长
//			
//			logger.info("old_w>" + old_w + ">old_h>" + old_h);
//			
//			double w2 = (old_w * 1.00) / (w * 1.00);
//			double h2 = (old_h * 1.00) / (h * 1.00);
//			
//			// 图片跟据长宽留白，成一个正方形图。
//			BufferedImage oldpic;
//			if (old_w > old_h) {
//				oldpic = new BufferedImage(old_w, old_w, BufferedImage.TYPE_INT_RGB);
//			} else {
//				if (old_w < old_h) {
//					oldpic = new BufferedImage(old_h, old_h, BufferedImage.TYPE_INT_RGB);
//				} else {
//					oldpic = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
//				}
//			}
//			Graphics2D g = oldpic.createGraphics();
//			g.setColor(Color.white);
//			if (old_w > old_h) {
//				g.fillRect(0, 0, old_w, old_w);
//				g.drawImage(src, 0, (old_w - old_h) / 2, old_w, old_h, Color.white, null);
//			} else {
//				if (old_w < old_h) {
//					g.fillRect(0, 0, old_h, old_h);
//					g.drawImage(src, (old_h - old_w) / 2, 0, old_w, old_h, Color.white, null);
//				} else {
//					// g.fillRect(0,0,old_h,old_h);
//					g.drawImage(src.getScaledInstance(old_w, old_h, Image.SCALE_SMOOTH), 0, 0, null);
//				}
//			}
//			g.dispose();
//			src = oldpic;
//			// 图片调整为方形结束
//			if (old_w > w)
//				new_w = (int) Math.round(old_w / w2);
//			else
//				new_w = old_w;
//			if (old_h > h)
//				new_h = (int) Math.round(old_h / h2);// 计算新图长宽
//			else
//				new_h = old_h;
//			BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
//			// tag.getGraphics().drawImage(src,0,0,new_w,new_h,null); //绘制缩小后的图
//			tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0,
//				0, null);
//			
//			newimage = new FileOutputStream(destObj); // 输出到文件流
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
//			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
//			/* 压缩质量 */
//			jep.setQuality(per, true);
//			encoder.encode(tag, jep);
//			// encoder.encode(tag); //近JPEG编码
//			
//			//newimage.close();
//		} catch (IOException ex) {
//			logger.error("builder image error:" + ex);
//		} finally {
//			try {
//				if (newimage != null)
//					newimage.close();
//			} catch (Exception e) {
//				logger.error("builder write image error:" + e);
//			}
//		}
//	}
//}
