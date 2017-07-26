package bicycle.reservation.dao;

import java.util.*;

import javax.sql.*;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.simple.*;
import org.springframework.stereotype.*;

import bicycle.reservation.domain.*;
import bicycle.reservation.dto.*;

@Repository
public class FileDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<File> rowMapper = BeanPropertyRowMapper.newInstance(File.class);
	private RowMapper<ImageDto> imageRowMapper = BeanPropertyRowMapper.newInstance(ImageDto.class);
	
	public FileDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("file").usingGeneratedKeyColumns("id");

	}

	public Integer insert(File file) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(file);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public Collection<ImageDto> selectImagesByProductId(Integer productId) {
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		return jdbc.query(FileSqls.SELECT_IMAGES_BY_PRODUCT_ID, params, imageRowMapper);
	}
	
	
	public ImageDto selectImageByFileId(Integer fileId) {
		Map<String, Object> params = new HashMap<>();
		params.put("fileId", fileId);
		return jdbc.queryForObject(FileSqls.SELECT_IMAGE_BY_FILE_ID, params, imageRowMapper);
	}
}
