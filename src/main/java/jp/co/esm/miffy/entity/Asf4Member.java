package jp.co.esm.miffy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "members")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asf4Member {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "idobata_id")
  private String idobataId;

  @Column(name = "skip")
  private boolean skip;

  @Column(name = "note")
  private String note;

  @Column(name = "is_cleaner")
  private boolean isCleaner;
}
