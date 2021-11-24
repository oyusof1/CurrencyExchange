package com.example.myfirstdatabase

import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var mDbDBAdapter: MyDBAdapter? = null
    private val mAllFaculties = arrayOf("Engineering", "Business", "Arts")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        initializeDatabase()
        loadList()
    }

    override fun onBackPressed() {
        val drawer_layout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val drawer_layout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun initializeViews() {
        val toolbar : androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        val drawer_layout: DrawerLayout = findViewById(R.id.drawer_layout)
        setSupportActionBar(toolbar)
        var toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        val nav_view :NavigationView = findViewById(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener(this)
        val faculties_spinner : Spinner = findViewById(R.id.faculties_spinner)
        faculties_spinner.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, mAllFaculties)
        val add_student : Button = findViewById(R.id.add_student)
        val student_name : EditText = findViewById(R.id.student_name)
        add_student.setOnClickListener{
            mDbDBAdapter?.insertStudent(student_name.text.toString(), faculties_spinner.selectedItemPosition + 1)
            loadList()
        }

        val delete_engineers : Button = findViewById(R.id.delete_engineers)
        delete_engineers.setOnClickListener {
            mDbDBAdapter?.deleteAllEngineers()
            loadList()
        }
    }
    private fun initializeDatabase() {
        mDbDBAdapter = MyDBAdapter(this@MainActivity)
        mDbDBAdapter?.open()
    }

    private fun loadList() {
        val allStudents: List<String>? = mDbDBAdapter?.selectAllStudents()
        val adapter: ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_list_item_1,allStudents!!)
        val student_list : ListView = findViewById(R.id.student_list)
        student_list.adapter = adapter
    }
}