package com.project.salon.main.api.controller.manage;

import com.project.salon.main.api.dto.common.ResponseMsg;
import com.project.salon.main.api.dto.manage.company.CompanyActive;
import com.project.salon.main.api.dto.manage.company.CompanyRegist;
import com.project.salon.main.api.dto.manage.company.CompanyUpdate;
import com.project.salon.main.api.service.manage.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main/company")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("/regist")
    public ResponseEntity<ResponseMsg> companyRegist(@RequestBody CompanyRegist companyRegist) {
        companyService.companyRegist(companyRegist);
        return ResponseMsg.successResponse("success");
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMsg> companyUpdate(@RequestBody CompanyUpdate companyUpdate) {
        companyService.companyUpdate(companyUpdate);
        return ResponseMsg.successResponse("success");
    }

    @PutMapping("/active")
    public ResponseEntity<ResponseMsg> companyActive(@RequestBody CompanyActive companyActive) {
        companyService.companyActive(companyActive);
        return ResponseMsg.successResponse("success");
    }

    @DeleteMapping("/delete/{companyGuid}")
    public ResponseEntity<ResponseMsg> companyDelete(@PathVariable String companyGuid) {
        companyService.companyDelete(companyGuid);
        return ResponseMsg.successResponse("success");
    }

    @GetMapping("/list/page")
    public ResponseEntity<ResponseMsg> companyListPage(
            @RequestParam String searchType,
            @RequestParam String searchValue,
            @RequestParam Long offset,
            @RequestParam int limit
    ) {
        return ResponseMsg.successResponse(companyService.companyListPage(searchType, searchValue, offset, limit));
    }

    @GetMapping("/info/{companyGuid}")
    public ResponseEntity<ResponseMsg> companyInfo(@PathVariable String companyGuid) {
        return ResponseMsg.successResponse(companyService.getCompanyInfo(companyGuid));
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseMsg> companyList() {
        return ResponseMsg.successResponse(companyService.getCompanyListAll());
    }
}
