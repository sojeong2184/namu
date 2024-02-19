package com.durianfirst.durian.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table (name = "persistent_logins")
public class PersistentLogin { //쿠키값을 생성할 때 필요한 정보들을 보관하기 위해서

    @Column (length = 64, nullable = false)
    private String username;

    @Id
    @Column (length = 64, nullable = false)
    private String series;

    @Column (length = 64, nullable = false)
    private String token;

    @Column (name = "last_used", length = 64, nullable = false)
    private Timestamp lastused; // 생성 시간 자동 기록
}
