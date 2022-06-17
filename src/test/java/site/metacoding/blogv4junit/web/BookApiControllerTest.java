package site.metacoding.blogv4junit.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import site.metacoding.blogv4junit.domain.book.Book;
import site.metacoding.blogv4junit.domain.book.BookRepository;
import site.metacoding.blogv4junit.service.BookService;
import site.metacoding.blogv4junit.web.dto.BookSaveReqDto;

/**
 * 1. 실제환경과 동일하게 테스트할 수 있다. (@SpringbootTest를 사용하면 모든게 메모리에 다 올라가게 된다 = 통합테스트)
 * 2. 내가 원하는 컨트롤러 , 서비스, 레파지토리만 분리해서 메모리에 올리고 테스트할 수 있다. => 필요한걸 다 일일이 띄워야해서 추천x
 * (@SpringbootTest(class={BookApiController.class, BookService.class,
 * BookRepository.class}))
 * 3. Controller, ControllerAdvice, Filter,
 * WebMvcConfigurer(WEB.xml) @WebMvcTest 배포직전에 최종적으로 테스트해본다?? => 뭐가 뜨는지 확인
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

    @Autowired
    private TestRestTemplate rt;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void bookFindOne_test() throws JsonProcessingException {
        // given
        Long id = 1L;
        bookRepository.save(new Book(1L, "제목1", "메타코딩"));

        // when
        ResponseEntity<String> response = rt.exchange("/api/book/" + id, HttpMethod.GET, null, String.class);

        System.out.println(response.getBody());

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        String author = dc.read("$.author");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("메타코딩", author);
    }

    @Test
    public void bookSave_test() throws JsonProcessingException {
        // given
        BookSaveReqDto reqDto = new BookSaveReqDto();
        reqDto.setTitle("제목1");
        reqDto.setAuthor("메타코딩");

        String body = new ObjectMapper().writeValueAsString(reqDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        // when
        // excute랑 exchange차이?
        ResponseEntity<String> response = rt.exchange("/api/book", HttpMethod.POST, request, String.class);

        System.out.println(response.getBody());

        DocumentContext dc = JsonPath.parse(response.getBody());
        String author = dc.read("$.author");
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("메타코딩", author);
    }

}
