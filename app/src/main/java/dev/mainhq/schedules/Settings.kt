package dev.mainhq.schedules

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import dev.mainhq.schedules.R

//TODO
//NEED TO CHANGE APPBAR
//NEED TO IMPROVE MENU
//NEED TO MAKE THEME CLICKABLE
class Settings : AppCompatActivity(), MenuProvider {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        showLanguageMenu()
    }

    private fun showLanguageMenu() {
        val langView = findViewById<View>(R.id.language_view)
        langView.setOnClickListener { innerView: View? ->
            val langMenu = PopupMenu(applicationContext, innerView)
            langMenu.menuInflater.inflate(R.menu.language_menu, langMenu.menu)
            langMenu.show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.back_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        menuItem.setChecked(true)
        return true
    }
}