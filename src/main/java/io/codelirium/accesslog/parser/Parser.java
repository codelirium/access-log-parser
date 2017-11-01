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


	@Inject
	private AccessLogManager accessLogManager;

	@Inject
	private CommandLineArguments commandLineArguments;


	@Override
	public void run(String... args) throws Exception {

		List<Argument> arguments = commandLineArguments.parse(args);

		if (arguments.isEmpty()) {
			return;
		}

		arguments.forEach(argument -> LOGGER.debug(argument.toString()));


		accessLogManager.parseAndPersist((String) arguments.get(0).getValue());


		List<String> ips = accessLogManager.findIPs((Date)         arguments.get(1).getValue(),
													(DurationEnum) arguments.get(2).getValue(),
													(Integer)      arguments.get(3).getValue());

		ips.forEach(LOGGER::info);
	}


	public static void main(String[] args) {

		SpringApplication.run(Parser.class, args);

	}
}
