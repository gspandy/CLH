package com.cl.web.controller;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;
import java.util.*;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cl.common.base.ImageUtils;
import com.cl.common.lang.util.StringUtil;
import com.cl.common.log.Logger;
import com.cl.common.log.LoggerFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.DispatcherServlet;


@Controller
@RequestMapping("upload")
public class FileUploadController  {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    private static List<String> fileTypeList = new ArrayList<String>();
    private static org.json.simple.JSONObject ueconfig = null;
    private static Random newRandom = new Random(1000);
    public static String FILE_PATH_ROOT = "upload" + File.separator + "file" + File.separator; // 文件相对web目录路径
    public static String HTTP_PATH_ROOT = "upload/file/"; // http访问目录路径
    public static String HTTP_DOMAIN_URL = "http://127.0.0.1:8081"; // http访问域名
    public static String SYS_PARAM_UPLOAD_FOLDER="/opt/apache-tomcat/webapps/ROOT";
    static {
        //图片
        fileTypeList.add(".jpg");
        fileTypeList.add(".jpeg");
        fileTypeList.add(".bmp");
        fileTypeList.add(".png");
        fileTypeList.add(".tiff");
        fileTypeList.add(".tif");
        fileTypeList.add(".pcx");
        fileTypeList.add(".tga");
        fileTypeList.add(".exif");
        fileTypeList.add(".fpx");
        fileTypeList.add(".svg");
        fileTypeList.add(".psd");
        fileTypeList.add(".cdr");
        fileTypeList.add(".pcd");
        fileTypeList.add(".dxf");
        fileTypeList.add(".ufo");
        fileTypeList.add(".eps");
        fileTypeList.add(".ai");
        fileTypeList.add(".raw");
        //文档
        fileTypeList.add(".doc");
        fileTypeList.add(".docx");
        fileTypeList.add(".xls");
        fileTypeList.add(".xlsx");
        fileTypeList.add(".ppt");
        fileTypeList.add(".pptx");
        fileTypeList.add(".pdf");
        fileTypeList.add(".txt");
        fileTypeList.add(".md");
        fileTypeList.add(".xml");
        //压缩文件
        fileTypeList.add(".apk");
        fileTypeList.add(".rar");
        fileTypeList.add(".zip");
        fileTypeList.add(".tar");
        fileTypeList.add(".gz");
        fileTypeList.add(".7z");
        fileTypeList.add(".bz2");
        fileTypeList.add(".cab");
        fileTypeList.add(".iso");
        //视频
        fileTypeList.add(".mp4");
        fileTypeList.add(".flv");
        fileTypeList.add(".mkv");
        fileTypeList.add(".mpg");
        fileTypeList.add(".mpeg");
        fileTypeList.add(".avi");
        fileTypeList.add(".3gp");
        //音频
        fileTypeList.add(".wmv");
        fileTypeList.add(".mp3");
    };

