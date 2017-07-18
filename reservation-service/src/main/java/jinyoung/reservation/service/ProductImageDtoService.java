package jinyoung.reservation.service;

import java.util.*;

import jinyoung.reservation.dto.*;

public interface ProductImageDtoService {
	public Collection<ProductImageDto> getByProId(Integer productId);
}
