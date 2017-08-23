package com.google.code.simplerule.ccmis.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class FileUtil {

	private static final Logger logger = Logger.getLogger(FileUtil.class);

	public static void export(HttpServletRequest request,
			HttpServletResponse response, HSSFWorkbook wb) {
		InputStream fis = null;

		try {
			response.reset();
			String filename = "审核日志.xls";

			response.setContentType("application/x-msdownload");
			String head = request.getHeader("User-Agent");
			String retFileName = "";
			if (head.indexOf("MSIE") > 0) { // ie
				retFileName = URLEncoder.encode(filename, "UTF-8");
				// 由于空格问题会被转成 +
				retFileName = retFileName.replace("+", "%20");
				if (retFileName.length() > 150
						&& (head.indexOf("MSIE 6.0") > 0 || head
								.indexOf("MSIE 7.0") > 0)) {// 解决IE 6.0 7.0 bug
					if (filename.indexOf("Fw:>>") < 0) {// 这种情况ie6下后乱码
						retFileName = new String(filename.getBytes("GBK"),
								"ISO-8859-1");
					}
				}
			} else if (head.indexOf("Opera") >= 0) {
				response.setContentType("application/octet-stream");
				retFileName = new String(filename.getBytes("UTF-8"),
						"ISO-8859-1");
			} else if (head.indexOf("Safari") >= 0
					&& head.indexOf("Chrome") < 0) {// 添加Safari浏览器的支持
				response.setContentType("application/octet-stream");
				retFileName = new String(filename.getBytes("UTF-8"),
						"ISO-8859-1");
			} else {// 不是IE浏览器
				retFileName = "=?UTF-8?B?"
						+ new String(Base64.encodeBase64(filename
								.getBytes("utf-8"))) + "?=";
			}
			if (retFileName.lastIndexOf("%00") > -1) {
				retFileName = retFileName.substring(0,
						retFileName.lastIndexOf("%00"));
			}
			response.setHeader("Content-Disposition", "attachment; filename="
					+ retFileName);
			wb.write(response.getOutputStream());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}
}
