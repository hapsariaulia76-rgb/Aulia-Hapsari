package com.example.data.rules

import com.example.data.model.RuleCategory
import com.example.data.model.RuleItem

object RulesData {
    val homeRules = listOf(
        RuleItem(
            id = "home_1",
            category = RuleCategory.HOME,
            title = "Kedisiplinan Ibadah & Doa Harian",
            subtitle = "Membangun pondasi spiritual sejak dini",
            description = "Anak membiasakan diri melaksanakan ibadah tepat waktu, berdoa sebelum dan sesudah beraktivitas (makan, belajar, tidur), serta mendengarkan nasihat kebaikan.",
            characterValue = "Religius & Ketakwaan",
            pointsGuide = "+10 Poin mingguan jika konsisten",
            actionItems = listOf(
                "Sholat/Ibadah 5 waktu tepat waktu bersama keluarga",
                "Membaca doa harian sebelum makan dan sebelum tidur",
                "Membaca kitab suci / mengaji minimal 15 menit setiap hari"
            ),
            tipsForParentsOrTeachers = "Orang tua memberikan keteladanan dengan mengajak ibadah bersama tanpa membentak, melainkan dengan ajakan yang hangat."
        ),
        RuleItem(
            id = "home_2",
            category = RuleCategory.HOME,
            title = "Waktu Belajar Mandiri & Pekerjaan Rumah (PR)",
            subtitle = "Konsistensi mengulang pelajaran di rumah",
            description = "Menyediakan waktu khusus 60-90 menit setiap malam (pukul 19.00 - 20.30) untuk mengulang materi sekolah, mengerjakan PR tuntas, dan menyiapkan buku pelajaran esok hari.",
            characterValue = "Tanggung Jawab & Mandiri",
            pointsGuide = "+10 Poin mingguan untuk ketuntasan PR",
            actionItems = listOf(
                "Mengerjakan PR tanpa menunggu disuruh berulang kali",
                "Memeriksa jadwal pelajaran dan menyiapkan buku ke dalam tas sendiri",
                "Merapikan meja belajar setelah selesai digunakan"
            ),
            tipsForParentsOrTeachers = "Bantu anak hanya saat mereka mengalami kesulitan konsep, jangan kerjakan PR-nya. Biarkan anak mandiri bertanggung jawab."
        ),
        RuleItem(
            id = "home_3",
            category = RuleCategory.HOME,
            title = "Batasan Screen Time & Penggunaan Gawai (Gadget)",
            subtitle = "Keseimbangan digital dan interaksi nyata",
            description = "Penggunaan smartphone/tablet/game dibatasi maksimal 1 jam per hari setelah tugas sekolah dan ibadah selesai. Bebas gawai di meja makan dan 1 jam sebelum tidur.",
            characterValue = "Disiplin Diri & Pengendalian Diri",
            pointsGuide = "+10 Poin kepatuhan batas layar",
            actionItems = listOf(
                "Tidak memegang HP saat waktu makan bersama keluarga",
                "Menghentikan penggunaan gawai tepat waktu saat timer berbunyi",
                "Menghindari konten yang tidak sesuai usia anak"
            ),
            tipsForParentsOrTeachers = "Sepakati sanksi edukatif bersama anak jika waktu screen time dilanggar, misalnya membantu menyiram tanaman esok pagi."
        ),
        RuleItem(
            id = "home_4",
            category = RuleCategory.HOME,
            title = "Kemandirian & Membantu Pekerjaan Rumah",
            subtitle = "Melatih kepedulian terhadap lingkungan keluarga",
            description = "Anak dilatih bertanggung jawab atas kebutuhan pribadinya serta berkontribusi meringankan tugas rumah tangga harian.",
            characterValue = "Gotong Royong & Kerja Keras",
            pointsGuide = "+5 Poin per inisiatif mandiri",
            actionItems = listOf(
                "Merapikan tempat tidur sendiri setiap bangun pagi",
                "Mencuci piring dan gelas sendiri setelah makan",
                "Menaruh pakaian kotor pada keranjang cucian",
                "Membantu menyapu teras atau merapikan mainan"
            ),
            tipsForParentsOrTeachers = "Apresiasi usaha anak meskipun hasilnya belum sempurna. Pujian tulus meningkatkan rasa percaya diri anak."
        ),
        RuleItem(
            id = "home_5",
            category = RuleCategory.HOME,
            title = "Adab Bicara & Sopan Santun kepada Orang Tua",
            subtitle = "Tata krama dan kelembutan tutur kata",
            description = "Membiasakan 3 kata ajaib (Tolong, Maaf, Terima Kasih), mencium tangan orang tua saat pergi dan pulang, serta tidak memotong pembicaraan orang dewasa.",
            characterValue = "Santun, Hormat & Berakhlak Mulia",
            pointsGuide = "+10 Poin adab terpuji",
            actionItems = listOf(
                "Mencium tangan orang tua saat pamit dan tiba di rumah",
                "Berbicara dengan nada suara lembut, tidak berteriak",
                "Mengucapkan terima kasih setiap menerima bantuan atau makanan"
            ),
            tipsForParentsOrTeachers = "Dengarkan cerita anak dengan kontak mata saat mereka bercerita tentang harinya di sekolah."
        )
    )

