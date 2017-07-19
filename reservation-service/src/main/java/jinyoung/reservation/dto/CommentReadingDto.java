package jinyoung.reservation.dto;

import java.util.*;

public class CommentReadingDto {
	Integer commentId;
	String productName;
	String username;
	Double score;
	String comment;
	Date createDate;
	Date modifyDate;
	Collection<Integer> fileIds;
	
	public CommentReadingDto() {
		this.fileIds = new ArrayList<>();
	}
	
	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Collection<Integer> getFileIds() {
		return fileIds;
	}

	public void setFileIds(Collection<Integer> fileIds) {
		this.fileIds = fileIds;
	}

}
