package jmaster.io.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jmaster.io.demo.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	//Tìm theo username
	//select user where username = ?
	User findByUsername(String username);
	
	//where name = :s
	Page<User> findByName(String s, Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE u.name LIKE :x")
	Page<User> searchByName(@Param("x") String s, Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE u.name LIKE :x OR u.username LIKE :x")
	Page<User> searchByNameAndUsername(@Param("x") String s, Pageable pageable);
	
	@Modifying
	@Query("DELETE FROM User u WHERE u.username = :x")
	int deleteUser(@Param("x") String username);
	
	//Tự gen lệnh xóa
	void deleteByUsername(String username);
}
