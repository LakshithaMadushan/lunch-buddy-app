package com.example.lunchbuddyapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
@MappedSuperclass
public class Base implements Serializable {
    @Serial
    private static final long serialVersionUID = 7653847631129082331L;

//    @Version
    @Column(name = "VERSION_NO")
    private Integer versionNo;

//    @PrePersist
//    @PreUpdate
//    private void beforeSaving() {
//        if (versionNo == null || versionNo == 0) {
//            versionNo = 1;
//        }
//        log.debug("BEFORE SAVING :: {}", this);
//    }
}
