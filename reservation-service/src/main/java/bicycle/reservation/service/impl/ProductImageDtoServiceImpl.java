package bicycle.reservation.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import bicycle.reservation.dao.*;
import bicycle.reservation.dto.*;
import bicycle.reservation.service.*;

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
