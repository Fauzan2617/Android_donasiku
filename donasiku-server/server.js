// server.js
const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");

// Import routes
const userRoutes = require("./routes/userRoutes");
const donationRoutes = require("./routes/donationRoutes");
const institutionRoutes = require("./routes/institutionRoutes");  // Tambahan

const app = express();
const port = process.env.PORT || 3000;

app.use(bodyParser.json());
app.use(cors());

// Daftarkan semua route dengan prefix /api
app.use("/api", userRoutes);
app.use("/api", donationRoutes);
app.use("/api", institutionRoutes);  // Daftarkan route Institutions

// Jalankan server
app.listen(port, () => {
  console.log(`Server berjalan di http://localhost:${port}`);
});
