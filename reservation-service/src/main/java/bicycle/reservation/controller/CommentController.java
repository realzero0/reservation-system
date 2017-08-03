package bicycle.reservation.controller;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bicycle.reservation.dto.CommentReadingDto;
import bicycle.reservation.service.FileService;
import bicycle.reservation.service.ReservationUserCommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	private static final Integer REVIEW_MAIN_COUNT = 3;
	@Autowired
	ReservationUserCommentService commentService;

	@Autowired
	FileService fileService;

	@GetMapping("/{productId}/{limitNum}")
	public List<CommentReadingDto> getAllAtFront(@PathVariable Integer productId, @PathVariable Integer limitNum) {
		logger.info("==============모든 Comment 로딩 시작==============");
		List<CommentReadingDto> commentReadingDto;
		try {
			commentReadingDto = (List<CommentReadingDto>) commentService.getByCommentsProId(productId, 10 * limitNum, REVIEW_MAIN_COUNT);
			logger.info("==============모든 Comment 로딩 성공==============");
			
		} catch (Exception e) {
			logger.info("==============모든 Comment 로딩 실패==============");
			return null;
		}
		return commentReadingDto;
	}

	@GetMapping("/pictures/{commentId}")
	public Collection<Integer> getCommentPictureCount(@PathVariable Integer commentId) {
		return fileService.getImagesByCommentId(commentId);
	}
}
