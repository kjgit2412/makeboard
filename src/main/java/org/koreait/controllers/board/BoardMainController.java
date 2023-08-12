package org.koreait.controllers.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardMainController {

    @GetMapping("/addBoard")
    public String addBoard() {

        return "board/addBoard";
    }
}
