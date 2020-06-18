package com.ssh.androidarchitectures

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ssh.androidarchitectures.mvc.MVCActivity
import com.ssh.androidarchitectures.mvp.MVPActivity
import com.ssh.androidarchitectures.mvvm.MVVMActivity

class ArchitecturesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_architectures)
    }

    fun onMVC(view: View) {
        startActivity(MVCActivity.getIntent(this@ArchitecturesActivity))
    }

    fun onMVP(view: View) {
        startActivity(MVPActivity.getIntent(this@ArchitecturesActivity))
    }

    fun onMVVM(view: View) {
        startActivity(MVVMActivity.getIntent(this@ArchitecturesActivity))
    }
}
