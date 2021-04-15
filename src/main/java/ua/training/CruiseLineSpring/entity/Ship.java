package ua.training.CruiseLineSpring.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ship {
	@Id
	@GeneratedValue(strategy = SEQUENCE)
	private Long id;

}
