package io.codelirium.accesslog.parser.repository;

import io.codelirium.accesslog.parser.model.AccessLogLine;
import io.codelirium.accesslog.parser.repository.core.PagingAndSortingReadWriteRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Transactional
public interface AccessLogRepository extends PagingAndSortingReadWriteRepository<AccessLogLine, Long> {

	final String SQL_FIND_IPS = "SELECT DISTINCT "                     +
									"ALL.IP "                          +
								"FROM "                                +
									"ACCESS_LOG_LINE ALL "             +
								"WHERE "                               +
									"ALL.TSTAMP BETWEEN ?2 AND ?3 "    +
								"GROUP BY "                            +
									"ALL.IP "                          +
								"HAVING COUNT(ALL.HTTP_REQUEST) > ?1 ";

	@Query(nativeQuery = true, value = SQL_FIND_IPS)
	List<String> findIpsAboveRequestCountWithinDateRange(int reqCount, Date startDate, Date endDate);
}
