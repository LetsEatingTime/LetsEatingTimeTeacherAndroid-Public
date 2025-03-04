package school.alt.teacher.network.teacher

import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import school.alt.teacher.network.dto.EditUserRequest
import school.alt.teacher.network.dto.ResponseDto
import school.alt.teacher.network.teacher.TeacherApi
import school.alt.teacher.network.teacher.TeacherRepository
import javax.inject.Inject

class TeacherRepositoryImpl @Inject constructor(
    private val teacherApi: TeacherApi
) : TeacherRepository {
    override suspend fun uploadPhoto(image: MultipartBody.Part, table: RequestBody, id: RequestBody) {
        return teacherApi.uploadPhoto(
            image = image,
            table = table,
            id = id
        )
    }

    override suspend fun editStudent(body: EditUserRequest): ResponseDto<String> {
        return teacherApi.editStudent(body)
    }

    override suspend fun getUser(id: Int?) = flow {
        emit(teacherApi.getUser(id))
    }

    override suspend fun getUsers() = flow {
        emit(teacherApi.getUsers())
    }

    override suspend fun deleteUser(id: String): ResponseDto<String> {
        return teacherApi.deleteUser(id)
    }

    override suspend fun appreve(page: Int, size: Int) = flow {
        emit(teacherApi.appreve(page = page, size = size))
    }

    override suspend fun appreveUser(id: String): ResponseDto<String> {
        return teacherApi.appreveUser(id = id)
    }

}