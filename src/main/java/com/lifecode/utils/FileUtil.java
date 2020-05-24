package com.lifecode.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lifecode.common.Const;

public class FileUtil {
	
	protected static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static String saveBase64Image(String encodedString, String subPath, String fileName) {
		try {
			String partSeparator = ",";
			if (encodedString.contains(partSeparator)) {
			  String encodedImg = encodedString.split(partSeparator)[1];
			  byte[] decodedImg = Base64.getDecoder().decode(encodedImg.getBytes(StandardCharsets.UTF_8));
			  if(!FilenameUtils.isExtension(fileName, Const.imgExtensions)) {
				  fileName = fileName+Const.DEFAULT_IMG_TYPE;
			  }
			  Path path = Paths.get(Const.UPLOAD_FOLDER_ROOT +"/"+subPath+"/"+fileName);
			  Files.write(path, decodedImg);
			  return fileName;
			}
		} catch (IOException e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return null;
	}
	
	public static String deleteImage(String subPath, String fileName) {
		try {
			 Path path = Paths.get(Const.UPLOAD_FOLDER_ROOT +"/"+subPath+"/"+fileName);
			 Files.delete(path);
		} catch (IOException e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return null;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String ext2 = FilenameUtils.getExtension("bar"); // returns "exe"
		System.out.println(ext2);
		System.out.println(URLEncoder.encode("phúcvinh", StandardCharsets.UTF_8.toString()));
		
	}
}
