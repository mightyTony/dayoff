package kr.tony.dayoff.util;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name = "created_date")
    @Comment("생성 일자/가입 일자")
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    @Comment("수정 일자/변경 일자")
    private LocalDate modifiedDate;

}
