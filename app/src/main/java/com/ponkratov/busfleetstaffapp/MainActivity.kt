package com.ponkratov.busfleetstaffapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ponkratov.busfleetstaffapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nestedController =
            (supportFragmentManager.findFragmentById(R.id.page_container) as NavHostFragment)
                .navController
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setupWithNavController(nestedController)
    }
}

/*
Приложение с 2 экранами через BottomNavigationView или ViewPager.
1 экран - Будет отображаться список записей из базы данных Room (RecyclerView + ListAdapter)
2 экран - Добавление данных в базу данных (форма с полями ввода данных и кнопкой добавить + валидация)

Опционально:
- добавление удаления из бд (диалог с подтверждением удаления при клике, либо свайпы, либо лонг клик)
- добавление редактирования (кастомный диалог полями для редактирования)
- поиск (добавить в тулбар меню для поиска и при вводе строки перезагружать список)
- MVVM + LiveData*/
