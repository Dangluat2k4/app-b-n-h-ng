var db = require('./db');

var userSchema = new db.mongoose.Schema(
    {
        Email: { type: String, required: true },
        Password: { type: String, required: true },
        FullName: { type: String, required: false },
        NumberPhone:{type:String,required:true},
        IDCategory:{type:String,required:false},
        IDBill:{type:String,required:false},
        Status:{type:Number,required:true},
        Credit:{type:Number,required:true,default:0},
        Level:{type:Number,required:true,default:0},
    }, { collection: 'Account' }
);

let Account1 = db.mongoose.model('Account1', userSchema);
module.exports = { Account1 };