    @ResponseBody
    @RequestMapping(value = "imagesUpload2Front", produces = "application/json")
    public Object imagesUpload2Front(String fileName, HttpServletRequest request,
                                     HttpServletResponse response) {
        fileName = fileName.split(";")[0];
        JSONObject jsonobj = new JSONObject();
        try {
            ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
            fileUpload.setHeaderEncoding("utf-8");
            List<FileItem> fileList = null;
            try {
                fileList = fileUpload.parseRequest(request);
            } catch (FileUploadException ex) {
                logger.error(ex.getMessage(), ex);
                //				return "{\"code\":\"1\",\"resData\":\"" + "文件接收异常！" + "\"}";
                jsonobj.put("success", false);
                jsonobj.put("message", "文件接收异常！");
                return jsonobj.toJSONString();
            }
            Iterator<FileItem> it = fileList.iterator();
            String name = "";
            String extName = "";
            while (it.hasNext()) {
                FileItem item = it.next();
                if (!item.isFormField()) {
                    // 解析文件
                    name = item.getName();
                    if (name == null || name.trim().equals("")) {
                        continue;
                    }
                    // 得到文件的扩展名
                    if (name.lastIndexOf(".") >= 0) {
                        extName = name.substring(name.lastIndexOf("."));
                    }
                    if (!fileTypeList.contains(extName.toLowerCase())) {
                        jsonobj.put("success", false);
                        jsonobj.put("message", "文件上传失败(文件类型不正确)！");
                        return jsonobj.toJSONString();
                    }
                    File file = null;

                    String imgDir = request.getSession().getServletContext().getRealPath("/")+File.separator + "uploadfile" + File.separator + "images";

                    // String savePath =
                    // request.getServletContext().getRealPath("/") + fileName;

                    String savePath = imgDir + File.separator + fileName;

                    logger.info("imagesUpload2Front  savePath:" + savePath);

                    try {
                        file = new File(savePath);
                        item.write(file);
                        if (".jpg".equalsIgnoreCase(extName) || ".bmp".equalsIgnoreCase(extName)
                                || ".png".equalsIgnoreCase(extName)) {
                            boolean pass = this.compressPic(savePath, savePath);
                            if (!pass) {
                                logger.info("文件压缩异常");
                                jsonobj.put("success", false);
                                jsonobj.put("message", "文件压缩异常");
                                return jsonobj.toJSONString();
                            }
                        }
                    } catch (Exception e) {
                        logger.info("文件写入异常，异常信息：{}", e.toString(), e);
                        //						return "{\"code\":\"3\",\"resData\":\"" + "文件写入异常" + "\"}";
                        jsonobj.put("success", false);
                        jsonobj.put("message", "文件写入异常");
                        return jsonobj.toJSONString();
                    }
                }

            }
        } catch (Exception e) {
            logger.info("文件上传异常，异常信息：{}", e.toString(), e);
            //			return "{\"code\":\"2\",\"resData\":\"" + "文件上传异常" + "\"}";
            jsonobj.put("success", false);
            jsonobj.put("message", "文件上传异常");
            return jsonobj.toJSONString();
        }
        // validateFikeExsit(fileName);
        jsonobj.put("success", true);
        jsonobj.put("message", "文件上传成功");
        response.setContentType("application/json"); JSONObject data = new JSONObject();
        data.put("fileName", fileName);
        jsonobj.put("data", data);
        return jsonobj.toJSONString();
    }


