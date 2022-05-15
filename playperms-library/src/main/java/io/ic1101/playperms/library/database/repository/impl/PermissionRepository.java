package io.ic1101.playperms.library.database.repository.impl;

import io.ic1101.playperms.library.database.PermissionDatabaseFactory;
import io.ic1101.playperms.library.database.model.Permission;
import io.ic1101.playperms.library.database.repository.Repository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@RequiredArgsConstructor
public class PermissionRepository implements Repository<Permission> {

  private final PermissionDatabaseFactory permissionDatabaseFactory;

  @Override
  public void save(Permission value) {
    Session session = this.permissionDatabaseFactory.getSessionFactory().openSession();
    session.beginTransaction();

    session.persist(value);

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void update(String filterColumn, Object filterValue, String column, Object value) {
    Session session = this.permissionDatabaseFactory.getSessionFactory().openSession();

    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaUpdate<Permission> criteriaUpdate =
        criteriaBuilder.createCriteriaUpdate(Permission.class);
    Root<Permission> root = criteriaUpdate.from(Permission.class);

    criteriaUpdate.set(column, value);
    criteriaUpdate.where(criteriaBuilder.equal(root.get(filterColumn), filterValue));

    session.beginTransaction();

    session.createMutationQuery(criteriaUpdate).executeUpdate();

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void delete(String filterColumn, Object filterValue) {
    Session session = this.permissionDatabaseFactory.getSessionFactory().openSession();

    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaDelete<Permission> criteriaDelete =
        criteriaBuilder.createCriteriaDelete(Permission.class);
    Root<Permission> root = criteriaDelete.from(Permission.class);

    criteriaDelete.where(criteriaBuilder.equal(root.get(filterColumn), filterValue));

    session.beginTransaction();

    session.createMutationQuery(criteriaDelete).executeUpdate();

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public Permission findFirstBy(String filterColumn, Object filterValue) {
    return this.findMultipleBy(filterColumn, filterValue).stream().findFirst().orElse(null);
  }

  @Override
  public List<Permission> findMultipleBy(String filterColumn, Object filterValue) {
    Session session = this.permissionDatabaseFactory.getSessionFactory().openSession();

    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<Permission> criteriaQuery = criteriaBuilder.createQuery(Permission.class);
    Root<Permission> root = criteriaQuery.from(Permission.class);

    criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(filterColumn), filterValue));

    Query<Permission> query = session.createQuery(criteriaQuery);
    List<Permission> results = query.getResultList();

    session.close();

    return results;
  }
}
