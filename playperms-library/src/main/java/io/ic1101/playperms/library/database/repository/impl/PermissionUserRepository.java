package io.ic1101.playperms.library.database.repository.impl;

import io.ic1101.playperms.library.database.PermissionDatabaseFactory;
import io.ic1101.playperms.library.database.model.PermissionGroup;
import io.ic1101.playperms.library.database.model.PermissionUser;
import io.ic1101.playperms.library.database.repository.Repository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@RequiredArgsConstructor
public class PermissionUserRepository implements Repository<PermissionUser> {

  private final PermissionDatabaseFactory permissionDatabaseFactory;

  @Override
  public void save(PermissionUser value) {
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
    CriteriaUpdate<PermissionUser> criteriaUpdate =
        criteriaBuilder.createCriteriaUpdate(PermissionUser.class);
    Root<PermissionUser> root = criteriaUpdate.from(PermissionUser.class);

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
    CriteriaDelete<PermissionUser> criteriaDelete =
        criteriaBuilder.createCriteriaDelete(PermissionUser.class);
    Root<PermissionUser> root = criteriaDelete.from(PermissionUser.class);

    criteriaDelete.where(criteriaBuilder.equal(root.get(filterColumn), filterValue));

    session.beginTransaction();

    session.createMutationQuery(criteriaDelete).executeUpdate();

    session.getTransaction().commit();
    session.close();
  }

  @Override
  public PermissionUser findFirstBy(String filterColumn, Object filterValue) {
    return this.findMultipleBy(filterColumn, filterValue).stream().findFirst().orElse(null);
  }

  @Override
  public List<PermissionUser> findMultipleBy(String filterColumn, Object filterValue) {
    Session session = this.permissionDatabaseFactory.getSessionFactory().openSession();

    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<PermissionUser> criteriaQuery = criteriaBuilder.createQuery(PermissionUser.class);
    Root<PermissionUser> root = criteriaQuery.from(PermissionUser.class);

    criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(filterColumn), filterValue));

    Query<PermissionUser> query = session.createQuery(criteriaQuery);
    List<PermissionUser> results = query.getResultList();

    session.close();

    return results;
  }

  public PermissionUser updateGroup(
      PermissionUser permissionUser, PermissionGroup permissionGroup) {
    return this.updateGroup(permissionUser, permissionGroup, -1);
  }

  public PermissionUser updateGroup(
      PermissionUser permissionUser, PermissionGroup permissionGroup, long length) {
    Session session = this.permissionDatabaseFactory.getSessionFactory().openSession();
    session.beginTransaction();

    permissionUser.setPermissionGroup(permissionGroup);
    permissionUser.setLength(length);

    if (length == -1) {
      permissionUser.setEndMillis(length);
    } else {
      permissionUser.setEndMillis(System.currentTimeMillis() + length);
    }

    session.merge(permissionUser);

    session.getTransaction().commit();
    session.close();

    return permissionUser;
  }

  public List<PermissionUser> findByGroup(long groupId) {
    Session session = this.permissionDatabaseFactory.getSessionFactory().openSession();

    Query<?> query =
        session.createQuery(
            "from PermissionUser permUser join PermissionGroup permGroup on permGroup.id=:permId where permUser.permissionGroup = permGroup");
    query.setParameter("permId", groupId);

    return (List<PermissionUser>) query.list();
  }
}
