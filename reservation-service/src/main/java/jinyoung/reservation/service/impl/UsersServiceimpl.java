package jinyoung.reservation.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import jinyoung.reservation.dao.*;
import jinyoung.reservation.domain.*;
import jinyoung.reservation.service.*;

@Service
public class UsersServiceimpl implements UsersService {
	
	@Autowired
	UsersDao usersDao;
	
	@Override
	public Collection<Users> getAll() {
		return usersDao.selectAll();
	}

}
