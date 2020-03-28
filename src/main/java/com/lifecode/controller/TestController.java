package com.lifecode.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping(value = "api/test")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class TestController {

	//Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "E:\\projects\\react\\lifecode-react-admin\\src\\images\\test\\";
    
    private static String REST_IMG_PATH = "http://127.0.0.1:8888/api/test/image/";
    
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/multiple")
	public <T> ResponseEntity<T> multiple(HttpServletResponse response, MultipartHttpServletRequest mRequest) {
		try {
//			response.addHeader("Access-Control-Allow-Credentials", "*");
			List<MultipartFile> multipartFiles = mRequest.getMultiFileMap().get("imageFiles");
			List<String> resultList = new ArrayList<String>();
		    for (MultipartFile multipartFile: multipartFiles) {
		    	saveFiles(multipartFile);
		    	resultList.add(REST_IMG_PATH+multipartFile.getOriginalFilename());
		    }
			response.addHeader("Access-Control-Allow-Credentials", "true");
			return (ResponseEntity<T>) ResponseEntity.ok().body(resultList);
		} catch (Exception e) {
		}
		return ResponseEntity.badRequest().build();
	}
	
	private void saveFiles(MultipartFile multipartFile) {
		try {
            // Get the file and save it somewhere
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + multipartFile.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@GetMapping(
			  value = "/image/{imageName:.+}",
			  produces = MediaType.IMAGE_JPEG_VALUE
			)
	public @ResponseBody byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {
//	    InputStream in = getClass().getResourceAsStream(classLoader.getResource(fileName).getFile());
		File file = new File(UPLOADED_FOLDER+imageName);
	    return IOUtils.toByteArray(new FileInputStream(file));
	}
}
