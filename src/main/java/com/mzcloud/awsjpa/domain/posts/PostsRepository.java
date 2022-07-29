package com.mzcloud.awsjpa.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Package : com.mzcloud.awsjpa.domain.posts
 * @Class : PostsRepository
 * @Description :
 * @Date : 2022-07-27
 * @Author : Daniel
 * @Comment : repository란 보통 ibatis, mybatis에서 Dao라고 불리는 DB Layer 접근자를 의미한다.
 *            interface 생성 후 JpaRepository<Entity 클래스, PK 타입> 을 상속하면 기본적인 CRUD 메소드가 자동으로 생성된다.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
