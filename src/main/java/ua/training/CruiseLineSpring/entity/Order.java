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
	private OrderStatus status;
	
	public void denied() {
		this.setStatus(OrderStatus.DENIED);
	}
	public void cancel() {
		this.setStatus(OrderStatus.CANCELED);
	}
	public void pay() {
		this.setStatus(OrderStatus.PAID);
	}
	public void start() {
		this.setStatus(OrderStatus.IN_PROCESS);
	}
	public void finish() {
		this.setStatus(OrderStatus.FINISHED);
	}
	public void confirm() {
		this.setStatus(OrderStatus.WATING_PAYMENT);
	}
}