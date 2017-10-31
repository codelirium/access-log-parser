package io.codelirium.accesslog.parser.component;

import io.codelirium.accesslog.parser.args.DurationEnum;
import io.codelirium.accesslog.parser.model.AccessLogLine;
import io.codelirium.accesslog.parser.repository.AccessLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import static io.codelirium.accesslog.parser.Parser.DATE_FORMAT_WITH_MS;


@Component
public class AccessLogManager {

	private  static final Logger LOGGER = LoggerFactory.getLogger(AccessLogManager.class);

	private AccessLogRepository accessLogRepository;


	@Inject
	public AccessLogManager(AccessLogRepository accessLogRepository) {
		this.accessLogRepository = accessLogRepository;
	}


	public void parseAndPersist(final String accessLogFileName) throws IOException {

		Assert.notNull(accessLogFileName, "The access log file name cannot be null.");


		try (Stream<String> stream = Files.lines(Paths.get(accessLogFileName))) {
			LOGGER.info("Started parsing file: {}", accessLogFileName);
			stream.parallel().forEach(line -> accessLogRepository.save(stringToEntity(line)));
		}
	}

	public List<String> findIPs(Date startDate, DurationEnum duration, Integer threshold) {

		Assert.notNull(startDate, "The startDate cannot be null.");
		Assert.notNull(duration, "The duration cannot be null.");
		Assert.notNull(threshold, "The threshold cannot be null.");

		Date endDate;
		switch (duration) {
			case hourly:
			default:
				endDate = new Date(startDate.getTime() + 1 * 3600 * 1000);
				break;
			case daily:
				endDate = new Date(startDate.getTime() + 24 * 3600 * 1000);
		}

		return accessLogRepository.findIpsAboveRequestCountWithinDateRange(threshold, startDate, endDate);
	}

	protected AccessLogLine stringToEntity(final String line) {

		Assert.notNull(line, "The line cannot be null.");


		StringTokenizer tokenizer = new StringTokenizer(line, "|");

		AccessLogLine accessLogLine = new AccessLogLine();

		while(tokenizer.hasMoreTokens() && tokenizer.countTokens() == 5) {
			try {
				accessLogLine.setTimestamp(new SimpleDateFormat(DATE_FORMAT_WITH_MS).parse(tokenizer.nextToken()));
			} catch (ParseException e) {
				LOGGER.error(e.getMessage());
			}

			accessLogLine.setIp(tokenizer.nextToken());
			accessLogLine.setHttpRequest(tokenizer.nextToken());
			accessLogLine.setHttpCode(Integer.valueOf(tokenizer.nextToken()));
			accessLogLine.setUserAgent(tokenizer.nextToken());
		}

		return accessLogLine;
	}
}