    @RequestMapping(value = "imagesUpload")
    @ResponseBody
    public String imagesUpload(HttpServletRequest request, HttpServletResponse response,
                             ModelMap modelMap) throws IOException {
        String[] pathArray = null;
        File file = null;
        JSONObject jsonobj = new JSONObject();
        DefaultMultipartHttpServletRequest mulRequest = null;
        if (request instanceof DefaultMultipartHttpServletRequest) {
            mulRequest = (DefaultMultipartHttpServletRequest) request;
        }
        String agent = request.getHeader("user-agent");
        boolean isIE = false;
        if (agent != null && (agent.indexOf("MSIE") > -1 || agent.indexOf("rv:") > -1)) {
            isIE = true;
        }
        if (isIE) {
            response.setHeader("ContentType", "text/html");
            response.setContentType("text/html");
        } else {
            response.setHeader("ContentType", "application/json");
            response.setContentType("application/json");
        }
        if (mulRequest == null) {
            jsonobj.put("code", "1");
            jsonobj.put("message", "文件上传失败(文件类型不正确)！");
            //			returnJson(response, isIE, jsonobj);
            return jsonobj.toJSONString();
        }
        MultipartFile multipartFile = null;
        multipartFile = getMultipartFile(mulRequest.getFileMap());

        //		if (multipartFile == null) {
        //            return "{\"code\":\"1\",\"resData\":\"" + "文件接收异常！" + "\"}";
        //        }
        // 解析文件
        String name = multipartFile.getOriginalFilename();

        try {
            // 得到文件的扩展名
            String extName = "";
            if (name.lastIndexOf(".") >= 0) {
                extName = name.substring(name.lastIndexOf("."));
            }
            if (!fileTypeList.contains(extName.toLowerCase())) {
                jsonobj.put("code", "1");
                jsonobj.put("message", "文件上传失败(文件类型不正确)！");
                return jsonobj.toJSONString();
            }
            if (".pdf".equalsIgnoreCase(extName)) {
                pathArray = getStaticFilesPdfPath(request, name);
            } else {
                pathArray = getStaticFilesImgPath(request, name);
            }
            String savePath = pathArray[0];
            FileOutputStream baos = null;
            try {
                file = new File(savePath);
                InputStream stream = multipartFile.getInputStream();
                baos = new FileOutputStream(savePath);
                byte[] b = new byte[1024];
                int readIndex = 0;
                while ((readIndex = stream.read(b)) > 0) {
                    baos.write(b, 0, readIndex);
                }
                baos.close();
                stream.close();
                //						if (".jpg".equalsIgnoreCase(extName) || ".bmp".equalsIgnoreCase(extName)
                //							|| ".png".equalsIgnoreCase(extName)) {
                //							boolean pass = this.compressPic(savePath, savePath);
                //							if (!pass) {
                //								logger.info("文件压缩异常");
                //								return "{\"code\":\"2\",\"resData\":\"" + "文件压缩异常" + "\"}";
                //							}
                //						}
            } catch (Exception e) {

                logger.error("文件写入异常，异常信息：{}", e.toString(), e);
                jsonobj.put("code", "3");
                jsonobj.put("resData", "文件写入异常");
                //				returnJson(response, isIE, jsonobj);
                return jsonobj.toJSONString();
                //						return "{\"code\":\"3\",\"resData\":\"" + "文件写入异常" + "\"}";
            }
            //				}
            //			}
        } catch (Exception e) {
            logger.error("文件上传异常，异常信息：{}", e.toString(), e);
            jsonobj.put("code", "2");
            jsonobj.put("resData", "文件上传异常");
            //			returnJson(response, isIE, jsonobj);
            return jsonobj.toJSONString();
            //			return "{\"code\":\"2\",\"resData\":\"" + "文件上传异常" + "\"}";
        }
        //validateFikeExsit(pathArray[1]);
        jsonobj.put("code", "0");
        jsonobj.put("resData", pathArray[1]);
        jsonobj.put("err", "");
        jsonobj.put("msg", pathArray[1]);
        jsonobj.put("serverPath", pathArray[0]);
        jsonobj.put("error", 0);
        jsonobj.put("url", pathArray[1]);
        jsonobj.put("fileName", file.getName());
        //		returnJson(response, isIE, jsonobj);
        return jsonobj.toJSONString();
    }
    public static MultipartFile getMultipartFile(Map<String, MultipartFile> fileMap) {
        if (fileMap.isEmpty())
            return null;
        Map.Entry<String, MultipartFile> entry = fileMap.entrySet().iterator().next();
        return entry.getValue();
    }

    protected void returnJson(HttpServletResponse response, boolean isIE, JSONObject jsonobj)
            throws IOException {
        response.reset();
        response.setHeader("ContentType", "text/html");
        response.setContentType("text/html");
        //		if (isIE) {
        //			response.setHeader("ContentType", "text/html");
        //			response.setContentType("text/html");
        //		} else {
        //			response.setHeader("ContentType", "application/json");
        //			response.setContentType("application/json");
        //		}

//        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonobj.toJSONString());
    }

