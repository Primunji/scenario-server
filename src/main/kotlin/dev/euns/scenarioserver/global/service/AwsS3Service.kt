package dev.euns.scenarioserver.global.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import dev.euns.scenarioserver.global.exception.CustomException
import dev.euns.scenarioserver.global.exception.S3ErrorCode
import dev.euns.scenarioserver.global.utils.RandomStringUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.InputStream

@Service
class AwsS3Service(
    private val amazonS3Client: AmazonS3Client,
    private val randomStringUtils: RandomStringUtil
)
{

    @Value("\${cloud.aws.s3.bucket}")
    private lateinit var bucket:String


    fun uploadFiles(file: MultipartFile) : String {

        val originalFileName: String? = "${randomStringUtils.getRandomString(24)}/${file.originalFilename}"

        val validExtensions = listOf(".png", ".jpeg", ".jpg")
        if (!validExtensions.any { file.originalFilename!!.toLowerCase().endsWith(it) }) {
            throw CustomException(S3ErrorCode.FileUploadNotImage)
        }

        val objectMetadata = ObjectMetadata()
        objectMetadata.setContentLength(file.getSize())
        objectMetadata.setContentType(file.getContentType())

        try {
            val inputStream: InputStream = file.inputStream
            amazonS3Client.putObject(bucket,originalFileName, inputStream,objectMetadata);
            val uploadFileUrl = amazonS3Client.getUrl(bucket, originalFileName).toString();
            return uploadFileUrl
        }catch (e: IOException) {
            e.printStackTrace();
            throw CustomException(S3ErrorCode.UnknownError)
        }

    }
}