package bicycle.reservation.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import bicycle.reservation.dto.*;
import bicycle.reservation.service.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	private static final Integer REVIEW_MAIN_COUNT = 10;
	@Autowired
	ReservationUserCommentService commentService;
	
	@Autowired
	FileService fileService;
	
	@GetMapping("/{productId}")
	public Collection<CommentReadingDto> getAllAtFront(@PathVariable Integer productId) {
		return commentService.getByCommentsProId(productId, 0, REVIEW_MAIN_COUNT);
	}
	
	@GetMapping("/pictures/{commentId}")
	public Collection<Integer> getCommentPictureCount(@PathVariable Integer commentId) {
		return fileService.getImagesByCommentId(commentId);
	}
}
