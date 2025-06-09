// routes/donationRoutes.js
const express = require("express");
const router = express.Router();
const donationController = require("../controllers/donationController");

router.post("/donations", donationController.addDonation);
router.get("/donations/:userId", donationController.getDonations);

module.exports = router;
