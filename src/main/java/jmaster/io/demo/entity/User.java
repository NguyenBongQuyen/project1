package jmaster.io.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
//ORM framework: Object - Record Table
//JPA - Hibernate
//JDBC - MySQL
@Data
@Table(name = "user") //Map to table SQL
@Entity //BEAN: new object
public class User {
	//Map
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; //0
	
	//@ManyToOnebắt buộc kiểu dữ liệu Entity
	//Many user to one department
	@Column(name = "department_id")
	private String department;
	
	private int age;
	private String name;
	// luu ten file path
	private String avatarURL;
	@Column(unique = true)
	private String username;
	
	private String password;
	
	@Temporal(TemporalType.DATE)
	private Date bỉrthdate;
	
}
