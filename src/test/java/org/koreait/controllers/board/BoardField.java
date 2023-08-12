package org.koreait.controllers.board;

import lombok.Builder;
import lombok.Data;

@Data
public class BoardField {

    // 게시글에서 입력받는 필수 fields

    private long id;
    private String poster;
    private String subject;
    private String content;

}
