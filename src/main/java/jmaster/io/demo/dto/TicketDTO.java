package jmaster.io.demo.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TicketDTO {
	private Integer Id;
	private String clientName;
	private String clientPhone;
	private String content;
	private boolean status;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date processDate;
	
	private DepartmentDTO department;
	
	private Date createdAt;
	
}