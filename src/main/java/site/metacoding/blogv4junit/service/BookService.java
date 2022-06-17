package site.metacoding.blogv4junit.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv4junit.domain.book.Book;
import site.metacoding.blogv4junit.domain.book.BookRepository;
import site.metacoding.blogv4junit.web.dto.BookRespDto;
import site.metacoding.blogv4junit.web.dto.BookSaveReqDto;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto 책등록하기(BookSaveReqDto reqDto) {
        System.out.println("되나?");
        Book bookEntity = bookRepository.save(reqDto.toEntity());

        return new BookRespDto().toDto(bookEntity);
    }

    public BookRespDto 책한건가져오기(Long id) {
        // 같은 코드
        // Book bookEntity = bookRepository.findById(id).orElseThrow(() -> {
        // return new RuntimeException("해당 책을 가져올 수 없습니다.");
        // });
        // return new BookRespDto().toDto(bookEntity);

        Optional<Book> bookOp = bookRepository.findById(id);

        if (bookOp.isPresent()) {
            return new BookRespDto().toDto(bookOp.get());
        }
        throw new RuntimeException("해당 책을 가져올 수 없습니다.");
    }
}
