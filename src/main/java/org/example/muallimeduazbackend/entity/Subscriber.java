package org.example.muallimeduazbackend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Subscriber{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    @CreationTimestamp
    private LocalDateTime createdDate;

}
