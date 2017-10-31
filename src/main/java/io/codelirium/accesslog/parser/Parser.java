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

	public static final String DEFAULT_LOG = "log/access.log";
	public static final String DATE_FORMAT_WITH_MS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_FORMAT_WITHOUT_MS = "yyyy-MM-dd.HH:mm:ss";


	@Inject
	private AccessLogManager accessLogManager;

	@Inject
	private CommandLineArguments commandLineArguments;


	@Override
	public void run(String... args) throws Exception {

		accessLogManager.parseAndPersist(DEFAULT_LOG);


		List<Argument> arguments = commandLineArguments.parse(args);

		arguments.forEach(argument -> LOGGER.debug(argument.toString()));


		List<String> ips = accessLogManager.findIPs((Date)         arguments.get(0).getValue(),
													(DurationEnum) arguments.get(1).getValue(),
													(Integer)      arguments.get(2).getValue());

		ips.forEach(LOGGER::info);
	}


	public static void main(String[] args) {

		SpringApplication.run(Parser.class, args);

	}
}
