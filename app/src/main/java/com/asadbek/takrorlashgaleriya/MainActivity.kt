package com.asadbek.takrorlashgaleriya

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.asadbek.takrorlashgaleriya.databinding.ActivityMainBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var absolutPath:String = ""
    var readBytes:ByteArray? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRasmOlish.setOnClickListener {
            // bu button bosilganida galeriya rasm tanlash uchun ochiladi
            rasmniOlish()
        }

    }
    // rasmni olish uchun yozilayotgan kod
    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()){
            it ?: return@registerForActivityResult // it -> tanlangan rasmning joylashgan o`rni
            binding.myImageView.setImageURI(it) // rasmni uri manzili bo`yicha imageviewga berib yuborish

            // databse ga saqlash uchun yoziladigan buyuruqlar
            // .jpg .png .bmp -> Integer
            val inputStream = contentResolver?.openInputStream(it) // uri orqali kirish oqimi
            val file = File(filesDir,"image.jpg") // kelayotgan rasmning saqlanish xolati
            val fileOutputStream = FileOutputStream(file) // rasmni saqlash uchun oqim
            inputStream?.copyTo(fileOutputStream) // uri orqali rasmning nusxasini ko`chirish
            inputStream?.close() // xolatni saqlash

            absolutPath = file.absolutePath // rasmning joylashgan manzili
            binding.textImagePath.text = absolutPath
            val fileInputStream = FileInputStream(file) // faylni kiritish uchun oqim yaratish
            readBytes = fileInputStream.readBytes() // rasmni bytearray tipiga o`tkazib olib shu o`zgaruvchiga saqlaydi
            val imageUser = ImageUser(absolutPath,readBytes) // rasm manzili va rasmning raqamli tipini objectga tenglash

            // mybase.addImage(imageUser)

            // mybase.getAllImage()
        }

    private fun rasmniOlish(){
        getImageContent.launch("image/*") // galerya ochilgandagi xar qanday rasmlarni chiqarib berishi uchun buyuruq
    }
}