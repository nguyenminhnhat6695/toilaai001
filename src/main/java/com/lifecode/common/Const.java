package com.lifecode.common;

public class Const {
	
	// pagination
    public static final  String DEFAULT_PAGE_NUMBER = "0";
    public static final  String DEFAULT_PAGE_SIZE = "30";
    public static int MAX_PAGE_SIZE = 50;

    // file
    public static final String UPLOAD_FOLDER_ROOT = "upload";
    public static final String IMG_COMMON_PATH = "images/common";
    public static final String IMG_CATEGORY_PATH = "images/category";
    public static final String IMG_NOT_FOUND_NAME = "not-found";
    public static final String IMG_NOT_FOUND_DIR = IMG_COMMON_PATH+"/"+IMG_NOT_FOUND_NAME+".png";
    public static final String DEFAULT_IMG_TYPE = ".png";
    
    public static final String[] imgExtensions = {"png","jpg"};
}
