package com.durianfirst.durian.repository;

import com.durianfirst.durian.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface HeartRepository extends JpaRepository<Heart, Long>, QuerydslPredicateExecutor<Heart> {
}
