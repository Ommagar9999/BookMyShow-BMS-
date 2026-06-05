package com.omizon.BMS.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cities")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , unique = true)
    private String name ;
     private String state;





    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private List<Theater> theaters;
}
