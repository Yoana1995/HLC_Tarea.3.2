package com.example.ficheros

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ficheros.databinding.ActivityMainBinding
import java.io.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if ((ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED
                    ) || (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED
                    )) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                123
            )
        }


            binding.guardar.setOnClickListener {
                Guardar("Nombre: "+binding.nombre.text.toString()
                        +" Email: "+binding.email.text.toString()
                        +" Teléfono: "+binding.tlf.text.toString())
            }

            binding.ver.setOnClickListener {
                binding.contenido.text = Cargar()
            }

            binding.borrar.setOnClickListener {
                Borrar()
            }
    }

        fun Guardar(texto: String) {
            try {
                val rutaSD = baseContext.getExternalFilesDir(null)?.absolutePath
                val miCarpeta = File(rutaSD, "datos")
                if (!miCarpeta.exists()) {
                    miCarpeta.mkdir()
                }
                val ficheroFisico = File(miCarpeta, "datos.txt")
                ficheroFisico.appendText("$texto\n")
            }
            catch (e: Exception) {
                Toast.makeText(this, "No se ha podido guardar", Toast.LENGTH_LONG).show()
            }
        }

        fun Cargar() : String {
            var texto = ""
            try {
                val rutaSD = baseContext.getExternalFilesDir(null)?.absolutePath
                val miCarpeta = File(rutaSD, "datos")
                val ficheroFisico = File(miCarpeta, "datos.txt")
                val fichero = BufferedReader(
                    InputStreamReader(FileInputStream(ficheroFisico))
                )
                texto = fichero.use(BufferedReader::readText)
            }
            catch (e : Exception) {
                Toast.makeText(this, "Error al cargar", Toast.LENGTH_LONG).show()
            }
            return texto
        }

    fun Borrar() {
        val rutaSD = baseContext.getExternalFilesDir(null)?.absolutePath
        val miCarpeta = File(rutaSD, "datos")
        val ficheroFisico = File(miCarpeta, "datos.txt")
        ficheroFisico.delete()
        binding.contenido.text = ""
    }
}