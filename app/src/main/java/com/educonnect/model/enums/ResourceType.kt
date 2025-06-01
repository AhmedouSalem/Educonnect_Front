package com.educonnect.model.enums

enum class ResourceType(val mimeType: String) {
    PDF("application/pdf"),
    VIDEO("video/mp4"),
    PRESENTATION("application/vnd.ms-powerpoint"),
    DOCUMENT("application/msword"),
    EXERCISE("application/zip"),
    CORRECTION("application/pdf"),
    OTHER("application/octet-stream");

    companion object {
        fun fromMimeType(mimeType: String): ResourceType {
            return values().firstOrNull { it.mimeType.equals(mimeType, ignoreCase = true) } ?: OTHER
        }
    }
}
