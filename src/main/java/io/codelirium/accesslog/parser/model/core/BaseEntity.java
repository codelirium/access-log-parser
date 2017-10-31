package io.codelirium.accesslog.parser.model.core;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;


@Data
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> {

	public static final String FIELD_NAME_ID = "id";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private ID id;
}
