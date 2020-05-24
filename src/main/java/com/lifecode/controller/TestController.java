package com.lifecode.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lifecode.mybatis.model.PostVO;
import com.lifecode.payload.Response;
import com.lifecode.service.TestService;

@RestController
@RequestMapping(value = "api/test")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class TestController {

	//Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "E:\\projects\\react\\lifecode-dashboard\\src\\images\\test\\";
    
    private static final String UPLOADE_FOLDER_ROOT = "upload-files";
    
    private static String REST_IMG_PATH = "http://127.0.0.1:8888/api/test/image/";

    protected Logger logger = LoggerFactory.getLogger(TestController.class);
    
	@Autowired
	private TestService testService;

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
			  value = "/image/{imageName:.+}"
			 /* produces = MediaType.IMAGE_PNG_VALUE*/
			)
	public @ResponseBody byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {
//	    InputStream in = getClass().getResourceAsStream(classLoader.getResource(fileName).getFile());
		File file = new File(UPLOADE_FOLDER_ROOT+"/category/"+imageName);
		byte[] byteFile = IOUtils.toByteArray(new FileInputStream(file));
	    return byteFile;
	}
	
//	@RequestMapping(value = "/posts", method = RequestMethod.GET, produces = {
//			MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<Map<String,Object>> getPosts(@RequestParam Map<String,Object> param) {
//
//		try {
//			Map<String,Object> result = testService.getPosts(param);
//			return ResponseEntity.ok().body(Utils.responseOK(result));
//		} catch (Exception e) {
//		}
//		return ResponseEntity.badRequest().body(Utils.responseERROR());
//	}
//	
	@RequestMapping(value = "/posts", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Response> getPosts(@RequestParam Map<String,Object> param) {

		try {
			List<PostVO> result = testService.getPosts(param);
			return ResponseEntity.ok().body(new Response(HttpStatus.OK,result));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.badRequest().body(new Response(HttpStatus.INTERNAL_SERVER_ERROR));
	}
}
