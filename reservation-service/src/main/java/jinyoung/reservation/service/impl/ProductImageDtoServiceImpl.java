package jinyoung.reservation.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import jinyoung.reservation.dao.*;
import jinyoung.reservation.dto.*;
import jinyoung.reservation.service.*;

@Service
public class ProductImageDtoServiceImpl implements ProductImageDtoService{
	
	@Autowired
	ProductImageDtoDao productImageDtoDao;
	
	@Override
	@Transactional(readOnly = true)
	public Collection<ProductImageDto> getByProId(Integer productId) {
		return productImageDtoDao.selectByProId(productId);
	}

}
