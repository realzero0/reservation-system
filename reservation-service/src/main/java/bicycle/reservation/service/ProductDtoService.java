package bicycle.reservation.service;

import java.util.*;

import bicycle.reservation.dto.*;

public interface ProductDtoService {

	public ProductDto getByProId(Integer productId);

	public Collection<ProductDto> getByCateId(Integer categoryId, Integer page);

	public Collection<ProductDto> getAll(Integer page);

	public Integer getCountAll();

	public Integer getCountByCateId(Integer categoryId);

}
