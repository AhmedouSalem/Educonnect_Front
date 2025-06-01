package com.educonnect.domain.teacher

import android.os.Build
import androidx.annotation.RequiresApi
import com.educonnect.model.CoursUiModel
import com.educonnect.model.TeacherDto
import com.educonnect.model.planning.PlanningDto
import com.educonnect.repository.UserRepository
import java.time.LocalDate

class GetTeacherUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: Long): TeacherDto? {
        return try {
            userRepository.getTeacherById(userId)
        } catch (e: Exception) {
            null
        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
fun PlanningDto.toCoursUiModel(): CoursUiModel {
    val heureDebutStr = "%02d:%02d".format(heureDebut[0], heureDebut[1])
    val heureFinStr = "%02d:%02d".format(heureFin[0], heureFin[1])
    val salleStr = "${salle.nums} (${salle.batiment.campus.nomC} / ${salle.batiment.codeB} / ${salle.etage})"
    val profStr = "${course.enseignant.nom} ${course.enseignant.prenom}"
    val groupesStr = course.listparcours.map { "${it.code} - ${it.name}" }
    val localDate = LocalDate.of(date[0], date[1], date[2])

    return CoursUiModel(
        heureDebut = heureDebutStr,
        heureFin = heureFinStr,
        titre = titre,
        salle = salleStr,
        professeur = profStr,
        groupes = groupesStr,
        description = course.description,
        isHighlighted = false,
        dayOfMonth = date[2],
        date = localDate
    )
}
