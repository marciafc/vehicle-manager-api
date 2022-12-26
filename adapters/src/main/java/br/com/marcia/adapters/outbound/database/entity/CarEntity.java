package br.com.marcia.adapters.outbound.database.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Car")
@Table(name = "Cars")
@Data
public class CarEntity extends VehicleEntity {
}
