package com.example.mobile_studentmanagement

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private val students = mutableListOf(
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


    private lateinit var studentAdapter: ArrayAdapter<String>
    private lateinit var addStudentLauncher: ActivityResultLauncher<Intent>
    private lateinit var editStudentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val listView = findViewById<ListView>(R.id.list_view_students)
        studentAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students.map { "${it.studentName} - ${it.studentId}" })
        listView.adapter = studentAdapter

        // Đăng ký context menu cho ListView
        registerForContextMenu(listView)

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedStudent = students[position]
            Toast.makeText(this, "Clicked on ${selectedStudent.studentName}", Toast.LENGTH_SHORT).show()
        }

        // Register the activity result launchers
        addStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val studentName = result.data?.getStringExtra("STUDENT_NAME") ?: ""
                val studentId = result.data?.getStringExtra("STUDENT_ID") ?: ""
                students.add(StudentModel(studentName, studentId))
                updateStudentAdapter()
            }
        }

        editStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val updatedStudentId = result.data?.getStringExtra("STUDENT_ID")
                val updatedStudentName = result.data?.getStringExtra("STUDENT_NAME")

                val student = students.find { it.studentId == updatedStudentId }
                student?.studentName = (updatedStudentName ?: student?.studentName).toString()
                updateStudentAdapter()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)  // Inflate menu layout
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_new -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                addStudentLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val selectedPosition = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        val selectedStudent = students[selectedPosition]

        return when (item.itemId) {
            R.id.context_edit -> {
                val intent = Intent(this, EditStudentActivity::class.java)
                intent.putExtra("STUDENT_ID", selectedStudent.studentId)
                intent.putParcelableArrayListExtra("STUDENT_LIST", ArrayList(students))
                editStudentLauncher.launch(intent)
                true
            }
            R.id.context_remove -> {
                confirmDeleteStudent(selectedStudent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun confirmDeleteStudent(student: StudentModel) {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to delete this student?")
            .setPositiveButton("Yes") { _, _ -> deleteStudent(student) }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteStudent(student: StudentModel) {
        students.remove(student)
        updateStudentAdapter()
        Toast.makeText(this, "Student deleted", Toast.LENGTH_SHORT).show()
    }

    private fun updateStudentAdapter() {
        studentAdapter.clear()
        studentAdapter.addAll(students.map { "${it.studentName} - ${it.studentId}" })
        studentAdapter.notifyDataSetChanged()
    }
}