package pl.azebrow.harvest.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long workerId;

    String name;

    String surname;

    Long qrNumber;

    LocalDate insuredFrom;

    LocalDate insuredTo;

    Boolean archived;
}
