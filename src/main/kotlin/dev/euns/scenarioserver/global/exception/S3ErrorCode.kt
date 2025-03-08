package dev.euns.scenarioserver.global.exception

import org.springframework.http.HttpStatus

enum class S3ErrorCode (
    override val status: HttpStatus,
    override val state: String,
    override val message: String,
): CustomErrorCode {

    FileUploadNotImage(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "사진 파일만 업로드할 수 있습니다."),
    UnknownError(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "파일을 업로드 하는중에 서버에서 오류가 발생 하였습니다."),
}