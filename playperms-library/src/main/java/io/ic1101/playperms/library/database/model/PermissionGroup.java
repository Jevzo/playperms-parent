package io.ic1101.playperms.library.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  private String chatPrefix;
  private String tabPrefix;

  private int weight;

  private boolean defaultGroup;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Permission> permissions = new ArrayList<>();
}
