package com.abdur.testing.entity.manytomany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompositeEntityIds implements Serializable {
    private Long postId;
    private Long tagId;

}
