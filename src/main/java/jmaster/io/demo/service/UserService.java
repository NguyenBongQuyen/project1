package jmaster.io.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jmaster.io.demo.dto.PageDTO;
import jmaster.io.demo.dto.SearchDTO;
import jmaster.io.demo.dto.UserDTO;
import jmaster.io.demo.entity.User;
import jmaster.io.demo.repository.DepartmentRepo;
import jmaster.io.demo.repository.UserRepo;

//@Component
@Service //Tạo bean: new UserService, quản lý SpringContainer
public class UserService {
//	private static List<User> list = new ArrayList<>();
//	
//	public void create(User user) {
//		list.add(user);
//	}
//	
//	public List<User> getAll() {
//		return list;
//	}
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	DepartmentRepo departmentRepo;
	
	@Transactional
	public void create(UserDTO userDTO) {
		User user = new ModelMapper().map(userDTO, User.class);
		//save entity
		userRepo.save(user);
	}
	
	@Transactional
	public void delete(int id) {
		userRepo.deleteById(id);
	}
	
	@Transactional
	public void update(UserDTO userDTO) {
		//check
		User currentUser = userRepo.findById(userDTO.getId()).orElse(null);
				
		if(currentUser != null) {
			currentUser.setName(userDTO.getName());
			currentUser.setAge(userDTO.getAge());
			
			userRepo.save(currentUser);
		}
	}
	
	@Transactional
	public void updatePassword(UserDTO userDTO) {
		//check
		User currentUser = userRepo.findById(userDTO.getId()).orElse(null);
		
		if(currentUser != null) {
			currentUser.setPassword(userDTO.getPassword());
			
			userRepo.save(currentUser);
		}
	}
	
	private UserDTO convert(User user) {
//		UserDTO userDTO = new UserDTO();
//		
//		user.setId(userDTO.getId());
//		user.setName(userDTO.getName());
//		user.setAge(userDTO.getAge());
//		user.setUsername(userDTO.getUsername());
//		user.setPassword(userDTO.getPassword());
//		user.setAvatarURL(userDTO.getAvatarURL());
//		
//		return userDTO;
		
		return new ModelMapper().map(user, UserDTO.class);
	}
	
	public UserDTO getById(int id) {
		//Optional
		User user = userRepo.findById(id).orElse(null);
		
		if (user != null) {
			return convert(user);
		}
		return null;
	}
	
	
	public List<UserDTO> getAll() {
		List<User> userList = userRepo.findAll();
		userList.forEach(u -> u.setDepartment(departmentRepo.getReferenceById(Integer.parseInt(u.getDepartment())).getName()));
//		List<UserDTO> userDTOs = new ArrayList<>();
//		for (User user : userList) {
//			userDTOs.add(convert(user));
//		}
//		return userDTOs;
		
		//java 8
		return userList.stream().map(u -> convert(u)).collect(Collectors.toList());
	}
	
	//T: List<UserDTO>
	public PageDTO<List<UserDTO>> searchName(SearchDTO searchDTO) {
		Sort sortBy = Sort.by("name").ascending().and(Sort.by("age").descending());
		
		if(StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}
		if(searchDTO.getCurrentPage() == null)
			searchDTO.setCurrentPage(0);
		if(searchDTO.getSize() == null)
			searchDTO.setSize(3);
			
		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);
		
		Page<User> page = userRepo.searchByName("%" + searchDTO.getKeyword() + "%", pageRequest);
		
		PageDTO<List<UserDTO>> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());
		
		//List<User> users = page.getContent();
		List<UserDTO> userDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		
		pageDTO.setData(userDTOs);
		
		return pageDTO;
	}
}
