package jmaster.io.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tiketId;

	private String clientName;
	private String clientPhone;
	private String content;
	private boolean status;
	private Date processDate;

	@ManyToOne
	private Department department;
	
	@CreatedDate
	@Column(updatable = false)
//	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date createdAt;
	
}