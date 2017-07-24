package jinyoung.reservation.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import jinyoung.reservation.dao.*;
import jinyoung.reservation.domain.*;
import jinyoung.reservation.dto.*;
import jinyoung.reservation.service.*;

@Service
public class ReservationUserCommentServiceImpl implements ReservationUserCommentService {

	@Autowired
	ReservationUserCommentImageDao commentImageDao;
	
	@Autowired
	ReservationUserCommentDao commentDao;
	
	@Autowired
	FileDao fileDao;
	
	@Override
	@Transactional(readOnly = false)
	public Integer addComment(CommentRegisterFormDto commentRegisterFormDto, Collection<ImageDto> images) {
		ReservationUserComment comment = new ReservationUserComment();
		comment.setComment(commentRegisterFormDto.getComment());
		comment.setCreateDate(new Date());
		comment.setProductId(commentRegisterFormDto.getProductId());
		comment.setScore(commentRegisterFormDto.getScore());
		comment.setUserId(commentRegisterFormDto.getUserId());
		Integer commentId = commentDao.insert(comment);
		for(ImageDto image : images) {
			File savingImage = new File();
			savingImage.setContentType(image.getContentType());
			savingImage.setCreateDate(new Date());
			savingImage.setFileLength(image.getFileLength());
			savingImage.setFileName(image.getFileName());
			savingImage.setSaveFileName(image.getSaveFileName());
			savingImage.setUserId(image.getUserId());
			Integer fileId = fileDao.insert(savingImage);
			ReservationUserCommentImage commentImage = new ReservationUserCommentImage();
			commentImage.setFileId(fileId);
			commentImage.setReservationUserCommentId(commentId);
			commentImageDao.insert(commentImage);
		}
		return commentId;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<CommentReadingDto> getByCommentsProId(Integer productId, Integer page, Integer count) {
		page *= count;
		Collection<CommentReadingDto> comments = commentDao.selectCommentsByProId(productId, page, count);
		if(comments == null) {
			return null;
		}
		for (CommentReadingDto comment : comments) {
			String username = comment.getUsername();
			String subName;
			if(username.length() > 4) {
				subName = username.substring(0, 4);	
			} else {
				subName = username;
			}
			comment.setUsername(subName);
			Collection<ReservationUserCommentImage> commentImages = commentImageDao.getByComId(comment.getCommentId());
			for (ReservationUserCommentImage commentImage : commentImages) {
				comment.getFileIds().add(commentImage.getFileId());
			}
		}
		return comments;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Double getAvgScoreByProId(Integer productId) {
		return commentDao.selectAvgScoreByProId(productId);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getCountByProId(Integer productId) {
		return commentDao.selectCountByProId(productId);
	}
}
