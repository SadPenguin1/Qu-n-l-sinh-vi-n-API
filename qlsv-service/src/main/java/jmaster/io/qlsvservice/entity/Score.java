package jmaster.io.qlsvservice.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "score")
@Data
@EqualsAndHashCode(callSuper = false)
public class Score  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(insertable = false)
	private Integer id;

	private double score;

	@ManyToOne
	private Student student;
	
	@ManyToOne
	private Course course;
}