    //身份证图片水印，特新加请求
    @ResponseBody
    @RequestMapping(value = "newImagesUpload", produces = "application/json")
    public Object newImagesUpload(HttpServletRequest request, HttpServletResponse response,
                                  ModelMap modelMap) throws IOException {
        String[] pathArray = null;
        File file = null;
        String oldFileName = "";
        JSONObject jsonobj = new JSONObject();
        try {
            ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
            fileUpload.setHeaderEncoding("utf-8");
            List<FileItem> fileList = null;
            try {
                fileList = fileUpload.parseRequest(request);
            } catch (FileUploadException ex) {
                logger.error(ex.getMessage(), ex);
                return "{\"code\":\"1\",\"resData\":\"" + "文件接收异常！" + "\"}";
            }
            Iterator<FileItem> it = fileList.iterator();
            String name = "";
            String extName = "";
            while (it.hasNext()) {
                FileItem item = it.next();
                if (!item.isFormField()) {
                    // 解析文件
                    name = item.getName();
                    oldFileName = name;
                    if (name == null || name.trim().equals("")) {
                        continue;
                    }
                    // 得到文件的扩展名
                    if (name.lastIndexOf(".") >= 0) {
                        extName = name.substring(name.lastIndexOf("."));
                    }
                    if (!fileTypeList.contains(extName.toLowerCase())) {
                        jsonobj.put("code", "1");
                        jsonobj.put("message", "文件上传失败(文件类型不正确)！");
                        return jsonobj.toJSONString();
                    }
                    if (".pdf".equalsIgnoreCase(extName)) {
                        pathArray = getStaticFilesPdfPath(request, name);
                    } else {
                        pathArray = getStaticFilesImgPath(request, name);
                    }
                    String savePath = pathArray[0];

                    try {
                        file = new File(savePath);
                        item.write(file);
                        //png压缩后图片失真？
                        if (".jpg".equalsIgnoreCase(extName) || ".bmp".equalsIgnoreCase(extName)) {
                            boolean pass = this.compressPic(savePath, savePath);
                            if (!pass) {
                                logger.info("文件压缩异常");
                                return "{\"code\":\"2\",\"resData\":\"" + "文件压缩异常" + "\"}";
                            }
                        }
                    } catch (Exception e) {

                        logger.error("文件写入异常，异常信息：{}", e.toString(), e);
                        return "{\"code\":\"3\",\"resData\":\"" + "文件写入异常" + "\"}";
                    }
                }
            }
        } catch (Exception e) {
            logger.error("文件上传异常，异常信息：{}", e.toString(), e);
            return "{\"code\":\"2\",\"resData\":\"" + "文件上传异常" + "\"}";
        }
        // validateFikeExsit(pathArray[1]);
        //添加水印
        ImageUtils.pressImage(request, "/styles/images/home/shuiying.png", pathArray[0]);

        jsonobj.put("code", "0");
        jsonobj.put("resData", pathArray[1]);
        jsonobj.put("err", "");
        jsonobj.put("msg", pathArray[1]);
        jsonobj.put("serverPath", pathArray[0]);
        jsonobj.put("error", 0);
        jsonobj.put("url", pathArray[1]);
        jsonobj.put("oldFileName", oldFileName);
        jsonobj.put("fileName", file.getName());
        response.setHeader("Content-Type", "text/html;charset=utf-8");
        response.setContentType("text/plain");
        //response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonobj.toJSONString());
        return null;
    }

    @ResponseBody
    @RequestMapping("getUploadImages")
    public Object getUploadImages(HttpServletResponse response, String imagePath, ModelMap modelMap)
            throws IOException {
        response.sendRedirect(imagePath);
        return "";
        // InputStream inputStream = null;
        // try {
        // inputStream = new FileInputStream(new File(imagePath));
        // if (!"".equals(imagePath) && imagePath != null) {
        // String imageExtNameWithOutDot =
        // imagePath.substring(imagePath.lastIndexOf(".") + 1);
        // ImageIO.setUseCache(false);
        // BufferedImage image = ImageIO.read(inputStream);
        // String imageExtName = "";
        // if ("jpg".equalsIgnoreCase(imageExtNameWithOutDot)) {
        // imageExtName = "jpeg";
        // imageExtNameWithOutDot = "jpg";
        // } else if ("jpeg".equalsIgnoreCase(imageExtNameWithOutDot)
        // || "jpe".equalsIgnoreCase(imageExtNameWithOutDot)) {
        // imageExtName = "jpeg";
        // } else if ("png".equalsIgnoreCase(imageExtNameWithOutDot)) {
        // imageExtName = "x-png";
        // } else if ("gif".equalsIgnoreCase(imageExtNameWithOutDot)) {
        // imageExtName = "gif";
        // } else if ("bmp".equalsIgnoreCase(imageExtNameWithOutDot)) {
        // imageExtName = "x-ms-bmp";
        // }
        // response.setContentType("image/" + imageExtName);
        // ServletOutputStream out = response.getOutputStream();
        // ImageIO.write(image, imageExtNameWithOutDot, out);
        // out.flush();
        // out.close();
        // }
        // } catch (Exception e) {
        // logger.error(e.getMessage());
        // return "";
        // } finally {
        // if (inputStream != null) {
        // inputStream.close();
        // }
        // }
        // return "";
    }

