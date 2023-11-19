package com.keills.blog.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="blog_table")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_table.id")
    private User user;

    private String blogName;

    private String blogInfo;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Post> posts;

    private int overallRating;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private Date updateDate;
}
