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
public class FileServiceImpl implements FileService {
	
	@Value("${app.file.basedir}")
	private String baseDir;
	
	@Autowired
	FileDao fileDao;
	
	@Autowired
	ProductImageDao productImageDao;
	
	@Autowired
	ReservationUserCommentImageDao commentImageDao;
	
	@Override
	@Transactional(readOnly = false)
	public Integer addProductImageFile(ImageFormDto productImageFormDto) {
		File file = new File();
		file.setFileName(productImageFormDto.getFileName());
		file.setContentType(productImageFormDto.getContentType());
		file.setFileLength(productImageFormDto.getFileLength());
		file.setCreateDate(productImageFormDto.getCreateDate());
		file.setSaveFileName(productImageFormDto.getSaveFileName());
		Integer fileId = fileDao.insert(file);
		ProductImage productImage = new ProductImage();
		productImage.setFileId(fileId);
		productImage.setProductId(productImageFormDto.getProductId());
		productImage.setType(0);
		Integer productImageId = productImageDao.insert(productImage);
		return productImageId;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Integer> getImagesByCommentId(Integer commentId) {
		Collection<ReservationUserCommentImage> commentImages = commentImageDao.getByComId(commentId);
		ArrayList<Integer> commentList = new ArrayList<>();
		for (ReservationUserCommentImage commentImage : commentImages) {
			commentList.add(commentImage.getFileId());
		}
		return commentList;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<ImageDto> getImagesByProId(Integer productId) {
		return fileDao.selectImagesByProductId(productId);
	}

	@Override
	@Transactional(readOnly = true)
	public ImageDto getImageByFileId(Integer fileId) {
		ImageDto image;
		try {
			image = fileDao.selectImageByFileId(fileId);
		} catch (Exception e) {
			return null;
		}
		return image;
	}

	@Override
	public String getBaseDir() {
		return baseDir.replace("/", java.io.File.separator);
	}
	
	
	
}
