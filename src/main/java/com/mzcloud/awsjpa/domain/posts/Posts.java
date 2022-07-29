package com.mzcloud.awsjpa.domain.posts;

import com.mzcloud.awsjpa.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

// 클래스 내 모든 필드의 getter메소드 자동 생성
@NoArgsConstructor
@Getter
@Setter
// 기본 생성자(Constructor) 자동 추가
@Entity // 테이블과 링크될 클래스임을 나타낸다. 클래스의 카멜케이스 이름을 스네이크케이스 테이블로 이름을 매칭한다. (EX. SalesManager.java -> sales_manager table)
public class Posts extends BaseTimeEntity {

    @Id // 해당 테이블의 PK 필드를 나타낸다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false) // PK 생성 규칙을 나타낸다.
    private Long id;

    @Column(length = 500, nullable = false) // 테이블의 칼럼, 굳이 선언 없이도 클래스의 필드는 모두 컬럼이 된다. 기본값 이외 추가 변경 필요시 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 클래스의 빌더 패턴 클래스 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
