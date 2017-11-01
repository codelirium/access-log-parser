package io.codelirium.accesslog.parser.args;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static io.codelirium.accesslog.parser.Parser.DATE_FORMAT_WITHOUT_MS;


@Component
public class CommandLineArguments {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineArguments.class);

	protected static final String ARG_DELIMITER = "=";
	protected static final String ARG_ALL_FILE  = "--accesslog";
	protected static final String ARG_STARTDATE = "--startDate";
	protected static final String ARG_DURATION  = "--duration";
	protected static final String ARG_THRESHOLD = "--threshold";


	public List<Argument> parse(String... args) throws Exception {

		List<Argument> arguments = new ArrayList<>(4);


		int found = 0;

		for (String arg : args) {

			if (!arg.contains(ARG_DELIMITER)) break;

			String value = arg.split(ARG_DELIMITER)[1];

			if (arg.contains(ARG_ALL_FILE)) {

				found++;
				Argument<String> argument = new Argument<>(ARG_ALL_FILE, value);
				arguments.add(argument);

			} else if (arg.contains(ARG_STARTDATE)) {

				found++;
				try {
					Argument<Date> argument = new Argument<>(ARG_STARTDATE, new SimpleDateFormat(DATE_FORMAT_WITHOUT_MS).parse(value));
					arguments.add(argument);
				} catch (ParseException e) {
					LOGGER.error(e.getMessage());
				}
			} else if (arg.contains(ARG_DURATION)) {

				found++;
				Argument<DurationEnum> argument = new Argument<>(ARG_DURATION, DurationEnum.valueOf(value));
				arguments.add(argument);

			} else if (arg.contains(ARG_THRESHOLD)) {

				found++;
				Argument<Integer> argument = new Argument<>(ARG_THRESHOLD, Integer.valueOf(value));
				arguments.add(argument);

			}
		}

		if (found != 4) {

			LOGGER.error("Some arguments are missing. The arguments are: {} / {} / {} / {}.", ARG_ALL_FILE, ARG_STARTDATE, ARG_DURATION, ARG_THRESHOLD);

			return new LinkedList<>();
		}


		return arguments;
	}
}
