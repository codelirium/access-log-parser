package io.codelirium.accesslog.parser.component;

import io.codelirium.accesslog.parser.model.AccessLogLine;
import io.codelirium.accesslog.parser.repository.AccessLogRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.codelirium.accesslog.parser.Parser.DATE_FORMAT_WITH_MS;
import static io.codelirium.accesslog.parser.args.DurationEnum.hourly;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AccessLogManagerTest {

	private static final String DEFAULT_LOG = "log/access.log";


	private AccessLogManager accessLogManager;
	@Mock
	private AccessLogRepository accessLogRepository;
	@Rule
	public ExpectedException expectedException = ExpectedException.none();


	@Before
	public void setUp() {
		accessLogManager = new AccessLogManager(accessLogRepository);
		reset(accessLogRepository);
	}


	@Test
	public void testThatParseToEntityFailsForNullInput() {
		expectedException.expect(IllegalArgumentException.class);
		accessLogManager.stringToEntity(null);
	}

	@Test
	public void testThatParseToEntityFailsForIncorrectlyFormattedInput() throws ParseException {
		assertEquals(accessLogManager.stringToEntity(getEntity().toString() + "|DUMMY"), new AccessLogLine());
	}

	@Test
	public void testThatParseToEntityIsSuccessfull() throws ParseException {
		assertEquals(accessLogManager.stringToEntity(getEntity().toString()), getEntity());
	}

	@Test
	public void testThatParseAnPersistFailsForNullInput() throws IOException {
		expectedException.expect(IllegalArgumentException.class);
		accessLogManager.parseAndPersist(null);
	}

	@Test
	public void testThatParseAndPersistIsSuccessfull() throws IOException {
		accessLogManager.parseAndPersist(DEFAULT_LOG);
		verify(accessLogRepository, times(116484)).save(Mockito.any(AccessLogLine.class));
	}

	@Test
	public void testThatFindIpsFailsForNullInputs() {
		expectedException.expect(IllegalArgumentException.class);
		accessLogManager.findIPs(null, null, null);
	}

	@Test
	public void testThatFindIpsIsSuccessfull() {
		accessLogManager.findIPs(new Date(), hourly, 123);
		verify(accessLogRepository, times(1)).findIpsAboveRequestCountWithinDateRange(anyInt(), any(Date.class), any(Date.class));
	}


	private AccessLogLine getEntity() throws ParseException {
		AccessLogLine accessLogLine = new AccessLogLine();

		accessLogLine.setTimestamp(new SimpleDateFormat(DATE_FORMAT_WITH_MS).parse("2017-01-01 00:00:00.000"));
		accessLogLine.setIp("127.0.0.1");
		accessLogLine.setHttpRequest("\"GET / HTTP/1.1\"");
		accessLogLine.setHttpCode(Integer.valueOf("200"));
		accessLogLine.setUserAgent("\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393\"");

		return accessLogLine;
	}
}
