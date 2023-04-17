package jmaster.io.qlsvservice.dto;

import lombok.Data;

@Data
public class ScoreDTO {
	private Integer id;
	
	private double score;
	
	private CourseDTO course;
	
	private StudentDTO student;
	
}