package site.metacoding.blogv4junit.web.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.blogv4junit.domain.book.Book;

// ReqDto의 경우 무조건 빈생성자 + 세터
@Getter // 테스트할 때만 확인을 위해 필요한 어노테이션이다.
@Setter
public class BookSaveReqDto {

    private String title;
    private String author;

    public BookSaveReqDto(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public BookSaveReqDto() {
    }

    public Book toEntity() {
        return Book
                .builder()
                .title(this.title)
                .author(this.author)
                .build();
    }
}
