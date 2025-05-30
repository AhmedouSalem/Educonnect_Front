package com.educonnect.di

import android.content.Context
import com.educonnect.domain.admin.AddBuildingUseCase
import com.educonnect.domain.admin.AddCampusUseCase
import com.educonnect.domain.admin.AddPlanningUseCase
import com.educonnect.domain.admin.AddSalleUseCase
import com.educonnect.domain.admin.GetAdminUseCase
import com.educonnect.domain.auth.LoginUseCase
import com.educonnect.repository.AdminRepository
import com.educonnect.repository.AuthRepository
import com.educonnect.repository.BuildingRepository
import com.educonnect.repository.CampusRepository
import com.educonnect.repository.CourseRepository
import com.educonnect.repository.MentionRepository
import com.educonnect.repository.ParcoursRepository
import com.educonnect.repository.PlanningRepository
import com.educonnect.repository.SalleRepository
import com.educonnect.repository.UserRepository
import com.educonnect.ui.auth.AuthViewModel
import com.educonnect.ui.building.BuildingViewModel
import com.educonnect.ui.campus.CampusViewModel
import com.educonnect.ui.home.HomeViewModel
import com.educonnect.ui.planning.PlanningViewModel
import com.educonnect.ui.salle.SalleViewModel
import com.educonnect.utils.SessionManager

object Injection {
    val sessionManager = AppSession.sessionManager
    /** Authentification **/
    fun provideAuthRepository(context: Context): AuthRepository {
        return AuthRepository(sessionManager)
    }

    fun provideLoginUseCase(context: Context): LoginUseCase {
        return LoginUseCase(provideAuthRepository(context))
    }

    fun provideAuthViewModel(context: Context): AuthViewModel {
        return AuthViewModel(provideLoginUseCase(context))
    }

    /** AdminHomePage For Load NOM-PRENOM **/
    fun provideAdminRepository(context: Context): AdminRepository {
        return AdminRepository(sessionManager)
    }

    fun provideGetAdminUseCase(context: Context): GetAdminUseCase {
        return GetAdminUseCase(provideAdminRepository(context))
    }

    fun provideHomeViewModel(context: Context): HomeViewModel {
        return HomeViewModel(provideGetAdminUseCase(context), sessionManager)
    }

    /** AddCampus **/
    fun provideCampusRepository(context: Context): CampusRepository {
        return CampusRepository()
    }

    fun provideAddCampusUseCase(context: Context): AddCampusUseCase {
        return AddCampusUseCase(provideCampusRepository(context))
    }

    fun provideCampusViewModel(context: Context): CampusViewModel {
        return CampusViewModel(provideAddCampusUseCase(context))
    }

    /** AddBuilding **/
    fun provideBuildingRepository(): BuildingRepository {
        return BuildingRepository()
    }

    fun provideAddBuildingUseCase(): AddBuildingUseCase {
        return AddBuildingUseCase(provideBuildingRepository())
    }

    fun provideBuildingViewModel(context: Context): BuildingViewModel {
        return BuildingViewModel(
            provideAddBuildingUseCase(),
            provideCampusRepository(context)
        )
    }

    /** AddSalle **/
    fun provideSalleRepository(): SalleRepository {
        return SalleRepository()
    }

    fun provideAddSalleUseCase(): AddSalleUseCase {
        return AddSalleUseCase(provideSalleRepository())
    }

    fun provideSalleViewModel(context: Context): SalleViewModel {
        return SalleViewModel(
            provideAddSalleUseCase(),
            provideBuildingRepository(),
            provideCampusRepository(context),
        )
    }

//    /** Add User: Etudiant professeur*/
//    fun provideUserService(): UserRepository {
//        return UserRepository(NetworkModule.userApi)
//    }

    /** Mention **/
    fun provideMentionRepository(): MentionRepository {
        return MentionRepository()
    }

    /** Parcours **/
    fun provideParcoursRepository(): ParcoursRepository {
        return ParcoursRepository()
    }

    /** Cours **/
    fun provideCourseRepository(): CourseRepository {
        return CourseRepository()
    }

    /** Planning **/
    fun providePlanningRepository(): PlanningRepository {
        return PlanningRepository()
    }

    fun provideAddPlanningUseCase(): AddPlanningUseCase {
        return AddPlanningUseCase(providePlanningRepository())
    }

    /** Planning ViewModel **/
    fun providePlanningViewModel(context: Context): PlanningViewModel {
        return PlanningViewModel(
            mentionRepository = provideMentionRepository(),
            parcoursRepository = provideParcoursRepository(),
            courseRepository = provideCourseRepository(),
            campusRepository = provideCampusRepository(context),
            batimentRepository = provideBuildingRepository(),
            salleRepository = provideSalleRepository(),
            addPlanningUseCase = provideAddPlanningUseCase()
        )
    }


    /** Add User: Etudiant professeur*/
    fun provideUserService(): UserRepository {
        return UserRepository(NetworkModule.userApi)
    }


}
