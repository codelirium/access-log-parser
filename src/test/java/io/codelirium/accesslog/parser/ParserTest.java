package io.codelirium.accesslog.parser;

import io.codelirium.accesslog.parser.args.CommandLineArguments;
import io.codelirium.accesslog.parser.component.AccessLogManager;
import io.codelirium.accesslog.parser.model.AccessLogLine;
import io.codelirium.accesslog.parser.repository.AccessLogRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.LinkedList;

import static io.codelirium.accesslog.parser.args.CommandLineArgumentsTest.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ParserTest {

	private static final String ARG_EMPTY = "";

	private Parser parser;
	@Mock
	private AccessLogRepository accessLogRepository;


	@Before
	public void setUp() {
		parser = new Parser(new AccessLogManager(accessLogRepository), new CommandLineArguments());
		reset(accessLogRepository);
		doReturn(new AccessLogLine()).when(accessLogRepository).save(any(AccessLogLine.class));
		doReturn(new LinkedList<>()).when(accessLogRepository).findIpsAboveRequestCountWithinDateRange(anyInt(), any(Date.class), any(Date.class));
	}


	@Test
	public void testThatParserBehavesCorrectlyScenarioOne() throws Exception {
		parser.run(getAccessLogArgumentString(), getStartDateArgumentString(), getDurationArgumentString(), getThresholdArgumentString());
		verify(accessLogRepository, times(116484)).save(any(AccessLogLine.class));
		verify(accessLogRepository, times(1)).findIpsAboveRequestCountWithinDateRange(anyInt(), any(Date.class), any(Date.class));
	}

	@Test
	public void testThatParserBehavesCorrectlyScenarioTwo() throws Exception {
		parser.run(ARG_EMPTY, getStartDateArgumentString(), getDurationArgumentString(), getThresholdArgumentString());
		verify(accessLogRepository, times(0)).save(any(AccessLogLine.class));
		verify(accessLogRepository, times(0)).findIpsAboveRequestCountWithinDateRange(anyInt(), any(Date.class), any(Date.class));
	}

	@Test
	public void testThatParserBehavesCorrectlyScenarioThree() throws Exception {
		parser.run(getAccessLogArgumentString(), ARG_EMPTY, getDurationArgumentString(), getThresholdArgumentString());
		verify(accessLogRepository, times(0)).save(any(AccessLogLine.class));
		verify(accessLogRepository, times(0)).findIpsAboveRequestCountWithinDateRange(anyInt(), any(Date.class), any(Date.class));
	}

	@Test
	public void testThatParserBehavesCorrectlyScenarioFour() throws Exception {
		parser.run(getAccessLogArgumentString(), getStartDateArgumentString(), ARG_EMPTY, getThresholdArgumentString());
		verify(accessLogRepository, times(0)).save(any(AccessLogLine.class));
		verify(accessLogRepository, times(0)).findIpsAboveRequestCountWithinDateRange(anyInt(), any(Date.class), any(Date.class));
	}

	@Test
	public void testThatParserBehavesCorrectlyScenarioFive() throws Exception {
		parser.run(getAccessLogArgumentString(), getStartDateArgumentString(), getDurationArgumentString(), ARG_EMPTY);
		verify(accessLogRepository, times(0)).save(any(AccessLogLine.class));
		verify(accessLogRepository, times(0)).findIpsAboveRequestCountWithinDateRange(anyInt(), any(Date.class), any(Date.class));
	}
}
