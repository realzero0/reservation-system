package jinyoung.reservation.service;

import java.util.*;

import jinyoung.reservation.dto.*;

public interface FileService {
	public Integer addProductImageFile(ImageFormDto productImageFormDto);
	public Collection<Integer> getImagesByCommentId(Integer commentId);
	public Collection<ImageDto> getImagesByProId(Integer productId);
	public ImageDto getImageByFileId(Integer fileId);
	public String getBaseDir();
}
