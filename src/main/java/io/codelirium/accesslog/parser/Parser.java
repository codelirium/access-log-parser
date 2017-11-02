package io.codelirium.accesslog.parser;

import io.codelirium.accesslog.parser.args.Argument;
import io.codelirium.accesslog.parser.args.CommandLineArguments;
import io.codelirium.accesslog.parser.args.DurationEnum;
import io.codelirium.accesslog.parser.component.AccessLogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;


@SpringBootApplication
public class Parser implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);

	public static final String DATE_FORMAT_WITH_MS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_FORMAT_WITHOUT_MS = "yyyy-MM-dd.HH:mm:ss";


	private AccessLogManager accessLogManager;
	private CommandLineArguments commandLineArguments;


	@Inject
	public Parser(AccessLogManager accessLogManager, CommandLineArguments commandLineArguments) {
		this.accessLogManager = accessLogManager;
		this.commandLineArguments = commandLineArguments;
	}


	@Override
	public void run(String... args) throws Exception {

		List<Argument> arguments = commandLineArguments.parse(args);

		if (arguments.isEmpty()) {
			return;
		}

		arguments.forEach(argument -> LOGGER.debug(argument.toString()));


		accessLogManager.parseAndPersist((String) getArgValue(arguments, 0));


		List<String> ips = accessLogManager.findIPs((Date)         getArgValue(arguments, 1),
													(DurationEnum) getArgValue(arguments, 2),
													(Integer)      getArgValue(arguments, 3));

		ips.forEach(LOGGER::info);
	}


	private Object getArgValue(List<Argument> args, int index) {

		return args.get(index).getValue();

	}


	public static void main(String[] args) {

		SpringApplication.run(Parser.class, args);

	}
}
