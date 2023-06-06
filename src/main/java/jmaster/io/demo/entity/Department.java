package jmaster.io.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@CreatedDate //auto gen new date
	@Column(updatable = false)
	private Date createAt;
	
	@LastModifiedDate
	private Date updateAt;
	
	// không bắt buộc
	//one department to many users
	//mapBy là tên thuộc tính manytoone bên entity user
	@OneToMany(mappedBy = "department")
	private List<User> users;
}