    /**
     * 压缩图片
     * @param srcFilePath
     * @param descFilePath
     * @return
     */
    public boolean compressPic(String srcFilePath, String descFilePath) {
        ImageIO.setUseCache(false);
        File file = null;
        BufferedImage src = null;
        FileOutputStream out = null;
        ImageWriter imgWrier;
        ImageWriteParam imgWriteParams;

        // 指定写图片的方式为 jpg
        imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
        imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
        imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        // 这里指定压缩的程度，参数qality是取值0~1范围内，
        imgWriteParams.setCompressionQuality(1);
        imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
        ColorModel colorModel = ColorModel.getRGBdefault();
        // 指定压缩时使用的色彩模式
        imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel,
                colorModel.createCompatibleSampleModel(16, 16)));

        try {
            if (StringUtil.isBlank(srcFilePath)) {
                return false;
            } else {
                file = new File(srcFilePath);
                src = ImageIO.read(file);
                out = new FileOutputStream(descFilePath);

                imgWrier.reset();
                // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
                // OutputStream构造
                imgWrier.setOutput(ImageIO.createImageOutputStream(out));
                // 调用write方法，就可以向输入流写图片
                imgWrier.write(null, new IIOImage(src, null, null), imgWriteParams);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @ResponseBody
    @RequestMapping(value = "ueServer.json", produces = "application/json")
    public Object ueditorConfig(String action, HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {
        Object json = null;
        FileReader fileReader = null;

        if (StringUtil.equals("config", action)) { //加载服务器配置
            try {
                if (ueconfig == null) {
                    //					FileReader fileReader = new FileReader(new File(this.getClass()
                    //						.getClassLoader().getResource("ueconfig.json").getFile()));
                    String configPath = request.getSession().getServletContext().getRealPath("/")
                            + "js/ueditor/jsp/ueconfig.json";
                    fileReader = new FileReader(configPath);
                    JSONParser jsonParser = new JSONParser();
                    ueconfig = (org.json.simple.JSONObject) jsonParser.parse(fileReader);
                }
                json = ueconfig;

            } catch (Exception e) {
                logger.error("加载服务端UEditor配置出错{}", e);
            } finally {
                if (fileReader != null)
                    try {
                        fileReader.close();
                    } catch (IOException e) {
                        logger.error("关闭流出错", e);
                    }
            }
        } else if (StringUtil.equals("upload", action)) {//上传文件

            String[] pathArray = null;
            File file = null;
            String oldFileName = "";
            JSONObject jsonobj = new JSONObject();
            try {
                ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
                fileUpload.setHeaderEncoding("utf-8");
                List<FileItem> fileList = null;
                try {
                    fileList = fileUpload.parseRequest(request);
                } catch (FileUploadException ex) {
                    logger.error(ex.getMessage(), ex);
                    jsonobj.put("state", false);
                    jsonobj.put("message", "文件接收异常！");
                    return jsonobj;
                }
                Iterator<FileItem> it = fileList.iterator();
                String name = "";
                String extName = "";
                while (it.hasNext()) {
                    FileItem item = it.next();
                    if (!item.isFormField()) {
                        // 解析文件
                        name = item.getName();
                        oldFileName = name;
                        if (name == null || name.trim().equals("")) {
                            continue;
                        }
                        // 得到文件的扩展名
                        if (name.lastIndexOf(".") >= 0) {
                            extName = name.substring(name.lastIndexOf("."));
                        }
                        if (!fileTypeList.contains(extName.toLowerCase())) {
                            jsonobj.put("state", false);
                            jsonobj.put("message", "文件上传失败(文件类型不正确)！");
                            return jsonobj;
                        }
                        if (".pdf".equalsIgnoreCase(extName)) {
                            pathArray = getStaticFilesPdfPath(request, name);
                        } else {
                            pathArray = getStaticFilesImgPath(request, name);
                        }
                        String savePath = pathArray[0];
                        try {
                            file = new File(savePath);
                            item.write(file);
                            //png压缩后图片失真？
                            if (".jpg".equalsIgnoreCase(extName)
                                    || ".bmp".equalsIgnoreCase(extName)) {
                                boolean pass = this.compressPic(savePath, savePath);
                                if (!pass) {
                                    logger.info("文件压缩异常");
                                    jsonobj.put("state", false);
                                    jsonobj.put("message", "文件压缩异常");
                                    return jsonobj;
                                }
                            }
                        } catch (Exception e) {
                            logger.error("文件写入异常，异常信息：{}", e.toString(), e);
                            jsonobj.put("state", false);
                            jsonobj.put("message", "文件写入异常");
                            return jsonobj;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("文件上传异常，异常信息：{}", e.toString(), e);
                jsonobj.put("success", false);
                jsonobj.put("message", "文件上传异常");
                return jsonobj;
            }
            jsonobj.put("state", "SUCCESS");
            jsonobj.put("message", "文件上传成功");
            jsonobj.put("original", oldFileName);
            jsonobj.put("name", file.getName());
            jsonobj.put("size", file.length());
            jsonobj.put("url", pathArray[1]);
            return jsonobj;
        }

        return json;
    }

    public static String[] getStaticFilesImgPath(HttpServletRequest req, String fileOrgName) {

        return getFilePath(req, "uploadfile", "images", fileOrgName);
    }

    public static String[] getStaticFilesPdfPath(HttpServletRequest req, String fileOrgName) {
        return getFilePath(req, "uploadfile", "pdf", fileOrgName);
    }

    public static String[] getFilePath(HttpServletRequest req, String fileRoot, String dir,
                                       String fileOrgName) {
        String[] pathArray = new String[2];

        String uploadFolder = req.getSession().getServletContext().getRealPath("/");

        String serverUrl = HTTP_DOMAIN_URL;

        if (StringUtil.isNotEmpty(uploadFolder)) {
            pathArray[0] = uploadFolder ;
            pathArray[1] = serverUrl + "/";
            pathArray = getFilePaths(uploadFolder, serverUrl, fileRoot, dir, fileOrgName, "110");
        } else {
            WebApplicationContext wac = (WebApplicationContext) req
                    .getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
            ServletContext context = wac.getServletContext();
            pathArray[0] = context.getRealPath("") ;
            pathArray[1] = "";
            pathArray = getFilePaths(pathArray[0], pathArray[1], fileRoot, dir, fileOrgName, "110");
        }
        return pathArray;
    }

    private static long getFileNameRandom(Calendar cal) {
        long temp = cal.get(Calendar.MINUTE) * 60l * 1000l;
        long temp1 = cal.get(Calendar.SECOND) * 1000l;
        long temp2 = cal.get(Calendar.MILLISECOND);

        long time = temp + temp1 + temp2;

        time = time * 1000l + newRandom.nextInt(1000);

        return time;
    }

    public static String[] getFilePaths(String realRootPath, String httpRootPath, String moudleDir,
                                        String dirName, String fileOrgName, String filePrefix) {
        if (StringUtil.isEmpty(realRootPath) && StringUtil.isEmpty(httpRootPath)) {
            realRootPath = FILE_PATH_ROOT;
            httpRootPath = HTTP_DOMAIN_URL + "/"
                    + HTTP_PATH_ROOT;
        }
        if (StringUtil.isEmpty(filePrefix))
            filePrefix = "";
        else
            filePrefix = filePrefix + "_";
        String[] pathArray = new String[2];
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String strMonth = month < 10 ? "0" + month : String.valueOf(month);
        String strDay = day < 10 ? "0" + day : String.valueOf(day);
        String realPath = realRootPath  + moudleDir + File.separator + dirName
                + File.separator + year + "-" + strMonth + File.separator + strDay;
        String httpUrl = httpRootPath + "/" + moudleDir + "/" + dirName + "/" + year + "-"
                + strMonth + "/" + strDay;
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = "";
        String newFileName = "";
        int maxCount = 1000;
        int count = 0;

        while (count < maxCount) {
            long time = getFileNameRandom(cal);
            String fileName = fileOrgName;
            String extName = fileName.substring(fileName.lastIndexOf('.') + 1);
            newFileName = filePrefix + time + "." + extName;
            filePath = realPath + File.separator + newFileName;
            File file = new File(filePath);
            if (!file.exists()) {
                break;
            } else
                logger.warn("filePath repeat " + filePath);
            count++;
            if (count >= maxCount) {
                logger.error("save file error 1000 time " + filePath);
                return pathArray;
            }
        }
        pathArray[0] = filePath;
        pathArray[1] = httpUrl + "/" + newFileName;
        return pathArray;
    }
}
