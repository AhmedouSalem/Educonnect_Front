package com.educonnect.repository

import com.educonnect.model.ResourceDto

class ResourceRepository(private val resourceService: ResourceService) {

    suspend fun getResourcesByCourseId(courseId: Long): List<ResourceDto> {
        return resourceService.getResourcesByCourse(courseId)
    }

    suspend fun createResource(resource: ResourceDto): ResourceDto {
        return resourceService.createResource(resource)
    }

    suspend fun updateResource(id: Long, resource: ResourceDto): ResourceDto {
        return resourceService.updateResource(id, resource)
    }

    suspend fun deleteResource(id: Long?) {
        resourceService.deleteResource(id)
    }
}
