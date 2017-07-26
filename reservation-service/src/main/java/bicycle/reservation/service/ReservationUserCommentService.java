package bicycle.reservation.service;

import java.util.*;

import bicycle.reservation.dto.*;

public interface ReservationUserCommentService {
	public Integer addComment(CommentRegisterFormDto commentRegisterFormDto, Collection<ImageDto> images);
	public Collection<CommentReadingDto> getByCommentsProId(Integer productId, Integer page, Integer count);
	public Double getAvgScoreByProId(Integer productId);
	public Integer getCountByProId(Integer productId);
}
