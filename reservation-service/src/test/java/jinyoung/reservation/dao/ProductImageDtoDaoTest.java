package jinyoung.reservation.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.*;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.springframework.transaction.annotation.*;

import jinyoung.reservation.config.*;
import jinyoung.reservation.dto.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class ProductImageDtoDaoTest {
	
	@Autowired
	ProductImageDtoDao productImageDtoDao;
	
	@Test
	public void shouldSelectAll() {
		//given
		Collection<ProductImageDto> results = productImageDtoDao.selectByProId(1);
		Iterator<ProductImageDto> resultsIter = results.iterator();
		//then
		while(resultsIter.hasNext()) {
			ProductImageDto image = resultsIter.next();
			assertThat(image.getProductId(), is(1));
		}
	}
}
