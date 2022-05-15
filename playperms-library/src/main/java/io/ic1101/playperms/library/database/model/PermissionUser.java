package io.ic1101.playperms.library.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionUser {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String uuid;
  private String name;

  private long length;
  private long endMillis;

  @OneToOne(fetch = FetchType.EAGER)
  private PermissionGroup permissionGroup;
}
