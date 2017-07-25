package com.cl.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public final class ImageUtils {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    public ImageUtils() {

    }

    public final static void pressImage(HttpServletRequest request, String pressImg, String targetImg) {
        try {
            String pathArray = null;
            WebApplicationContext wac = (WebApplicationContext) request
                    .getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
            ServletContext context = wac.getServletContext();
            pathArray = context.getRealPath("") + File.separator;
            //目标文件
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);

            //水印文件
            File _filebiao = new File(pathArray + pressImg);
            logger.info("水印文件路径："+_filebiao);
            Image src_biao = ImageIO.read(_filebiao);
            int width_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.drawImage(src_biao,width-width_biao,
                    height-height_biao,width_biao, height_biao, null);
            //水印文件结束
            g.dispose();

            String formatName = targetImg.substring(targetImg.lastIndexOf(".") + 1);
            ImageIO.write(image, formatName , new File(targetImg) );

//            FileOutputStream out = new FileOutputStream(targetImg);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            encoder.encode(image);
//            out.close();
        } catch (Exception e) {
            logger.error("水印图片出错，错误原因:"+e.getMessage(),e);
        }
    }

    public final static void pressText(String targetImg) throws IOException {
        Integer left=139;
        Integer top=192;
        Integer fontsize=46;
        Integer rect_width=804;
        Integer rect_height=65;
        Double imgwidth=804d;
        Double imgheight=451d;

        ImageIcon imgIcon = new ImageIcon(targetImg);
        Image img = imgIcon.getImage();
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        Double width_diff=width/imgwidth;
        Double height_diff=height/imgheight;
        System.out.println(width_diff+"    "+height_diff);

        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();

        g.setColor(Color.white);//设置字体颜色
        g.setBackground(Color.white);
        g.drawImage(img, 0, 0, null);
        g.setFont(new Font("黑体", Font.BOLD,(int)(fontsize*height_diff)));//字体样式    

        FontMetrics metrics = new FontMetrics(new Font("黑体", Font.BOLD,(int)(fontsize*height_diff))){};
        Rectangle2D bounds = metrics.getStringBounds("中国", null);
        int textWidth = (int) bounds.getWidth();//字体长度
        int textHeight = (int) bounds.getHeight();//字体高度


        g.drawString(".玻璃之窗", (int)(left*width_diff),  (int)(top*height_diff)+(int)(fontsize*height_diff));//绘制字体  

        //画矩形
        g.setColor(Color.red);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f)); //设置透明度
        g.fillRect(0, (int)(top*height_diff), width, (int)(rect_height*height_diff));

        g.dispose();
        String formatName = targetImg.substring(targetImg.lastIndexOf(".") + 1);
        ImageIO.write((RenderedImage) img, formatName , new File(targetImg) );

//        try {
//            FileOutputStream out = new FileOutputStream(targetImg);
//            ImageIO.write(bimage, formatName, out);
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    /**
     *根据url获取图片
     * @param imgUrl 图片地址
     * @return
     */
    public final static BufferedImage getBufferedImage(String imgUrl) {
        URL url = null;
        InputStream is = null;
        BufferedImage img = null;
        try {
            url = new URL(imgUrl);
            is = url.openStream();
            img = ImageIO.read(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }



    public static void main(String[] args) {

//        pressImage("http://127.0.0.1:8088/resources/images/common/rn.png","C:\\Users\\min\\Desktop\\4cbc56c1a57e26873c140000.jpg");
    }
}
