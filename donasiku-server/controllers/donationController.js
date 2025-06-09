// controllers/donationController.js
const pool = require("../db");

exports.addDonation = async (req, res) => {
  const { userId, institutionId, namaBarang, deskripsi, tanggalDonasi } = req.body;

  // Validasi input: pastikan semua field wajib diisi (sesuai kebutuhan)
  if (!userId || !namaBarang || !deskripsi || !tanggalDonasi) {
    return res.status(400).json({ message: "Semua field wajib diisi." });
  }

  try {
    // Periksa apakah user ada
    const [users] = await pool.execute("SELECT * FROM users WHERE userId = ?", [userId]);
    if (users.length === 0) {
      return res.status(400).json({ message: "User tidak ditemukan." });
    }

    // Jika institutionId diberikan, periksa juga keberadaan lembaga
    if (institutionId) {
      const [institutions] = await pool.execute("SELECT * FROM institutions WHERE institutionId = ?", [institutionId]);
      if (institutions.length === 0) {
        return res.status(400).json({ message: "Lembaga (institution) tidak ditemukan." });
      }
    }

    // Masukkan donasi, dengan atau tanpa institutionId (opsional)
    const [result] = await pool.execute(
      "INSERT INTO donations (userId, institutionId, namaBarang, deskripsi, tanggalDonasi) VALUES (?, ?, ?, ?, ?)",
      [userId, institutionId || null, namaBarang, deskripsi, tanggalDonasi]
    );

    res.json({ message: "Donasi berhasil ditambahkan", donationId: result.insertId });
  } catch (error) {
    console.error("Error in addDonation:", error);
    res.status(500).json({ message: "Server error" });
  }
};

// Fungsi getDonations tetap sama, kecuali jika nantinya ingin menampilkan detail lembaga bersama data donasi.
exports.getDonations = async (req, res) => {
  const userId = parseInt(req.params.userId, 10);
  if (isNaN(userId)) {
    return res.status(400).json({ message: "UserId tidak valid." });
  }
  try {
    const [rows] = await pool.execute("SELECT * FROM donations WHERE userId = ?", [userId]);
    res.json(rows);
  } catch (error) {
    console.error("Error in getDonations:", error);
    res.status(500).json({ message: "Server error" });
  }
};
