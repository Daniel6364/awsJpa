package com.mzcloud.awsjpa.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After // 단위 테스트가 끝날때 마다 수행되는 메소드를 지정, 여러 테스타가 동시에 수행되면 데이터가 그대로 남아있어 다음 테스트 실행 시 테스트가 실팰할 수 있다.
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void contents_load() {
        String title = "테스트 타이틀";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder().title(title).content(content).author("author").build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }

    @Test
    public void baseTimeEntity() {

        LocalDateTime dateTime = LocalDateTime.of(2022, 7, 29, 0, 0);

        postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);

        System.out.println("1. ==> createDate : " + posts.getCreatedDate());
        System.out.println("2. ==> modifiedDate : " + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(dateTime);
        assertThat(posts.getModifiedDate()).isAfter(dateTime);
    }
}