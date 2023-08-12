package org.koreait.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.koreait.controllers.board.BoardField;
import org.koreait.models.board.BoardAddService;
import org.koreait.models.board.BoardDbData;
import org.koreait.models.board.BoardFieldValidException;
import org.koreait.models.board.BoardViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("게시글 추가 서비스 테스트")
@Transactional
public class BoardAddServiceTest {


    @Autowired
    private BoardAddService boardAddService;

    @Autowired
    private BoardViewService boardViewService;

    private BoardField boardField;

    @BeforeEach
    void init() {
        boardField = getField();
    }

    private BoardField getField() {   // 테스트용 BoardField
        BoardField boardField = new BoardField();
        boardField.setPoster("TDD작성자");
        boardField.setSubject("TDD제목");
        boardField.setContent("TDD내용");

        return boardField;
    }
    @Test
    @DisplayName("게시글 추가, 수정 시에 성공 여부 테스트 : 성공하면 예외발생하지 않음")
    void addSuccessTest() {
        assertDoesNotThrow(() -> {
            boardAddService.addBoard(boardField);
        });
    }

    @Test
    @DisplayName("게시글 항목 검증 테스트 : 검증 실패시에 BoardFieldValidException 발생")
    void boardFieldTest() {
        assertAll(
                () -> {
                    // poster가 null
                    boardField = getField();
                    boardField.setPoster(null);
                    requiredFieldTest(boardField, "작성자");
                },
                () -> {
                    // poster가 빈값
                    boardField = getField();
                    boardField.setPoster("     ");
                    requiredFieldTest(boardField, "작성자");
                },
                () -> {
                    // subject가 null
                    boardField = getField();
                    boardField.setSubject(null);
                    requiredFieldTest(boardField, "제목");
                },
                () -> {
                    // subject가 빈값
                    boardField = getField();
                    boardField.setSubject("     ");
                    requiredFieldTest(boardField, "제목");
                },
                () -> {
                    // content가 null
                    boardField = getField();
                    boardField.setContent(null);
                    requiredFieldTest(boardField, "내용");
                },
                () -> {
                    // content가 빈값
                    boardField = getField();
                    boardField.setContent("    ");
                    requiredFieldTest(boardField, "내용");
                }
        );
    }

    private void requiredFieldTest(BoardField boardField, String message) {
        BoardFieldValidException thrown = assertThrows(BoardFieldValidException.class, () -> {
            boardAddService.addBoard(boardField);
        });
        assertTrue(thrown.getMessage().contains(message));
    }

    @Test
    @DisplayName("게시글 저장 검증 테스트 : 전후 데이터의 일치 시에 예외발생하지 않음")
    void afterEuqalTest() {
        boardAddService.addBoard(boardField);
        BoardDbData result = boardViewService.getBoard(boardField.getId());

        assertAll(
           () -> assertEquals(boardField.getPoster(), result.getPoster()),
           () -> assertEquals(boardField.getSubject(), result.getSubject()),
           () -> assertEquals(boardField.getContent(), result.getContent())
        );
    }


    @Test
    @DisplayName("게시글 조회 성공 여부 테스트 : 성공하면 예외발생하지 않음")
    void getBoardDataSuccessTest() {
        assertDoesNotThrow(() -> {
            boardViewService.getBoard(boardField.getId());
        });
    }

}
