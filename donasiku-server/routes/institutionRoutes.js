const express = require("express");
const router = express.Router();
const institutionController = require("../controllers/institutionController");

router.post("/institutions", institutionController.addInstitution);
router.get("/institutions", institutionController.getInstitutions); // sudah ada

module.exports = router;
