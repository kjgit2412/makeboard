package org.koreait.models.board;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class BoardViewService {


    private final BoardValidator boardValidator;
    private final JdbcTemplate jdbcTemplate;

    public BoardDbData getBoard(long id) {
        try {
            String sql = "SELECT * FROM BOARD_DATA WHERE ID = ?";
            BoardDbData boardDbData = jdbcTemplate.queryForObject(sql, this::mapper, id);

            return boardDbData;
        } catch (Exception e) {
            return null;
        }
    }

    public BoardDbData mapper(ResultSet rs, int i) throws SQLException {
        Timestamp modDt = rs.getTimestamp("MODDT");
        return BoardDbData.builder()
                .id(rs.getLong("ID"))
                .poster(rs.getString("POSTER"))
                .subject(rs.getString("SUBJECT"))
                .content(rs.getString("CONTENT"))
                .regiDate(rs.getTimestamp("REGDT").toLocalDateTime())
                .modyData(modDt == null ? null : modDt.toLocalDateTime())
                .build();
    }
}
