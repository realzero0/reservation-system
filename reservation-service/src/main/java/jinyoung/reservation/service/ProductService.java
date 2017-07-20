package jinyoung.reservation.service;

import java.util.*;

import jinyoung.reservation.domain.*;
import jinyoung.reservation.dto.*;

public interface ProductService {

	public ProductDto getByProId(Integer productId);

	public Collection<ProductDto> getByCateId(Integer categoryId, Integer page);

	public Collection<ProductDto> getAll(Integer page);

	public Integer getCountAll();

	public Integer getCountByCateId(Integer categoryId);

	public List<ProductPrice> getProductPricesByProductId(Integer productId);
}
