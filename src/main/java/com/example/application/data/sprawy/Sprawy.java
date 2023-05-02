package com.example.application.data.sprawy;

import com.example.application.data.entity.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@ToString
@EqualsAndHashCode
@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Sprawy  extends AbstractEntity {
    private Long id;
    private String  nrSprawy;
    private String temat;
    private LocalDateTime data;
//    private static int version = 3;


    public Sprawy(String nrSprawy, String temat, LocalDateTime data) {
        this.nrSprawy = nrSprawy;
        this.temat = temat;
        this.data = data;
    }

    public Sprawy(Long id, String nrSprawy, String temat, LocalDateTime data) {
        this.id = id;
        this.nrSprawy = nrSprawy;
        this.temat = temat;
        this.data = data;
    }
//    public Sprawy(Long id, String nrSprawy, String temat, LocalDateTime data, int version) {
//        this.id = id;
//        this.nrSprawy = nrSprawy;
//        this.temat = temat;
//        this.data = data;
//        this.version = version;
//    }
}
