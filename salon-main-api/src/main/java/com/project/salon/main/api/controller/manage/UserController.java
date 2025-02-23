package com.project.salon.main.api.controller.manage;

import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.dto.manage.user.UserActive;
import com.project.salon.main.api.dto.manage.user.UserRegist;
import com.project.salon.main.api.dto.manage.user.UserUpdate;
import com.project.salon.main.api.service.manage.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> userRegist(@RequestBody UserRegist userRegist) {
        userService.userRegist(userRegist);
        return ResponseMsg.successResponse("success");
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMsg> userUpdate(@RequestBody UserUpdate userUpdate) {
        userService.userUpdate(userUpdate);
        return ResponseMsg.successResponse("success");
    }

    @PutMapping("/active")
    public ResponseEntity<ResponseMsg> userActive(@RequestBody UserActive userActive) {
        userService.userActive(userActive);
        return ResponseMsg.successResponse("success");
    }

    @DeleteMapping("/delete/{userGuid}")
    public ResponseEntity<ResponseMsg> userDelete(@PathVariable String userGuid) {
        userService.userDelete(userGuid);
        return ResponseMsg.successResponse("success");
    }

    @GetMapping("/list/page")
    public ResponseEntity<ResponseMsg> userList(
            @RequestParam String searchType,
            @RequestParam String searchValue,
            @RequestParam Long offset,
            @RequestParam int limit
    ) {
        return ResponseMsg.successResponse(userService.userListPage(searchType, searchValue, offset, limit));
    }
}
