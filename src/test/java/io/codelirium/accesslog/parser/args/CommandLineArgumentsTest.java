package io.codelirium.accesslog.parser.args;

import io.codelirium.accesslog.parser.component.AccessLogManager;
import io.codelirium.accesslog.parser.component.AccessLogManagerTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static io.codelirium.accesslog.parser.Parser.DATE_FORMAT_WITHOUT_MS;
import static io.codelirium.accesslog.parser.args.CommandLineArguments.*;
import static io.codelirium.accesslog.parser.args.DurationEnum.daily;
import static io.codelirium.accesslog.parser.component.AccessLogManagerTest.DEFAULT_LOG;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
public class CommandLineArgumentsTest {

	private CommandLineArguments commandLineArguments;


	@Before
	public void setUp() {
		commandLineArguments = new CommandLineArguments();
	}


	@Test
	public void testThatArgumentParsingIsSuccessfull() throws Exception {
		String argAccessLog = getAccessLogArgumentString();
		String argStartDate = getStartDateArgumentString();
		String argDuration  = getDurationArgumentString();
		String argThreshold = getThresholdArgumentString();

		List<Argument> arguments = commandLineArguments.parse(argAccessLog, argStartDate, argDuration, argThreshold);

		assertTrue(arguments.size() == 4);
		assertTrue(arguments.contains(getAccessLogArgumentObject()));
		assertTrue(arguments.contains(getStartDateArgumentObject()));
		assertTrue(arguments.contains(getDurationArgumentObject()));
		assertTrue(arguments.contains(getThresholdArgumentObject()));
	}

	@Test
	public void testThatArgumentParsingFailsScenarioOne() throws Exception {
		String argStartDate = getStartDateArgumentString();
		String argDuration  = getDurationArgumentString();
		String argThreshold = getThresholdArgumentString();

		List<Argument> arguments = commandLineArguments.parse("", argStartDate, argDuration, argThreshold);

		assertTrue(arguments.isEmpty());
	}

	@Test
	public void testThatArgumentParsingFailsScenarioTwo() throws Exception {
		String argAccessLog = getAccessLogArgumentString();
		String argDuration  = getDurationArgumentString();
		String argThreshold = getThresholdArgumentString();

		List<Argument> arguments = commandLineArguments.parse(argAccessLog, "", argDuration, argThreshold);

		assertTrue(arguments.isEmpty());
	}

	@Test
	public void testThatArgumentParsingFailsScenarioThree() throws Exception {
		String argAccessLog = getAccessLogArgumentString();
		String argStartDate = getStartDateArgumentString();
		String argThreshold = getThresholdArgumentString();

		List<Argument> arguments = commandLineArguments.parse(argAccessLog, argStartDate, "", argThreshold);

		assertTrue(arguments.isEmpty());
	}

	@Test
	public void testThatArgumentParsingFailsScenarioFour() throws Exception {
		String argAccessLog = getAccessLogArgumentString();
		String argStartDate = getStartDateArgumentString();
		String argDuration  = getDurationArgumentString();

		List<Argument> arguments = commandLineArguments.parse(argAccessLog, argStartDate, argDuration, "");

		assertTrue(arguments.isEmpty());
	}


	public static String getAccessLogArgumentString() {
		return String.format("%s%s%s", ARG_ALL_FILE, ARG_DELIMITER, DEFAULT_LOG);
	}

	public static Argument<String> getAccessLogArgumentObject() {
		return new Argument<>(ARG_ALL_FILE, DEFAULT_LOG);
	}

	public static String getStartDateArgumentString() {
		return String.format("%s%s2017-01-01.00:00:00", ARG_STARTDATE, ARG_DELIMITER);
	}

	public static Argument<Date> getStartDateArgumentObject() throws ParseException {
		return new Argument<>(ARG_STARTDATE, new SimpleDateFormat(DATE_FORMAT_WITHOUT_MS).parse("2017-01-01.00:00:00"));
	}

	public static String getDurationArgumentString() {
		return String.format("%s%s%s", ARG_DURATION, ARG_DELIMITER, daily.name());
	}

	public static Argument<DurationEnum> getDurationArgumentObject() {
		return new Argument<>(ARG_DURATION, DurationEnum.valueOf(daily.name()));
	}

	public static String getThresholdArgumentString() {
		return String.format("%s%s500", ARG_THRESHOLD, ARG_DELIMITER);
	}

	public static Argument<Integer> getThresholdArgumentObject() {
		return new Argument<>(ARG_THRESHOLD, Integer.valueOf("500"));
	}
}
