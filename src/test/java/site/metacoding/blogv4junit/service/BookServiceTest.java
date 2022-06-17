package site.metacoding.blogv4junit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import site.metacoding.blogv4junit.domain.book.Book;
import site.metacoding.blogv4junit.domain.book.BookRepository;
import site.metacoding.blogv4junit.web.dto.BookRespDto;
import site.metacoding.blogv4junit.web.dto.BookSaveReqDto;

@ExtendWith(MockitoExtension.class) // IoC를 사용하기 위해?
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    // @Autowired // @datajpa~ 없어서 안된다.
    @Mock // 가짜 객체를 생성하는것
    private BookRepository bookRepository;

    @Test
    public void 책등록하기_test() {
        // given
        BookSaveReqDto reqDto = new BookSaveReqDto();
        reqDto.setTitle("스프링부트1강");
        reqDto.setAuthor("메타코딩");

        // // stub(행동 정의 = 가설) // 메서드를 받는것이다?!
        Mockito.when(bookRepository.save(reqDto.toEntity())).thenReturn(new Book(1L, "스프링부트1강", "메타코딩"));

        // // when(Repository를 써서 테스트해도 되지만 느려지기 때문에 좋지는 않다.)
        BookRespDto respDto = bookService.책등록하기(reqDto);

        // // then
        assertEquals(1L, respDto.getId());
        assertEquals("스프링부트1강", respDto.getTitle());
        assertEquals("메타코딩", respDto.getAuthor());
    }

    public void 책한건가져오기_test() {

        Optional<Book> bookOp = Optional.of(new Book(1L, "스프링부트1강", "메타코딩"));

        // given
        Long id = 1L;

        // stub
        Mockito.when(bookRepository.findById(id)).thenReturn(bookOp);

        // when
        BookRespDto respDto = bookService.책한건가져오기(id);

        // then
        assertEquals(1L, respDto.getId());
        assertEquals("스프링부트1강", respDto.getTitle());
        assertEquals("메타코딩", respDto.getAuthor());
    }
}
