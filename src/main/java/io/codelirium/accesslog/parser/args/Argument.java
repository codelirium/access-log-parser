package io.codelirium.accesslog.parser.args;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Argument<T> {

	private String handle;
	private T      value;

}
