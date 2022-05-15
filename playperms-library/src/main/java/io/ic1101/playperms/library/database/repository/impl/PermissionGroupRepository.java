package io.ic1101.playperms.library.database.repository.impl;

import io.ic1101.playperms.library.database.PermissionDatabaseFactory;
import io.ic1101.playperms.library.database.model.Permission;
import io.ic1101.playperms.library.database.model.PermissionGroup;
import io.ic1101.playperms.library.database.repository.Repository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Query;
import org.hibernate.Session;

import java.util.List;

@RequiredArgsConstructor
public class PermissionGroupRepository implements Repository<PermissionGroup> {

  private final PermissionDatabaseFactory permissionDatabaseFactory;

  @Override
  public void save(PermissionGroup value) {
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
    CriteriaUpdate<PermissionGroup> criteriaUpdate =
        criteriaBuilder.createCriteriaUpdate(PermissionGroup.class);
    Root<PermissionGroup> root = criteriaUpdate.from(PermissionGroup.class);

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
    CriteriaDelete<PermissionGroup> criteriaDelete =
        criteriaBuilder.createCriteriaDelete(PermissionGroup.class);
    Root<PermissionGroup> root = criteriaDelete.from(PermissionGroup.class);

    criteriaDelete.where(criteriaBuilder.equal(root.get(filterColumn), filterValue));

    session.beginTransaction();

    session.createMutationQuery(criteriaDelete).executeUpdate();

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public PermissionGroup findFirstBy(String filterColumn, Object filterValue) {
    return this.findMultipleBy(filterColumn, filterValue).stream().findFirst().orElse(null);
  }

  @Override
  public List<PermissionGroup> findMultipleBy(String filterColumn, Object filterValue) {
    Session session = this.permissionDatabaseFactory.getSessionFactory().openSession();

    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<PermissionGroup> criteriaQuery =
        criteriaBuilder.createQuery(PermissionGroup.class);
    Root<PermissionGroup> root = criteriaQuery.from(PermissionGroup.class);

    criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(filterColumn), filterValue));

    Query<PermissionGroup> query = session.createQuery(criteriaQuery);
    List<PermissionGroup> results = query.getResultList();

    session.close();

    return results;
  }

  public PermissionGroup addPermission(PermissionGroup permissionGroup, Permission permission) {
    Session session = this.permissionDatabaseFactory.getSessionFactory().openSession();
    session.beginTransaction();

    permissionGroup.getPermissions().add(permission);

    session.merge(permissionGroup);

    session.getTransaction().commit();
    session.close();

    return permissionGroup;
  }

  public PermissionGroup removePermission(PermissionGroup permissionGroup, Permission permission) {
    Session session = this.permissionDatabaseFactory.getSessionFactory().openSession();
    session.beginTransaction();

    permissionGroup.getPermissions().remove(permission);

    session.merge(permissionGroup);

    session.getTransaction().commit();
    session.close();

    return permissionGroup;
  }
}
