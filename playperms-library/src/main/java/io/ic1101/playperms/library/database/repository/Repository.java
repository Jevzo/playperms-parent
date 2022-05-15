package io.ic1101.playperms.library.database.repository;

import java.util.List;

public interface Repository<T> {

  void save(T value);

  void update(String filterColumn, Object filterValue, String column, Object value);

  void delete(String filterColumn, Object filterValue);

  T findFirstBy(String filterColumn, Object filterValue);

  List<T> findMultipleBy(String filterColumn, Object filterValue);
}
