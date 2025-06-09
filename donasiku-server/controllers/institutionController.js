// controllers/institutionController.js
const pool = require("../db");

// Endpoint untuk mengambil semua data lembaga
exports.getInstitutions = async (req, res) => {
  try {
    const [rows] = await pool.execute("SELECT * FROM institutions");
    res.json(rows);
  } catch (error) {
    console.error("Error fetching institutions:", error);
    res.status(500).json({ message: "Server error" });
  }
};

// Endpoint untuk menambahkan lembaga baru
exports.addDonation = async (req, res) => {
    const { userId, institutionId, namaBarang, deskripsi, tanggalDonasi } = req.body;
    // Pastikan validasi awal: semua field wajib terisi (sesuai logika bisnis)
    if (!userId || !namaBarang || !deskripsi || !tanggalDonasi) {
        return res.status(400).json({ message: "Semua field wajib diisi." });
    }
    try {
        const [result] = await pool.execute(
            "INSERT INTO donations (userId, institutionId, namaBarang, deskripsi, tanggalDonasi) VALUES (?, ?, ?, ?, ?)",
            [userId, institutionId || null, namaBarang, deskripsi, tanggalDonasi]
        );
        res.json({ message: "Donasi berhasil ditambahkan", donationId: result.insertId });
    } catch (error) {
        console.error("Error in addDonation:", error);
        res.status(500).json({ message: "Internal Server Error" });
    }
};

