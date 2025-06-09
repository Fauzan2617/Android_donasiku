// db.js
const mysql = require('mysql2/promise');

const pool = mysql.createPool({
    host: process.env.DB_HOST || 'localhost',           // Gunakan 'localhost' atau host MySQL kamu
    user: process.env.DB_USER || 'root',                  // Gunakan username MySQL kamu
    password: process.env.DB_PASSWORD || '12345678910',   // Ganti dengan password MySQL kamu
    database: process.env.DB_NAME || 'donasiku_db',       // Nama database yang sudah kamu buat
    waitForConnections: true,
    connectionLimit: 10,
    queueLimit: 0
});

module.exports = pool;
