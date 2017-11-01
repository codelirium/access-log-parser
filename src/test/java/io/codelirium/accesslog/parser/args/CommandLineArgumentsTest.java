package io.codelirium.accesslog.parser.args;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static io.codelirium.accesslog.parser.args.CommandLineArguments.*;
import static io.codelirium.accesslog.parser.args.DurationEnum.daily;
import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class CommandLineArgumentsTest {

	private CommandLineArguments commandLineArguments;


	@Before
	public void setUp() {
		commandLineArguments = new CommandLineArguments();
	}


	@Test
	public void testThatArgumentParsingIsSuccessfull() throws Exception {
		String argAccessLog = getAccessLogArgument();
		String argStartDate = getStartDateArgument();
		String argDuration  = getDurationArgument();
		String argThreshold = getThresholdArgument();

		List<Argument> arguments = commandLineArguments.parse(argAccessLog, argStartDate, argDuration, argThreshold);

		assertTrue(arguments.size() == 4);
	}

	@Test
	public void testThatArgumentParsingFailsScenarioOne() throws Exception {
		String argStartDate = getStartDateArgument();
		String argDuration  = getDurationArgument();
		String argThreshold = getThresholdArgument();

		List<Argument> arguments = commandLineArguments.parse("", argStartDate, argDuration, argThreshold);

		assertTrue(arguments.isEmpty());
	}

	@Test
	public void testThatArgumentParsingFailsScenarioTwo() throws Exception {
		String argAccessLog = getAccessLogArgument();
		String argDuration  = getDurationArgument();
		String argThreshold = getThresholdArgument();

		List<Argument> arguments = commandLineArguments.parse(argAccessLog, "", argDuration, argThreshold);

		assertTrue(arguments.isEmpty());
	}

	@Test
	public void testThatArgumentParsingFailsScenarioThree() throws Exception {
		String argAccessLog = getAccessLogArgument();
		String argStartDate = getStartDateArgument();
		String argThreshold = getThresholdArgument();

		List<Argument> arguments = commandLineArguments.parse(argAccessLog, argStartDate, "", argThreshold);

		assertTrue(arguments.isEmpty());
	}

	@Test
	public void testThatArgumentParsingFailsScenarioFour() throws Exception {
		String argAccessLog = getAccessLogArgument();
		String argStartDate = getStartDateArgument();
		String argDuration  = getDurationArgument();

		List<Argument> arguments = commandLineArguments.parse(argAccessLog, argStartDate, argDuration, "");

		assertTrue(arguments.isEmpty());
	}


	private String getAccessLogArgument() {
		return String.format("%s%sfoo.log", ARG_ALL_FILE, ARG_DELIMITER);
	}

	private String getStartDateArgument() {
		return String.format("%s%s2017-01-01.00:00:00", ARG_STARTDATE, ARG_DELIMITER);
	}

	private String getDurationArgument() {
		return String.format("%s%s%s", ARG_DURATION, ARG_DELIMITER, daily.name());
	}

	private String getThresholdArgument() {
		return String.format("%s%s500", ARG_THRESHOLD, ARG_DELIMITER);
	}
}
