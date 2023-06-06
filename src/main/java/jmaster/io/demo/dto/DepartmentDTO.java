package jmaster.io.demo.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DepartmentDTO {
	private int id;
	
	@NotBlank(message = "{not.blank}")
	private String name;
	private Date createdAt;
	private Date updatedAt;
	
}
