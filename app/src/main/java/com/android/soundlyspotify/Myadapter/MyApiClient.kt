import com.android.soundlyspotify.models.BestSeller2
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("bestsellers")
    suspend fun getBestSellers(@Query("query") query: String): Response<List<BestSeller2>>
}

object MyApiClient {
    private const val BASE_URL = "https://test-mkcw.onrender.com/api/" // Replace with your API base URL

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
