package chummong.io.catimagelist.common

import java.util.concurrent.TimeUnit

/**

 * 어플리케이션 상수 값

 * Created by LeeJongHun on 2015-12-18.
 */
object Const {

    val API_KEY = "pnCXuNcDAoOdOVHVHfaCRQbRap9mCo3aXS3ccM7b"
    /**
     * Test End Point
     */
    val END_PONIT = "https://api.500px.com/v1/"

    /**
     * Retry Delay Time(Second)
     */
    val NETWORK_RETRY_DELAY = 5

    /**
     * Retry Delay Count
     */
    val NETWORT_RETRY_COUNT = 6

    /**
     * Time Unit 설정
     */
    val TIME_UNIT = TimeUnit.SECONDS

    /**
     * Read, Write, Connect Time Out 설정 시간
     */
    val TIME_OUT_SECOND: Long = 10

    /**
     * 토큰 Request 횟수
     */
    val AUTHORIZED_COUNT = 3
}
