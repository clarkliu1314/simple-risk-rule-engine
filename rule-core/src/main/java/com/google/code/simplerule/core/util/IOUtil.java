package com.google.code.simplerule.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * io工具
 * @author drizzt
 *
 */
public class IOUtil {
	/**
	 * 从流返回字符内容
	 * @param in
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static String readFromStream(InputStream in) throws UnsupportedEncodingException, IOException {
		StringBuffer sb = new StringBuffer("");
		try {
			if (in == null) {
				return null;
			}
			byte[] temp = new byte[1024];
			int num = 0;
			while ((num = in.read(temp)) > -1) {
				sb.append(new String(temp, 0, num, "utf-8"));
				temp = new byte[1024];
			}
			in.close();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return sb.toString();
	}
	
	/**
	 * 从流写入文件，存在文件则删除
	 * @param in
	 * @param toFile
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static boolean readToFile(InputStream in, String toFile) throws UnsupportedEncodingException, IOException {
		FileOutputStream out = null;
		
		try {
			if (in == null) {
				return false;
			}
			File f = new File(toFile);
			if (f.exists()) {
				f.delete();
			}
			
			out = new FileOutputStream(f);
			byte[] temp = new byte[1024];
			int num = 0;
			while ((num = in.read(temp)) > -1) {
				out.write(temp, 0, num);
				
				temp = new byte[1024];
			}
			in.close();
		} finally {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		}
		return true;
	}
}
