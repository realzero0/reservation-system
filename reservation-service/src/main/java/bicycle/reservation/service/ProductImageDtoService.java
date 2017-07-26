package bicycle.reservation.service;

import java.util.*;

import bicycle.reservation.dto.*;

public interface ProductImageDtoService {
	public Collection<ProductImageDto> getByProId(Integer productId);
}
