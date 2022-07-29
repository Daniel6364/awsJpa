package com.mzcloud.awsjpa.web;

import com.mzcloud.awsjpa.domain.posts.Posts;
import com.mzcloud.awsjpa.domain.posts.PostsRepository;
import com.mzcloud.awsjpa.web.dto.PostsSaveRequestDto;
import com.mzcloud.awsjpa.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void posts_save() {

        String title = "title";
        String content = " content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title(title).content(content).author("author").build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(title);
        assertThat(postsList.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void posts_update() {

        Posts posts = postsRepository.save(Posts.builder().title("title").content("content").author("author").build());
        System.out.println("\n==// posts : " + posts.toString());

        Long updateId = posts.getId();
        String expectedTitle =  "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title(expectedTitle).content(expectedContent).build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestDtoHttpEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestDtoHttpEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(postsList.get(0).getContent()).isEqualTo(expectedContent);


    }

    @Test
    public void posts_update_mock() {

        String mockTitle = "mock_title";
        String mockContent = "mock_content";
        String mockAuthor = "mock_author";

        // mock 객체 생성
        PostsRepository mockPostRepository = Mockito.mock(PostsRepository.class);

        System.out.println("\n==// mockPostRepository : " + mockPostRepository.toString());

        // save data
//        Posts posts = mockPostRepository.save(Posts.builder().title(mockTitle).content(mockContent).author(mockAuthor).build());
        Posts posts = new Posts();
        posts.setId(66L);
        posts.setTitle(mockTitle);
        posts.setContent(mockContent);
        posts.setAuthor(mockAuthor);

        Mockito.when(posts.getTitle()).thenReturn(mockTitle);
        System.out.println("0. ==// posts : " + posts.toString());

        Long updateId = posts.getId();
        System.out.println("1. ==// updateId : " + updateId);


        // build data
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title("mock_title").content("mock_content").build();
        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestDtoHttpEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestDtoHttpEntity, Long.class);



/*
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(mockTitle);
        assertThat(postsList.get(0).getContent()).isEqualTo(mockContent);
*/


    }
}