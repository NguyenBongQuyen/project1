package jmaster.io.demo.controller;

import javax.persistence.NoResultException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionController {
//	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(NoResultException.class)
	public String notFound(Exception e) {
//		e.printStackTrace();
//		logger.info("INFO", e);
		log.info("INFO", e);
		return "no-data.html";
	}
}
