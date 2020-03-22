package ru.kk.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "URL")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlTable implements Serializable, DomainModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "URL")
    private String url;
    @Column(name = "SHORT_URL")
    private String shortUrl;
    @Column(name = "TIME")
    private Timestamp time;

}
