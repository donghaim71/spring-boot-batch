package com.batch.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(schema="public", name = "user")
public class User {

	@Id
	@Column(name="user_id")
	private String userId;
	@Column(name="user_name")
	private String userName;
}
