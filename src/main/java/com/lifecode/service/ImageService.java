package com.lifecode.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lifecode.common.Const;

@Service
public class ImageService {
	
	protected Logger logger = LoggerFactory.getLogger(ImageService.class);
	
	public byte[] getImage(String subPath, String imageName) throws FileNotFoundException, IOException {

		String imagePath = null;
		switch (subPath) {
			case "common":
				imagePath = Const.IMG_COMMON_PATH+"/"+imageName;
				break;
			case "category":
				imagePath = Const.IMG_CATEGORY_PATH+"/"+imageName;
				break;
			default:
				return getNotFoundImage();
		}
		
		File file = new File(Const.UPLOAD_FOLDER_ROOT+"/"+imagePath);
		
		byte[] byteFile = null;
		try {
			byteFile = IOUtils.toByteArray(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException : {}", ExceptionUtils.getStackTrace(e));
			return getNotFoundImage();
		} catch (IOException e) {
			logger.error("IOException : {}", ExceptionUtils.getStackTrace(e));
		}
	
	    return byteFile;
	}

	private byte[] getNotFoundImage() throws FileNotFoundException, IOException {
		File file = new File(Const.UPLOAD_FOLDER_ROOT+"/"+Const.IMG_NOT_FOUND_DIR);
		byte[] byteFile = IOUtils.toByteArray(new FileInputStream(file));
	    return byteFile;
	}
}
