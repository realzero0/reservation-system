package bicycle.reservation.controller;

import java.util.*;

import javax.servlet.http.*;

import bicycle.reservation.security.AuthUser;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import bicycle.reservation.domain.*;
import bicycle.reservation.dto.*;
import bicycle.reservation.service.*;

@Controller
@RequestMapping("/")
public class MainConroller {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productDtoService;

	@Autowired
	private FileService productImageDtoService;

	@Autowired
	private ReservationUserCommentService commentService;

	@GetMapping
	public String index(@AuthUser Map<String, String> user,  HttpServletRequest request) {
		for(String str : user.keySet()){
			System.out.println(str + " " + user.get(str));
		}
		System.out.println(user);
		Collection<Category> categories = categoryService.getAll();
		request.setAttribute("categories", categories);
		return "index";
	}

	@GetMapping("/exhibition/{productId}")
	public String getDetailPage(@PathVariable Integer productId, HttpServletRequest request) {
		ProductDto productDto = productDtoService.getByProductId(productId);
		Collection<ImageDto> productImages = productImageDtoService.getImagesByProId(productId);
		Collection<CommentReadingDto> comments = commentService.getByCommentsProId(productId, 0, 3);
		Integer avgScore = 0;
		try {
			avgScore = (int) (commentService.getAvgScoreByProId(productId) * 100);
		} catch (Exception e) {
			avgScore = 0;
		}
		Integer commentCount = commentService.getCountByProId(productId);
		request.setAttribute("avgScore", avgScore);
		request.setAttribute("commentCount", commentCount);
		request.setAttribute("productDto", productDto);
		request.setAttribute("productImages", productImages);
		request.setAttribute("comments", comments);
		return "productdetail";
	}

	@GetMapping("/exhibition/{productId}/reviews")
	public String getReviewPage(@PathVariable Integer productId, HttpServletRequest request) {
		ProductDto productDto = productDtoService.getByProductId(productId);
		Collection<CommentReadingDto> comments = commentService.getByCommentsProId(productId, 0, 10);
		Integer avgScore = (int) (commentService.getAvgScoreByProId(productId) * 100);
		Integer commentCount = commentService.getCountByProId(productId);
		request.setAttribute("productDto", productDto);
		request.setAttribute("comments", comments);
		request.setAttribute("avgScore", avgScore);
		request.setAttribute("commentCount", commentCount);
		return "review";
	}
}
