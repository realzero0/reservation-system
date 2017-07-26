package bicycle.reservation.controller;

import java.io.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;

import bicycle.reservation.dto.*;
import bicycle.reservation.service.*;

@Controller
@RequestMapping("/img")
public class ImageLoadingController {
	
	@Autowired
	FileService fileService;
	
	@GetMapping("/{fileId}")
	public void downloadReservationUserCommentImage(@PathVariable(name = "fileId") Integer fileId,
			HttpServletResponse response) {
		
		ImageDto image = fileService.getImageByFileId(fileId);
		// id를 이용하여 파일의 정보를 읽어온다.
		// 이 부분은 원래 db에서 읽어오는 것인데 db부분은 생략했다.

		String originalFilename = image.getFileName();
		String contentType = image.getContentType();
		Long fileSize = image.getFileLength();
		// 해당 파일이 이미 존재해야한다.
		String saveFileName = image.getSaveFileName();

		response.setHeader("Content-Disposition", "attachment; filename=\"" + originalFilename + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Type", contentType);
		response.setHeader("Content-Length", "" + fileSize);
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");

		java.io.File readFile = new java.io.File(saveFileName);
		if (!readFile.exists()) { // 파일이 존재하지 않다면
			throw new RuntimeException("file not found");
		}

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(readFile);
			FileCopyUtils.copy(fis, response.getOutputStream()); // 파일을 저장할때도
																	// 사용할 수 있다.
			response.getOutputStream().flush();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				fis.close();
			} catch (Exception ex) {
				// 아무것도 하지 않음 (필요한 로그를 남기는 정도의 작업만 함.)
			}
		}

	}
}
