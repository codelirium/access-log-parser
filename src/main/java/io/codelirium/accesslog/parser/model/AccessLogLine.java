package io.codelirium.accesslog.parser.model;

import io.codelirium.accesslog.parser.model.core.BaseEntity;
import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.codelirium.accesslog.parser.Parser.DATE_FORMAT_WITH_MS;


@Data
@Entity
@Table(name = AccessLogLine.TABLE_NAME)
@AttributeOverride(name = BaseEntity.FIELD_NAME_ID, column = @Column(name = AccessLogLine.COLUMN_NAME_ID))
public class AccessLogLine extends BaseEntity<Long> implements Serializable {

	private static final long serialVersionUID = -657943060587105330L;

	static final String TABLE_NAME     = "ACCESS_LOG_LINE";
	static final String COLUMN_NAME_ID = "ID";


	@Column(name = "TSTAMP", nullable = false, unique = false)
	private Date timestamp;
	@Column(name = "IP", nullable = false, unique = false)
	private String ip;
	@Column(name = "HTTP_REQUEST", nullable = false, unique = false)
	private String httpRequest;
	@Column(name = "HTTP_CODE", nullable = false, unique = false)
	private Integer httpCode;
	@Column(name = "USER_AGENT", nullable = false, unique = false)
	private String userAgent;


	@Override
	public String toString() {
		return new SimpleDateFormat(DATE_FORMAT_WITH_MS).format(timestamp) + "|" + ip + "|" + httpRequest + "|" + httpCode + "|" + userAgent;
	}
}
