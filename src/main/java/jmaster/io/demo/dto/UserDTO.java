package jmaster.io.demo.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserDTO {
	private String department;
	
	private int id;
	@Min(value = 1, message = "Tuổi lớn hơn 0")
	private int age;
	@NotBlank(message = "Tên bắt buộc phải nhập")
	private String name;
	private String avatarURL;
	private String username;
	private String password;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date bỉrthdate;
	
	private MultipartFile file;
}
