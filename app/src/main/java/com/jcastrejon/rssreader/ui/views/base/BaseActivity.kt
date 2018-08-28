package com.jcastrejon.rssreader.ui.views.base

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.jcastrejon.rssreader.R
import com.jcastrejon.rssreader.ui.contracts.base.BaseContract

/**
 * Base activity
 */
abstract class BaseActivity : AppCompatActivity(), BaseContract.View {

    abstract val layoutId: Int
    abstract val toolbarView: Toolbar
    private val progress: AlertDialog by lazy { createProgressDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        setSupportActionBar(toolbarView)
        initialize(savedInstanceState)
    }

    override fun showProgress() {
        progress.show()
    }

    override fun hideProgress() {
        progress.dismiss()
    }

    /**
     * Perform the necessary operations
     */
    abstract fun initialize(state: Bundle?)

    /**
     * Creates a dialog with a progressbar
     */
    private fun createProgressDialog() =
            AlertDialog.Builder(this)
                    .setView(layoutInflater.inflate(R.layout.progress_layout, null))
                    .setCancelable(false)
                    .create()
                    .apply { window!!.decorView.setBackgroundResource(android.R.color.transparent) }
}