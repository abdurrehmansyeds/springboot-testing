package com.abdur.testing.entity.manytomany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTag {


    @EmbeddedId
    //Two or more Pk ids are embedded in this class
    private CompositeEntityIds id;

    @ManyToOne(fetch = FetchType.LAZY)
    //mentioning the @MapsId() value is important or else it gets confused between two ids
    //defined in the CompositeEntityIds
    @MapsId("postId")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    //mentioning the @MapsId() value is important or else it gets confused between two ids
    //defined in the CompositeEntityIds
    @MapsId("tagId")
    private Tag tag;

    PostTag(Post post,Tag tag){
        this.post = post;
        this.tag = tag;
    }


}
