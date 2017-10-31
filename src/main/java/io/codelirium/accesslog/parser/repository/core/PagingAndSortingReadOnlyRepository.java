package io.codelirium.accesslog.parser.repository.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import java.io.Serializable;


@NoRepositoryBean
public interface PagingAndSortingReadOnlyRepository<T, ID extends Serializable> extends ReadOnlyRepository<T, ID> {

	Iterable<T> findAll(final Sort sort);

	Page<T> findAll(final Pageable pageable);
}
