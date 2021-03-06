package com.faforever.api.data.domain;

import com.faforever.api.data.checks.permission.HasBanRead;
import com.faforever.api.data.checks.permission.HasBanUpdate;
import com.yahoo.elide.annotation.Audit;
import com.yahoo.elide.annotation.Audit.Action;
import com.yahoo.elide.annotation.CreatePermission;
import com.yahoo.elide.annotation.DeletePermission;
import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.annotation.ReadPermission;
import com.yahoo.elide.annotation.UpdatePermission;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ban_revoke")
@Include(rootLevel = true, type = "banRevokeData")
@Setter
@DeletePermission(expression = "Prefab.Role.None")
@ReadPermission(expression = HasBanRead.EXPRESSION)
@CreatePermission(expression = HasBanUpdate.EXPRESSION)
@UpdatePermission(expression = HasBanUpdate.EXPRESSION)
@AttributeOverride(name = "id", column = @Column(name = "ban_id"))
@Audit(action = Action.CREATE, logStatement = "Revoked ban with id `{0}` for player `{1}`", logExpressions = {"${banRevokeData.id}", "${banRevokeData.player}"})
public class BanRevokeData extends AbstractEntity {
  private BanInfo ban;
  private String reason;
  private Player author;

  @OneToOne(mappedBy = "banRevokeData")
  @NotNull
  public BanInfo getBan() {
    return ban;
  }

  @Column(name = "reason")
  @NotNull
  public String getReason() {
    return reason;
  }

  @ManyToOne
  @JoinColumn(name = "author_id")
  @NotNull
  public Player getAuthor() {
    return author;
  }
}
