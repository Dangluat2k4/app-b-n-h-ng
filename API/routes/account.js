var express = require('express');
var router = express.Router();

var ACCtrl = require('../controller/account.crl');
// khu vực viết code định nghia các link ở đây


// http://localhost:3000/product
router.get('/account',ACCtrl.ListAccout );

router.get('/accountNV',ACCtrl.ListAccoutNV );

router.get('/accountKH',ACCtrl.ListAccoutKH );

router.get("/account/add", ACCtrl.addAccount);
router.post("/account/add", ACCtrl.addAccount);

router.get("/account/edit/:id",ACCtrl.updateAC)
router.post("/account/edit/:id",ACCtrl.updateAC)


router.get('/account/delete/:id', ACCtrl.deleteAccount);
router.post('/account/delete/:id', ACCtrl.deleteAccount);


module.exports = router;
