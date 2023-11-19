package com.keills.blog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="review_table")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name="user_table.id")
    private User user;

    @ManyToOne
    @JoinColumn(name="post_table.id")
    private Post post;

    private String text;

    @CreatedDate
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private Date creationDate;

    private int rating;


}
