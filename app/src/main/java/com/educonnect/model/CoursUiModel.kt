package com.educonnect.model

data class CoursUiModel(
    val heureDebut: String,
    val heureFin: String,
    val titre: String,
    val salle: String,
    val professeur: String,
    val groupes: List<String>,
    val description: String,
    val isHighlighted: Boolean = false
)


val sampleCours = listOf(
    CoursUiModel(
        "11:35", "13:05", "HAIXXX", "A36.03", "SERIA ABDELHAK",
        listOf("M1 INFORMATIQUE - GL", "M1 INFORMATIQUE - IASD", "M1 INFORMATIQUE - IMAGINE", "M1 MEEF Sciences Nums - SNT NSI"),
        "Développement et programmation pour supports mobiles", isHighlighted = true
    ),
    CoursUiModel(
        "13:15", "14:45", "HAIXXX", "A36.03", "SERIA ABDELHAK",
        listOf("M1 INFORMATIQUE - GL", "M1 INFORMATIQUE - IASD", "M1 INFORMATIQUE - IMAGINE", "M1 MEEF Sciences Nums - SNT NSI"),
        "Développement et programmation pour supports mobiles"
    ),
    CoursUiModel(
        "15:10", "16:40", "HAIXXX", "A36.03", "SERIA ABDELHAK",
        listOf("M1 INFORMATIQUE - GL", "M1 INFORMATIQUE - IASD", "M1 INFORMATIQUE - IMAGINE", "M1 MEEF Sciences Nums - SNT NSI"),
        "Développement et programmation pour supports mobiles"
    )
)

