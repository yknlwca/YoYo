package com.yoyo.banking.domain.account.controller;

import com.yoyo.banking.domain.account.dto.account.AccountAuthDTO;
import com.yoyo.banking.domain.account.dto.account.AccountCreateDTO;
import com.yoyo.banking.domain.account.dto.account.AccountPinDTO;
import com.yoyo.banking.domain.account.dto.pay.PayTransactionDTO;
import com.yoyo.banking.domain.account.service.AccountService;
import com.yoyo.banking.domain.account.service.SsafyBankService;
import com.yoyo.common.dto.response.BodyValidationExceptionResopnse;
import com.yoyo.common.dto.response.CommonResponse;
import com.yoyo.common.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/yoyo/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final SsafyBankService ssafyBankService;

    /**
     * PIN 번호 수정
     * */
    @PatchMapping("/pin/update")
    @Operation(summary = "계좌 PIN 번호 수정", description = "계좌 PIN 번호를 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PIN 수정 성공",
            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
    @ApiResponse(responseCode = "400", description = "요청 dto 필드값 오류",
                         content = @Content(schema = @Schema(implementation = BodyValidationExceptionResopnse.class))),
    })
    ResponseEntity<?> updateAccountPin(@RequestHeader("memberId") Long memberId,
                                      @RequestBody @Valid AccountPinDTO.Request request) {
        return accountService.updateAccountPin(request, memberId);
    }
    /**
     * PIN 번호 확인
     * */
    @PostMapping("/pin/check")
    @Operation(summary = "계좌 PIN 번호 확인", description = "계좌 PIN 번호를 확인한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PIN 번호 일치",
                         content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "401", description = "PIN 번호 일치하지 않음",
                         content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "요청 dto 필드값 오류",
                         content = @Content(schema = @Schema(implementation = BodyValidationExceptionResopnse.class))),
    })
    ResponseEntity<?> checkAccountPin(@RequestHeader("memberId") Long memberId,
                                      @RequestBody @Valid AccountPinDTO.Request request) {
        return accountService.checkAccountPin(request, memberId);
    }

    /**
     *  [ssafy 금융 API] 1원 송금
     * */
    @PostMapping("/open")
    @Operation(summary = "계좌 1원 송금", description = "입력 계좌에 1원 송금한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "1원 송금 성공",
                         content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "1원 송금 실패",
                         content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    ResponseEntity<?> openAccountAuth(@RequestHeader("memberId") Long memberId,
                                      @RequestBody @Valid AccountAuthDTO.Request request) {
        return ssafyBankService.openAccountAuth(request, memberId);
    }

    /**
     *  [ssafy 금융 API] 1원 송금 확인
     * */
    @PostMapping("/check")
    @Operation(summary = "계좌 1원 송금 확인", description = "1원 송금 코드를 확인한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "1원 송금 코드를 확인 성공",
                         content = @Content(schema = @Schema(implementation = PayTransactionDTO.Response.class))),
            @ApiResponse(responseCode = "400", description = "요청 dto 필드값 오류",
                         content = @Content(schema = @Schema(implementation = BodyValidationExceptionResopnse.class))),
    })
    ResponseEntity<?> checkAccountAuth(@RequestHeader("memberId") Long memberId,
                                       @RequestBody @Valid AccountAuthDTO.Request request) {
        return ssafyBankService.checkAccountAuth(request, memberId);
    }

    /**
    * * 계좌 등록(수정) + 페이 생성
    * <p>
     * - 계좌 있을 시, 계좌 수정
     * - 계좌 없을 시, 계좌 등록
     * @param request 계좌생성요청 DTO
    * */
    @PostMapping
    @Operation(summary = "계좌 등록, 수정", description = "계좌를 등록(수정)한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "계좌 등록(수정) 성공",
                content = @Content(schema = @Schema(implementation = PayTransactionDTO.Response.class))),
            @ApiResponse(responseCode = "400", description = "요청 dto 필드값 오류",
                         content = @Content(schema = @Schema(implementation = BodyValidationExceptionResopnse.class))),
    })
    ResponseEntity<CommonResponse> createAccount(@RequestHeader("memberId") Long memberId,
                                                 @RequestBody @Valid AccountCreateDTO.Request request) {
//        log.info("----------------계좌 생성------------------");
        CommonResponse response = accountService.createAccount(request, memberId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 계좌 조회
     * */
    @GetMapping
    @Operation(summary = "등록된 계좌 조회", description = "계좌를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계좌 조회 성공",
                         content = @Content(schema = @Schema(implementation = AccountCreateDTO.Response.class))),
    })
    ResponseEntity<AccountCreateDTO.Response> getAccount(@RequestHeader("memberId") Long memberId){
        return accountService.getAccount(memberId);
    }
}

