package org.koreait.models.board;

import lombok.RequiredArgsConstructor;
import org.koreait.controllers.board.BoardField;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;

@Service
@RequiredArgsConstructor
public class BoardAddService {

    private final BoardValidator boardValidator;
    private final JdbcTemplate jdbcTemplate;

    public boolean addBoard(BoardField boardField) {

        // 기본 field chcek
        boardValidator.check(boardField);

        // Id를 읽어서 등록된 번호면 수정, 등록된 번호가 아니면 추가
        long id = boardField.getId();
        int affectedRows = 0;

        if (id > 0) {   // 수정
            String sql = "UPDATE BOARD_DATA " +
                    " SET " +
                    " POSTER = ?, " +
                    " SUBJECT = ?, " +
                    " CONTENT = ?, " +
                    " MODDT = SYSDATE " +
                    " WHERE ID = ?";

            affectedRows = jdbcTemplate.update(sql, boardField.getPoster(),
                    boardField.getSubject(), boardField.getContent(), boardField.getId());

        } else { // 추가
            String sql = "INSERT INTO BOARD_DATA (ID, POSTER, SUBJECT, CONTENT) " +
                    " VALUES (BOARD_DATA_SEQ.nextval, ?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            affectedRows = jdbcTemplate.update(con -> {
                PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"ID"});
                pstmt.setString(1, boardField.getPoster());
                pstmt.setString(2, boardField.getSubject());
                pstmt.setString(3, boardField.getContent());

                return pstmt;
            }, keyHolder);

            id = keyHolder.getKey().longValue();
        }

        boardField.setId(id);

        return affectedRows > 0;
    }


}
