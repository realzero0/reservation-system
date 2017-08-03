package bicycle.reservation.controller;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import bicycle.reservation.dto.*;
import bicycle.reservation.service.*;

@Controller
@RequestMapping("/poster")
public class PosterFileController {
	private static final Logger logger = LoggerFactory.getLogger(PosterFileController.class);
	
	@Autowired
	FileService fileService;
	
	@GetMapping
	public String posterUpload(HttpServletRequest request) {
		return "posterUpload";
	}

	@PostMapping
	public String create(@RequestParam("productid") Integer productId, @RequestParam("file") MultipartFile[] files) {
		String baseDir = fileService.getBaseDir();
		logger.info("==============File 생성 시작==============");
		if (files != null && files.length > 0) {

			// windows 사용자라면 "c:\boost\storage\년도\월\일" 형태의 문자열을 구한다.
			String formattedDate = baseDir
					+ new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd").format(new Date());
			File f = new File(formattedDate);
			if (!f.exists()) { // 파일이 존재하지 않는다면, 여기서는 폴더가 있는지 없는지 확인
				f.mkdirs(); // 해당 디렉토리를 만든다. 하위폴더까지 한꺼번에 만든다.
			}

			for (MultipartFile file : files) {
				if (file.isEmpty()) {
					continue;
				}
				String contentType = file.getContentType();
				// String name = file.getName();
				String originalFilename = file.getOriginalFilename();
				Long size = file.getSize();
				String uuid = UUID.randomUUID().toString(); // 중복될 일이 거의 없다.
				String saveFileName = formattedDate + File.separator + uuid;
				// 아래에서 출력되는 결과는 모두 database에 저장되야 한다.
				// pk 값은 자동으로 생성되도록 한다.
				ImageFormDto imageFormDto = new ImageFormDto();
				imageFormDto.setProductId(productId);
				imageFormDto.setContentType(contentType);
				imageFormDto.setFileLength(size);
				imageFormDto.setFileName(originalFilename);
				imageFormDto.setCreateDate(new Date());
				imageFormDto.setSaveFileName(saveFileName);
				fileService.addProductImageFile(imageFormDto);

				// 실제 파일을 저장함.
				// try-with-resource 구문. close()를 할 필요가 없다. java 7 이상에서 가능
				try (InputStream in = file.getInputStream();
						FileOutputStream fos = new FileOutputStream(saveFileName)) {
					int readCount = 0;
					byte[] buffer = new byte[512];
					while ((readCount = in.read(buffer)) != -1) {
						fos.write(buffer, 0, readCount);
					}
				} catch (Exception e) {
					logger.error("==============File : "+originalFilename+" 생성 오류==============");
					e.printStackTrace();
				}
			} // for
		} // if
		logger.info("==============File 생성 성공==============");
		return "redirect:/poster";
	}

}
