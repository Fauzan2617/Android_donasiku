// controllers/userController.js
const pool = require("../db");

// Fungsi registrasi user
exports.register = async (req, res) => {
  const { name, email, password, alamat, noHp } = req.body;

  // Validasi input dasar: pastikan field utama terisi
  if (!name || !email || !password) {
    return res.status(400).json({ message: "Name, email, dan password harus diisi." });
  }

  try {
    // Periksa apakah email sudah terdaftar
    const [existing] = await pool.execute(
      "SELECT * FROM users WHERE email = ?",
      [email]
    );
    if (existing.length > 0) {
      return res.status(400).json({ message: "Email sudah terdaftar." });
    }
    
    // Insert user baru ke tabel users
    const [result] = await pool.execute(
      "INSERT INTO users (name, email, password, alamat, noHp) VALUES (?, ?, ?, ?, ?)",
      [name, email, password, alamat, noHp]
    );
    res.json({ message: "Registrasi berhasil", userId: result.insertId });
  } catch (error) {
    console.error("Error saat registrasi:", error);
    res.status(500).json({ message: "Server error" });
  }
};

// Fungsi login user
exports.login = async (req, res) => {
  const { email, password } = req.body;

  // Validasi input dasar: pastikan email dan password tidak kosong
  if (!email || !password) {
    return res.status(400).json({ message: "Email dan password harus diisi." });
  }

  try {
    // Ambil data user berdasarkan email dan password
    const [rows] = await pool.execute(
      "SELECT * FROM users WHERE email = ? AND password = ?",
      [email, password]
    );
    if (rows.length > 0) {
      const user = rows[0];
      // Buat token dummy (untuk produksi sebaiknya gunakan JWT atau metode autentikasi yang lebih aman)
      const token = "dummy-token-for-" + user.userId;
      res.json({ token, userId: user.userId, name: user.name, email: user.email });
    } else {
      res.status(401).json({ message: "Kredensial tidak valid" });
    }
  } catch (error) {
    console.error("Error saat login:", error);
    res.status(500).json({ message: "Server error" });
  }
};
