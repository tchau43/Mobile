package com.example.studentman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {

    // Tách việc khởi tạo danh sách sinh viên ra thành một phương thức riêng.
    private val _students = MutableLiveData<List<StudentModel>>().apply {
        value = initializeStudents()
    }
    val students: LiveData<List<StudentModel>> get() = _students

    // Phương thức khởi tạo danh sách sinh viên
    private fun initializeStudents(): List<StudentModel> {
        return listOf(
            StudentModel("Nguyễn Minh Tuấn", "20210615"),
            StudentModel("Trần Mai Linh", "20211402"),
            StudentModel("Lê Hoàng Anh", "20210923"),
            StudentModel("Phạm Minh Duy", "20211258"),
            StudentModel("Đỗ Thanh Sơn", "20211567"),
            StudentModel("Vũ Lan Anh", "20210187"),
            StudentModel("Hoàng Kim Tuyền", "20211945"),
            StudentModel("Bùi Thanh Hà", "20210876"),
            StudentModel("Đinh Minh Đức", "20211329"),
            StudentModel("Nguyễn Thu Trang", "20210734"),
            StudentModel("Phạm Hương Giang", "20211856"),
            StudentModel("Trần Thảo Nhi", "20210592"),
            StudentModel("Lê Tường Vi", "20211063"),
            StudentModel("Vũ Quang Duy", "20211680"),
            StudentModel("Hoàng Anh Thư", "20210421"),
            StudentModel("Đỗ Trường Sơn", "20211145"),
            StudentModel("Nguyễn Hoàng Hà", "20210312"),
            StudentModel("Trần Quốc Anh", "20211798"),
            StudentModel("Phạm Minh Tuyết", "20210239"),
            StudentModel("Lê Hoài Nam", "20212001")
        )
    }

    // Thêm sinh viên mới vào danh sách
    fun addStudent(student: StudentModel) {
        val updatedList = _students.value?.toMutableList() ?: mutableListOf()
        updatedList.add(student)
        _students.postValue(updatedList)
    }

    // Cập nhật thông tin sinh viên theo id
    fun updateStudent(oldId: String, newName: String, newId: String) {
        val updatedList = _students.value?.map {
            if (it.studentId == oldId) it.copy(studentName = newName, studentId = newId)
            else it
        } ?: return
        _students.postValue(updatedList)
    }

    // Xóa sinh viên khỏi danh sách
    fun deleteStudent(student: StudentModel) {
        val updatedList = _students.value?.toMutableList()?.apply {
            remove(student)
        } ?: return
        _students.postValue(updatedList)
    }

    // Lấy thông tin sinh viên theo id
    fun getStudentById(id: String?): StudentModel? {
        return _students.value?.find { it.studentId == id }
    }
}
