package br.ufrn.imd.framework.models;

import br.ufrn.imd.framework.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="roles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Role extends AbstractEntity {



    @Enumerated(EnumType.STRING)
    private RoleName name;

}
