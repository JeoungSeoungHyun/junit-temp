package site.metacoding.blogv4junit.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import site.metacoding.blogv4junit.service.BookService;
import site.metacoding.blogv4junit.web.dto.BookSaveReqDto;

@WebMvcTest(BookApiController.class)
public class BookApiControllerWebMvcTest {

    // 이 상태로 실행하면 의존적인 service가 뜨지 않기 때문에 오류가 발생한다. 추상화를 이용한 mokito => 껍데기

    @Autowired
    private MockMvc mockMvc; // IoC 컨테이너가 들고있기 때문에 들고온다.

    @MockBean // BookApiController가 IoC에 뜬걸 가져오기 때문에 의존성이 있는 service도 실제 IoC에 떠야하기 때문이다?
    private BookService bookService;

    @Test
    public void bookSave_테스트() throws Exception {
        // given
        BookSaveReqDto reqDto = new BookSaveReqDto();
        reqDto.setTitle("제목1");
        reqDto.setAuthor("메타코딩");

        // 오브젝트를 json으로 변환하기
        String body = new ObjectMapper().writeValueAsString(reqDto); // catch를 굳이 내가 제어 x
        System.out.println("===============================");
        System.out.println(body);
        System.out.println("===============================");

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/book")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON) // 내가 던지는 데이터
                        .accept(MediaType.APPLICATION_JSON) // 이런 데이터를 기대해..? 필수값은 아니다.
        ); // 끝에 연결하면 then과 when이 같이 쓰여져서 분리

        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("제목1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("메타코딩"))
                .andDo(MockMvcResultHandlers.print());                                
    }

    @Test
    public void bookFindOne_테스트() {

    }

}
