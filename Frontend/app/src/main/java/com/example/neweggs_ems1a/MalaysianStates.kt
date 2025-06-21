package com.example.neweggs_ems1a

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MalaysianStates : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_states)

        val backbtn = findViewById<Button>(R.id.backBtn)
        backbtn.setOnClickListener { finish() }

        val states = listOf(
            // Peninsular Malaysia
            State(
                "Johor",
                "Johor Bahru",
                "Southernmost state, bordering Singapore",
                "Zapin Johor dance (Arab-Malay fusion), Kuda Kepang (spiritual dance), Royal court traditions",
                "Johor Laksa, Mee Rebus, Otak-Otak, Kacang Pool",
                "Strong royal influence (Johor Sultanate). Blend of Malay, Chinese (Teochew/Hokkien), and Indian (Tamil) cultures. Famous for traditional kite-making (Wau).",
                R.drawable.johor
            ),
            State(
                "Kedah",
                "Alor Setar",
                "Northwest, bordering Thailand",
                "Paddy harvest festivals, Lagu Ulik Mayang (traditional songs), traditional silver crafts",
                "Nasi Ulam, Gulai Nangka, Laksa Kedah, Pulut Mangga",
                "Known as Malaysia's 'Rice Bowl'. Strong Thai cultural influences. Birthplace of Malaysian traditional theatre (Mek Mulung).",
                R.drawable.kedah
            ),
            State(
                "Kelantan",
                "Kota Bharu",
                "Northeast, near Thai border",
                "Wayang Kulit (shadow puppetry), Mak Yong (UNESCO dance drama), Dikir Barat, Batik/Songket weaving",
                "Nasi Kerabu, Nasi Dagang, Budu, Ayam Percik, Akok",
                "Heartland of traditional Malay culture. Strong Islamic influences. Known for silverware and traditional kite-making.",
                R.drawable.kelantan
            ),
            State(
                "Malacca",
                "Malacca City",
                "Southwest coast",
                "Baba-Nyonya customs, Portuguese folk dances, Chingay parade, Kristang festivals",
                "Chicken Rice Balls, Nyonya Laksa, Cendol, Satay Celup",
                "UNESCO World Heritage Site. Blend of Portuguese, Dutch, British, Chinese (Peranakan) and Malay cultures. Birthplace of Malay sultanate.",
                R.drawable.malacca
            ),
            State(
                "Negeri Sembilan",
                "Seremban",
                "South of Selangor",
                "Adat Perpatih (matrilineal system), Minangkabau dance forms, buffalo-horn roof architecture",
                "Masak Lemak Cili Api, Rendang Minang, Lemang, Dodol",
                "Strong Minangkabau (Sumatran) influences. Unique social structure where property passes through female line.",
                R.drawable.negeri_sembilan
            ),
            State(
                "Pahang",
                "Kuantan",
                "East coast, largest peninsula state",
                "Orang Asli (indigenous) rituals, traditional medicine, royal ceremonies",
                "Gulai Tempoyak Ikan Patin, Lemang, Sambal Hitam, Tenggol fish dishes",
                "Malaysia's ecotourism hub. Home to Taman Negara (world's oldest rainforest). Strong royal traditions.",
                R.drawable.pahang
            ),
            State(
                "Penang",
                "George Town",
                "Northwest coast",
                "Chinese opera, Thaipusam festival, Peranakan customs, lantern festivals",
                "Char Kway Teow, Penang Laksa, Rojak, Nasi Kandar, Tau Sar Pneah",
                "UNESCO World Heritage Site. Famous 'food paradise' with Chinese (Hokkien), Indian, Malay and British influences.",
                R.drawable.penang
            ),
            State(
                "Perak",
                "Ipoh",
                "West coast",
                "Malay palace traditions, Chinese clan customs, Hindu temple festivals",
                "Ipoh White Coffee, Salted Chicken, Dim Sum, Pomelo",
                "Former tin mining powerhouse. Blend of Malay royalty, Chinese tin miners and Indian laborers' cultures.",
                R.drawable.perak
            ),
            State(
                "Perlis",
                "Kangar",
                "Northernmost state",
                "Tarian Canggung, Thai-influenced traditions, rural folk arts",
                "Laksa Perlis, Pulut Mempelam, Ikan Bakar, Durian",
                "Malaysia's smallest state. Strong Thai cultural influences. Only state ruled by a Raja (not Sultan).",
                R.drawable.perlis
            ),
            State(
                "Selangor",
                "Shah Alam",
                "West coast, surrounds KL",
                "Chinese lion dances, Thaipusam, Malay martial arts (Silat), modern arts scene",
                "Satay Kajang, Bak Kut Teh, Nasi Lemak, Cendol",
                "Most developed state. Cultural melting pot with urban Malay, Chinese and Indian communities.",
                R.drawable.selangor
            ),
            State(
                "Terengganu",
                "Kuala Terengganu",
                "East coast",
                "Songket weaving, traditional boat-making, Gamelan music, Islamic arts",
                "Nasi Dagang, Keropok Lekor, Laksam, Bekang",
                "Center of Malay traditional crafts. Strong Islamic identity with beautiful mosques and madrasahs.",
                R.drawable.terengganu
            ),

            // East Malaysia
            State(
                "Sabah",
                "Kota Kinabalu",
                "Northern Borneo",
                "Kaamatan harvest festival, Magunatip bamboo dance, Monsopiad cultural village",
                "Hinava, Bambangan, Ambuyat, Linopot",
                "Home to 32 ethnic groups including Kadazan-Dusun, Bajau and Murut. Famous for Mount Kinabalu.",
                R.drawable.sabah
            ),
            State(
                "Sarawak",
                "Kuching",
                "Northwest Borneo",
                "Gawai Dayak festival, Ngajat dance, Sape music, longhouse traditions",
                "Sarawak Laksa, Manok Pansoh, Umai, Midin fern",
                "Land of Hornbills. Rich Iban, Bidayuh and Orang Ulu indigenous cultures. Famous for rainforests and longhouses.",
                R.drawable.sarawak
            ),

            // Federal Territories
            State(
                "Kuala Lumpur",
                "Kuala Lumpur",
                "West Malaysia",
                "All major Malaysian festivals, modern arts scene, multicultural events",
                "Nasi Lemak, Banana Leaf Rice, Hokkien Mee, Durian",
                "Cosmopolitan capital. Blend of modern skyscrapers and traditional kampung culture.",
                R.drawable.wpkl
            ),
            State(
                "Putrajaya",
                "Putrajaya",
                "South of KL",
                "National day parades, Islamic arts festivals, government ceremonies",
                "Modern Malaysian cuisine, international dining",
                "Planned administrative capital showcasing Islamic-Malay architecture and smart city concepts.",
                R.drawable.putrajaya
            ),
            State(
                "Labuan",
                "Victoria",
                "Off Sabah's coast",
                "Sea festivals, fishing traditions, international business events",
                "Seafood, Kelupis, Wajid, Amplang",
                "Duty-free island with mix of Malay, Bruneian and Chinese cultures. Important oil and gas hub.",
                R.drawable.labuan
            )
        )

        setupRecyclerView(states)
    }

    private fun setupRecyclerView(states: List<State>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = StateAdapter(states) { state ->
            val intent = Intent(this, StateDetailsActivity::class.java).apply {
                putExtra("state", state)
            }
            startActivity(intent)
        }
    }
}