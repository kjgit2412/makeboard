package org.koreait.models.board;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardDbData {

    // 게시글과 DB와 연동되어 관리되는 항목들

    private long id;
    private String poster;
    private String subject;
    private String content;
    private LocalDateTime regiDate;
    private LocalDateTime modyData;

}
