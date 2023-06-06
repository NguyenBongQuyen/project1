package jmaster.io.demo.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SearchTicketDTO extends SearchDTO{
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date start;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date end;
	
	private boolean status;
	
	private Integer departmentId;
}