    val schoolRules = listOf(
        RuleItem(
            id = "school_1",
            category = RuleCategory.SCHOOL,
            title = "Ketepatan Waktu & Kehadiran Tertib",
            subtitle = "Disiplin gerbang sekolah pukul 06.45 WIB",
            description = "Hadir di sekolah minimal 15 menit sebelum bel masuk berbunyi (pukul 07.00 WIB). Mengikuti apel pagi, berdoa bersama, dan tidak meninggalkan sekolah tanpa izin resmi.",
            characterValue = "Disiplin Waktu & Integritas",
            pointsGuide = "+10 Poin kehadiran tepat waktu sepekan",
            actionItems = listOf(
                "Tiba di sekolah sebelum pukul 06.50 WIB",
                "Langsung masuk ke area kelas dan menyapa bapak/ibu guru piket",
                "Membawa surat izin resmi dari orang tua jika berhalangan hadir"
            ),
            tipsForParentsOrTeachers = "Guru piket menyambut siswa dengan senyum 5S (Senyum, Salam, Sapa, Sopan, Santun) di gerbang masuk."
        ),
        RuleItem(
            id = "school_2",
            category = RuleCategory.SCHOOL,
            title = "Kerapihan Seragam & Atribut Lengkap",
            subtitle = "Identitas dan kesetaraan peserta didik",
            description = "Mengenakan seragam sesuai jadwal harian (Senin-Selasa: Putih Merah, Rabu: Batik, Kamis: Pramuka, Jumat: Olahraga/Muslim), bersepatu hitam, kaus kaki putih, dan rambut rapi.",
            characterValue = "Kerapihan & Kepatuhan Norma",
            pointsGuide = "+5 Poin seragam lengkap dan rapi",
            actionItems = listOf(
                "Mengenakan ikat pinggang hitam dan dasi/topi saat upacara",
                "Baju dimasukkan rapi dan kancing terpasang rapi",
                "Kuku dipotong pendek dan bersih setiap hari Senin"
            ),
            tipsForParentsOrTeachers = "Orang tua mendampingi anak memeriksa kelengkapan seragam sejak malam hari."
        ),
        RuleItem(
            id = "school_3",
            category = RuleCategory.SCHOOL,
            title = "Adab Belajar di Kelas & Partisipasi Aktif",
            subtitle = "Menghargai proses transfer ilmu pengetahuan",
            description = "Fokus memperhatikan guru yang sedang menjelaskan, mengangkat tangan sebelum bertanya atau berpendapat, tidak mengganggu teman sebangku, dan menjaga ketenangan kelas.",
            characterValue = "Rasa Ingin Tahu & Rasa Hormat",
            pointsGuide = "+10 Poin keaktifan dan ketertiban kelas",
            actionItems = listOf(
                "Mendengarkan penjelasan guru tanpa mengobrol sendiri",
                "Mengangkat tangan kanan saat ingin berbicara atau bertanya",
                "Menyelesaikan lembar kerja siswa tepat waktu"
            ),
            tipsForParentsOrTeachers = "Guru memberikan apresiasi bagi siswa yang aktif dan memberi motivasi bagi siswa yang pemalu."
        ),
        RuleItem(
            id = "school_4",
            category = RuleCategory.SCHOOL,
            title = "Kebersihan Lingkungan & Tanggung Jawab Piket",
            subtitle = "Sekolah sehat dan nyaman belajar",
            description = "Melaksanakan piket kelas sesuai jadwal, membuang sampah pada tempat sampah pilah (organik/anorganik), dan merawat fasilitas sekolah seperti buku perpustakaan.",
            characterValue = "Peduli Lingkungan & Tanggung Jawab",
            pointsGuide = "+5 Poin ketuntasan tugas piket",
            actionItems = listOf(
                "Menyapu lantai kelas dan menghapus papan tulis saat jadwal piket",
                "Membawa tumbler minum dan kotak bekal ramah lingkungan",
                "Menjaga dinding, meja, dan fasilitas dari coretan"
            ),
            tipsForParentsOrTeachers = "Piket bersama menumbuhkan rasa kepemilikan dan kerjasama antar teman sekelas."
        ),
        RuleItem(
            id = "school_5",
            category = RuleCategory.SCHOOL,
            title = "Pertemanan Sehat, Toleransi & Anti-Bullying",
            subtitle = "Menciptakan ruang belajar aman dan penuh kasih",
            description = "Menyayangi seluruh teman tanpa membeda-bedakan latar belakang, dilarang keras melakukan kekerasan fisik maupun verbal (mengejek, memanggil nama orang tua), serta tolong-menolong.",
            characterValue = "Cinta Kasih, Empati & Gotong Royong",
            pointsGuide = "+15 Poin sikap teladan & tolong-menolong",
            actionItems = listOf(
                "Tidak memanggil teman dengan julukan yang mengejek atau merendahkan",
                "Membantu teman yang terjatuh atau kesulitan membawa barang",
                "Melaporkan ke guru jika melihat tindakan perundungan (bullying)"
            ),
            tipsForParentsOrTeachers = "Budayakan saling memaafkan dan menyelesaikan perselisihan dengan musyawarah damai."
        )
    )

    fun getAllRules(): List<RuleItem> = homeRules + schoolRules
}
