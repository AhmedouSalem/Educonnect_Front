package com.educonnect.di

import com.educonnect.repository.AdminService
import com.educonnect.repository.AuthService
import com.educonnect.repository.BuildingService
import com.educonnect.repository.CampusService
import com.educonnect.repository.CourseService
import com.educonnect.repository.MentionService
import com.educonnect.repository.ParcoursService
import com.educonnect.repository.PlanningService
import com.educonnect.repository.SalleService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    private const val BASE_URL = "http://10.0.2.2:8089/"

    /**
     * Crée un client OkHttp avec un Interceptor pour le logging.
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * Crée une instance de Retrofit.
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * Instance de AuthService.
     */
    val authService: AuthService = retrofit.create(AuthService::class.java)
    /**
     * Instance de AdminService.
     */
    val adminService: AdminService = retrofit.create(AdminService::class.java)
    /**
     * Instancce de CampusService
     */
    val campusService: CampusService = retrofit.create(CampusService::class.java)
    /**
     * Instance de BatimentService
     */
    val buildingService: BuildingService = retrofit.create(BuildingService::class.java)
    /**
     * Instance de SalleService
     */
    val salleService: SalleService = retrofit.create(SalleService::class.java)

    /**
     * Mention Service
     */
    val mentionService: MentionService = retrofit.create(MentionService::class.java)

    /**
     * Parcours Service
     */
    val parcoursService: ParcoursService = retrofit.create(ParcoursService::class.java)
    /**
     * Cours Service
     */
    val courseService: CourseService = retrofit.create(CourseService::class.java)
    /**
     * Planning Service
     */
    val planningService: PlanningService = retrofit.create(PlanningService::class.java)

}
