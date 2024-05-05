package software.galaniberico.restorer

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import software.galaniberico.restorer.facade.Restorer
import software.galaniberico.restorer.tags.OnRestore
import software.galaniberico.restorer.tags.OnSave
import software.galaniberico.restorer.tags.Restore

class MainActivity : AppCompatActivity() {
    @Restore
    var string = "default"

    @Restore
    var int = 0

    var notRestored = "default"

    var execution = "default"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            notRestored = "fail"
            findViewById<TextView>(R.id.textView).text = findViewById<EditText>(R.id.hello).text
        }
    }

    @OnSave
    fun onSave() {
       Restorer.with("a", findViewById<TextView>(R.id.textView).text.toString())
    }

    @OnRestore
    fun onRestore() {
        findViewById<TextView>(R.id.textView).text = Restorer.get("a", "default")
    }



}