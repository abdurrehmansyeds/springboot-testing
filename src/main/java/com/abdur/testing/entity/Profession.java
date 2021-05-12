package com.abdur.testing.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Profession{
    @Id
    private Long id;
    private String companyName;
    private String companyAddress;
    @JsonFormat(pattern = "dd:MM:yyyy")
    private LocalDate startedFrom;
    private String designation;
    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @MapsId
    private UserInfo userInfo;

}
