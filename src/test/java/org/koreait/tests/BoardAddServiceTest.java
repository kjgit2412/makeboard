package org.koreait.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.koreait.controllers.board.BoardField;
import org.koreait.models.board.BoardAddService;
import org.koreait.models.board.BoardFiledValidException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("게시글 추가 서비스 테스트")
@Transactional
public class BoardAddServiceTest {


    private BoardField boardField;

    private BoardAddService boardAddService;

    @Test
    @DisplayName("게시글 추가, 수정 시에 예외 발생 여부 테스트")
    void addSuccessTest() {
        assertDoesNotThrow(() -> {
            boardAddService.addBoard();
        });
    }

    @Test
    @DisplayName("게시글 항목 검증, 검증 실패시에 BoardFiledValidException 발생")
    void boardFieldTest() {


    }

    private void requiredFieldTest(BoardField boardField, String message) {
        BoardFiledValidException thrown = assertThrows(BoardFiledValidException.class, () -> {
            boardAddService.addBoard();  //ave(data);
        });
        assertTrue(thrown.getMessage().contains(message));
    }
}
