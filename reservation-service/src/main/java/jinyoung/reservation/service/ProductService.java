package jinyoung.reservation.service;

import java.util.*;

import jinyoung.reservation.domain.*;
import jinyoung.reservation.dto.*;

public interface ProductService {

	public ProductDto getByProductId(Integer productId);

	public List<ProductDto> getByCategoryId(Integer categoryId, Integer page);

	public List<ProductDto> getAll(Integer page);

	public Integer getCountAll();

	public Integer getCountByCateId(Integer categoryId);

	public List<ProductPrice> getProductPricesByProductId(Integer productId);
}
