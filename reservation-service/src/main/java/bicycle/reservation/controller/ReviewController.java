package bicycle.reservation.controller;

import java.io.*;
import java.io.File;
import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import bicycle.reservation.domain.*;
import bicycle.reservation.dto.*;
import bicycle.reservation.service.*;

//임시 컨트롤러, 나중에 수정 필요
@Controller
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	ReservationUserCommentService commentService;

	@Autowired
	UsersService usersService;

	@Autowired
	ReservationInfoService reservationInfoService;
	
	@Autowired
	FileService fileService;
	
	@GetMapping
	public String review(HttpServletRequest request) {
		return "review";
	}

	@GetMapping("/write")
	public String reviewWrite(@RequestParam(name = "bookingNumber") Integer bookingNumber, HttpServletRequest request)
			throws Exception {
		if (request.getSession().getAttribute("user") != null) {
			Users user = (Users) request.getSession().getAttribute("user");
			BookedListDto bookedList = reservationInfoService.getBookedListByBookingNumber(bookingNumber);
//			if (bookedList.getUserId().longValue() == user.getId().longValue() && bookedList.getCommentId() == null) {
			if(bookedList.getUserId().longValue() == user.getId().longValue()) {
				request.setAttribute("user", user);
				request.setAttribute("bookedList", bookedList);
				return "reviewWrite";
			} else {
				throw new Exception();
			}
		} else {
			throw new Exception();
		}
	}

	@PostMapping
	public String create(@ModelAttribute CommentRegisterFormDto commentForm,
			@RequestParam("file") MultipartFile[] files) {
		ArrayList<ImageDto> images = new ArrayList<>();
		String baseDir = fileService.getBaseDir();
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
				String originalFilename = file.getOriginalFilename();
				Long size = file.getSize();
				String uuid = UUID.randomUUID().toString(); // 중복될 일이 거의 없다.
				String saveFileName = formattedDate + File.separator + uuid;
				ImageDto reviewImage = new ImageDto();
				reviewImage.setUserId(commentForm.getUserId());
				reviewImage.setContentType(contentType);
				reviewImage.setFileLength(size);
				reviewImage.setFileName(originalFilename);
				reviewImage.setSaveFileName(saveFileName);
				images.add(reviewImage);
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
					e.printStackTrace();
				}
			} // for
		} // if
		commentService.addComment(commentForm, images);
		return "redirect:/review";
	}

}
