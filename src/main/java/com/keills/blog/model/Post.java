package com.keills.blog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name="post_table")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="blog_table.id")
    private Blog blog;

    private String post_name;

    private String text;

    @CreatedDate
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private Date creation_date;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private Date update_date;

    private int rating;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="review_table.id")
    private Set<Review> reviews;
}
