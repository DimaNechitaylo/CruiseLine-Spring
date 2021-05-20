package ua.training.CruiseLineSpring.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cruise {
	@Id
	@GeneratedValue(strategy = SEQUENCE)
	private Long id;
	private String name;
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="ship_id", nullable = false)
	private Ship ship;
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Port> ports;
	@ManyToMany(fetch = FetchType.LAZY)
	private List<User> passengers;
	private LocalDate start;
	private LocalDate finish;
	
	public void addPassenger(User user) {
		passengers.add(user);
	}
	public void removePassenger(User user) {
		passengers.remove(user);
	}

}
