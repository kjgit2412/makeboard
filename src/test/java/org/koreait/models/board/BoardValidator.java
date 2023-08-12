package org.koreait.models.board;

import org.koreait.controllers.board.BoardField;
import org.springframework.stereotype.Component;

@Component
public class BoardValidator {

    public void check(BoardField boardField) {
        checkRequired(boardField.getPoster(), new BoardFieldValidException("작성자를 입력하세요"));
        checkRequired(boardField.getSubject(), new BoardFieldValidException("제목를 입력하세요"));
        checkRequired(boardField.getContent(), new BoardFieldValidException("내용를 입력하세요"));
    }

    void checkRequired(String str, BoardFieldValidException e) {
        if(str==null || str.isBlank()) {
            throw e;
        }
    }

}
