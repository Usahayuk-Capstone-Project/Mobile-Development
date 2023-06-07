package com.example.usahayuk.ui.home

import android.net.http.SslCertificate.saveState
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.bumptech.glide.util.Util
import com.example.usahayuk.R
import com.example.usahayuk.Utils
import com.example.usahayuk.data.model.PostDataRequest
import com.example.usahayuk.data.remote.ApiConfig
import com.example.usahayuk.databinding.ActivityFormBinding

class FormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.cariRekomendasi.setOnClickListener {
            val skalaUsaha = skalaUsaha()
            val modalUsaha = modalUsaha()
            val bidangUsaha = bidangUsaha()
            val omsetUsaha = omsetUsaha()
            val usiaTarget = usiaTarget()
            val listUsiaTarget: List<String> = usiaTarget.split(",").toList()
            val targetPekerjaan = targetPekerjaan()
            val listTargetPekerjaan: List<String> = targetPekerjaan.split(",").toList()
            val pilihKelasSosial = pilihKelasSosial()
            val listKelasSosial: List<String> = pilihKelasSosial.split(",").toList()
            val lokasi = pilihLokasi()
            val listLokasi: List<String> = lokasi.split(",").toList()

            when {
                skalaUsaha!!.isEmpty() -> {
                    Toast.makeText(
                        this@FormActivity,
                        "Pilih Skala usaha",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                modalUsaha.isEmpty() -> {
                    Toast.makeText(
                        this@FormActivity,
                        "Pilih Modal usaha",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                bidangUsaha.isEmpty() -> {
                    Toast.makeText(
                        this@FormActivity,
                        "Pilih Bidang usaha",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                omsetUsaha.isEmpty() -> {
                    Toast.makeText(
                        this@FormActivity,
                        "Pilih Omset usaha",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                usiaTarget.isEmpty() -> {
                    Toast.makeText(
                        this@FormActivity,
                        "Pilih Usia target",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                targetPekerjaan.isEmpty() -> {
                    Toast.makeText(
                        this@FormActivity,
                        "Pilih Target pekerjaan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                pilihKelasSosial.isEmpty() -> {
                    Toast.makeText(
                        this@FormActivity,
                        "Pilih Kelas sosial",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                lokasi.isEmpty() -> {
                    Toast.makeText(
                        this@FormActivity,
                        "Pilih Lokasi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    postData(
                        Utils.token,
                        Utils.EXTRA_UID,
                        skalaUsaha,
                        modalUsaha,
                        bidangUsaha,
                        omsetUsaha,
                        listUsiaTarget,
                        listTargetPekerjaan,
                        listKelasSosial,
                        listLokasi
                    )
                    Toast.makeText(this, Utils.EXTRA_UID, Toast.LENGTH_SHORT).show()
                    saveState()
                }
            }
        }

    }

    private fun postData(
        token: String,
        extraUid: String,
        skalaUsaha: String,
        modalUsaha: String,
        bidangUsaha: String,
        omsetUsaha: String,
        listUsiaTarget: List<String>,
        listTargetPekerjaan: List<String>,
        listKelasSosial: List<String>,
        listLokasi: List<String>
    ) {
        isLoading(true)
        val dataRequest = PostDataRequest(token, skalaUsaha, modalUsaha, bidangUsaha, omsetUsaha, listTargetPekerjaan,listKelasSosial,listLokasi )
        val client = ApiConfig.getApiService().postData()
    }

    private fun skalaUsaha(): String? {
        val radioGroup: RadioGroup = binding.rgSkalausaha
        var skalaUsaha: String? = null

        // Set a listener for radio group
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_mikro -> skalaUsaha = "Mikro"
                R.id.rb_kecil -> skalaUsaha = "Kecil"
                R.id.rb_menengah -> skalaUsaha = "Menengah"
            }
        }
        return skalaUsaha
    }

    private fun modalUsaha(): String? {
        val radioGroup: RadioGroup = binding.rgModalusaha
        var modalUsaha: String? = null

        // Set a listener for radio group
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_3_juta -> modalUsaha = "<= 3 JT"
                R.id.rb_3_5_juta -> modalUsaha = "3 - 5 JT"
                R.id.rb_5_10_juta -> modalUsaha = "5 - 10 JT"
                R.id.rb_10_30_juta -> modalUsaha = "10 - 30 JT"
                R.id.rb_30_50_juta -> modalUsaha = "30 - 50 JT"
                R.id.rb_50_500_juta -> modalUsaha = "50 - 500 JT"
                R.id.rb_1_miliar -> modalUsaha = "<= 1 M"

            }
        }
        return modalUsaha
    }

    private fun bidangUsaha(): String? {
        val radioGroup: RadioGroup = binding.rgBidangusaha
        var bidangUsaha: String? = null

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_pertanian_perkebunan -> getString(R.string.pertanian_dan_perkebunan)
                R.id.rb_perikanan_kelautan -> getString(R.string.perikanan_dan_kelautan)
                R.id.rb_industri_makanan_minuman -> getString(R.string.industri_makanan_dan_minuman)
                R.id.rb_industri_fashion_tekstil -> getString(R.string.industri_fashion_dan_tekstil)
                R.id.rb_industri_kerajinan_souvenir -> getString(R.string.industri_kerajinan_tangan_dan_souvenir)
                R.id.rb_pendidikan_pelatihan -> getString(R.string.pendidikan_dan_pelatihan)
                R.id.rb_jasa_kesehatan_kecantikan -> getString(R.string.jasa_kesehatan_dan_kecantikan)
                R.id.rb_jasa_keuangan_perbankan -> getString(R.string.jasa_keuangan_dan_perbankan)
                R.id.rb_jasa_konsultasi_manajemen -> getString(R.string.jasa_konsultasi_dan_manajemen)
                R.id.rb_jasa_teknologi_informasi_komunikasi -> getString(R.string.jasa_teknologi_informasi_dan_komunikasi)
                R.id.rb_jasa_kebersihan -> getString(R.string.jasa_kebersihan)
                R.id.rb_jasa_keamanan -> getString(R.string.jasa_keamanan)
                R.id.rb_otomotif -> getString(R.string.otomotif)
            }
        }
        return bidangUsaha
    }

    private fun omsetUsaha(): String? {
        val radioGroup: RadioGroup = binding.rgOmsetusaha
        var omsetUsaha: String? = null

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_0_10_juta -> getString(R.string._0_10_juta)
                R.id.rb_11_20_juta -> getString(R.string._11_20_juta)
                R.id.rb_21_30_juta -> getString(R.string._21_30_juta)
                R.id.rb_31_40_juta -> getString(R.string._31_40_juta)
                R.id.rb_41_50_juta -> getString(R.string._41_50_juta)
                R.id.rb_50_juta -> getString(R.string._50_juta)
            }
        }
        return omsetUsaha
    }

    private fun usiaTarget(): StringBuilder {
        val usiaTarget = StringBuilder()

        val checkBox: CheckBox = binding.checkBox
        val checkBox2: CheckBox = binding.checkBox2
        val checkBox3: CheckBox = binding.checkBox3
        val checkBox4: CheckBox = binding.checkBox4
        val checkBox5: CheckBox = binding.checkBox5
        val checkBox6: CheckBox = binding.checkBox6

        if (checkBox.isChecked) {
            usiaTarget.append(checkBox.text.toString())
        }

        if (checkBox2.isChecked) {
            if (usiaTarget.isNotEmpty()) {
                usiaTarget.append(", ")
            }
            usiaTarget.append(checkBox2.text.toString())
        }

        if (checkBox3.isChecked) {
            if (usiaTarget.isNotEmpty()) {
                usiaTarget.append(", ")
            }
            usiaTarget.append(checkBox3.text.toString())
        }

        if (checkBox4.isChecked) {
            if (usiaTarget.isNotEmpty()) {
                usiaTarget.append(", ")
            }
            usiaTarget.append(checkBox4.text.toString())
        }

        if (checkBox5.isChecked) {
            if (usiaTarget.isNotEmpty()) {
                usiaTarget.append(", ")
            }
            usiaTarget.append(checkBox5.text.toString())
        }

        if (checkBox6.isChecked) {
            if (usiaTarget.isNotEmpty()) {
                usiaTarget.append(", ")
            }
            usiaTarget.append(checkBox6.text.toString())
        }

        return usiaTarget
    }

    private fun targetPekerjaan(): StringBuilder {
        val pekerjaanTarget = StringBuilder()

        val cbPengangguran: CheckBox = binding.cbPengangguran
        val cbPelajar: CheckBox = binding.cbPelajar
        val cbPegawai: CheckBox = binding.cbPegawai
        val cbWiraswasta: CheckBox = binding.cbWiraswasta
        val cbPetani: CheckBox = binding.cbPetani
        val cbNelayan: CheckBox = binding.cbNelayan
        val cbPensiunan: CheckBox = binding.cbPensiunan
        val cbTidakBekerja: CheckBox = binding.cbTdkBekerja
        val cbPekerjaInformal: CheckBox = binding.cbPekerjaInformal
        val cbIrt: CheckBox = binding.cbIrt

        if (cbPengangguran.isChecked) {
            pekerjaanTarget.append(cbPengangguran.text.toString())
        }

        if (cbPelajar.isChecked) {
            if (pekerjaanTarget.isNotEmpty()) {
                pekerjaanTarget.append(", ")
            }
            pekerjaanTarget.append(cbPelajar.text.toString())
        }

        if (cbPegawai.isChecked) {
            if (pekerjaanTarget.isNotEmpty()) {
                pekerjaanTarget.append(", ")
            }
            pekerjaanTarget.append(cbPegawai.text.toString())
        }

        if (cbWiraswasta.isChecked) {
            if (pekerjaanTarget.isNotEmpty()) {
                pekerjaanTarget.append(", ")
            }
            pekerjaanTarget.append(cbWiraswasta.text.toString())
        }

        if (cbPetani.isChecked) {
            if (pekerjaanTarget.isNotEmpty()) {
                pekerjaanTarget.append(", ")
            }
            pekerjaanTarget.append(cbPetani.text.toString())
        }

        if (cbNelayan.isChecked) {
            if (pekerjaanTarget.isNotEmpty()) {
                pekerjaanTarget.append(", ")
            }
            pekerjaanTarget.append(cbNelayan.text.toString())
        }

        if (cbPensiunan.isChecked) {
            if (pekerjaanTarget.isNotEmpty()) {
                pekerjaanTarget.append(", ")
            }
            pekerjaanTarget.append(cbPensiunan.text.toString())
        }

        if (cbTidakBekerja.isChecked) {
            if (pekerjaanTarget.isNotEmpty()) {
                pekerjaanTarget.append(", ")
            }
            pekerjaanTarget.append(cbTidakBekerja.text.toString())
        }

        if (cbPekerjaInformal.isChecked) {
            if (pekerjaanTarget.isNotEmpty()) {
                pekerjaanTarget.append(", ")
            }
            pekerjaanTarget.append(cbPekerjaInformal.text.toString())
        }

        if (cbIrt.isChecked) {
            if (pekerjaanTarget.isNotEmpty()) {
                pekerjaanTarget.append(", ")
            }
            pekerjaanTarget.append(cbIrt.text.toString())
        }

        return pekerjaanTarget
    }

    private fun pilihKelasSosial(): StringBuilder {
        val kelasSosial = StringBuilder()

        val cbKelasAtas: CheckBox = binding.cbKelasAtas
        val cbKelasMenengah: CheckBox = binding.cbKelasMenengah
        val cbKelasMenujuMenengah: CheckBox = binding.cbKelasMenujumenengah
        val cbKelasRentan: CheckBox = binding.cbKelasRentan
        val cbKelasMiskin: CheckBox = binding.cbKelasMiskin

        if (cbKelasAtas.isChecked) {
            kelasSosial.append(cbKelasAtas.text.toString())
        }

        if (cbKelasMenengah.isChecked) {
            if (kelasSosial.isNotEmpty()) {
                kelasSosial.append(", ")
            }
            kelasSosial.append(cbKelasMenengah.text.toString())
        }

        if (cbKelasMenujuMenengah.isChecked) {
            if (kelasSosial.isNotEmpty()) {
                kelasSosial.append(", ")
            }
            kelasSosial.append(cbKelasMenujuMenengah.text.toString())
        }

        if (cbKelasRentan.isChecked) {
            if (kelasSosial.isNotEmpty()) {
                kelasSosial.append(", ")
            }
            kelasSosial.append(cbKelasRentan.text.toString())
        }

        if (cbKelasMiskin.isChecked) {
            if (kelasSosial.isNotEmpty()) {
                kelasSosial.append(", ")
            }
            kelasSosial.append(cbKelasMiskin.text.toString())
        }

        return kelasSosial
    }

    private fun pilihLokasi(): StringBuilder {
        val lokasi = StringBuilder()

        val cbPegunungan: CheckBox = binding.cbPegunungan
        val cbPusatKota: CheckBox = binding.cbPusatkota
        val cbDesaAlam: CheckBox = binding.cbDesaalam
        val cbLingkunganPendidikan: CheckBox = binding.cbLingkunganpendidikan
        val cbPelabuhan: CheckBox = binding.cbPelabuhan
        val cbAreaWisata: CheckBox = binding.cbAreawisata
        val cbPemukiman: CheckBox = binding.cbPemukiman
        val cbPusatBelanja: CheckBox = binding.cbPusatbelanja
        val cbAreaIbadah: CheckBox = binding.cbAreaibadah
        val cbPerkantoran: CheckBox = binding.cbPerkantoran
        val cbAreaIndustri: CheckBox = binding.cbAreaindustri
        val cbTerminal: CheckBox = binding.cbTerminal

        if (cbPegunungan.isChecked) {
            lokasi.append(cbPegunungan.text.toString())
        }

        if (cbPusatKota.isChecked) {
            if (lokasi.isNotEmpty()) {
                lokasi.append(", ")
            }
            lokasi.append(cbPusatKota.text.toString())
        }

        if (cbDesaAlam.isChecked) {
            if (lokasi.isNotEmpty()) {
                lokasi.append(", ")
            }
            lokasi.append(cbDesaAlam.text.toString())
        }

        if (cbLingkunganPendidikan.isChecked) {
            if (lokasi.isNotEmpty()) {
                lokasi.append(", ")
            }
            lokasi.append(cbLingkunganPendidikan.text.toString())
        }

        if (cbPelabuhan.isChecked) {
            if (lokasi.isNotEmpty()) {
                lokasi.append(", ")
            }
            lokasi.append(cbPelabuhan.text.toString())
        }

        if (cbAreaWisata.isChecked) {
            if (lokasi.isNotEmpty()) {
                lokasi.append(", ")
            }
            lokasi.append(cbAreaWisata.text.toString())
        }

        if (cbPemukiman.isChecked) {
            if (lokasi.isNotEmpty()) {
                lokasi.append(", ")
            }
            lokasi.append(cbPemukiman.text.toString())
        }

        if (cbPusatBelanja.isChecked) {
            if (lokasi.isNotEmpty()) {
                lokasi.append(", ")
            }
            lokasi.append(cbPusatBelanja.text.toString())
        }

        if (cbAreaIbadah.isChecked) {
            if (lokasi.isNotEmpty()) {
                lokasi.append(", ")
            }
            lokasi.append(cbAreaIbadah.text.toString())
        }

        if (cbPerkantoran.isChecked) {
            if (lokasi.isNotEmpty()) {
                lokasi.append(", ")
            }
            lokasi.append(cbPerkantoran.text.toString())
        }

        if (cbAreaIndustri.isChecked) {
            if (lokasi.isNotEmpty()) {
                lokasi.append(", ")
            }
            lokasi.append(cbAreaIndustri.text.toString())
        }

        if (cbTerminal.isChecked) {
            if (lokasi.isNotEmpty()) {
                lokasi.append(", ")
            }
            lokasi.append(cbTerminal.text.toString())
        }

        return lokasi
    }

}