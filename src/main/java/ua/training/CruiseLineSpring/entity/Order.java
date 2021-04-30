package ua.training.CruiseLineSpring.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = SEQUENCE)
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cruise_id", nullable = false)
	private Cruise cruise;
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public void denied() {
		this.setStatus(Status.DENIED);
	}
	public void pay() {
		this.setStatus(Status.PAID);
	}
	public void finish() {
		this.setStatus(Status.FINISHED);
	}
	public void confirm() {
		this.setStatus(Status.WATING_PAYMENT);
	}
}