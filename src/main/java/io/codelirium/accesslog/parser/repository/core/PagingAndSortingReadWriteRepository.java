package io.codelirium.accesslog.parser.repository.core;

import org.springframework.data.repository.NoRepositoryBean;
import java.io.Serializable;


@NoRepositoryBean
public interface PagingAndSortingReadWriteRepository<T, ID extends Serializable> extends PagingAndSortingReadOnlyRepository<T, ID> {

	<S extends T> S save(final S entity);

	<S extends T> S saveAndFlush(final S entity);

	void flush();

	<S extends T> Iterable<S> save(final Iterable<S> entities);
}
